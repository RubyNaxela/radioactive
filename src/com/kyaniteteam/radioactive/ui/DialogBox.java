package com.kyaniteteam.radioactive.ui;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.Vector2f;

public class DialogBox extends CompoundEntity {

    private static Texture speechBoxNarrTexture = GameContext.getInstance().getAssetsBundle().get("texture.dialog_box_narrow");
    private static Texture speechBoxTalkTexture = GameContext.getInstance().getAssetsBundle().get("texture.dialog_box_talking");
    private final Texture squirrelBasicTexture = GameContext.getInstance().getAssetsBundle().get("texture.squirrel_basic");
    private final RectangleShape textBox;
    private final RectangleShape talkingDude;
    private final Label label;
    private int fontSize = 24, margin = 16;

    public DialogBox() {
        textBox = new RectangleShape();
        textBox.setFillColor(Colors.WHITE);
        add(textBox);

        label = new Label(true);
        label.setCharacterSize(fontSize);
        label.setColor(Colors.WHITE);
        add(label);

        talkingDude = new RectangleShape(new Vector2f(200, 200));
        talkingDude.setTexture(squirrelBasicTexture);
        add(talkingDude);
    }

    public void setText(String text) {
        label.setText(text);
        textBox.setSize(new Vector2f(label.getGlobalBounds().width + 6 * margin, label.getGlobalBounds().height + 6 * margin));
        textBox.setFillColor(Colors.WHITE);
        textBox.setTexture(speechBoxTalkTexture);
    }

    // x 1080
    public void setLocation(Vector2f location) {
        talkingDude.setPosition(location);
        textBox.setPosition(new Vector2f(talkingDude.getPosition().x - textBox.getGlobalBounds().width + 2 * margin,
                                         talkingDude.getPosition().y - textBox.getGlobalBounds().height + 2 * margin));
        label.setPosition(new Vector2f(textBox.getPosition().x + 3 * margin, textBox.getPosition().y + 3 * margin));
    }

    public void hide() {
        textBox.setFillColor(Colors.TRANSPARENT);
        label.remove();
        talkingDude.setFillColor(Colors.TRANSPARENT);
    }

    public void show() {
        textBox.setFillColor(Colors.WHITE);
        label.setColor(Colors.WHITE);
        talkingDude.setFillColor(Colors.WHITE);
        talkingDude.setTexture(squirrelBasicTexture);
    }
}