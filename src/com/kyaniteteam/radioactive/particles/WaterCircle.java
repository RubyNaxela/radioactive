package com.kyaniteteam.radioactive.particles;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AnimatedTexture;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.assets.TextureAtlas;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.system.Clock;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;


public class WaterCircle extends CircleShape implements AnimatedEntity {

    private final static AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private final Clock clock = GameContext.getInstance().getClock();

    private static Texture[] frames = ((TextureAtlas) assets.get("texture.water_circles")).getRow(128, 128, 3);
    private final float timeSpawned;
    private int stage = -1;

    public WaterCircle(Vector2f position) {
        super(64);
        setPosition(position);
        setOrigin(64, 64);
        timeSpawned = clock.getTime().asSeconds();
        nextFrame();
    }

    void nextFrame() {
        stage++;
        frames[stage].apply(this);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        if (stage == 0 && elapsedTime.asSeconds() - timeSpawned > 0.25f) nextFrame();
        if (stage == 1 && elapsedTime.asSeconds() - timeSpawned > 0.5f) nextFrame();
        if (stage == 2 && elapsedTime.asSeconds() - timeSpawned > 0.75f)
            GameContext.getInstance().getWindow().getScene().scheduleToRemove(this);
    }
}
