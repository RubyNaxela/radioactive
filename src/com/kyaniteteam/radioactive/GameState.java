package com.kyaniteteam.radioactive;

public class GameState {

    public int day, barrels, money, time;
    public float fuel;

    public GameState sethDay(int day) {
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

    public GameState setFuel(int fuel) {
        this.fuel = fuel;
        return this;
    }
}
