package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.graphics.*;
import org.jetbrains.annotations.NotNull;

public class Label extends CompoundEntity {

    private static final Typeface font = GameContext.getInstance().getAssetsBundle().get("font.dpcomic");
    private final Text foregroundText = new Text(), shadowText = new Text();

    public Label(boolean shadow) {
        setColor(Colors.WHITE);
        foregroundText.setPosition(0, 0);
        foregroundText.setTypeface(font, false);
        if (shadow) {
            shadowText.setPosition(2, 2);
            shadowText.setTypeface(font, false);
            add(shadowText);
        }
        add(foregroundText);
    }

    public Label() {
        this(true);
    }

    public void setCharacterSize(int characterSize) {
        foregroundText.setCharacterSize(characterSize);
        shadowText.setCharacterSize(characterSize);
    }

    public void setColor(Color color) {
        foregroundText.setColor(color);
        shadowText.setColor(color.darker(0.75f));
    }

    public void setText(@NotNull String text) {
        foregroundText.setText(text);
        shadowText.setText(text);
    }

    public void setAlignment(@NotNull Alignment alignment) {
        foregroundText.setAlignment(alignment);
        shadowText.setAlignment(alignment);
    }

    public void remove() {
        foregroundText.setColor(Colors.TRANSPARENT);
        shadowText.setColor(Colors.TRANSPARENT);
    }
}
