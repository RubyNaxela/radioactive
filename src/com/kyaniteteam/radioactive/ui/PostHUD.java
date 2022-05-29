package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.GameState;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class PostHUD extends HUD implements KeyListener {

    private static final GameState gameState = GameContext.getInstance().getResource("data.game_state");
    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final DataAsset lang = assets.get("lang.en_us");
    private static final Texture
            barrelLeakedTexture = assets.get("texture.barrel_leaked"),
            barrelTexture = assets.get("texture.barrel"),
            plateTexture = assets.get("texture.price_tag");
    private final ArrayList<RectangleShape> allBarrels = new ArrayList<>();
    private final int characterSize = 36;

    @Override
    protected void init() {

        int leakedBarrels = 0;
        for (int i = 0; i < 6; i++) if (gameState.barrelStates.get(i).equals("leakyDropped")) leakedBarrels++;
        gameState.money = (int) Math.max(0.0f, gameState.startingMoney * (1.0f - 0.5f * leakedBarrels));

        int levelScore = gameState.money;
        gameState.fullScore += levelScore;

        setBackgroundColor(new Color(40, 40, 80));

        final RectangleShape priceTag = plateTexture.createRectangleShape();
        priceTag.setSize(Vec2.f(484, 306));
        priceTag.setOrigin(242, 153);
        priceTag.setPosition(Vec2.divideFloat(getContext().getWindow().getSize(), 2f));
        add(priceTag);

        for (int i = 0; i < 6; i++) {
            RectangleShape barrel;
            if (gameState.barrelStates.get(i).equals("safelyDropped")) barrel = barrelTexture.createRectangleShape();
            else if (gameState.barrelStates.get(i).equals("leakyDropped")) barrel = barrelLeakedTexture.createRectangleShape();
            else break;
            barrel.setSize(Vec2.f(characterSize * 3.0f, characterSize * 3.0f));
            barrel.setPosition(420 + i * characterSize * 2, 300 + characterSize); //TODO center the barrels, show money earned
            add(barrel);
            allBarrels.add(barrel);
        }

        final Label summryLabel = new Label();
        summryLabel.setPosition(640, 560);
        summryLabel.setCharacterSize(60);
        summryLabel.setText(String.format(lang.getString("label.summary"), levelScore, gameState.fullScore));
        summryLabel.setAlignment(Text.Alignment.CENTER);
        add(summryLabel);

        getContext().getWindow().addKeyListener(this);
        final RectangleButton nextLevelButton = new RectangleButton(Vec2.f(200, 100)) {
            @Override
            public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
                proceed();
            }
        };
        plateTexture.apply(nextLevelButton);
        nextLevelButton.setSize(Vec2.f(161, 102));
        nextLevelButton.setFillColor(Colors.INDIAN_RED);
        nextLevelButton.setOrigin(81, 51);
        nextLevelButton.setPosition(Vec2.subtract(Vec2.divideFloat(getContext().getWindow().getSize(), 2f), Vec2.f(0, 250)));
        add(nextLevelButton);

        final Label nextLevelLabel = new Label();
        nextLevelLabel.setCharacterSize(characterSize);
        nextLevelLabel.setText(lang.getString("button.next_level"));
        nextLevelLabel.setPosition(Vec2.subtract(Vec2.divideFloat(getContext().getWindow().getSize(), 2f), Vec2.f(0, 260)));
        nextLevelLabel.setAlignment(Text.Alignment.CENTER);
        add(nextLevelLabel);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.key == Keyboard.Key.SPACE) proceed();
    }

    private void proceed() {
        getContext().getWindow().removeKeyListener(this);
        getContext().<Runnable>getResource("function.next_level").run();
    }
}
