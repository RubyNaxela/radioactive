package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.entities.FieldOfView;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
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
    protected float baseSpeed = 50, aggroTime = 10, chaseStartedTime = Float.NEGATIVE_INFINITY;
    private Vector2f target = Vector2f.ZERO;
    private int patrolPathPoint = 0;

    private Color idleLightColor = Colors.opacity(new Color(89, 221, 117), 0.2f);
    private Color aggroLightColor = Colors.opacity(new Color(219, 74, 44), 0.6f);

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

    public void setLightColor(@NotNull Color color) {
        lightRay.setFillColor(color);
    }

    private void updateColor() {
        float chaseTime = scene.getContext().getClock().getTime().asSeconds() - chaseStartedTime;
        if (chase) {
            setLightColor(new Color((int) (idleLightColor.r + (aggroLightColor.r - idleLightColor.r)
                    * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime),
                    (int) (idleLightColor.g + (aggroLightColor.g - idleLightColor.g)
                            * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime),
                    (int) (idleLightColor.b + (aggroLightColor.b - idleLightColor.b)
                            * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime),
                    (int) (idleLightColor.a + (aggroLightColor.a - idleLightColor.a)
                            * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime)));
        }
    }
    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {

        if (getRotation() >= 360) setRotation(getRotation() - 360);
        else if (getRotation() <= -360) setRotation(getRotation() + 360);

        float deltaAlpha = MathUtils.radToDeg((float) Math.atan2(target.x - getPosition().x, getPosition().y - target.y))
                           - getRotation();
        if (deltaAlpha > 180) deltaAlpha -= 360;
        else if (deltaAlpha < -180) deltaAlpha += 360;

        if (Math.abs(deltaAlpha) > baseSpeed) deltaAlpha = baseSpeed * (deltaAlpha < 0 ? -1 : 1);
        rotate(deltaAlpha*1.5f * deltaTime.asSeconds());

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
        updateColor();
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

