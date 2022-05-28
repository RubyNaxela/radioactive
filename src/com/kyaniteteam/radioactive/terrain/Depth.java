package com.kyaniteteam.radioactive.terrain;

import com.kyaniteteam.radioactive.GameScene;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public class Depth extends RectangleShape {
    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture depth = assets.get("texture.deep_water1.1");
    private final Window window = GameContext.getInstance().getWindow();
    private int barrelCounter = 0;

    public Depth(GameScene scene) {
        setPosition(randomSpawnPlace());
        float x = MathUtils.randomFloat(0.5f, 1);
        setSize(Vec2.f(256 * x, 256 * x));
        setOrigin(Vec2.divideFloat(getSize(), 2));
        depth.apply(this);
    }

    public Vector2f randomSpawnPlace() {
        final float margin = 150;
        float x = MathUtils.randomFloat(margin, window.getSize().x - margin);
        float y = MathUtils.randomFloat(margin, window.getSize().y - margin);
        return new Vector2f(x, y);
    }

}
