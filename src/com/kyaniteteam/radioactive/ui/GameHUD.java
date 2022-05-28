package com.kyaniteteam.radioactive.ui;

import com.kyaniteteam.radioactive.GameScene;
import com.kyaniteteam.radioactive.GameState;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.window.event.MouseButtonEvent;

@SuppressWarnings("FieldCanBeLocal")
public class GameHUD extends HUD {

    private final DataAsset lang = getContext().getAssetsBundle().get("lang.en_us");
    private final Label day = new Label(), money = new Label(), time = new Label(), pauseText = new Label();
    private final int fontSize = 24, margin = 16;
    private final BarrelCounter barrels = new BarrelCounter(fontSize);
    private final RectangleButton pause = new RectangleButton(Vec2.f(75, 40)) {
        @Override
        public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
            final GameScene scene = getContext().getWindow().getScene();
            if (!scene.isSuspended()) {
                scene.suspend();
                pauseText.setText(lang.getString("button.resume"));
            } else {
                scene.resume();
                pauseText.setText(lang.getString("button.pause"));
            }
        }
    };

    @Override
    protected void init() {
        final Window window = getContext().getWindow();

        time.setText(String.format(lang.getString("label.time"), 0));
        time.setCharacterSize(fontSize);
        time.setPosition(margin, margin);
        time.setColor(Colors.WHITE);
        add(time);

        money.setText(String.format(lang.getString("label.money"), 0));
        money.setCharacterSize(fontSize);
        money.setPosition(margin, margin + fontSize);
        money.setColor(Colors.WHITE);
        add(money);

        barrels.setText(String.format(lang.getString("label.barrels"), 0));
        barrels.setPosition(margin, margin + 2 * fontSize);
        barrels.setColor(Colors.WHITE);
        add(barrels);

        day.setText(String.format(lang.getString("label.day"), 0));
        day.setCharacterSize(fontSize);
        day.setAlignment(Text.Alignment.BOTTOM_LEFT);
        day.setPosition(margin, window.getSize().y - fontSize / 2f - margin);
        day.setColor(Colors.WHITE);
        add(day);

        pause.setFillColor(Colors.RED);
        pause.setOrigin(pause.getSize());
        pause.setPosition(window.getSize().x - margin, margin + pause.getSize().y);
        add(pause);

        pauseText.setText(lang.getString("button.pause"));
        pauseText.setCharacterSize(fontSize);
        pauseText.setAlignment(Text.Alignment.CENTER);
        pauseText.setColor(Colors.WHITE);
        pauseText.setPosition(Vec2.subtract(GlobalRect.from(pause.getGlobalBounds()).getCenter(), Vec2.f(0, fontSize / 3f)));
        add(pauseText);
    }

    public void update() {
        final GameState state = GameContext.getInstance().getResource("data.game_state");
        day.setText(String.format(lang.getString("label.day"), state.day));
        barrels.setBarrelsCount(state.barrels);
        money.setText(String.format(lang.getString("label.money"), state.money));
        time.setText(String.format(lang.getString("label.time"), state.time));
    }
}
