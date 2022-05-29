package com.kyaniteteam.radioactive;

import java.util.ArrayList;

public class GameState {

    public int currentLevel = 1, day, barrels, startingBarrelsCount, money, startingMoney, time, fullScore = 0;
    public float dropProgress;
    public float fuel;
    public ArrayList<String> barrelStates;

    public GameState setDay(int day) {
        this.day = day;
        return this;
    }

    public GameState setBarrels(int barrels) {
        this.barrels = barrels;
        prepBarrels();
        return this;
    }

    public GameState setMoney(int money) {
        this.money = money;
        this.startingMoney = money;
        return this;
    }

    public GameState setTime(int time) {
        this.time = time;
        return this;
    }

    public GameState setDropProgress(int dropProgress) {
        this.dropProgress = dropProgress;
        return this;
    }

    public GameState setFuel(float fuel) {
        this.fuel = fuel;
        return this;
    }

    public void prepBarrels() {
        barrelStates = new ArrayList<>(6);
        startingBarrelsCount = barrels;
        for (int i = 0; i < startingBarrelsCount; i++) {
            barrelStates.add("ready");
        }
        for (int i = startingBarrelsCount; i < 6; i++) {
            barrelStates.add("nonexistent");
        }
    }
}
