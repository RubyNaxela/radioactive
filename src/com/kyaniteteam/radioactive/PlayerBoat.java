package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;

public class PlayerBoat extends RectangleShape implements AnimatedEntity, KeyListener {

    private static AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static Texture tex = assets.get("texture.player_boat");
    private Window window = GameContext.getInstance().getWindow();

    public PlayerBoat() {
        super(Vec2.f(100, 100));
        setPosition(100, 600);
        setOrigin(Vec2.divideFloat(getSize(), 2));
        tex.apply(this);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        float baseVelocity = 40;
        if (Keyboard.isKeyPressed(Keyboard.Key.A))
            rotate(-10 * deltaTime.asSeconds());
        if (Keyboard.isKeyPressed(Keyboard.Key.D))
            rotate(10 * deltaTime.asSeconds());
        final float rotation = getRotation();
        final float velocityX = baseVelocity * (float) Math.sin(MathUtils.degToRad(rotation));
        final float velocityY = baseVelocity * (float) -Math.cos(MathUtils.degToRad(rotation));
        move(Vec2.multiply(Vec2.f(velocityX, velocityY), deltaTime.asSeconds()));
    }
}
