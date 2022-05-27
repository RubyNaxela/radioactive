package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.assets.*;

public class Radioactive extends Game {

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {
        final AssetsBundle assets = getContext().getAssetsBundle();
        assets.register("lang.en_us", new DataAsset("src/res/en_us.json"));
        assets.register("texture.barrel", new Texture("src/res/textures/barrel_clean.png"));
        assets.register("texture.barrel_top", new Texture("src/res/textures/barrel_top.png"));
        assets.register("texture.patrol_boat", new Texture("src/res/textures/boat_control_v1.png"));
        assets.register("texture.player_boat", new Texture("src/res/textures/barrel_boat.png"));
        assets.register("texture.toxic_water", new Texture("src/res/textures/toxic_waters.png"));
        assets.register("texture.water_circles", new TextureAtlas("src/res/textures/water_circles.png"));
        assets.register("background", new Texture("src/res/textures/background.png"));
    }

    @Override
    protected void init() {
        getContext().setupWindow(1280, 720, "Radioactive")
                .setHUD(new GameHUD()).setScene(new GameScene());
    }
}
