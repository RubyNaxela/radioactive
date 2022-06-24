package com.kyaniteteam.radioactive.particles;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.entities.Particle;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.graphics.TextureAtlas;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

public class WaterCircle extends Particle {

    private final static AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture[] frames = ((TextureAtlas) assets.get("texture.water_circles")).getRow(128, 128, 5);

    public WaterCircle(@NotNull GameScene scene, @NotNull Vector2f position) {
        super(scene, frames, 0.5f);
        setPosition(position);
        setOrigin(64, 64);
        setFillColor(Colors.CADET_BLUE.withOpacity(0.2f));
    }
}
