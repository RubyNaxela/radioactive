package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.entities.FieldOfView;
import com.rubynaxela.kyanite.game.GameContext;
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

public class EnemyBoat extends CompoundEntity implements AnimatedEntity {

    protected final RectangleShape boat;
    private final FieldOfView lightRay;
    private final ArrayList<Vector2f> patrolPath = new ArrayList<>();
    private final Window window = GameContext.getInstance().getWindow();
    private final GameScene scene;
    private final float minMoveSpeed = 20, maxMoveSpeed = 60, maxRotationSpeed = 40, aggroTime = 0.5f, deAggroTime = 2.5f;
    protected boolean chasing = false;
    protected float currentSpeed = 50;
    private PlayerBoat chaseTarget = null;
    private int targetIndex = 0;
    private float chaseTime = 0;
    private boolean patrolling = true, investigating = false;
    private Vector2f destination;
    private Color idleLightColor = new Color(89, 221, 117, (int)(255 * 0.2f));
    private Color aggroLightColor = new Color(219, 74, 44, (int)(255 * 0.6f));

    public EnemyBoat(@NotNull GameScene scene, @NotNull Vector2f size, float lightLength, float lightSpread, Color fogColor) {
        this(scene, size, lightLength, lightSpread);
        setFogColor(fogColor);
    }

    public EnemyBoat(@NotNull GameScene scene, @NotNull Vector2f size, float lightLength, float lightSpread) {
        this.scene = scene;
        boat = new RectangleShape(size);
        lightRay = new FieldOfView(lightLength, lightSpread);

        boat.setOrigin(Vec2.divideFloat(boat.getSize(), 2));

        lightRay.setPosition(Vec2.add(getPosition(), Vec2.f(0, -50f)));

        scene.scheduleToAdd(lightRay);
        scene.schedule(s -> s.bringToTop(this));
        add(boat);
    }

    public void setTexture(@NotNull Texture texture) {
        texture.apply(boat);
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

        if (deltaRot > 180) deltaRot -= 360;
        if (deltaRot < -180) deltaRot += 360;

        if (Math.abs(deltaRot) > maxRotationSpeed) {
            int factor = 1;
            if (deltaRot < 0) factor = -1;
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
        updateColor();

        if (chasing) {
            if (chaseTarget != null) {
                if (scene.getPlayer().isVisibleBy(this)/*player boat is not in the field of view*/) {//TODO
                    destination = chaseTarget.getPosition();
                    chaseTime = 0;
                } else {
                    chaseTime += deltaTime.asSeconds();
                    //TODO fill color
                    if (chaseTime >= deAggroTime/* || MathUtils.distance(getPosition(), destination) < 20*/) {
                        chasing = false;
                        patrolling = true;
                        chaseTarget = null;
                    }
                }
            } else {
                patrolling = true;
                chasing = false;
            }
        } else {
            if (scene.getPlayer().isVisibleBy(this)/*player boat is in the field of view*/) {//TODO
                chaseTime += deltaTime.asSeconds();
                //TODO fill color
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
                    else {
                        targetIndex = 0;
                    }
                    destination = patrolPath.get(targetIndex);
                }
            } else {
                if (investigating) {
                    if (lightRay.containsPoint(destination)) {
                        patrolling = true;
                        investigating = false;
                        destination = patrolPath.get(targetIndex);
                    }
                }
            }
        }

        if (Keyboard.isKeyPressed(Keyboard.Key.P)) {
            System.out.println("HI");
        }

        move(currentSpeed * (float) Math.sin(Math.toRadians(getRotation())) * deltaTime.asSeconds(),
             -currentSpeed * (float) Math.cos(Math.toRadians(getRotation())) * deltaTime.asSeconds());
    }

    public void setLightColor(@NotNull Color color) {
        lightRay.setFillColor(color);
    }

    private void updateColor(){
        if(patrolling){
            setLightColor(new Color((int)(aggroLightColor.r - (aggroLightColor.r - idleLightColor.r)
                        * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime),
                    (int)(aggroLightColor.g - (aggroLightColor.g - idleLightColor.g)
                        * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime),
                    (int)(aggroLightColor.b - (aggroLightColor.b - idleLightColor.b)
                        * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime),
                    (int)(aggroLightColor.a - (aggroLightColor.a - idleLightColor.a)
                        * Math.min(aggroTime - chaseTime, aggroTime) / aggroTime)));
        }
        
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
    public void setRotation(float angle) {
        super.setRotation(angle);
        lightRay.setRotation(angle);
    }

    @Override
    public void move(float x, float y) {
        super.move(x, y);
        lightRay.move(x, y);
    }

    @Override
    public void rotate(float angle) {
        super.rotate(angle);
        lightRay.rotate(angle);
    }
}

