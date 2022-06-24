package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.graphics.RectangleShape;

public class FadingBar extends CompoundEntity {

    public FadingBar() {
        final RectangleShape indicator = new RectangleShape();
        indicator.setSize(300.0f, 24 * 2);
        indicator.setPosition(506, 16 * 4);
        indicator.setFillColor(new Color(150, 70, 70, 200));
        add(indicator);
    }
}
