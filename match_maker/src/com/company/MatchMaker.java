package com.company;


import java.util.*;
import java.util.stream.Collectors;

import static com.company.Main.MIN_TIME_DIFF;
import static com.company.Match.FULL_TEAM_SIZE;
import static com.company.PlayersGenerator.MAX_RANK;
import static com.company.PlayersGenerator.MIN_RANK;

class MatchMaker {
    private static Map<Integer, List<Player>> playersGroupedByRank;

    static List<Match> createTeams(int currentTime, int playersCount) {
        PlayersGenerator playersGenerator = new PlayersGenerator();
        //генерация списка случайных игроков с рангами от 1 до 30, зарегистрировавшихся в интервале MIN_TIME_DIFF
        List<Player> playersQueue = playersGenerator.getRandomUsers(playersCount, currentTime);

        //сортировка игроков по времени регистрации в очереди и группировка по рангам, т.к.
        //из игроков одного ранга сразу же можно формировать матчи
        Collections.sort(playersQueue, Comparator.comparingInt(Player::getTime));
        Map<Integer, List<Player>> newPlayersGroupedByRank = playersQueue
                .stream()
                .collect(Collectors.groupingBy(Player::getRank));

        //добавление новых игроков к тем, которые не были распределены по командам
        //во время предыдущего формирования матчей
        if (playersGroupedByRank == null) {
            playersGroupedByRank = newPlayersGroupedByRank;
        } else {
            for (int i = MIN_RANK; i <= MAX_RANK; i++) {
                List<Player> remainingPlayers = playersGroupedByRank.get(i);
                if (remainingPlayers != null && newPlayersGroupedByRank.get(i) != null) {
                    remainingPlayers.addAll(newPlayersGroupedByRank.get(i));
                }
            }
        }

        List<Match> matches = new ArrayList<>();

        for (int currentRank = MAX_RANK; currentRank >= MIN_RANK; currentRank--) {
            List<Player> players = playersGroupedByRank.get(currentRank);
            if (players == null) {
                continue;
            }

            //формируем матчи из игроков одного ранга и удаляем их из списков ожидающих
            int teamsCount = players.size() / FULL_TEAM_SIZE;
            for (int k = teamsCount; k > 0; k--) {
                Match match = new Match(currentTime);
                while (!match.isFull()) {
                    match.add(players.get(0));
                    players.remove(0);
                }
                matches.add(match);
            }

            //пытаемся создать матч из оставшихся игроков одного ранга и игроков ближайших рангов
            if (!players.isEmpty()) {
                Player leastWaitingPlayer = players.get(players.size() - 1);
                //получаем разницу рангов, игроков которых можно попытаться добавить в матч
                //если игроку, который дольше всех в очереди, никого нельзя поставить в матч,
                //то и всем остальным игрокам текущего ранга тоже
                int possibleRankDiff = (currentTime - leastWaitingPlayer.getTime()) / MIN_TIME_DIFF;

                if (possibleRankDiff > 0) {
                    Match match = new Match(currentTime);
                    match.addAll(players);

                    //получаем границы рангов, чьих игроков возможно добавить в матч
                    int minRankBound = Math.max(currentRank - possibleRankDiff, MIN_RANK);
                    int maxRankBound = Math.min(currentRank + possibleRankDiff, MAX_RANK);

                    for (int rank = minRankBound; rank <= maxRankBound && !match.isFull(); rank++) {
                        //игроки текущего ранга и так добавлены в матч
                        if (rank == currentRank) {
                            continue;
                        }

                        List<Player> nextRankPlayers = playersGroupedByRank.get(rank);

                        //пытаемся добавить в матч игроков другого ранга, начиная с того, кто дольше всех в очереди
                        int index = 0;
                        Player possibleTeammate = nextRankPlayers.get(index);
                        while ((currentTime - possibleTeammate.getTime()) / MIN_TIME_DIFF >= possibleRankDiff
                                && !match.isFull()) {
                            match.add(possibleTeammate);
                            possibleTeammate = nextRankPlayers.get(++index);
                        }
                    }

                    if (match.isFull()) {
                        matches.add(match);
                        //удаляем игроков матча из списков ожидания
                        match.getPlayers().forEach(
                                player -> playersGroupedByRank.get(player.getRank()).remove(player));
                    }
                }
            }
        }

        return matches;
    }
}