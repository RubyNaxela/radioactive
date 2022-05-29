package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.GameState;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;

import java.util.ArrayList;
import java.util.List;

public class BarrelCounter extends CompoundEntity {

    private static final GameState gameState = GameContext.getInstance().getResource("data.game_state");
    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture barrelTexture = assets.get("texture.barrel");
    private static final Texture barrelLeakedTexture = assets.get("texture.barrel_leaked");
    private static final Texture checkTexture = assets.get("texture.barrel_check");

    private final Label label = new Label();
    private final List<RectangleShape> barrels = new ArrayList<>(6);
    private final List<RectangleShape> leakedBarrels = new ArrayList<>(6);
    private final List<RectangleShape> checks = new ArrayList<>(6);

    public BarrelCounter(int characterSize) {

        for (int i = 0; i < 6; i++) {

            final RectangleShape barrel = barrelTexture.createRectangleShape();
            barrel.setSize(Vec2.f(characterSize * 3.0f, characterSize * 3.0f));
            barrel.setPosition(i * characterSize * 2, characterSize);
            add(barrel);
            barrels.add(barrel);

            final RectangleShape leakedBarrel = barrelLeakedTexture.createRectangleShape();
            leakedBarrel.setSize(Vec2.f(characterSize * 3.0f, characterSize * 3.0f));
            leakedBarrel.setPosition(i * characterSize * 2, characterSize);
            add(leakedBarrel);
            leakedBarrels.add(leakedBarrel);

            final RectangleShape check = checkTexture.createRectangleShape();
            check.setSize(Vec2.f(characterSize * 3.0f, characterSize * 3.0f));
            check.setPosition(i * characterSize * 2, characterSize);
            add(check);
            checks.add(check);
        }
    }

    public void setBarrelsCount(@NotNull ArrayList<String> barrelStates) {
        for (int i = 0; i < 6; i++) {
            barrels.get(i).setFillColor(barrelStates.get(i).equals("safelyDropped") ||
                    barrelStates.get(i).equals("ready")
                                        ? Colors.WHITE : Colors.TRANSPARENT);
            leakedBarrels.get(i).setFillColor(barrelStates.get(i).equals("leakyDropped")
                                        ? Colors.WHITE : Colors.TRANSPARENT);
            checks.get(i).setFillColor(barrelStates.get(i).equals("leakyDropped") ||
                                        barrelStates.get(i).equals("safelyDropped")
                                        ? Colors.WHITE : Colors.TRANSPARENT);
        }
        //for (int i = 0; i < 6; i++)
        //    System.out.println(barrelStates.get(i));
    }

    public void setText(@NotNull String text) {
        label.setText(text);
    }

    public void setColor(@NotNull Color color) {
        label.setColor(color);
    }
}
