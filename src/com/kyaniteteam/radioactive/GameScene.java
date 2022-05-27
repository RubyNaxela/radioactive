package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.util.Colors;

public class GameScene extends Scene {

    private final PlayerBoat player = new PlayerBoat();
    
    @Override
    protected void init() {
        setBackgroundColor(Colors.DARK_BLUE);
        add(player);
    }

    @Override
    protected void loop() {

    }
}
