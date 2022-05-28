package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.GameState;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.window.Mouse;
import org.jsfml.window.event.MouseButtonEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class PostHUD extends HUD {
    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture barrelTexture = assets.get("texture.barrel");
    private static final Texture barrelLeakedTexture = assets.get("texture.barrel_leaked");
    private final GameState state = GameContext.getInstance().getResource("data.game_state");
    private final int characterSize = 36;
    private ArrayList<RectangleShape> allBarrels;

    @Override
    protected void init() {
        allBarrels = new ArrayList<RectangleShape>();
        for (int i = 0; i < 5; i++){
            RectangleShape barrel;
            if(state.barrelStates.get(i).equals("safelyDropped")) barrel = barrelTexture.createRectangleShape();
            else if(state.barrelStates.get(i).equals("leakyDropped")) barrel = barrelLeakedTexture.createRectangleShape();
            else break;
            barrel.setSize(Vec2.f(characterSize * 3.0f, characterSize * 3.0f));
            barrel.setPosition(i * characterSize * 2, characterSize);
            add(barrel);
            allBarrels.add(barrel);
        }
    }

    public void update(){

    }
}
