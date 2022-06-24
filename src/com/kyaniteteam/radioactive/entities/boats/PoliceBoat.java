package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.audio.Sound;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class PoliceBoat extends EnemyBoat {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture
            idleTexture = assets.get("texture.police.idle"),
            angryTex1 = assets.get("texture.police.angry1"),
            angryTex2 = assets.get("texture.police.angry2");
    private static final AudioHandler audioHandler = GameContext.getInstance().getAudioHandler();
    private static final AnimatedTexture animatedTexture = new AnimatedTexture(new Texture[]{angryTex1, angryTex2}, 0.2f);
    private boolean chasingMode = false;
    private Sound sound;

    public PoliceBoat(@NotNull GameScene scene, float lightLength, float lightSpread) {
        super(scene, Vec2.f(55, 97), lightLength, lightSpread);
        setTexture(idleTexture);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        super.animate(deltaTime, elapsedTime);
        if (chase) {
            if (!chasingMode) {
                setTexture(animatedTexture);
                chasingMode = true;
                sound = audioHandler.playSound("sound.police", "boats", 100, 1, true);
            }
        } else {
            if (chasingMode) {
                setTexture(idleTexture);
                chasingMode = false;
                sound.stop();
            }
        }
    }
}
