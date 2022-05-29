package com.kyaniteteam.radioactive.terrain;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Depth extends RectangleShape {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();

    private static final Texture depth_small = assets.get("texture.small_hole");
    private static final Texture depth_medium = assets.get("texture.medium_hole");
    private static final Texture depth_big = assets.get("texture.big_hole");

    private final Window window = GameContext.getInstance().getWindow();
    private final GameScene scene;
    private final int capacity;
    private int barrelCounter = 0;
    private boolean isFull = false;

    public Depth(@NotNull GameScene scene, @NotNull Vector2f position, float sizeFactor, int capacity) {
        this.scene = scene;
        this.capacity = capacity;
        setPosition(position);
        setSize(Vec2.f(256 * sizeFactor, 256 * sizeFactor));
        setOrigin(Vec2.divideFloat(getSize(), 2));
        if(capacity == 1){
            depth_small.apply(this);
        } else if (capacity == 2) {
            depth_medium.apply(this);
        } else {
            depth_big.apply(this);
        }

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
            System.out.println("adding barrel to depth");
            if (getBarrelCounter() == getCapacity()) {
                isFull = true;
                System.out.println("this hole is full now");
            }
        }
    }

    public boolean isPlayerInside() {
        return MathUtils.isInsideCircle(scene.getPlayer().getPosition(), getPosition(), getSize().x / 2);
    }
}
