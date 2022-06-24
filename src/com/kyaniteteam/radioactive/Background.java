package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

public class Background extends CompoundEntity implements AnimatedEntity {

    private final Texture texture = GameContext.getInstance().getAssetsBundle().get("texture.background");
    private final Window window = GameContext.getInstance().getWindow();
    private final Vector2f size = Vec2.f(window.getSize());
    private final RectangleShape tile1 = new RectangleShape(size), tile2 = new RectangleShape(size);

    public Background() {
        super();
        tile1.setTexture(texture);
        tile2.setTexture(texture);
        tile1.setPosition(0, 0);
        tile2.setPosition(0, size.y);
        tile2.setScale(1, -1);
        tile2.setOrigin(0, size.y);
        add(tile1, tile2);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        tile1.move(0, -10 * deltaTime.asSeconds());
        tile2.move(0, -10 * deltaTime.asSeconds());
        if (!window.isInside(tile1)) tile1.setPosition(Vec2.add(tile2.getPosition(), Vec2.f(0, size.y)));
        if (!window.isInside(tile2)) tile2.setPosition(Vec2.add(tile1.getPosition(), Vec2.f(0, size.y)));
    }
}
