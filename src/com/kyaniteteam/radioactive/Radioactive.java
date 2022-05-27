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
        getContext().setupWindow(1024, 768, "Radioactive")
                    .setScene(new GameScene()).setHUD(new GameHUD());
    }
}
