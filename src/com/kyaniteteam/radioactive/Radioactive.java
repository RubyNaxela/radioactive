package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.ui.GameHUD;
import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.*;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

public class Radioactive extends Game {

    final AssetsBundle assets = getContext().getAssetsBundle();

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {
        assets.register("font.dpcomic", new FontFace("src/res/fonts/dpcomic.ttf"));
        assets.register("lang.en_us", new DataAsset("src/res/lang/en_us.json"));
        assets.register("icon.barrel", new Icon("src/res/textures/barrels/barrel_clean.png"));
        assets.register("sound.astronomia", new Sound("src/res/sounds/astronomia.ogg"));
        assets.register("sound.police", new Sound("src/res/sounds/police.ogg"));
        assets.register("texture.background", new Texture("src/res/textures/terrain/background.png"));
        assets.register("texture.barrel", new Texture("src/res/textures/barrels/barrel_clean.png"));
        assets.register("texture.barrel_top", new Texture("src/res/textures/barrels/barrel_top.png"));
        assets.register("texture.barrel_top_leak", new Texture("src/res/textures/barrels/barrel_top_leak.png"));
        assets.register("texture.patrol_boat", new Texture("src/res/textures/boats/boat_control_v1.png"));
        assets.register("texture.player_boat", new Texture("src/res/textures/boats/barrel_boat.png"));
        assets.register("texture.player_boat1", new Texture("src/res/textures/boats/barrel_boat_1.png"));
        assets.register("texture.police.idle", new Texture("src/res/textures/boats/police_kalm.png"));
        assets.register("texture.police.angry1", new Texture("src/res/textures/boats/police_angry_1.png"));
        assets.register("texture.police.angry2", new Texture("src/res/textures/boats/police_angry_2.png"));
        assets.register("texture.toxic_water", new Texture("src/res/textures/terrain/toxic_waters.png"));
        assets.register("texture.water_circles", new TextureAtlas("src/res/textures/particles/water_circles.png"));
        assets.register("texture.deep_water1.1", new Texture("src/res/textures/terrain/deep_water1.1.png"));
        assets.register("texture.shallow_water_1", new Texture("src/res/textures/terrain/shallow_water_1.png"));
    }

    @Override
    protected void init() {
        getContext().putResource("data.game_state", new GameState());
        getContext().getAudioHandler().createChannel("boats");
        getContext().getAudioHandler().createChannel("music");
        final GameHUD hud = new GameHUD();
        getContext().setupWindow(1280, 720, "Radioactive")
                    .setHUD(hud)
                    .setScene(new GameScene())
                    .addKeyListener(new KeyListener() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.key == Keyboard.Key.ESCAPE) hud.togglePause();
                        }
                    });
        getContext().getWindow().setIcon(assets.<Icon>get("icon.barrel"));
    }
}
