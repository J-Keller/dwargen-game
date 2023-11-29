package de.jkeller.dwargen.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.jkeller.dwargen.GameEntity;
import de.jkeller.dwargen.LevelLoader;
import de.jkeller.dwargen.screens.GameScreen;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * Abstract class to set standards to all Item-types
 */
public abstract class Item extends GameEntity implements Disposable {

    public final float SIZE = 2;
    public static final float BASE_HEIGHT = 32;
    public static final float BASE_WIDTH = 32;

    final int VALUE;
    final int WEIGHT;
    String filepath;
    public Texture icon;

    public boolean isPickedUp = false;

    public Item(int value, int weight, String filepath) {
        this.VALUE = value;
        this.WEIGHT = weight;
        this.filepath = filepath;

        this.xPosMap = 60;
        this.yPosMap = 60;

        this.icon = new Texture(Gdx.files.internal("items/" + filepath));
    }

    private SpriteBatch batch = new SpriteBatch();

    public void draw() {
        batch.begin();
        batch.draw(new Texture(Gdx.files.internal("items/item_underlay.png")), xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
        batch.draw(icon, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
        batch.end();
    }

    public void drawIconAt(int xPos, int yPos) {
        batch.begin();
        batch.draw(icon, xPos, yPos, BASE_WIDTH, BASE_HEIGHT);
        batch.end();
    }

    public void pickUp() {
        //TODO: Add item to inventory
        //Checks if the player has hit the item
        if(GameScreen.player.xPosScreen >= this.xPosScreen && GameScreen.player.xPosScreen <= this.xPosScreen + SIZE * BASE_WIDTH && GameScreen.player.yPosScreen >= this.yPosScreen && GameScreen.player.yPosScreen <= this.yPosScreen + SIZE * BASE_HEIGHT) {
            isPickedUp = true;
            System.out.println("Picked UP!");
            GameScreen.player.inventory.add(this);
            GameScreen.items.remove(this);
        }

    }

    public boolean getIsOnScreen() {
        System.out.println("X: " + this.xPosMap + " Y: " + this.yPosMap);
        return !isPickedUp && Math.abs(GameScreen.player.getxPosMap() - this.xPosMap) < 30 && Math.abs(GameScreen.player.getyPosMap() - this.yPosMap) < 30;
    }

    public void setScreenCoordinates() {
        this.xPosScreen = GameScreen.player.getxPosScreen() + (this.xPosMap - GameScreen.player.xPosMap) * LevelLoader.TILESIZE;
        this.yPosScreen = GameScreen.player.getyPosScreen() + (this.yPosMap - GameScreen.player.yPosMap) * LevelLoader.TILESIZE;

    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        icon.dispose();
    }
}
