package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Game;

public class Radioactive extends Game {

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {

    }

    @Override
    protected void init() {
        getContext().setupWindow(800, 600, "Radioactive").setScene(new GameScene());
    }
}
