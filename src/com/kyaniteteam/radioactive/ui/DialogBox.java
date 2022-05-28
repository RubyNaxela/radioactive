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
        textBox = new RectangleShape();
        textBox.setFillColor(Color.WHITE);
        add(textBox);

        label = new Label(true);
        label.setCharacterSize(fontSize);
        label.setColor(Color.WHITE);
        add(label);

        talkingDude = new RectangleShape(new Vector2f(200,200));
        SquirrelBasicTexture.apply(talkingDude);
        add(talkingDude);
    }
    public void setText(String text) {
        label.setText(text);
        textBox.setSize(new Vector2f(label.getGlobalBounds().width + 6 * margin, label.getGlobalBounds().height + 6 * margin));
        textBox.setFillColor(Color.WHITE);
        speechBoxTalkTexture.apply(textBox);
        //talkingDude.setPosition(new Vector2f(textBox.getPosition().x + textBox.getGlobalBounds().width - 50, textBox.getPosition().y + textBox.getGlobalBounds().height - 50));
        //GlobalRect.from(label.getGlobalBounds());
    }
    // x 1080
    public void setLocation(Vector2f location){
        talkingDude.setPosition(location);
        textBox.setPosition(new Vector2f(talkingDude.getPosition().x - textBox.getGlobalBounds().width + 2 * margin, talkingDude.getPosition().y - textBox.getGlobalBounds().height + 2 * margin));
        label.setPosition(new Vector2f(textBox.getPosition().x + 3 * margin, textBox.getPosition().y + 3 * margin));
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