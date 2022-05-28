        package com.kyaniteteam.radioactive.ui;

        import com.rubynaxela.kyanite.game.GameContext;
        import com.rubynaxela.kyanite.game.assets.DataAsset;
        import com.rubynaxela.kyanite.game.assets.FontFace;
        import com.rubynaxela.kyanite.game.assets.Texture;
        import com.rubynaxela.kyanite.game.entities.CompoundEntity;
        import com.rubynaxela.kyanite.game.entities.GlobalRect;
        import com.rubynaxela.kyanite.util.Colors;
        import org.jsfml.graphics.Color;
        import org.jsfml.graphics.ConstTexture;
        import org.jsfml.graphics.RectangleShape;
        import org.jsfml.system.Vector2f;



public class DialogBox extends CompoundEntity {
    private final Texture SquirrelBasicTexture = GameContext.getInstance().getAssetsBundle().get("texture.squirrel_basic");

    private static Texture speechBoxNarrTexture = GameContext.getInstance().getAssetsBundle().get("texture.dialogBox_narrow");
    private static Texture speechBoxTalkTexture = GameContext.getInstance().getAssetsBundle().get("texture.dialogBox_talking");
    private final RectangleShape textBox;
    private final RectangleShape talkingDude;
    private final Label label;
    private int fontSize = 24, margin = 16;

    public DialogBox(){
        textBox = new RectangleShape(new Vector2f(1240, 240));
        textBox.setPosition(40, 480);
        textBox.setFillColor(Color.WHITE);
        //textBox.setOrigin(new Vector2f(margin, 0));
        add(textBox);

        label = new Label(true);
        label.setPosition(40 + margin, 480 + margin);
        label.setCharacterSize(fontSize);
        label.setColor(Color.WHITE);
        //label.setOrigin(new Vector2f(-margin, -0));
        add(label);

        talkingDude = new RectangleShape();
        talkingDude.setPosition(40, 480);
        talkingDude.setFillColor(Color.WHITE);
        add(talkingDude);
    }
    public void setText(String text) {
        label.setText(text);
        textBox.setSize(new Vector2f(label.getGlobalBounds().width + 4 * margin, label.getGlobalBounds().height + 4 * margin));
        textBox.setFillColor(Color.WHITE);
        speechBoxTalkTexture.apply(textBox);
        //GlobalRect.from(label.getGlobalBounds());

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