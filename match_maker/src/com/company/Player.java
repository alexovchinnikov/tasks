package com.company;

class Player {
    private int time;
    private int rank;
    private int id;

    int getTime() {
        return time;
    }

    void setTime(int time) {
        this.time = time;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
