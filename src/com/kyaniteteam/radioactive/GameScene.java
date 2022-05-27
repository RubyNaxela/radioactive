package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.util.Colors;

public class GameScene extends Scene {

    private final PlayerBoat player = new PlayerBoat();
    private final PatrolBoat patrolBoat = new PatrolBoat();

    @Override
    protected void init() {
        patrolBoat.setPosition(700, 500);
        setBackgroundColor(Colors.DARK_BLUE);
        add(player);
        add(patrolBoat);
    }

    @Override
    protected void loop() {

    }
}
