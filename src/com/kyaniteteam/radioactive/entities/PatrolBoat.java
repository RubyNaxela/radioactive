package com.kyaniteteam.radioactive.entities;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;

public class PatrolBoat extends CompoundEntity implements AnimatedEntity {

    private PlayerBoat chaseTarget = null;
    private final RectangleShape boat;
    private final FieldOfView lightRay;
    private final ArrayList<Vector2f> patrolPath = new ArrayList<>();
    private final Window window = GameContext.getInstance().getWindow();
    private final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private int targetIndex = 0;
    private final float minMoveSpeed = 20, maxMoveSpeed = 60, maxRotationSpeed = 40, aggroTime = 1.5f, deAggroTime = 5;
    private float currentSpeed = 50, chaseTime = 0;
    private boolean patrolling = true, investigating = false, chasing = false;
    private Vector2f destination;

    public PatrolBoat(@NotNull GameScene scene, float lightLength, float lightSpread, Color fogColor) {
        this(scene, lightLength, lightSpread);
        setFogColor(fogColor);
    }

    public PatrolBoat(@NotNull GameScene scene, float lightLength, float lightSpread) {
        boat = new RectangleShape(Vec2.f(120, 120));
        lightRay = new FieldOfView(lightLength, lightSpread);

        assets.<Texture>get("texture.patrol_boat").apply(boat);

        boat.setOrigin(Vec2.divideFloat(boat.getSize(), 2));

        lightRay.setPosition(Vec2.add(getPosition(), Vec2.f(0, -50f)));

        scene.scheduleToAdd(lightRay);
        scene.schedule(s -> s.bringToTop(this));
        add(boat);
    }

    public boolean isPointWithinFOV(@NotNull Vector2f point) {
        return lightRay.containsPoint(point);
    }

    public void setFogColor(Color color) {
        boat.setFillColor(Colors.opacity(color, 0.2f));
    }

    public void setBoatSize(Vector2f size) {
        boat.setSize(size);
        boat.setOrigin(Vec2.divideFloat(boat.getSize(), 2));
        lightRay.setPosition(Vec2.add(getPosition(), Vec2.f(0, -boat.getSize().y / 2.4f)));
    }

    public void investigate(Vector2f place) {
        investigating = true;
        patrolling = false;
        destination = place;
    }

    public void setPatrolPath(Vector2f... points) {
        patrolPath.addAll(Arrays.asList(points));
        destination = patrolPath.get(0);
    }

    private float calculateRotation(Vector2f currentPosition, Vector2f desiredPosition) {
        float distX = desiredPosition.x - currentPosition.x;
        float distY = desiredPosition.y - currentPosition.y;
        return (float) Math.toDegrees(Math.atan2(distX, -distY));
    }

    private void motionLogic(float loopDuration) {
        if (getRotation() >= 360)
            setRotation(getRotation() - 360);
        if (getRotation() <= -360)
            setRotation(getRotation() + 360);
        float deltaRot = calculateRotation(getPosition(), destination) - getRotation();

        if (deltaRot > 180)
            deltaRot -= 360;
        if (deltaRot < -180)
            deltaRot += 360;

        if (Math.abs(deltaRot) > maxRotationSpeed) {
            int factor = 1;
            if (deltaRot < 0)
                factor = -1;
            deltaRot = maxRotationSpeed * factor;
        }
        rotate(deltaRot * loopDuration);
        currentSpeed = minMoveSpeed + (maxMoveSpeed - minMoveSpeed) * (1 - Math.abs(deltaRot) / maxRotationSpeed);
        if (((GameScene) window.getScene()).getBarrels().stream().anyMatch(b -> MathUtils.isInsideCircle(
                getPosition(), b.getPosition(), b.getToxicRadius()))) currentSpeed *= 0.5f;
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        motionLogic(deltaTime.asSeconds());


        if (chasing) {
            if (chaseTarget != null) {
                destination = chaseTarget.getPosition();
                if (false/*player boat is not in the field of view*/) {//TODO
                    chaseTime += deltaTime.asSeconds();
                    if (chaseTime >= deAggroTime) {
                        chasing = false;
                        patrolling = true;
                        chaseTarget = null;
                    }
                } else chaseTime = 0;
            } else {
                patrolling = true;
                chasing = false;
            }
        } else {
            if (false/*player boat is in the field of view*/) {//TODO
                chaseTime += deltaTime.asSeconds();
                if (chaseTime >= aggroTime) {
                    patrolling = false;
                    investigating = false;
                    chasing = true;
                    chaseTarget = ((GameScene) GameContext.getInstance().getWindow().getScene()).getPlayer();
                }
            } else chaseTime = 0;


            if (patrolling) {
                if (lightRay.containsPoint(destination)) {
                    if (targetIndex != patrolPath.size() - 1)
                        targetIndex++;
                    else{
                        System.out.println("BLERGH");
                        targetIndex = 0;
                    }
                    destination = patrolPath.get(targetIndex);
                }
            } else {
                if (lightRay.containsPoint(destination)) {
                    patrolling = true;
                    investigating = false;
                    destination = patrolPath.get(targetIndex);
                }
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.P)) {
            System.out.println("HI");
        }

        if (lightRay.containsPoint(Vec2.subtract(destination, getPosition())))
            System.out.println("I SEE YOU.");

        move(currentSpeed * (float) Math.sin(Math.toRadians(getRotation())) * deltaTime.asSeconds(),
                -currentSpeed * (float) Math.cos(Math.toRadians(getRotation())) * deltaTime.asSeconds());
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
        lightRay.move(x, y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        lightRay.setPosition(x, y);
    }

    @Override
    public void setPosition(@NotNull Vector2f position) {
        super.setPosition(position);
        lightRay.setPosition(position);
    }

    @Override
    public void rotate(float angle) {
        super.rotate(angle);
        lightRay.rotate(angle);
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        lightRay.setRotation(angle);
    }
}

