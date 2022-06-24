package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.ui.GameHUD;
import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Icon;
import com.rubynaxela.kyanite.game.assets.Sound;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.window.event.KeyEvent;
import com.rubynaxela.kyanite.window.event.KeyListener;

public class Radioactive extends Game {

    public static final Color HUD_COLOR = new Color(40, 40, 80);
    private final AssetsBundle assets = getContext().getAssetsBundle();
    private GameState gameState = new GameState();

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {
        assets.registerFromIndex("assets/index.json");
    }

    @Override
    protected void init() {
        gameState = new GameState();
        getContext().getAudioHandler().stopAllSounds();
        getContext().putResource("data.game_state", gameState);
        getContext().putResource("function.next_level", (Runnable) this::loadNextLevel);
        getContext().putResource("function.restart", (Runnable) () -> loadNextLevel(true));
        getContext().getAudioHandler().createChannel("boats");
        getContext().getAudioHandler().createChannel("music");
        getContext().setupWindow(1280, 720, "Radioactive").setIcon(assets.<Icon>get("icon.barrel"));
        getContext().getAudioHandler().playSound("sound.astronomia", "music", 100, 1, true);
        loadNextLevel();
    }

    public void loadNextLevel() {
        loadNextLevel(false);
    }

    public void loadNextLevel(boolean resetGameState) {
        getContext().getAudioHandler().stopAllSounds("boats");
        if (resetGameState) gameState = new GameState();
        final GameHUD hud = new GameHUD();
        gameState.day = gameState.currentLevel;
        hud.update();
        getContext().getWindow().setHUD(hud)
                    .setScene(new GameScene(assets.<DataAsset>get("data.level." + gameState.currentLevel++)
                                                  .convertTo(SceneLoader.SceneData.class)))
                    .addKeyListener(new KeyListener() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.key == Keyboard.Key.ESCAPE) hud.togglePause();
                            if (e.key == Keyboard.Key.L) hud.showDialog();
                        }
                    });
    }
}
