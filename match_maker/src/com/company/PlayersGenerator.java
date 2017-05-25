package com.company;

import java.util.ArrayList;
import java.util.List;

import static com.company.Main.MIN_TIME_DIFF;

class PlayersGenerator {
    static final byte MIN_RANK = 1;
    static final byte MAX_RANK = 30;
    private static int uniquePlayerId = 0;

    List<Player> getRandomUsers(int count, int currentTime) {
        final List<Player> randomUsers = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            randomUsers.add(generate(currentTime));
        }
        return randomUsers;
    }

    private Player generate(int currentTime) {
        Player newUser = new Player();
        newUser.setId(++uniquePlayerId);
        newUser.setTime(currentTime + getRandomFromRange(1, MIN_TIME_DIFF));
        newUser.setRank(getRandomLevel());
        return newUser;
    }

    private static byte getRandomLevel() {
        return (byte) getRandomFromRange(MIN_RANK, MAX_RANK);
    }

    private static int getRandomFromRange(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }
}