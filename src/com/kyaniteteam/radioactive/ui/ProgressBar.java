package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;

public class ProgressBar extends CompoundEntity {

    private final Label label = new Label();
    private final RectangleShape indicator = new RectangleShape();
    public Color startingColor = new Color(237, 215, 3);
    public Color endingColor = new Color(237, 3, 3);
    public float startingWidth;

    public ProgressBar(@NotNull String text, int characterSize) {

        label.setCharacterSize(characterSize);
        label.setText(text);
        add(label);

        final GlobalRect labelBounds = GlobalRect.from(label.getGlobalBounds());
        indicator.setSize(Vec2.f(100, characterSize));
        startingWidth = 100;
        indicator.setPosition(labelBounds.right + 4, labelBounds.top - 2);
        add(indicator);
    }

    public void setPercentage(float percentage) {
        indicator.setSize(Vec2.f(percentage * startingWidth / 100.0f, indicator.getSize().y));
        setBarColor(new Color((int)(endingColor.r - (endingColor.r - startingColor.r) * percentage / 100.0f),
                            (int)(endingColor.g - (endingColor.g - startingColor.g) * percentage / 100.0f),
                            (int)(endingColor.b - (endingColor.b - startingColor.b) * percentage / 100.0f)));

    }

    public void setLabelColor(@NotNull Color color) {
        label.setColor(color);
    }

    public void setBarColor(@NotNull Color color) {
        indicator.setFillColor(color);
    }

    public void setHeight(@NotNull float height) {
        indicator.setSize(Vec2.f(indicator.getSize().x, height));
    }

    public void setStartingWidth(@NotNull float width) {
        startingWidth = width;
    }
}
