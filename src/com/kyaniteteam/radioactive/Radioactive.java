package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.Game;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.DataAsset;

public class Radioactive extends Game {

    public static void main(String[] args) {
        Game.run(Radioactive.class, args);
    }

    @Override
    protected void preInit() {
        final AssetsBundle assets = getContext().getAssetsBundle();
        assets.register("lang.en_us", new DataAsset("src/res/en_us.json"));
    }

    @Override
    protected void init() {
        getContext().setupWindow(1280, 720, "Radioactive")
                    .setScene(new GameScene()).setHUD(new GameHUD());
    }
}
