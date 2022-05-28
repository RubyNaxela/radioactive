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
        indicator.setPosition(labelBounds.right + 4, labelBounds.top - 2);
        setBarColor(new Color(220, 200, 20));
        add(indicator);
    }

    public void setPercentage(float percentage) {
        indicator.setSize(Vec2.f(percentage, indicator.getSize().y));
        setBarColor(new Color((int)(200 + (Math.max(0.0f, percentage - 20.0f) * 50.0f)/100.0f), (int)(5 + (Math.max(0.0f, percentage - 20.0f) * 200.0f)/100.0f), 20));
    }

    public void setLabelColor(@NotNull Color color) {
        label.setColor(color);
    }

    public void setBarColor(@NotNull Color color) {
        indicator.setFillColor(color);
    }
}
