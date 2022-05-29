package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.GameState;
import com.kyaniteteam.radioactive.SceneLoader;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
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
public class PostHUD extends HUD {

    private static final GameState gameState = GameContext.getInstance().getResource("data.game_state");
    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture barrelTexture = assets.get("texture.barrel");
    private static final Texture barrelLeakedTexture = assets.get("texture.barrel_leaked");
    private final ArrayList<RectangleShape> allBarrels = new ArrayList<>();
    private final int characterSize = 36;

    @Override
    protected void init() {

        setBackgroundColor(new Color(40, 40, 80));

        final RectangleShape priceTag = assets.<Texture>get("texture.price_tag").createRectangleShape();
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
            barrel.setPosition(420+i * characterSize * 2, 300+characterSize); //TODO center the barrels, show money earned
            add(barrel);
            allBarrels.add(barrel);
        }

        final RectangleButton nextLevelButton = new RectangleButton(Vec2.f(200, 100)) {
            @Override
            public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
                getContext().<Runnable>getResource("function.next_level").run();
            }
        };
        nextLevelButton.setOrigin(100, 500);
        nextLevelButton.setPosition(Vec2.add(Vec2.divideFloat(getContext().getWindow().getSize(), 2f), Vec2.f(0, 200)));
        add(nextLevelButton);
    }

    public void update() {

    }
}
