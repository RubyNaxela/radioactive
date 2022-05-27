package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
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

    private int currentPoint = 0;
    private final float maxMoveSpeed = 160, maxRotateSpeed = 40;
    private float currentSpeed = 50, desiredRotation = 0;
    private boolean patrolling = true, investigating = false, turning = false, reachedDestination = false, msg = false;

    private final RectangleShape boat, lightRay;
    private Vector2f destination;
    private final ArrayList<Vector2f> patrolPath = new ArrayList<>();
    private final Window window = GameContext.getInstance().getWindow();

    public PatrolBoat(Color fogColor) {
        this();
        setFogColor(fogColor);
    }

    public PatrolBoat() {
//        Texture texture_boat = GameContext.getInstance().getAssetsBundle().get("texture_boat");
//        Texture texture_ray = GameContext.getInstance().getAssetsBundle().get("texture_light_ray");

        boat = new RectangleShape();
        lightRay = new RectangleShape();

//        texture_boat.apply(boat);
//        texture_ray.apply(lightRay);

        boat.setSize(Vec2.f(40, 120));
        lightRay.setSize(Vec2.f(60, 100));

        boat.setFillColor(Colors.SANDY_BROWN);
        lightRay.setFillColor(Colors.LIGHT_LIGHT_YELLOW);

        boat.setOrigin(Vec2.divideFloat(boat.getSize(), 2));
        lightRay.setOrigin(lightRay.getSize().x / 2f, lightRay.getSize().y);

        lightRay.setPosition(0, -50);

        add(lightRay, boat);

        setPatrolPath(Vec2.f(200, 200),
                Vec2.f(window.getSize().x - 200, 200),
                Vec2.f(window.getSize().x - 200, window.getSize().y - 200),
                Vec2.f(100, window.getSize().y - 200));
        setScale(0.2f, 0.2f);
        setRotation(110);
    }

    public void setFogColor(Color color) {
        boat.setFillColor(Colors.opacity(color, 90));
    }

    public void investigate(Vector2f place) {
        investigating = true;
        destination = place;
    }

    public void setPatrolPath(Vector2f... points) {
        patrolPath.addAll(Arrays.asList(points));
        destination = patrolPath.get(0);
    }

    public void patrol() {
        patrolling = true;
    }

    private float calculateRotation(Vector2f currentPosition, Vector2f desiredPosition) {
        float distX = desiredPosition.x - currentPosition.x;
        float distY = desiredPosition.y - currentPosition.y;
        return (float) Math.toDegrees(Math.atan2(distX, -distY));
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        Vector2f centerOfLight = Vec2.f(getPosition().x + (50 + lightRay.getSize().y / 2f) * Math.sin(getRotation()), getPosition().y - (50 + lightRay.getSize().y / 2f) * Math.cos(getRotation()));
        desiredRotation = calculateRotation(centerOfLight, destination);//MathUtils.direction(centerOfLight, destination);
        float deltaRot = desiredRotation - getRotation();
        if (deltaRot < -180)
            deltaRot += 360;
        if (deltaRot > 180)
            deltaRot -= 360;
        if (Math.abs(deltaRot) > maxRotateSpeed) {
            int factor = 1;
            if (deltaRot < 0) {
                factor = -1;
            }
            deltaRot = maxRotateSpeed * factor;
        }
        rotate(deltaRot * deltaTime.asSeconds());


        if (!reachedDestination && lightRay.getGlobalBounds().contains(destination)) {
            reachedDestination = true;
        }
        if (!investigating) {
            if (reachedDestination) {
                int index = -1;
                boolean found = false;
                boolean last = true;
                for (int i = 0; i < patrolPath.size() - 1; i++) {
                    if (destination == patrolPath.get(i)) {
                        index = i;
                        if (found) {
                            last = false;
                        }
                        found = true;
                    }
                }
                if (last) {
                    destination = patrolPath.get(0);
                } else {
                    destination = patrolPath.get(index + 1);
                }
                reachedDestination = false;
            }

        }

        if (Keyboard.isKeyPressed(Keyboard.Key.F)) {
            if (!msg) {
                System.out.println("Position: " + getPosition());
                System.out.println("Light center position: " + centerOfLight);
                System.out.println("Destination: " + destination);
                System.out.println("Desired rotation: " + desiredRotation);
//                System.out.println("Vector from desired rotation: " + Vec2.f(Math.sin(desiredRotation), -Math.cos(desiredRotation)));
                System.out.println("Vector from desired rotation: " + Vec2.multiply(MathUtils.direction(getPosition(), destination), Vec2.f(1, 1)));
                System.out.println("Delta distance: " + Vec2.subtract(destination, centerOfLight));
                System.out.println("Delta rotation: " + deltaRot);
//                System.out.println("Distance: " + distance);
                System.out.println("--------------------------------------");
                msg = true;
            }
        } else msg = false;

        move(currentSpeed * (float) Math.sin(Math.toRadians(getRotation())) * deltaTime.asSeconds(),
                -currentSpeed * (float) Math.cos(Math.toRadians(getRotation())) * deltaTime.asSeconds());
    }
}

