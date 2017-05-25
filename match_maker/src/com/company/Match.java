package com.company;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class Match {
    static final int FULL_TEAM_SIZE = 8;

    private Set<Player> players = new HashSet<>();
    private int creationTime;

    Match(int creationTime) {
        this.creationTime = creationTime;
    }

    void add(Player player) {
        players.add(player);
    }

    void addAll(Collection<Player> others) {
        players.addAll(others);
    }

    Set<Player> getPlayers() {
        return players;
    }

    boolean isFull() {
        return players.size() == FULL_TEAM_SIZE;
    }

    @Override
    public String toString() {
        return creationTime + " " + players.toString();
    }
}