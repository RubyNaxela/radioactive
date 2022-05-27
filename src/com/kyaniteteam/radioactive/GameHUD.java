package com.kyaniteteam.radioactive;

import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.assets.DataAsset;
import com.rubynaxela.kyanite.game.assets.FontFace;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.gui.Font;
import com.rubynaxela.kyanite.game.gui.RectangleButton;
import com.rubynaxela.kyanite.game.gui.Text;
import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.window.event.MouseButtonEvent;

public class GameHUD extends HUD {

    private final DataAsset lang = getContext().getAssetsBundle().get("lang.en_us");
    private final Text day = new Text(), barrels = new Text(), money = new Text(), time = new Text(), pauseText = new Text();
    private final RectangleButton pause = new RectangleButton(Vec2.f(60, 40)) {
        @Override
        public void mouseButtonPressed(@NotNull MouseButtonEvent event) {
            getContext().getWindow().getScene().suspend();
        }
    };

    @Override
    protected void init() {
        final int fontSize = 16, margin = 16;
        final Window window = getContext().getWindow();
        final Font font = new Font(FontFace.JETBRAINS_MONO, fontSize);

        time.setText(String.format(lang.getString("label.time"), 0));
        time.setFont(font);
        time.setPosition(margin, margin);
        time.setColor(Colors.WHITE);
        add(time);

        money.setText(String.format(lang.getString("label.money"), 0));
        money.setFont(font);
        money.setPosition(margin, margin + fontSize);
        money.setColor(Colors.WHITE);
        add(money);

        barrels.setText(String.format(lang.getString("label.barrels"), 0));
        barrels.setFont(font);
        barrels.setPosition(margin, margin + 2 * fontSize);
        barrels.setColor(Colors.WHITE);
        add(barrels);

        day.setText(String.format(lang.getString("label.day"), 0));
        day.setFont(font);
        day.setAlignment(Text.Alignment.BOTTOM_LEFT);
        day.setPosition(margin, window.getSize().y - margin);
        day.setColor(Colors.WHITE);
        add(day);

        pause.setFillColor(Colors.RED);
        pause.setOrigin(pause.getSize());
        pause.setPosition(Vec2.subtract(window.getSize(), Vec2.f(margin, margin)));
        add(pause);

        pauseText.setText(lang.getString("button.pause"));
        pauseText.setFont(font);
        pauseText.setAlignment(Text.Alignment.CENTER);
        pauseText.setColor(Colors.WHITE);
        pauseText.setPosition(Vec2.subtract(GlobalRect.from(pause.getGlobalBounds()).getCenter(), Vec2.f(0, fontSize / 3f)));
        add(pauseText);
    }

    public void update(@NotNull GameState state) {
        day.setText(String.format(lang.getString("label.day"), state.day));
        barrels.setText(String.format(lang.getString("label.barrels"), state.barrels));
        money.setText(String.format(lang.getString("label.money"), state.money));
        time.setText(String.format(lang.getString("label.time"), state.time));
    }
}
