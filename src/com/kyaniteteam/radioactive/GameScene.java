package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.entities.DroppedBarrel;
import com.kyaniteteam.radioactive.entities.FieldOfView;
import com.kyaniteteam.radioactive.entities.PatrolBoat;
import com.kyaniteteam.radioactive.entities.PlayerBoat;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;

import java.util.List;
import java.util.stream.Collectors;

public class GameScene extends Scene {

    private final Window window = getContext().getWindow();

    private final Background background = new Background();
    private final PlayerBoat player = new PlayerBoat(this);
    private final PatrolBoat patrolBoat = new PatrolBoat(300, 300);

    public PlayerBoat getPlayer() {
        return player;
    }

    public List<DroppedBarrel> getBarrels() {
        return drawables.stream().filter(d -> d instanceof DroppedBarrel)
                        .map(d -> (DroppedBarrel) d).collect(Collectors.toList());
    }

    public List<PatrolBoat> getPatrolBoats() {
        return drawables.stream().filter(p -> p instanceof PatrolBoat)
                        .map(p -> (PatrolBoat) p).collect(Collectors.toList());
    }

    @Override
    protected void init() {
        patrolBoat.setPosition(Vec2.subtract(window.getSize(),
                Vec2.f(200, 200)));
        patrolBoat.setPatrolPath(Vec2.f(200, 200),
                                 Vec2.f(window.getSize().x - 200, 200),
                                 Vec2.f(window.getSize().x - 200, window.getSize().y - 200),
                                 Vec2.f(100, window.getSize().y - 200));
        add(background);
        add(player);
        add(patrolBoat);
    }

    @Override
    protected void loop() {

    }
}
