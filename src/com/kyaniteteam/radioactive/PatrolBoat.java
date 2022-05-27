package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.debug.Marker;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;

public class PatrolBoat extends CompoundEntity implements AnimatedEntity {

    private final float minMoveSpeed = 40, maxMoveSpeed = 160, maxRotateSpeed = 40;
    private final RectangleShape boat, lightRay;
    private final ArrayList<Vector2f> patrolPath = new ArrayList<>();
    private final Window window = GameContext.getInstance().getWindow();
    private final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private final Texture boatTexture = assets.get("texture.patrol_boat");
    private int currentPoint = 0;
    private float currentSpeed = 50, desiredRotation = 0;
    private boolean patrolling = true, investigating = false, turning = false, reachedDestination = false, msg = false;
    private Vector2f destination;

    public PatrolBoat(Color fogColor) {
        this();
        setFogColor(fogColor);
    }

    public PatrolBoat() {
//        Texture texture_ray = GameContext.getInstance().getAssetsBundle().get("texture_light_ray");

        boat = new RectangleShape(Vec2.f(120, 120));
        lightRay = new RectangleShape(Vec2.f(60, 100));

        boatTexture.apply(boat);
//        texture_ray.apply(lightRay);

//        boat.setFillColor(Colors.SANDY_BROWN);
        lightRay.setFillColor(Colors.NAVAJO_WHITE);

        boat.setOrigin(Vec2.divideFloat(boat.getSize(), 2));
        lightRay.setOrigin(lightRay.getSize().x / 2f, lightRay.getSize().y);
        setOrigin(boat.getOrigin());

        lightRay.setPosition(0, -50);

        add(lightRay, boat);

        setPatrolPath(Vec2.f(200, 200),
                      Vec2.f(window.getSize().x - 200, 200),
                      Vec2.f(window.getSize().x - 200, window.getSize().y - 200),
                      Vec2.f(100, window.getSize().y - 200));
//        setScale(0.2f, 0.2f);
//        setRotation(110);
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
        CircleShape dest = new CircleShape(4);
        dest.setFillColor(Colors.RED);
        dest.setPosition(destination);
        window.getScene().scheduleToAdd(dest);
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
//        Vector2f centerOfLight = Vec2.f(getPosition().x + (50 + lightRay.getSize().y / 2f) * Math.sin(getRotation()),
//        getPosition().y - (50 + lightRay.getSize().y / 2f) * Math.cos(getRotation()));
//        desiredRotation = calculateRotation(centerOfLight, destination);//MathUtils.direction(centerOfLight, destination);
//        float deltaRot = desiredRotation - getRotation();
//        if (deltaRot < -180)
//            deltaRot += 360;
//        if (deltaRot > 180)
//            deltaRot -= 360;
//        if (Math.abs(deltaRot) > maxRotateSpeed) {
//            int factor = 1;
//            if (deltaRot < 0) {
//                factor = -1;
//            }
//            deltaRot = maxRotateSpeed * factor;
//        }
//        currentSpeed = minMoveSpeed + (maxMoveSpeed - minMoveSpeed) * (1 - Math.abs(deltaRot) / maxRotateSpeed);
//        rotate(deltaRot * deltaTime.asSeconds());
//
//
//        if (!reachedDestination && lightRay.getGlobalBounds().contains(destination)) {
//            reachedDestination = true;
//        }
//        if (!investigating) {
//            if (reachedDestination) {
//                int index = -1;
//                boolean found = false;
//                boolean last = true;
//                for (int i = 0; i < patrolPath.size() - 1; i++) {
//                    if (destination == patrolPath.get(i)) {
//                        index = i;
//                        if (found) {
//                            last = false;
//                        }
//                        found = true;
//                    }
//                }
//                if (last) {
//                    destination = patrolPath.get(0);
//                } else {
//                    destination = patrolPath.get(index + 1);
//                }
//                reachedDestination = false;
//            }
//
//        }
//
//        if (Keyboard.isKeyPressed(Keyboard.Key.F)) {
//            if (!msg) {
//                System.out.println("Position: " + getPosition());
//                System.out.println("Light center position: " + centerOfLight);
//                System.out.println("Destination: " + destination);
//                System.out.println("Desired rotation: " + desiredRotation);
////                System.out.println("Vector from desired rotation: " + Vec2.f(Math.sin(desiredRotation), -Math.cos
// (desiredRotation)));
//                System.out.println("Vector from desired rotation: " + Vec2.multiply(MathUtils.direction(getPosition(),
//                destination),MathUtils.distance(getPosition(), destination)));
//                System.out.println("Delta distance: " + Vec2.subtract(destination, centerOfLight));
//                System.out.println("Delta rotation: " + deltaRot);
////                System.out.println("Distance: " + distance);
//                System.out.println("--------------------------------------");
//                msg = true;
//            }
//        } else msg = false;
//
//        move(currentSpeed * (float) Math.sin(Math.toRadians(getRotation())) * deltaTime.asSeconds(),
//                -currentSpeed * (float) Math.cos(Math.toRadians(getRotation())) * deltaTime.asSeconds());
        currentSpeed = 100;
        Vector2f direction = MathUtils.direction(getPosition(), destination);
        System.out.println(direction);
        move(Vec2.multiply(direction, currentSpeed * deltaTime.asSeconds()));
    }
}

