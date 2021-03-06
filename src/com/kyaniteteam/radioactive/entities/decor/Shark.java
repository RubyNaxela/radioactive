package com.kyaniteteam.radioactive.entities.decor;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

public class Shark extends RectangleShape implements AnimatedEntity, MovingEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final AnimatedTexture idleTexture = assets.get("texture.shark");
    private final Window window = GameContext.getInstance().getWindow();
    private final GameScene scene;
    private final float angle = MathUtils.randomFloat(0, 360);
    private final Vector2f direction = MathUtils.direction(angle);
    private float opacity = 0;
    private boolean broughtToBottom = false;

    public Shark(@NotNull GameScene scene) {
        super(Vec2.f(192, 192));
        this.scene = scene;
        setOrigin(96, 96);
        setPosition(Vec2.f(MathUtils.randomFloat(0, window.getSize().x), MathUtils.randomFloat(0, window.getSize().y)));
        setRotation(angle - 150);
        setFillColor(Colors.TRANSPARENT);
        setTexture(idleTexture);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        opacity += deltaTime.asSeconds() / 5f;
        setFillColor(Colors.DARK_SLATE_BLUE.withOpacity(Math.min(opacity, 1)));
        if (!broughtToBottom) {
            scene.schedule(s -> s.bringToBottom(this));
            scene.schedule(s -> ((GameScene) s).getDepths().forEach(s::bringToBottom));
            broughtToBottom = true;
        }
        if (!window.isInside(this)) scene.scheduleToRemove(this);
    }

    @Override
    @NotNull
    public Vector2f getVelocity() {
        return Vec2.multiply(direction, 16);
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
    }
}
