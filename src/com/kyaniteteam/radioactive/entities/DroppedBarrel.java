package com.kyaniteteam.radioactive.entities;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.particles.WaterCircle;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

public class DroppedBarrel extends CompoundEntity implements AnimatedEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture
            barrelTexture = assets.get("texture.barrel_top"),
            toxicBarrelTexture = assets.get("texture.barrel_top_leak"),
            toxicTexture = assets.get("texture.toxic_water");
    private RectangleShape water;
    private final CircleShape barrel;
    public boolean safelyDropped;
    float animationProgress = 0;

    public DroppedBarrel(@NotNull GameScene scene, @NotNull Vector2f position, boolean safe) {

        safelyDropped = safe;
        if(!safe){
            water = new RectangleShape(Vec2.f(128, 128));
            water.setOrigin(64, 64);
            toxicTexture.apply(water);
            water.setRotation(MathUtils.randomFloat(0, 360));
            if (MathUtils.probability(0.5f)) water.scale(-1, 1);
            if (MathUtils.probability(0.5f)) water.scale(1, -1);
            add(water);
        }

        barrel = new CircleShape(15);
        barrel.setOrigin(15, 15);
        if(safelyDropped) barrelTexture.apply(barrel);
        if(!safelyDropped) toxicBarrelTexture.apply(barrel);
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
            if(!safelyDropped){
                water.setFillColor(Colors.opacity(Colors.WHITE, Math.max(0, animationProgress * 2 - 1f)));
                water.setScale(Math.max(0, animationProgress * 2 - 1f), Math.max(0, animationProgress * 2 - 1f));
            }
            barrel.setFillColor(Colors.opacity(Colors.WHITE, 1 - animationProgress));
        }
    }

    public float getToxicRadius() {
        if(safelyDropped) return 0;
        else return water.getGlobalBounds().width / 2;
    }
}
