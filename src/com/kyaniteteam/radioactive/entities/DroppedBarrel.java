package com.kyaniteteam.radioactive.entities;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.particles.WaterCircle;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.graphics.CircleShape;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

public class DroppedBarrel extends CompoundEntity implements AnimatedEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture
            barrelTexture = assets.get("texture.barrel_top"),
            toxicBarrelTexture = assets.get("texture.barrel_top_leak"),
            toxicTexture1 = assets.get("texture.toxic_water.1"),
            toxicTexture2 = assets.get("texture.toxic_water.2"),
            toxicTexture3 = assets.get("texture.toxic_water.3");
    private final CircleShape barrel;
    public boolean safelyDropped;
    float animationProgress = 0;
    private RectangleShape water;

    public DroppedBarrel(@NotNull GameScene scene, @NotNull Vector2f position, boolean safe) {

        safelyDropped = safe;
        if (!safe) {
            water = new RectangleShape(128, 128);
            water.setOrigin(64, 64);
            if (MathUtils.probability(1 / 3f)) water.setTexture(toxicTexture1);
            else if (MathUtils.probability(1 / 2f)) water.setTexture(toxicTexture2);
            else water.setTexture(toxicTexture3);
            water.setRotation(MathUtils.randomFloat(0, 360));
            if (MathUtils.probability(0.5f)) water.scale(-1, 1);
            if (MathUtils.probability(0.5f)) water.scale(1, -1);
            add(water);
        }

        barrel = new CircleShape(15);
        barrel.setOrigin(15, 15);
        if (safelyDropped) barrel.setTexture(barrelTexture);
        if (!safelyDropped) barrel.setTexture(toxicBarrelTexture);
        barrel.setRotation(MathUtils.randomFloat(0, 360));
        if (MathUtils.probability(0.5f)) barrel.scale(-1, 1);
        if (MathUtils.probability(0.5f)) barrel.scale(1, -1);
        add(barrel);

        setPosition(position);
        scene.scheduleToAdd(new WaterCircle(scene, getPosition()));
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        animationProgress += 0.25f * deltaTime.asSeconds();
        if (animationProgress <= 1) {
            if (!safelyDropped) {
                water.setFillColor(Colors.WHITE.withOpacity(Math.max(0, animationProgress * 2 - 1f)));
                water.setScale(Math.max(0, animationProgress * 2 - 1f), Math.max(0, animationProgress * 2 - 1f));
            }
            barrel.setFillColor(Colors.WHITE.withOpacity(1 - animationProgress * (!safelyDropped ? 1 : 0.75f)));
        }
    }

    public float getToxicRadius() {
        if (safelyDropped) return 0;
        else return water.getGlobalBounds().width / 2;
    }
}
