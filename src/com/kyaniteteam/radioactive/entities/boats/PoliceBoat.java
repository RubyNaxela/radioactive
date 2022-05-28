package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AnimatedTexture;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.audio.Sound;
import org.jsfml.system.Time;

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
        if (chasing) {
            if (!chasingMode) {
                animatedTexture.apply(boat);
                chasingMode = true;
                sound = audioHandler.playSound("sound.police", "boats", 100, 1, true);
            }
        } else {
            if (chasingMode) {
                animatedTexture.remove(boat);
                idleTexture.apply(boat);
                chasingMode = false;
                sound.stop();
            }
        }
    }
}
