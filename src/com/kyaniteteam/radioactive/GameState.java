package com.kyaniteteam.radioactive;

import java.util.ArrayList;

public class GameState {

    public int day, barrels, startingBarrelsCount, money, time, salary;
    public float dropProgress;
    public float fuel;
    public ArrayList<String> barrelStates;

    public GameState setDay(int day) {
        this.day = day;
        return this;
    }

    public GameState setBarrels(int barrels) {
        this.barrels = barrels;
        return this;
    }

    public GameState setMoney(int money) {
        this.money = money;
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

    public GameState setFuel(int fuel) {
        this.fuel = fuel;
        return this;
    }

    public void prepBarrels(int count){
        barrelStates = new ArrayList<String>(count);
        startingBarrelsCount = count;
        for(int i = 0; i < startingBarrelsCount; i++){
            barrelStates.add("ready");
        }
        for(int i = startingBarrelsCount; i < 5; i++){
            barrelStates.add("nonexistent");
        }
    }
}
