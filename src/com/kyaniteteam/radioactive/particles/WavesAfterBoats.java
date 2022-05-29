package com.kyaniteteam.radioactive.particles;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.assets.TextureAtlas;
import com.rubynaxela.kyanite.game.entities.Particle;
import com.rubynaxela.kyanite.util.Colors;
import org.jetbrains.annotations.NotNull;
import org.jsfml.system.Vector2f;

public class WavesAfterBoats extends Particle {

    private final static AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture[] frames = new Texture[]{
            new Texture("src/res/textures/waves/wave_1.png"),
            new Texture("src/res/textures/waves/wave_2.png"), new Texture("src/res/textures/waves/wave_3.png"),
            new Texture("src/res/textures/waves/wave_4.png"), new Texture("src/res/textures/waves/wave_5.png")
    };

    public WavesAfterBoats(@NotNull GameScene scene, @NotNull Vector2f position) {
        super(scene, frames, 0.5f);
        setPosition(position);
        setOrigin(64, 64);
    }
}