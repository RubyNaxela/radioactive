package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;

public class FuelIndicator extends CompoundEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();

    private final Label label = new Label();
    private final RectangleShape indicator = new RectangleShape();

    public FuelIndicator(int characterSize) {

        label.setCharacterSize(characterSize);
        label.setText(((DataAsset) assets.get("lang.en_us")).getString("label.fuel"));
        add(label);

        final GlobalRect labelBounds = GlobalRect.from(label.getGlobalBounds());
        indicator.setSize(Vec2.f(100, characterSize));
        indicator.setPosition(labelBounds.right, labelBounds.top);
    }

    public void setFuel(float fuel) {
        indicator.setSize(Vec2.f(indicator.getSize().x, fuel));
    }

    public void setText(@NotNull String text) {
        label.setText(text);
    }

    public void setColor(@NotNull Color color) {
        label.setColor(color);
    }
}
