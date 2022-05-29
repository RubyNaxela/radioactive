package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.util.Vec2;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;

public class FadingBar extends CompoundEntity {

    private final Label label = new Label();
    private final RectangleShape indicator = new RectangleShape();

    public FadingBar(){

        final GlobalRect labelBounds = GlobalRect.from(label.getGlobalBounds());
        indicator.setSize(Vec2.f(300.0f, 24*2));
        indicator.setPosition(506, 16*4);
        indicator.setFillColor(new Color(150, 70, 70, 200));
        add(indicator);
    }
}
