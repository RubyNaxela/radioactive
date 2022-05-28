package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.entities.DroppedBarrel;
import com.kyaniteteam.radioactive.entities.PatrolBoat;
import com.kyaniteteam.radioactive.entities.PlayerBoat;
import com.rubynaxela.kyanite.game.Scene;

import java.util.List;
import java.util.stream.Collectors;

public class GameScene extends Scene {

    private final Background background = new Background();
    private final PlayerBoat player = new PlayerBoat(this);
    private final PatrolBoat patrolBoat = new PatrolBoat();

    public List<DroppedBarrel> getBarrels() {
        return drawables.stream().filter(d -> d instanceof DroppedBarrel)
                        .map(d -> (DroppedBarrel) d).collect(Collectors.toList());
    }

    @Override
    protected void init() {
        add(background);
        add(player);
        add(patrolBoat);
    }

    @Override
    protected void loop() {

    }
}
