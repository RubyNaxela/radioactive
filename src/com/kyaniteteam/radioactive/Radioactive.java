package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.ui.GameHUD;
import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.*;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jsfml.graphics.Color;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

public class Radioactive extends Game {

    public static final Color HUD_COLOR = new Color(40, 40, 80);
    private final AssetsBundle assets = getContext().getAssetsBundle();
    private GameState gameState;

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {
        assets.register("data.level.1", new DataAsset("src/res/levels/level1.json"));
        assets.register("data.level.2", new DataAsset("src/res/levels/level2.json"));
        assets.register("data.level.3", new DataAsset("src/res/levels/level3.json"));
        assets.register("font.dpcomic", new FontFace("src/res/fonts/dpcomic.ttf"));
        assets.register("lang.en_us", new DataAsset("src/res/lang/en_us.json"));
        assets.register("icon.barrel", new Icon("src/res/textures/barrels/barrel_clean.png"));
        assets.register("sound.astronomia", new Sound("src/res/sounds/astronomia.ogg"));
        assets.register("sound.police", new Sound("src/res/sounds/police.ogg"));
        assets.register("texture.background", new Texture("src/res/textures/waves/waves_shader.png"));
        assets.register("texture.barrel", new Texture("src/res/textures/barrels/barrel_clean.png"));
        assets.register("texture.barrel_leaked", new Texture("src/res/textures/barrels/barrel_leak.png"));
        assets.register("texture.barrel_check", new Texture("src/res/textures/barrels/x_for_barrel.png"));
        assets.register("texture.barrel_top", new Texture("src/res/textures/barrels/barrel_top.png"));
        assets.register("texture.barrel_top_leak", new Texture("src/res/textures/barrels/barrel_top_leak.png"));
        assets.register("texture.small_hole", new Texture("src/res/textures/terrain/small_hole.png"));
        assets.register("texture.medium_hole", new Texture("src/res/textures/terrain/medium_hole.png"));
        assets.register("texture.big_hole", new Texture("src/res/textures/terrain/big_hole.png"));
        assets.register("texture.dialogBox_narrow", new Texture("src/res/textures/speech_boxes/narratoru_boxu.png"));
        assets.register("texture.dialogBox_talking", new Texture("src/res/textures/speech_boxes/talking_boxu.png"));
        assets.register("texture.molo", new Texture("src/res/textures/molo.png"));
        assets.register("texture.patrol_boat", new Texture("src/res/textures/boats/boat_control_v1.png"));
        assets.register("texture.player_boat", new Texture("src/res/textures/boats/barrel_boat.png"));
        assets.register("texture.player_boat1", new Texture("src/res/textures/boats/barrel_boat_1.png"));
        assets.register("texture.police.idle", new Texture("src/res/textures/boats/police_kalm.png"));
        assets.register("texture.police.angry1", new Texture("src/res/textures/boats/police_angry_1.png"));
        assets.register("texture.police.angry2", new Texture("src/res/textures/boats/police_angry_2.png"));
        assets.register("texture.price_tag", new Texture("src/res/textures/rprice_tag.png"));
        assets.register("texture.shallow_water_1", new Texture("src/res/textures/terrain/shallow_water_1.png"));
        assets.register("texture.shark", new AnimatedTexture(new Texture[]{
                new Texture("src/res/textures/shark/shark_2.png"), new Texture("src/res/textures/shark/shark_1.png"),
                new Texture("src/res/textures/shark/shark_3.png"), new Texture("src/res/textures/shark/shark_1.png")
        }, 1 / 3f));
        assets.register("texture.squirrel_basic", new Texture("src/res/textures/squirrel_basic.png"));
        assets.register("texture.toxic_water.1", new Texture("src/res/textures/terrain/toxic_water_1.png"));
        assets.register("texture.toxic_water.2", new Texture("src/res/textures/terrain/toxic_water_2.png"));
        assets.register("texture.toxic_water.3", new Texture("src/res/textures/terrain/toxic_water_3.png"));
        assets.register("texture.water_circles", new TextureAtlas("src/res/textures/particles/water_circles.png"));
    }

    @Override
    protected void init() {
        gameState = new GameState();
        getContext().getAudioHandler().stopAllSounds();
        getContext().putResource("data.game_state", gameState);
        getContext().putResource("function.next_level", (Runnable) this::loadNextLevel);
        getContext().getAudioHandler().createChannel("boats");
        getContext().getAudioHandler().createChannel("music");
        getContext().setupWindow(1280, 720, "Radioactive").setIcon(assets.<Icon>get("icon.barrel"));
        getContext().getAudioHandler().playSound("sound.astronomia", "music", 100, 1, true);
        loadNextLevel();
    }

    public void loadNextLevel() {
        final GameHUD hud = new GameHUD();
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
