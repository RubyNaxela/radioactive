package com.kyaniteteam.radioactive.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldOfView implements Drawable, Transformable {

    private final float opacity = 0.15f, distance, angle;
    private final VertexArray vertices;
    private final RectangleShape transformHolder = new RectangleShape(Vector2f.ZERO);

    public FieldOfView(float distance, float angle) {
        this.distance = distance;
        this.angle = angle;
        this.vertices = new VertexArray(PrimitiveType.TRIANGLE_FAN);
        final Window window = GameContext.getInstance().getWindow();
        final Vector2f mid = Vec2.f(0, 0);
        final Color color = Colors.opacity(Colors.WHITE, opacity);
        vertices.add(new Vertex(mid, color));
        for (final Vector2f v : getCirclePoints(distance, MathUtils.degToRad(angle)))
            vertices.add(new Vertex(Vec2.add(mid, v), color));
        vertices.add(new Vertex(mid, color));
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

    public void setFillColor(@NotNull Color color) {
        vertices.forEach(v -> {
            try {
                final Field colorField = Vertex.class.getField("color");
                colorField.setAccessible(true);
                colorField.set(v, color);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public FloatRect getGlobalBounds() {
        return getTransform().transformRect(vertices.getBounds());
    }

    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        final RenderStates renderStates = new RenderStates(states.blendMode,
                                                           Transform.combine(states.transform, getTransform()),
                                                           states.texture, states.shader);
        target.draw(vertices, renderStates);
    }

    @Override
    public void setPosition(float x, float y) {
        transformHolder.setPosition(x, y);
    }

    @Override
    public void setPosition(Vector2f v) {
        transformHolder.setPosition(v);
    }

    @Override
    public void setRotation(float angle) {
        transformHolder.setRotation(angle);
    }

    @Override
    public void setScale(float x, float y) {
        transformHolder.setScale(x, y);
    }

    @Override
    public void setScale(Vector2f factors) {
        transformHolder.setScale(factors);
    }

    @Override
    public void setOrigin(float x, float y) {

    }

    @Override
    public void setOrigin(Vector2f v) {
        transformHolder.setOrigin(v);
    }

    @Override
    public Vector2f getPosition() {
        return transformHolder.getPosition();
    }

    @Override
    public float getRotation() {
        return transformHolder.getRotation();
    }

    @Override
    public Vector2f getScale() {
        return transformHolder.getScale();
    }

    @Override
    public Vector2f getOrigin() {
        return transformHolder.getOrigin();
    }

    @Override
    public void move(float x, float y) {
        transformHolder.move(x, y);
    }

    @Override
    public void move(Vector2f v) {
        transformHolder.move(v);
    }

    @Override
    public void rotate(float angle) {
        transformHolder.rotate(angle);
    }

    @Override
    public void scale(float x, float y) {
        transformHolder.scale(x, y);
    }

    @Override
    public void scale(Vector2f factors) {
        transformHolder.scale(factors);
    }

    @Override
    public Transform getTransform() {
        return transformHolder.getTransform();
    }

    @Override
    public Transform getInverseTransform() {
        return transformHolder.getInverseTransform();
    }
}
