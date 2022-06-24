package com.kyaniteteam.radioactive.entities.boats;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.GameState;
import com.kyaniteteam.radioactive.entities.DroppedBarrel;
import com.kyaniteteam.radioactive.particles.WavesAfterBoats;
import com.kyaniteteam.radioactive.terrain.Depth;
import com.kyaniteteam.radioactive.ui.GameHUD;
import com.kyaniteteam.radioactive.ui.GameOverHUD;
import com.rubynaxela.kyanite.data.Pointer;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.graphics.AnimatedTexture;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.input.Keyboard;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.system.ConstClock;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoat extends CompoundEntity implements AnimatedEntity, MovingEntity {

    private static final AssetsBundle assets = GameContext.getInstance().getAssetsBundle();
    private static final Texture
            hullTexture = assets.get("texture.player_boat"),
            hullTexture1 = assets.get("texture.player_boat1"),
            barrelTexture = assets.get("texture.barrel_top");
    private static final AnimatedTexture animatedTexture = new AnimatedTexture(new Texture[]{hullTexture, hullTexture1}, 0.2f);

    private final Window window = GameContext.getInstance().getWindow();
    private final ConstClock clock = GameContext.getInstance().getClock();
    private final GameScene scene;
    private final GameHUD hud;
    private final GameState gameState = GameContext.getInstance().getResource("data.game_state");

    private final RectangleShape hull;
    private final List<RectangleShape> barrelSlots = new ArrayList<>(6);
    public float lastBrokenFocusLength = 0.0f;
    private int waveCycle = 0;
    private float baseVelocity = 80;
    private float lastBarrelDroppedTime = -1;

    private boolean currentlyDropping = false, movingTexture = false;
    private float barrelDropOrderTime;

    public PlayerBoat(@NotNull GameScene scene) {
        super(Vec2.f(600, 600));
        this.scene = scene;
        this.hud = window.getHUD();

        hull = new RectangleShape(50, 87);
        hull.setPosition(Vec2.divide(hull.getSize(), -2));
        hull.setTexture(animatedTexture);
        hull.freezeAnimatedTexture();
        add(hull);

        final float barrelSlotSize = 32f;
        for (int i = 0; i < gameState.barrels; i++) {
            final RectangleShape barrelSlot = barrelTexture.createRectangleShape(true);
            barrelSlot.setSize(barrelSlotSize, barrelSlotSize);
            barrelSlot.setPosition((i % 2 + 0.5f) * barrelSlotSize / 2, (i % 3 - 0.5f) * barrelSlotSize / 2);
            add(barrelSlot);
            barrelSlots.add(barrelSlot);
        }

        hud.update();
    }

    public boolean isVisibleBy(@NotNull EnemyBoat boat) {
        for (int i = 0; i < 4; i++)
            if (boat.isPointWithinFOV(getTransform().transformPoint(hull.getPoint(i)))) return true;
        for (int i = 0; i < 4; i++) {
            final Vector2f point1 = hull.getPoint(i), point2 = hull.getPoint(i % 4),
                    middlePoint = Vec2.f((point1.x + point2.x) / 2f, (point1.y + point2.y) / 2f);
            if (boat.isPointWithinFOV(getTransform().transformPoint(middlePoint))) return true;
        }
        return false;
    }

    public boolean touchingAnyBoat() {
        final Pointer<Boolean> intersection = new Pointer<>(false);
        scene.getEnemyBoats().forEach(b -> {
            if (MathUtils.isInsideCircle(getPosition(), b.getPosition(), b.getSize().x / 2 + hull.getSize().x / 2))
                intersection.value = true;
        });
        return intersection.value;
    }

    private void dropBarrel(boolean safe) {
        if (!safe && MathUtils.probability(0.5f)) safe = true;
        scene.scheduleToAdd(new DroppedBarrel(scene, getPosition(), safe));
        scene.schedule(s -> ((GameScene) s).getEnemyBoats().forEach(s::bringToTop));
        scene.schedule(s -> s.bringToTop(this));
        gameState.barrels--;
        if (gameState.barrels >= 0) barrelSlots.get(gameState.barrels).setFillColor(Colors.TRANSPARENT);
        if (safe) {
            gameState.barrelStates.set(gameState.barrels, "safelyDropped");
        } else {
            gameState.money = Math.max(0, (int) (gameState.money - gameState.startingMoney * 0.5f));
            gameState.barrelStates.set(gameState.barrels, "leakyDropped");
        }
        gameState.dropProgress = 0.0f;
        hud.update();
        lastBarrelDroppedTime = clock.getTime().asSeconds();
    }

    private void checkForDefeat() {
        boolean caughtDefeat = touchingAnyBoat(), depthDefeat = false;
        if (gameState.barrels > 0 && gameState.fuel <= 0) {
            for (final var d : scene.getDepths())
                if ((d.isPlayerInside() && d.ifFull()) || !d.isPlayerInside()) depthDefeat = true;
        }
        if (caughtDefeat || depthDefeat) {
            scene.suspend();
            scene.getContext().getWindow().setHUD(new GameOverHUD());
        }
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        boolean rotating = false;
        checkForDefeat();
        if (((GameScene) window.getScene()).getBarrels().stream().anyMatch(b -> MathUtils.isInsideCircle(
                getPosition(), b.getPosition(), b.getToxicRadius()))) baseVelocity = 40;
        else baseVelocity = 80;
        if (Keyboard.isKeyPressed(Keyboard.Key.A) && gameState.fuel > 0) {
            rotate(-100 * deltaTime.asSeconds());
            rotating = true;
        }
        if (Keyboard.isKeyPressed(Keyboard.Key.D) && gameState.fuel > 0) {
            rotate(100 * deltaTime.asSeconds());
            rotating = true;
        }
        final Depth playerDepth = scene.getDepths().stream().filter(Depth::isPlayerInside).findFirst().orElse(null);
        if (Keyboard.isKeyPressed(Keyboard.Key.SPACE) && playerDepth != null && !playerDepth.ifFull() &&
            gameState.barrels > 0 && clock.getTime().asSeconds() - lastBarrelDroppedTime > 2) {
            if (!currentlyDropping) {
                playerDepth.addBarrel();
                currentlyDropping = true;
                barrelDropOrderTime = elapsedTime.asSeconds();
            }
        }

        if (currentlyDropping) {
            gameState.dropProgress = Math.min(100.0f, (elapsedTime.asSeconds() - barrelDropOrderTime) / 2 * 100);
            hud.update();
            if (Keyboard.isKeyPressed(Keyboard.Key.W)) {
                lastBrokenFocusLength = (elapsedTime.asSeconds() - barrelDropOrderTime) / 2 * 100;
                currentlyDropping = false;
                dropBarrel(false);
            } else if ((elapsedTime.asSeconds() - barrelDropOrderTime) >= 2) {
                currentlyDropping = false;
                dropBarrel(true);
            }
        }

        if (!getVelocity().equals(Vector2f.zero()) || rotating) {
            if (!movingTexture) hull.resumeAnimatedTexture();
            if (waveCycle++ == 10) {
                waveCycle = 0;
                final WavesAfterBoats waves = new WavesAfterBoats(
                        scene, Vec2.add(getPosition(), Vec2.multiply(MathUtils.direction(getRotation()), -40)));
                waves.setRotation(getRotation());
                scene.scheduleToAdd(waves);
                scene.schedule(s -> s.bringToTop(this));
            }
            gameState.fuel -= deltaTime.asSeconds();
            hud.update();
            movingTexture = true;
        } else {
            if (movingTexture) hull.freezeAnimatedTexture();
            movingTexture = false;
        }
    }

    @Override
    public @NotNull Vector2f getVelocity() {
        if (Keyboard.isKeyPressed(Keyboard.Key.W) && gameState.fuel > 0)
            return Vec2.multiply(MathUtils.direction(getRotation()), baseVelocity);
        else return Vector2f.zero();
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
    }

    public boolean isCurrentlyDropping() {
        return currentlyDropping;
    }
}
