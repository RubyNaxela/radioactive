package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Scene;

import java.util.List;
import java.util.stream.Collectors;

public class GameScene extends Scene {

    private final Background background = new Background();
    private final GameState gameState = new GameState();
    private final PlayerBoat player = new PlayerBoat(gameState);
    private final PatrolBoat patrolBoat = new PatrolBoat();

    public List<DroppedBarrel> getBarrels() {
        return drawables.stream().filter(d -> d instanceof DroppedBarrel)
                        .map(d -> (DroppedBarrel) d).collect(Collectors.toList());
    }

    @Override
    protected void init() {
        //setBackgroundColor(Colors.DARK_BLUE);
        add(background);
        add(player);
        add(patrolBoat);
    }

    @Override
    protected void loop() {

    }
}
