package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.math.FloatRect;
import org.jetbrains.annotations.NotNull;

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

        final FloatRect labelBounds = label.getGlobalBounds();
        indicator.setSize(100, characterSize);
        startingWidth = 100;
        indicator.setPosition(labelBounds.right + 4, labelBounds.top - 2);
        add(indicator);
    }

    public void setPercentage(float percentage) {
        indicator.setSize(percentage * startingWidth / 100.0f, indicator.getSize().y);
        setBarColor(new Color((int) (endingColor.r - (endingColor.r - startingColor.r) * percentage / 100.0f),
                              (int) (endingColor.g - (endingColor.g - startingColor.g) * percentage / 100.0f),
                              (int) (endingColor.b - (endingColor.b - startingColor.b) * percentage / 100.0f)));
    }

    public void setLabelColor(@NotNull Color color) {
        label.setColor(color);
    }

    public void setBarColor(@NotNull Color color) {
        indicator.setFillColor(color);
    }

    public void setHeight(float height) {
        indicator.setSize(indicator.getSize().x, height);
    }

    public void setStartingWidth(float width) {
        startingWidth = width;
    }
}
