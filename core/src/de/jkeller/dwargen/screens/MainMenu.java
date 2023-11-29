package de.jkeller.dwargen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jkeller.dwargen.GameInit;

/**
 * Created by viking on 11/10/16. \[T]/
 */
public class MainMenu extends AbstractScreen {

    public static Stage stage;

    public static void create() {
        stage = new Stage();


        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        createBasicSkin();
        TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        newGameButton.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Got it!");
                GameInit.game.setScreen(new GameScreen());
            }
        });
        stage.addActor(newGameButton);
    }

    @Override
    public void show() {
        create();
        stage.act();
        stage.draw();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
