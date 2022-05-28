package com.kyaniteteam.radioactive.entities;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.GameState;
import com.kyaniteteam.radioactive.ui.GameHUD;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoat extends CompoundEntity implements AnimatedEntity, MovingEntity, KeyListener {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture
            hullTexture = assets.get("texture.player_boat"),
            barrelTexture = assets.get("texture.barrel_top");

    private final Window window = GameContext.getInstance().getWindow();
    private final Clock clock = GameContext.getInstance().getClock();
    private final GameScene scene;
    private final GameHUD hud;
    private final GameState gameState;

    private final RectangleShape hull = hullTexture.createRectangleShape(false);
    private final List<RectangleShape> barrelSlots = new ArrayList<>(6);
    private float baseVelocity = 80;
    private float lastBarrelDroppedTime = -1;

    private boolean currentlyDropping = false;
    private float barrelDropOrderTime;

    public PlayerBoat(@NotNull GameScene scene) {
        super(Vec2.f(600, 600));
        this.scene = scene;
        this.hud = window.getHUD();
        this.gameState = GameContext.getInstance().getResource("data.game_state");
        GameContext.getInstance().putResource("data.game_state", gameState.withBarrels(5).withFuel(100));

        hull.setSize(Vec2.f(50, 87));
        hull.setPosition(Vec2.divideFloat(hull.getSize(), -2));
        add(hull);

        final float barrelSlotSize = 32f;
        for (int i = 0; i < gameState.barrels; i++) {
            final RectangleShape barrelSlot = barrelTexture.createRectangleShape(true);
            barrelSlot.setSize(Vec2.f(barrelSlotSize, barrelSlotSize));
            barrelSlot.setPosition(Vec2.f((i % 2 + 0.5f) * barrelSlotSize / 2, (i % 3 - 0.5f) * barrelSlotSize / 2));
            add(barrelSlot);
            barrelSlots.add(barrelSlot);
        }

        window.addKeyListener(this);
        hud.update();
    }

    public boolean isVisibleBy(@NotNull PatrolBoat boat) {
        for (int i = 0; i < 4; i++)
            if (boat.isPointWithinFOV(getTransform().transformPoint(hull.getPoint(i)))) return true;
        for (int i = 0; i < 4; i++) {
            final Vector2f point1 = hull.getPoint(i), point2 = hull.getPoint(i % 4),
                    middlePoint = Vec2.f((point1.x + point2.x) / 2f, (point1.y + point2.y) / 2f);
            if (boat.isPointWithinFOV(getTransform().transformPoint(middlePoint))) return true;
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    private void dropBarrel(boolean safe){
        scene.scheduleToAdd(new DroppedBarrel(scene, getPosition(), safe));
        scene.schedule(s -> ((GameScene) s).getPatrolBoats().forEach(s::bringToTop));
        scene.schedule(s -> s.bringToTop(this));
        if (gameState.barrels-- > 0) barrelSlots.get(gameState.barrels).setFillColor(Colors.TRANSPARENT);
        gameState.time = 0;
        hud.update();
        lastBarrelDroppedTime = clock.getTime().asSeconds();
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        if (((GameScene) window.getScene()).getBarrels().stream().anyMatch(b -> MathUtils.isInsideCircle(
                getPosition(), b.getPosition(), b.getToxicRadius()))) baseVelocity = 40;
        else baseVelocity = 80;
        if (Keyboard.isKeyPressed(Keyboard.Key.A)) rotate(-100 * deltaTime.asSeconds());
        if (Keyboard.isKeyPressed(Keyboard.Key.D)) rotate(100 * deltaTime.asSeconds());
        if (Keyboard.isKeyPressed(Keyboard.Key.H) &&
                gameState.barrels > 0 && clock.getTime().asSeconds() - lastBarrelDroppedTime > 2){
            if(!currentlyDropping){
                currentlyDropping = true;
                barrelDropOrderTime = elapsedTime.asSeconds();
            }
        }
        if (currentlyDropping) {
            gameState.time = (int)(Math.max(0.0f, (elapsedTime.asSeconds() - barrelDropOrderTime)*1000));
            hud.update();
            if(Keyboard.isKeyPressed(Keyboard.Key.W)){
                dropBarrel(false);
                currentlyDropping = false;
            }

            else if((elapsedTime.asSeconds() - barrelDropOrderTime) >= 2){
                dropBarrel(true);
                currentlyDropping = false;
            }
        }
    }

    @Override
    public @NotNull Vector2f getVelocity() {
        if (Keyboard.isKeyPressed(Keyboard.Key.W))
            return Vec2.multiply(MathUtils.direction(getRotation()), baseVelocity);
        else return Vector2f.ZERO;
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
    }
}
