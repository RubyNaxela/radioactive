package com.kyaniteteam.radioactive.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.graphics.PrimitiveType;
import com.rubynaxela.kyanite.graphics.Vertex;
import com.rubynaxela.kyanite.graphics.VertexArray;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FieldOfView extends VertexArray {

    private final float distance, angle;

    public FieldOfView(float distance, float angle) {
        super(PrimitiveType.TRIANGLE_FAN);
        this.distance = distance;
        this.angle = angle;
        final Window window = GameContext.getInstance().getWindow();
        final Vector2f mid = Vec2.f(0, 0);
        final Color color = new Color(89, 221, 117, 38);
        add(new Vertex(mid, color));
        for (final Vector2f v : getCirclePoints(distance, MathUtils.degToRad(angle)))
            add(new Vertex(Vec2.add(mid, v), color));
        add(new Vertex(mid, color));
    }

    private static float angle(@NotNull Vector2f v) {
        return (float) Math.atan2(v.y, v.x);
    }

    private static List<Vector2f> getCirclePoints(float distance, float angle) {
        List<Vector2f> ret = new ArrayList<>();
        final int maxpts = 24;
        for (int i = 0; i < maxpts; i++) {
            final float alpha = (float) ((Math.PI / -2 - angle / 2) + (i * angle) / (maxpts - 1));
            ret.add(Vec2.multiply(Vec2.f(Math.cos(alpha), Math.sin(alpha)), distance));
        }
        return ret;
    }

    private static float normalizeAngle(float angle) {
        final int fullRotations = (int) (angle / 360f);
        return 360 + angle - 360 * fullRotations;
    }

    public boolean containsPoint(@NotNull Vector2f point) {
        float alpha = normalizeAngle(MathUtils.radToDeg((float) Math.atan2(point.x - getPosition().x,
                                                                           getPosition().y - point.y)));
        float leftBound = normalizeAngle(getRotation() - angle / 2f), rightBound = normalizeAngle(getRotation() + angle / 2f);
        return MathUtils.distance(getPosition(), point) <= distance && alpha >= leftBound && alpha <= rightBound;
    }
}
