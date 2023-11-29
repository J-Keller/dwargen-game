package de.jkeller.dwargen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.jkeller.dwargen.creatures.Creature;
import de.jkeller.dwargen.items.Item;
import de.jkeller.dwargen.screens.GameScreen;

/**
 * Created by viking on 11/6/16. \[T]/
 *
 * Class to manage all GameUI things
 */
public class GameUI implements Disposable, UI{

    public static final float STATUS_BAR_WIDTH = 200;
    public static final float STATUS_BAR_HEIGHT = 30;

    ShapeRenderer shape;

    public GameUI() {
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);
    }

    /**
     * Draws the Player's health, mana and stamina bar to the bottom of the screen
     */

    @Override
    public void drawPlayerStatusBars() {
        //player health bar
        shape.begin();
        shape.setColor(Color.BLACK);
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.rect(Gdx.graphics.getWidth()/2 - STATUS_BAR_WIDTH/2, 20, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT);
        shape.setColor(Color.RED);
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.rect(Gdx.graphics.getWidth()/2 - STATUS_BAR_WIDTH/2, 21, ((float) GameScreen.player.getHealth()/GameScreen.player.getMaxHealth()) * (STATUS_BAR_WIDTH - 1), STATUS_BAR_HEIGHT - 1);
        shape.end();

        //player stamina bar
        shape.begin();
        shape.setColor(Color.BLACK);
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.rect(Gdx.graphics.getWidth()/2 + (-STATUS_BAR_WIDTH/2 + 1.5f * STATUS_BAR_WIDTH), 20, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT);
        shape.setColor(Color.GREEN);
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.rect(Gdx.graphics.getWidth()/2 + (-STATUS_BAR_WIDTH/2 + 1.5f * STATUS_BAR_WIDTH), 21, ((float) GameScreen.player.getStamina()/GameScreen.player.getMaxStamina())* (STATUS_BAR_WIDTH - 1), STATUS_BAR_HEIGHT - 1);
        shape.end();

        //player mana bar
        shape.begin();
        shape.setColor(Color.BLACK);
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.rect(Gdx.graphics.getWidth()/2 - (STATUS_BAR_WIDTH/2 + 1.5f * STATUS_BAR_WIDTH), 20, STATUS_BAR_WIDTH, STATUS_BAR_HEIGHT);
        shape.setColor(Color.BLUE);
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.rect(Gdx.graphics.getWidth()/2 - (STATUS_BAR_WIDTH/2 + 1.5f * STATUS_BAR_WIDTH), 21, ((float) GameScreen.player.getMana()/GameScreen.player.getMaxMana()) * (STATUS_BAR_WIDTH - 1), STATUS_BAR_HEIGHT - 1);
        shape.end();
    }

    /**
     * Draws health bar over enemies in level
     */
    @Override
    public void drawEnemyHealthBars() {
        //enemy health bar
        shape.begin();
        shape.setColor(Color.BLACK);
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.rect(GameScreen.orc.getxPosScreen(), GameScreen.orc.getyPosScreen() + GameScreen.orc.SIZE * Creature.BASE_HEIGHT + 10, GameScreen.orc.SIZE * Creature.BASE_WIDTH, 10);
        shape.setColor(Color.RED);
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.rect(GameScreen.orc.getxPosScreen(), GameScreen.orc.getyPosScreen() + GameScreen.orc.SIZE * Creature.BASE_HEIGHT + 11, ((float) GameScreen.orc.getHealth()/200f) * (GameScreen.orc.SIZE * Creature.BASE_WIDTH - 1), 9);
        shape.end();

    }

    /**
     * Draws a mini map of the current level on the screen
     */

    @Override
    public void drawMiniMap() {

    }

    /**
     * Opens the Player's Inventory
     */

    @Override
    public void showInventory() {

    }

    /**
     * Opens the game menu for settings etc.
     */

    @Override
    public void showMenu() {

    }

    /**
     * Opens the map of the current level
     */

    @Override
    public void showMap() {

    }

    /**
     * Opens the quest log
     */

    @Override
    public void showQuestLog() {

    }

    /**
     * Opens Player info
     */

    @Override
    public void showStats() {

    }

    /**
     * Opens Player's skill menu
     */

    @Override
    public void showSkills() {

    }

    /**
     * Opens the bestiary, to give an overview of all seen monsters
     */

    @Override
    public void showBestiary() {

    }

    /**
     * Opens the Weapon catalogue, to give an overview of all obtained weapons
     * It separates between all weapon kinds:
     *  -Long ranged:
     *   -Bow
     *   -Slingshot
     *   -Crossbow
     *   -Throwing spear
     *
     *  -Short ranged:
     *   -Sword
     *   -Axe
     *   -Hammer
     *   -Halberd
     *   -Spear
     */

    @Override
    public void showWeaponCatalogue() {

    }

    /**
     * Opens the Armor catalogue, to give an overview of all obtained Armor pieces
     * It is separated between helms, chest armor, gloves, pants and boots
     */

    @Override
    public void showArmorCatalogue() {

    }

    /**
     * Opens the Item catalogue, to give an overview of all obtained Items
     * It differentiates between  Consumables, and Miscellaneous
     */

    @Override
    public void showItemCatalogue() {

    }

    /**
     * Draws a box around the player, where a hit is detected
     */

    @Override
    public void drawPlayerHitBox() {
        shape.begin();
        shape.setColor(Color.RED);
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.rect(GameScreen.player.getxPosScreen(), GameScreen.player.getyPosScreen(), GameScreen.player.SIZE * Creature.BASE_WIDTH, GameScreen.player.SIZE * Creature.BASE_HEIGHT);
        shape.end();
    }

    /**
     * Draws a box around the enemy, where a hit is detected
     */

    @Override
    public void drawEnemyHitBox() {
        shape.begin();
        shape.setColor(Color.RED);
        shape.set(ShapeRenderer.ShapeType.Line);
        shape.rect(GameScreen.orc.getxPosScreen(), GameScreen.orc.getyPosScreen(), GameScreen.orc.SIZE * Creature.BASE_WIDTH, GameScreen.orc.SIZE * Creature.BASE_HEIGHT);
        shape.end();
    }

    /**
     * Draws a box around the item, where a hit is detected
     */

    @Override
    public void drawItemHitBox() {
        shape.begin();
        shape.setColor(Color.RED);
        shape.set(ShapeRenderer.ShapeType.Line);
        for(Item i : GameScreen.items) {
            shape.rect(i.xPosScreen, i.yPosScreen, i.SIZE * Item.BASE_WIDTH, i.SIZE * Item.BASE_HEIGHT);
        }
        shape.end();
    }

    @Override
    public void dispose() {
        shape.dispose();
    }

}
