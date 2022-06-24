package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.GameState;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
import com.rubynaxela.kyanite.graphics.Alignment;
import com.rubynaxela.kyanite.graphics.Color;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.input.Mouse;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.window.Window;
import com.rubynaxela.kyanite.window.event.MouseButtonEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("FieldCanBeLocal")
public class GameHUD extends HUD {

    private static final GameState gameState = GameContext.getInstance().getResource("data.game_state");
    private final DataAsset lang = getContext().getAssetsBundle().get("lang.en_us");
    private final AudioHandler audioHandler = getContext().getAudioHandler();
    private final GameState state = GameContext.getInstance().getResource("data.game_state");
    private final int fontSize = 24, margin = 16;
    private final ProgressBar fuel = new ProgressBar(lang.getString("label.fuel"), fontSize);
    private final ProgressBar barrelBar = new ProgressBar("", fontSize);
    //    private final FadingBar fadingBar = new FadingBar();
    private final BarrelCounter barrels = new BarrelCounter(fontSize);
    private final Label day = new Label(), money = new Label(), pauseText = new Label(), pausedLabel = new Label(false);
    private final RectangleShape overlay = new RectangleShape();
    private final RectangleButton pause = new RectangleButton(Vec2.f(80, 40)) {
        @Override
        public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
            if (event.button == Mouse.Button.LEFT) togglePause();
        }
    };

    DialogBox dialogueBox = new DialogBox();

    @Override
    protected void init() {
        final Window window = getContext().getWindow();

        fuel.setLabelColor(Colors.WHITE);
        fuel.setPosition(margin, margin);
        add(fuel);

        barrelBar.setLabelColor(Colors.WHITE);
        barrelBar.startingColor = new Color(27, 25, 37);
        barrelBar.endingColor = new Color(27, 25, 37);
        barrelBar.setHeight(fontSize * 2);
        barrelBar.setStartingWidth(300.0f);
        barrelBar.setPosition(500, margin);
        add(barrelBar);

        money.setText(String.format(lang.getString("label.money"), 0));
        money.setCharacterSize(fontSize);
        money.setPosition(margin, margin + fontSize);
        money.setColor(Colors.WHITE);
        add(money);

        barrels.setText(String.format(lang.getString("label.barrels"), 0));
        barrels.setPosition(0, 2 * fontSize);
        barrels.setColor(Colors.WHITE);
        add(barrels);

        day.setText(String.format(lang.getString("label.day"), gameState.currentLevel));
        day.setCharacterSize(fontSize);
        day.setAlignment(Alignment.BOTTOM_LEFT);
        day.setPosition(margin, window.getSize().y - fontSize / 2f - margin);
        day.setColor(Colors.WHITE);
        add(day);

        overlay.setSize(Vec2.f(window.getSize()));
        overlay.setFillColor(Colors.TRANSPARENT);
        add(overlay);

        pause.setFillColor(Colors.RED);
        pause.setOrigin(pause.getSize());
        pause.setPosition(window.getSize().x - margin, margin + pause.getSize().y);
        add(pause);

        pausedLabel.setText(lang.getString("label.paused"));
        pausedLabel.setCharacterSize(fontSize * 2);
        pausedLabel.setAlignment(Alignment.BOTTOM_CENTER);
        pausedLabel.setPosition(Vec2.f(Vec2.divideFloat(window.getSize(), 2)));
        pausedLabel.setColor(Colors.TRANSPARENT);
        add(pausedLabel);

        pauseText.setText(lang.getString("button.pause"));
        pauseText.setCharacterSize(fontSize);
        pauseText.setAlignment(Alignment.CENTER);
        pauseText.setColor(Colors.WHITE);
        pauseText.setPosition(Vec2.subtract(pause.getGlobalBounds().getCenter(), Vec2.f(0, fontSize / 3f)));
        add(pauseText);
    }

    public void update() {
        if (state.barrelStates == null) state.prepBarrels();
        day.setText(String.format(lang.getString("label.day"), state.day));
        barrels.setBarrelsCount(state.barrelStates);
        money.setText(String.format(lang.getString("label.money"), state.money));
        fuel.setPercentage(state.fuel);
        barrelBar.setBarColor(Colors.TRANSPARENT);
        if (getContext().getWindow().getScene() instanceof final GameScene scene) {
            barrelBar.setPercentage(100.0f - state.dropProgress);
            if (!scene.getPlayer().isCurrentlyDropping()) barrelBar.setBarColor(Colors.TRANSPARENT);
        }
    }

    public void togglePause() {
        final GameScene scene = getContext().getWindow().getScene();
        if (!scene.isSuspended()) {
            scene.suspend();
            pauseText.setText(lang.getString("button.resume"));
            overlay.setFillColor(Colors.BLACK.withOpacity(0.5f));
            pausedLabel.setColor(Colors.WHITE);
            audioHandler.pauseAllPlayingSounds();
        } else {
            scene.resume();
            pauseText.setText(lang.getString("button.pause"));
            overlay.setFillColor(Colors.TRANSPARENT);
            pausedLabel.setColor(Colors.TRANSPARENT);
            audioHandler.resumeAllPausedSounds();
        }
    }

    public void showDialog() {
        final GameScene scene = getContext().getWindow().getScene();
        if (!scene.isSuspended()) {
            scene.suspend();
            dialogueBox.setText(lang.getString("dialogue.intro"));
            dialogueBox.setLocation(new Vector2f(1080, 480));
            dialogueBox.show();
            add(dialogueBox);
        } else {
            scene.resume();
            dialogueBox.hide();
        }
    }
}
