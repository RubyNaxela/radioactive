package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.entities.boats.EnemyBoat;
import com.kyaniteteam.radioactive.entities.boats.PatrolBoat;
import com.kyaniteteam.radioactive.entities.boats.PoliceBoat;
import com.kyaniteteam.radioactive.terrain.Depth;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.system.Vector2f;

import java.util.List;
import java.util.NoSuchElementException;

public final class SceneLoader {

    public static class SceneData {

        public int barrels, salary;
        public List<EnemyData> enemies;
        public List<DepthData> depths;
    }

    public static class EnemyData {

        public String type;
        public PointData initialPosition;
        public List<PointData> points;

        public EnemyBoat createEnemyBoat(@NotNull GameScene scene) {
            final EnemyBoat boat;
            if (type.equals("police")) boat = new PoliceBoat(scene, 150, 30);
            else if (type.equals("patrol")) boat = new PatrolBoat(scene, 250, 45);
            else throw new NoSuchElementException("No matching class for enemy type: " + type);
            boat.setPosition(Vec2.f(initialPosition.x, initialPosition.y));
            boat.setPatrolPath(points.stream().map(PointData::createVector2f).toArray(Vector2f[]::new));
            return boat;
        }
    }

    public static class PointData {

        public float x, y;

        public Vector2f createVector2f() {
            return Vec2.f(x, y);
        }
    }

    public static class DepthData {

        public String type;
        public PointData position;

        public Depth createDepth(@NotNull GameScene scene) {
            final float sizeFactor;
            final int capacity;
            switch (type) {
                case "small" -> {
                    sizeFactor = 0.25f;
                    capacity = 1;
                }
                case "medium" -> {
                    sizeFactor = 0.5f;
                    capacity = 2;
                }
                case "large" -> {
                    sizeFactor = 1f;
                    capacity = 10;
                }
                default -> throw new NoSuchElementException("No such depth type: " + type);
            }
            return new Depth(scene, position.createVector2f(), sizeFactor, capacity);
        }
    }
}
