package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.util.Colors;

public class GameScene extends Scene {

    public final GameState gameState = new GameState();
    public final PlayerBoat player = new PlayerBoat(gameState);
    private final PatrolBoat patrolBoat = new PatrolBoat();


    @Override
    protected void init() {
        setBackgroundColor(Colors.DARK_BLUE);
        add(player);
        add(patrolBoat);
    }

    @Override
    protected void loop() {

    }
}
