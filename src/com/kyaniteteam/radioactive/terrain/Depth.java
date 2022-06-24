package com.kyaniteteam.radioactive.terrain;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

public class Depth extends RectangleShape {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();

    private static final Texture depthSmall = assets.get("texture.hole.small");
    private static final Texture depthMedium = assets.get("texture.hole.medium");
    private static final Texture depthBig = assets.get("texture.hole.big");

    private final GameScene scene;
    private final int capacity;
    private int barrelCounter = 0;
    private boolean isFull = false;

    public Depth(@NotNull GameScene scene, @NotNull Vector2f position, float sizeFactor, int capacity) {
        this.scene = scene;
        this.capacity = capacity;
        setPosition(position);
        setSize(256 * sizeFactor, 256 * sizeFactor);
        setOrigin(Vec2.divide(getSize(), 2));
        if (capacity == 1) setTexture(depthSmall);
        else if (capacity == 2) setTexture(depthMedium);
        else setTexture(depthBig);
    }

    public int getBarrelCounter() {
        return barrelCounter;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean ifFull() {
        return isFull;
    }

    public void addBarrel() {
        if (getBarrelCounter() < getCapacity()) {
            barrelCounter++;
            if (getBarrelCounter() == getCapacity()) isFull = true;
        }
    }

    public boolean isPlayerInside() {
        return MathUtils.isInsideCircle(scene.getPlayer().getPosition(), getPosition(), getSize().x / 2);
    }
}
