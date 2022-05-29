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
    private final Text foregroundText = new Text(), shadowText = new Text();

    public Label(boolean shadow) {
        setColor(Colors.WHITE);
        foregroundText.setPosition(0, 0);
        font.apply(foregroundText);
        if (shadow) {
            shadowText.setPosition(2, 2);
            font.apply(shadowText);
            add(shadowText);
        }
        add(foregroundText);
    }

    public Label() {
        this(true);
    }

    public void setCharacterSize(int characterSize) {
        Objects.requireNonNull(FontFace.of(foregroundText)).disableAntialiasing(characterSize);
        foregroundText.setCharacterSize(characterSize);
        shadowText.setCharacterSize(characterSize);
    }

    public void setColor(Color color) {
        foregroundText.setColor(color);
        shadowText.setColor(Colors.darker(Colors.darker(color)));
    }

    public void setText(@NotNull String text) {
        foregroundText.setText(text);
        shadowText.setText(text);
    }

    public void setAlignment(@NotNull Text.Alignment alignment) {
        foregroundText.setAlignment(alignment);
        shadowText.setAlignment(alignment);
    }
    public void remove(){
        foregroundText.setColor(Color.TRANSPARENT);
        shadowText.setColor(Color.TRANSPARENT);
    }
}
