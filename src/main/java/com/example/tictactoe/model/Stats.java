package com.example.tictactoe.model;

public class Stats {
    private String name;
    private int wins;
    private int loses;
    private int tied;

    public Stats(String name, int wins, int loses, int tied) {
        this.name = name;
        this.wins = wins;
        this.loses = loses;
        this.tied = tied;
    }

    public Stats() {
    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getTied() {
        return tied;
    }

    public void plusWin(){
        this.wins++;
    }

    public void plusLoses(){
        this.loses++;
    }

    public void plusTied(){
        this.tied++;
    }
}
