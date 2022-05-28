        package com.kyaniteteam.radioactive.ui;

        import com.rubynaxela.kyanite.game.GameContext;
        import com.rubynaxela.kyanite.game.assets.DataAsset;
        import com.rubynaxela.kyanite.game.assets.FontFace;
        import com.rubynaxela.kyanite.game.assets.Texture;
        import com.rubynaxela.kyanite.game.entities.CompoundEntity;
        import com.rubynaxela.kyanite.util.Colors;
        import org.jsfml.graphics.Color;
        import org.jsfml.graphics.ConstTexture;
        import org.jsfml.graphics.RectangleShape;
        import org.jsfml.system.Vector2f;



public class DialogBox extends CompoundEntity {
    private final Texture SquirrelBasicTexture = GameContext.getInstance().getAssetsBundle().get("texture.squirrel_basic");
    private final RectangleShape textBox;
    private final RectangleShape talkingDude;
    private final Label label;
    private int fontSize = 24, margin = 16;

    public DialogBox(){
        textBox = new RectangleShape(new Vector2f(1240, 240));
        textBox.setPosition(20, 480);
        textBox.setFillColor(Color.WHITE);
        add(textBox);

        label = new Label(true);
        label.setPosition(40, 480);
        label.setCharacterSize(fontSize);
        label.setColor(Color.WHITE);
        add(label);

        talkingDude = new RectangleShape(new Vector2f(200,200));
        talkingDude.setPosition(1080, 440);
        talkingDude.setFillColor(Color.WHITE);
        add(talkingDude);
    }
    public void setText(String text) {
        label.setText(text);
    }
    /*
    public void setTexture(Texture inTexture){
        dudeTexture = inTexture;
    }

     */


    public void hide(){
        textBox.setFillColor(Color.TRANSPARENT);
        label.remove();
        talkingDude.setFillColor(Color.TRANSPARENT);
    }
    public void show(){
        textBox.setFillColor(Color.WHITE);
        label.setColor(Color.WHITE);
        talkingDude.setFillColor(Color.WHITE);
        SquirrelBasicTexture.apply(talkingDude);
    }
}