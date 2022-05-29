package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.entities.DroppedBarrel;
import com.kyaniteteam.radioactive.entities.boats.EnemyBoat;
import com.kyaniteteam.radioactive.entities.boats.PlayerBoat;
import com.kyaniteteam.radioactive.entities.decor.Shark;
import com.kyaniteteam.radioactive.terrain.Depth;
import com.kyaniteteam.radioactive.ui.PostHUD;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.util.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;

import java.util.List;

public class GameScene extends Scene {

    private static final GameState gameState = GameContext.getInstance().getResource("data.game_state");
    private final Background backgroundShader = new Background();
    private final PlayerBoat player;

    private float latestShark = Float.NEGATIVE_INFINITY, nextShark = 8f;

    public GameScene(@NotNull SceneLoader.SceneData data) {
        gameState.setMoney(data.salary).setBarrels(data.barrels).setFuel(100);
        data.enemies.stream().map(e -> e.createEnemyBoat(this)).forEach(EnemyBoat::addToScene);
        data.depths.stream().map(d -> d.createDepth(this)).forEach(this::add);
        setBackgroundColor(new Color(40, 40, 80));
        player = new PlayerBoat(this);
    }

    public PlayerBoat getPlayer() {
        return player;
    }

    public List<DroppedBarrel> getBarrels() {
        return drawables.stream().filter(b -> b instanceof DroppedBarrel).map(b -> (DroppedBarrel) b).toList();
    }

    public List<EnemyBoat> getEnemyBoats() {
        return drawables.stream().filter(b -> b instanceof EnemyBoat).map(b -> (EnemyBoat) b).toList();
    }

    public List<Depth> getDepths() {
        return drawables.stream().filter(d -> d instanceof Depth).map(d -> (Depth) d).toList();
    }

    @Override
    protected void init() {
        add(backgroundShader);
        add(player);
    }

    @Override
    protected void loop() {
        final float elapsedTime = getContext().getClock().getTime().asSeconds();
        if (elapsedTime >= latestShark + nextShark) {
            nextShark = MathUtils.randomFloat(8, 16);
            scheduleToAdd(new Shark(this));
            latestShark = elapsedTime;
        }
        if (gameState.barrels == 0) {
            getContext().getWindow().setHUD(new PostHUD());
            suspend();
        }
    }
}
