package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.util.Colors;

public class GameScene extends Scene {

    private final PlayerBoat player = new PlayerBoat();
    private final PatrolBoat pBoat1 = new PatrolBoat();
    
    @Override
    protected void init() {
        pBoat1.setPosition(700, 500);
        setBackgroundColor(Colors.DARK_BLUE);
        add(player, pBoat1);
    }

    @Override
    protected void loop() {

    }
}
