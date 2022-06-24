package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.Vec2;
import org.jetbrains.annotations.NotNull;

public class PatrolBoat extends EnemyBoat {

    public PatrolBoat(@NotNull GameScene scene, float lightLength, float lightSpread) {
        super(scene, Vec2.f(40, 106), lightLength, lightSpread);
        setTexture(GameContext.getInstance().getAssetsBundle().<Texture>get("texture.patrol_boat"));
    }
}

