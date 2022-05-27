package com.kyaniteteam.radioactive;

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
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import static java.awt.SystemColor.window;

public class DroppedBarrel extends CompoundEntity implements AnimatedEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture
            barrelTexture = assets.get("texture.barrel"),
            toxicTexture = assets.get("texture.toxic_water");
    float opacity = 0;

    private final RectangleShape water;
    private final CircleShape barrel;

    public DroppedBarrel(Vector2f position) {

        water = new RectangleShape(Vec2.f(128, 128));
        water.setOrigin(64, 64);
        toxicTexture.apply(water);
        water.setRotation(MathUtils.randomFloat(0, 360));
        if (MathUtils.probability(0.5f)) water.scale(-1, 1);
        if (MathUtils.probability(0.5f)) water.scale(1, -1);
        add(water);

        barrel = new CircleShape(15);
        barrel.setOrigin(15, 15);
        barrelTexture.apply(barrel);
        add(barrel);

        setPosition(position);
        final Window window = GameContext.getInstance().getWindow();
        window.getScene().scheduleToAdd(new WaterCircle(getPosition()));
    }


    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        opacity += 0.25f * deltaTime.asSeconds();
        if (opacity <= 1) {
            water.setFillColor(Colors.opacity(Colors.WHITE, opacity));
            barrel.setFillColor(Colors.opacity(Colors.WHITE, 1 - opacity));
        }
    }
}
