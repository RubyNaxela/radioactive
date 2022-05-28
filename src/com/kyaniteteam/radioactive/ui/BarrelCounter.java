package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RectangleShape;

import java.util.ArrayList;
import java.util.List;

public class BarrelCounter extends CompoundEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture barrelTexture = assets.get("texture.barrel");

    private final Label label = new Label();
    private final List<RectangleShape> barrels = new ArrayList<>(6);

    public BarrelCounter(int characterSize) {

        label.setCharacterSize(characterSize);
        label.setText(((DataAsset) assets.get("lang.en_us")).getString("label.barrels"));
        add(label);

        final FloatRect labelBounds = label.getGlobalBounds();
        for (int i = 0; i < 6; i++) {
            final RectangleShape barrel = barrelTexture.createRectangleShape();
            barrel.setSize(Vec2.f(characterSize * 3.0f, characterSize * 3.0f));
            barrel.setPosition(i * characterSize * 2, characterSize * 2);
            add(barrel);
            barrels.add(barrel);
        }
    }

    public void setBarrelsCount(int barrelsCount) {
        for (int i = 0; i < 6; i++) barrels.get(i).setFillColor(i < barrelsCount ? Colors.WHITE : Colors.TRANSPARENT);
    }

    public void setText(@NotNull String text) {
        label.setText(text);
    }

    public void setColor(@NotNull Color color) {
        label.setColor(color);
    }
}
