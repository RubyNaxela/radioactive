package com.kyaniteteam.radioactive;

public class GameState {

    public int day, barrels, money, time;
    public float fuel;

    public GameState withDay(int day) {
        this.day = day;
        return this;
    }

    public GameState withBarrels(int barrels) {
        this.barrels = barrels;
        return this;
    }

    public GameState withTime(int time) {
        this.time = time;
        return this;
    }

    public GameState withMoney(int money) {
        this.money = money;
        return this;
    }

    public GameState withFuel(int fuel) {
        this.fuel = fuel;
        return this;
    }
}
