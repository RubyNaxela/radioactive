package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.entities.FieldOfView;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnemyBoat extends RectangleShape implements AnimatedEntity, MovingEntity {

    private final Window window = GameContext.getInstance().getWindow();
    private final GameScene scene;
    private final FieldOfView lightRay;
    private final List<Vector2f> patrolPath = new ArrayList<>();
    protected boolean chase = false;
    protected float baseSpeed = 50, chaseStartedTime = Float.NEGATIVE_INFINITY;
    private Vector2f target = Vector2f.ZERO;
    private int patrolPathPoint = 0;

    public EnemyBoat(@NotNull GameScene scene, @NotNull Vector2f size, float lightLength, float lightSpread) {
        super(size);
        this.scene = scene;
        this.lightRay = new FieldOfView(lightLength, lightSpread);

        setOrigin(Vec2.divideFloat(getSize(), 2));

        scene.scheduleToAdd(lightRay);
        scene.schedule(s -> s.bringToTop(this));
    }

    public void addToScene() {
        scene.add(lightRay, this);
    }

    public boolean isPointWithinFOV(@NotNull Vector2f point) {
        return lightRay.containsPoint(point);
    }

    public void setPatrolPath(@NotNull Vector2f... points) {
        patrolPath.addAll(Arrays.asList(points));
        target = patrolPath.get(0);
    }

    private boolean targetReached() {
        return MathUtils.isInsideCircle(getPosition(), target, 16);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {

        if (getRotation() >= 360) setRotation(getRotation() - 360);
        else if (getRotation() <= -360) setRotation(getRotation() + 360);

        float deltaAlpha = MathUtils.radToDeg((float) Math.atan2(target.x - getPosition().x, getPosition().y - target.y))
                           - getRotation();
        if (deltaAlpha > 180) deltaAlpha -= 360;
        else if (deltaAlpha < -180) deltaAlpha += 360;

        if (Math.abs(deltaAlpha) > baseSpeed * 2) deltaAlpha = baseSpeed * 2 * (deltaAlpha < 0 ? -1 : 1);
        rotate(deltaAlpha * deltaTime.asSeconds());

        if (targetReached() && !chase) target = patrolPath.get(++patrolPathPoint % patrolPath.size());
        if (scene.getPlayer().isVisibleBy(this)) {
            chase = true;
            chaseStartedTime = elapsedTime.asSeconds();
        }
        if (elapsedTime.asSeconds() - chaseStartedTime > 10) chase = false;
        if (chase) target = scene.getPlayer().getPosition();
        else target = patrolPath.get(patrolPathPoint % patrolPath.size());

        lightRay.setPosition(getPosition());
        lightRay.setRotation(getRotation());
    }

    @Override
    public @NotNull Vector2f getVelocity() {
        return Vec2.multiply(MathUtils.direction(getRotation()),
                             ((GameScene) window.getScene()).getBarrels().stream().anyMatch(b -> MathUtils.isInsideCircle(
                                     getPosition(), b.getPosition(), b.getToxicRadius())) ? baseSpeed / 2 : baseSpeed);
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
        throw new UnsupportedOperationException("EnemyBoat is not controllable via setVelocity");
    }
}

