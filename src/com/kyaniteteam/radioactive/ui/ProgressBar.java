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

    public ProgressBar(@NotNull String text, int characterSize) {

        label.setCharacterSize(characterSize);
        label.setText(text);
        add(label);

        final GlobalRect labelBounds = GlobalRect.from(label.getGlobalBounds());
        indicator.setSize(Vec2.f(100, characterSize));
        indicator.setPosition(labelBounds.right, labelBounds.top);
        add(indicator);
    }

    public void setPercentage(float percentage) {
        indicator.setSize(Vec2.f(percentage, indicator.getSize().y));
    }

    public void setLabelColor(@NotNull Color color) {
        label.setColor(color);
    }

    public void setBarColor(@NotNull Color color) {
        indicator.setFillColor(color);
    }
}
