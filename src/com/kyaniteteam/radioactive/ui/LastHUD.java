package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.window.Window;
import com.rubynaxela.kyanite.window.event.KeyEvent;
import com.rubynaxela.kyanite.window.event.KeyListener;
import com.rubynaxela.kyanite.window.event.MouseButtonEvent;
import org.jetbrains.annotations.NotNull;

public class LastHUD extends HUD implements KeyListener {

    private static final Window window = GameContext.getInstance().getWindow();
    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final DataAsset lang = assets.get("lang.en_us");
    private static final Texture
            squirrelTexture = assets.get("texture.squirrel_basic"),
            plateTexture = assets.get("texture.price_tag");

    @Override
    protected void init() {
        setBackgroundColor(new Color(40, 40, 80));

        final RectangleShape squirrel = new RectangleShape(512, 512);
        squirrel.setTexture(squirrelTexture);
        squirrel.setOrigin(Vec2.divide(squirrel.getSize(), 2));
        squirrel.setPosition(window.getSize().x * 0.5f, window.getSize().y * 0.6f);
        add(squirrel);

        final RectangleButton restartButton = new RectangleButton(Vec2.f(200, 100)) {
            @Override
            public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
                endGame(true);
            }
        };
        final RectangleButton quitButton = new RectangleButton(Vec2.f(200, 100)) {
            @Override
            public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
                endGame(false);
            }
        };

        restartButton.setTexture(plateTexture);
        restartButton.setSize(161, 102);
        restartButton.setFillColor(Colors.INDIAN_RED);
        restartButton.setOrigin(81, 51);
        restartButton.setPosition(240, 560);
        add(restartButton);
        quitButton.setTexture(plateTexture);
        quitButton.setSize(161, 102);
        quitButton.setFillColor(Colors.INDIAN_RED);
        quitButton.setOrigin(81, 51);
        quitButton.setPosition(window.getSize().x - 240, 560);
        add(quitButton);
        final Label restartLabel = new Label();
        final int characterSize = 36;
        restartLabel.setCharacterSize(characterSize);
        restartLabel.setText(lang.getString("button.play_again"));
        restartLabel.setPosition(240, 560);
        restartLabel.setAlignment(Alignment.CENTER);
        add(restartLabel);
        final Label quitLabel = new Label();
        quitLabel.setCharacterSize(characterSize);
        quitLabel.setText(lang.getString("button.quit"));
        quitLabel.setPosition(window.getSize().x - 240, 560);
        quitLabel.setAlignment(Alignment.CENTER);
        add(quitLabel);
        final Label titleLabel = new Label();
        titleLabel.setCharacterSize(characterSize);
        titleLabel.setText(lang.getString("dialogue.outro"));
        titleLabel.setPosition(window.getSize().x / 2f, 120);
        titleLabel.setAlignment(Alignment.CENTER);
        add(titleLabel);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.key == Keyboard.Key.SPACE) endGame(true);
        if (e.key == Keyboard.Key.ESCAPE) endGame(false);
    }

    private void endGame(boolean restart) {
        if (restart) {
            getContext().<Runnable>getResource("function.restart").run();
            getContext().getWindow().removeKeyListener(this);
        } else window.close();
    }
}
