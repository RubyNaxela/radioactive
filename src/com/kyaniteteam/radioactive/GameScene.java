package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.entities.DroppedBarrel;
import com.kyaniteteam.radioactive.entities.boats.EnemyBoat;
import com.kyaniteteam.radioactive.entities.boats.PatrolBoat;
import com.kyaniteteam.radioactive.entities.boats.PlayerBoat;
import com.kyaniteteam.radioactive.entities.boats.PoliceBoat;
import com.kyaniteteam.radioactive.terrain.Depth;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class GameScene extends Scene {

    private static final AudioHandler audioHandler = GameContext.getInstance().getAudioHandler();
    private final Window window = getContext().getWindow();
//    private final Background background = new Background();
    private final PlayerBoat player = new PlayerBoat(this);

    private final Depth depth1 = new Depth(this);
    private final Depth depth2 = new Depth(this);
    private final Depth depth3 = new Depth(this);

    public GameScene(@NotNull SceneLoader.SceneData data) {
        add(data.enemies.stream().map(e -> e.createEnemyBoat(this)).toList());
        setBackgroundColor(new Color(83, 85, 158));
    }

    public PlayerBoat getPlayer() {
        return player;
    }

    public List<DroppedBarrel> getBarrels() {
        return drawables.stream().filter(b -> b instanceof DroppedBarrel)
                .map(b -> (DroppedBarrel) b).collect(Collectors.toList());
    }

    public List<EnemyBoat> getEnemyBoats() {
        return drawables.stream().filter(b -> b instanceof EnemyBoat)
                .map(b -> (EnemyBoat) b).collect(Collectors.toList());
    }

    public List<Depth> getDepths() {
        return drawables.stream().filter(d -> d instanceof Depth)
                .map(d -> (Depth) d).collect(Collectors.toList());
    }

    @Override
    protected void init() {
        add(depth1, depth2, depth3);
        add(player);
        audioHandler.playSound("sound.astronomia", "music", 100, 1, true);
    }

    @Override
    protected void loop() {

    }
}
