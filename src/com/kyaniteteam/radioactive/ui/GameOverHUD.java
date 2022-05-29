package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.Radioactive;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

public class GameOverHUD extends HUD implements KeyListener {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final DataAsset lang = assets.get("lang.en_us");
    private static final Texture
            barrelLeakedTexture = assets.get("texture.barrel_leaked"),
            plateTexture = assets.get("texture.price_tag");

    @Override
    protected void init() {
        setBackgroundColor(Radioactive.HUD_COLOR);

        getContext().getWindow().addKeyListener(this);

        final RectangleShape icon = barrelLeakedTexture.createRectangleShape();
        icon.setSize(Vec2.f(128, 128));
        icon.setOrigin(64, 64);

        final Label nextLevelLabel = new Label();
        nextLevelLabel.setCharacterSize(48);
        nextLevelLabel.setText(lang.getString("label.game_over"));
        nextLevelLabel.setPosition(Vec2.divideFloat(getContext().getWindow().getSize(), 2f));
        nextLevelLabel.setAlignment(Text.Alignment.CENTER);
        add(nextLevelLabel);

        final Label pressSpaceLabel = new Label();
        pressSpaceLabel.setCharacterSize(30);
        pressSpaceLabel.setText(lang.getString("label.press_space"));
        pressSpaceLabel.setPosition(Vec2.add(Vec2.divideFloat(getContext().getWindow().getSize(), 2f), Vec2.f(0, 128)));
        pressSpaceLabel.setAlignment(Text.Alignment.CENTER);
        add(pressSpaceLabel);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.key == Keyboard.Key.SPACE) GameContext.getInstance().restartGame();
    }
}
