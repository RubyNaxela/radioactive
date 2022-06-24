package com.kyaniteteam.radioactive.particles;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.entities.Particle;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

public class WavesAfterBoats extends Particle {

    private final static AssetsBundle assets = GameContext.getInstance().getAssetsBundle();

    public WavesAfterBoats(@NotNull GameScene scene, @NotNull Vector2f position) {
        super(scene, new Texture[]{assets.get("texture.wave.1"), assets.get("texture.wave.2"), assets.get("texture.wave.3"),
                                   assets.get("texture.wave.4"), assets.get("texture.wave.5")}, 0.5f);
        setPosition(position);
        setOrigin(64, 64);
    }
}