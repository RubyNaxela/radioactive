package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.entities.DroppedBarrel;
import com.kyaniteteam.radioactive.entities.boats.EnemyBoat;
import com.kyaniteteam.radioactive.entities.boats.PatrolBoat;
import com.kyaniteteam.radioactive.entities.boats.PlayerBoat;
import com.kyaniteteam.radioactive.entities.boats.PoliceBoat;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class GameScene extends Scene {

    private static final AudioHandler audioHandler = GameContext.getInstance().getAudioHandler();
    private final Window window = getContext().getWindow();
    private final Background background = new Background();
    private final PlayerBoat player = new PlayerBoat(this);
    private final PatrolBoat patrolBoat = new PatrolBoat(this, 150, 30);
    private final PoliceBoat policeBoat = new PoliceBoat(this, 150, 30);

    public PlayerBoat getPlayer() {
        return player;
    }

    public List<DroppedBarrel> getBarrels() {
        return drawables.stream().filter(d -> d instanceof DroppedBarrel)
                        .map(d -> (DroppedBarrel) d).collect(Collectors.toList());
    }

    public List<EnemyBoat> getEnemyBoats() {
        return drawables.stream().filter(p -> p instanceof EnemyBoat)
                        .map(p -> (EnemyBoat) p).collect(Collectors.toList());
    }

    @Override
    protected void init() {
        patrolBoat.setPosition(Vec2.subtract(window.getSize(),
                                             Vec2.f(200, 200)));
        patrolBoat.setPatrolPath(Vec2.f(200, 200),
                                 Vec2.f(window.getSize().x - 200, 200),
                                 Vec2.f(window.getSize().x - 200, window.getSize().y - 100),
                                 Vec2.f(300, window.getSize().y - 100));
        policeBoat.setPosition(Vec2.subtract(window.getSize(),
                                             Vec2.f(400, 400)));
        policeBoat.setPatrolPath(Vec2.f(window.getSize().x / 2, 200),
                                 Vec2.f(window.getSize().x - 200, window.getSize().y / 2),
                                 Vec2.f(window.getSize().x / 2, window.getSize().y - 200),
                                 Vec2.f(200, window.getSize().y / 2));
        add(background);
        add(player);
        add(patrolBoat);
        add(policeBoat);
        audioHandler.playSound("sound.astronomia", "music", 100, 1, true);
    }

    @Override
    protected void loop() {

    }
}
