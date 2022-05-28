package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.FontFace;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.util.Colors;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;

import java.util.Objects;

public class Label extends CompoundEntity {

    private static final FontFace font = GameContext.getInstance().getAssetsBundle().get("font.dpcomic");
    private final Text foreground = new Text(), shadow = new Text();

    public Label() {
        setColor(Colors.WHITE);
        foreground.setPosition(0, 0);
        font.apply(foreground);
        shadow.setPosition(2, 2);
        font.apply(shadow);
        add(shadow);
        add(foreground);
    }

    public void setCharacterSize(int characterSize) {
        Objects.requireNonNull(FontFace.of(foreground)).disableAntialiasing(characterSize);
        foreground.setCharacterSize(characterSize);
        shadow.setCharacterSize(characterSize);
    }

    public void setColor(Color color) {
        foreground.setColor(color);
        shadow.setColor(Colors.darker(Colors.darker(color)));
    }

    public void setText(@NotNull String text) {
        foreground.setText(text);
        shadow.setText(text);
    }

    public void setAlignment(@NotNull Text.Alignment alignment) {
        foreground.setAlignment(alignment);
        shadow.setAlignment(alignment);
    }
}
