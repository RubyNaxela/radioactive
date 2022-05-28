package com.kyaniteteam.radioactive;

import com.kyaniteteam.radioactive.ui.GameHUD;
import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.*;

public class Radioactive extends Game {

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {
        final AssetsBundle assets = getContext().getAssetsBundle();
        assets.register("font.dpcomic", new FontFace("src/res/fonts/dpcomic.ttf"));
        assets.register("lang.en_us", new DataAsset("src/res/lang/en_us.json"));
        assets.register("texture.background", new Texture("src/res/textures/terrain/background.png"));
        assets.register("texture.barrel", new Texture("src/res/textures/barrels/barrel_clean.png"));
        assets.register("texture.barrel_top", new Texture("src/res/textures/barrels/barrel_top_leak.png"));
        assets.register("texture.patrol_boat", new Texture("src/res/textures/boats/boat_control_v1.png"));
        assets.register("texture.player_boat", new Texture("src/res/textures/boats/barrel_boat.png"));
        assets.register("texture.toxic_water", new Texture("src/res/textures/terrain/toxic_waters.png"));
        assets.register("texture.water_circles", new TextureAtlas("src/res/textures/particles/water_circles.png"));
    }

    @Override
    protected void init() {
        getContext().setupWindow(1280, 720, "Radioactive")
                    .setHUD(new GameHUD()).setScene(new GameScene());
    }
}
