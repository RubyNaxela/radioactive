package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

public class DroppedBarrel extends CircleShape {
    private static AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static Texture tex = assets.get("texture.barrel_test");
    private final Clock clock = GameContext.getInstance().getClock();
    private Window window = GameContext.getInstance().getWindow();
    private float droppedTime;
    int numberOfCircles;

    public DroppedBarrel(Vector2f position) {
        super(15);
        //setFillColor(Colors.AQUA);
        setPosition(position);
        setOrigin(15, 15);
        tex.apply(this);
        droppedTime = clock.getTime().asSeconds();
        numberOfCircles = 3;
        window.getScene().scheduleToAdd(new WaterCircle(getPosition()));
    }


}
