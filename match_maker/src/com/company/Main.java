package com.company;

import java.util.List;
import java.util.Scanner;

public class Main {
    static final int MIN_TIME_DIFF = 5000;

    public static void main(String[] args) {
        int currentTime = 8000;

        while (true) {
            System.out.println("Введите количество зарегистрировавшихся игроков:");
            Scanner scanner = new Scanner(System.in);
            short playersCount = scanner.nextShort();
            if (playersCount <= 0) {
                continue;
            }

            List<Match> matches = MatchMaker.createTeams(currentTime, playersCount);
            System.out.println("Сформированные матчи:");
            matches.forEach(match -> System.out.println(match.toString()));
            System.out.println("Всего матчей:" + matches.size());

            currentTime += MIN_TIME_DIFF;
        }
    }
}
