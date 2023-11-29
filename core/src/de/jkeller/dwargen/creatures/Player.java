package de.jkeller.dwargen.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import de.jkeller.dwargen.GameStates;
import de.jkeller.dwargen.items.Item;
import de.jkeller.dwargen.items.consumables.Food;
import de.jkeller.dwargen.screens.GameScreen;
import de.jkeller.dwargen.screens.InventoryScreen;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * The Player class used for the main character of the game, who is played by the player
 */
public class Player extends Creature implements MainCharacter {

    public Controls controls;
    public Motion motion;
    public Inventory inventory;
    private Vector2 direction;

    //Used in controls //TODO: Find a better place for these two fields
    public boolean hasItemSelected = false;
    public Item selectedItem;

    /**
     * Constructor initialized controls and motion
     * @param size
     * Size of the player
     * @param name
     * Name of the player
     * @param race
     * Race of the player
     */
    public Player(int size, String name, String race) {
        super(size, name, race);
        controls = new Controls();
        motion = new Creature.Motion("nakedchar.png");
        inventory = new Creature.Inventory();
        setHealth(getMaxHealth());
        setMana(getMaxMana());
        setStamina(getMaxStamina());
        direction = new Vector2();
    }

    /**
     * Gets called when the player accumulated enough exp to level up.
     * Increases a few player stats.
     * @param strengthUp
     * The amount of strength that is increased
     * @param speedUp
     * The amount of speed that is increased
     * @param intelligenceUp
     * The amount of intelligence that is increased
     * @param luckUp
     * The amount of luck that is increased
     */
    @Override
    public void levelUp(int strengthUp, int speedUp, int intelligenceUp, int luckUp) {
        super.setStrength(super.getStrength() + strengthUp);
        super.setSpeed(super.getSpeed() + speedUp);
        super.setIntelligence(super.getIntelligence() + intelligenceUp);
        super.setLuck(super.getLuck() + luckUp);
        super.setLvl(super.getLvl() + 1);
    }

    @Override
    public void changeStat(Stats stat, int amount) {
        switch (stat) {
            case HEALTH:
                super.setHealth(super.getHealth() + amount);
                break;

            case STAMINA:
                super.setStamina(super.getStamina() + amount);
                break;

            case MANA:
                super.setMana(super.getMana() + amount);
                break;

            default:
                System.out.println("No valid Stat!");
                break;
        }
    }

    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void dispose() {

    }

    public enum Stats {
        HEALTH,
        STAMINA,
        MANA
    }

    /**
     * Manages the game Controls, used as global InputProcessor
     */
    private class Controls implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            switch(keycode) {
                case Input.Keys.W:
                    //Walk Up
                    Player.super.setVelocity(Player.super.getVelocity().add(0, 1));
                    break;
                case Input.Keys.A:
                    //Walk Left
                    Player.super.setVelocity(Player.super.getVelocity().add(-1, 0));
                    break;
                case Input.Keys.S:
                    //Walk Down
                    Player.super.setVelocity(Player.super.getVelocity().add(0, -1));
                    break;
                case Input.Keys.D:
                    //Walk Right
                    Player.super.setVelocity(Player.super.getVelocity().add(1, 0));
                    break;
                case Input.Keys.I:
                    GameScreen.state = GameStates.INVENTORY;
                    break;
                case Input.Keys.ESCAPE:
                    if(GameScreen.state == GameStates.RUNNING) GameScreen.state = GameStates.PAUSED;
                    else GameScreen.state = GameStates.RUNNING;
                    break;
                case Input.Buttons.LEFT:
                    //TODO: Add automatic detection where mouse pointer is to determine the action to do (attack, talk, pick up, etc...)

                    break;
                case Input.Buttons.RIGHT:
                    //TODO: Maybe use for inspection of items and stuff
                    break;
            }

            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch(keycode) {
                case Input.Keys.W:
                    Player.super.setVelocity(Player.super.getVelocity().sub(0, Player.super.getVelocity().y));
                    break;
                case Input.Keys.A:
                    Player.super.setVelocity(Player.super.getVelocity().sub(Player.super.getVelocity().x, 0));
                    break;
                case Input.Keys.S:
                    Player.super.setVelocity(Player.super.getVelocity().sub(0, Player.super.getVelocity().y));
                    break;
                case Input.Keys.D:
                    Player.super.setVelocity(Player.super.getVelocity().sub(Player.super.getVelocity().x, 0));
                    break;
                case Input.Buttons.LEFT:

                    break;
                case Input.Buttons.RIGHT:

                    break;
            }

            return true;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {

            if(hasItemSelected) {
                selectedItem.drawIconAt(screenX, Gdx.graphics.getHeight() - screenY);
            }

            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            //Shoots an arrow
            if(button == Input.Buttons.LEFT) {
                if(GameScreen.state == GameStates.RUNNING) {
                    //if (!GameScreen.bow.isShooting) {
                        GameScreen.bow.createArrow(GameScreen.player, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                        GameScreen.bow.isShooting = true;
                        System.out.println(Gdx.input.getX());
                        System.out.println(Gdx.input.getY());
                    //}
                }
                if(GameScreen.state == GameStates.INVENTORY) {
                    for (int i = 0; i < InventoryScreen.INVENTORY_ROWS; i++) {
                        for (int j = 0; j < InventoryScreen.INVENTORY_COLUMNS; j++) {
                            if (screenX >= GameScreen.inventoryScreen.defaultSlots[i][j].xPos && screenX <= GameScreen.inventoryScreen.defaultSlots[i][j].xPos + 32 &&
                                    Gdx.graphics.getHeight() - screenY >= GameScreen.inventoryScreen.defaultSlots[i][j].yPos && Gdx.graphics.getHeight() - screenY <= GameScreen.inventoryScreen.defaultSlots[i][j].yPos + 32) {
                                System.out.println("Row: " + i + " Col: " + j);
                                if(!hasItemSelected) {
                                    if (GameScreen.inventoryScreen.defaultSlots[i][j].isOccupied) {
                                        //TODO: Do something with the item
                                        hasItemSelected = true;
                                        selectedItem = GameScreen.inventoryScreen.defaultSlots[i][j].heldItem;
                                        GameScreen.inventoryScreen.defaultSlots[i][j].isOccupied = false;
                                        GameScreen.inventoryScreen.defaultSlots[i][j].heldItem = null;
                                    }
                                }
                                else {
                                    if (!GameScreen.inventoryScreen.defaultSlots[i][j].isOccupied) {
                                        //TODO: Do something with the item
                                        GameScreen.inventoryScreen.defaultSlots[i][j].isOccupied = true;
                                        GameScreen.inventoryScreen.defaultSlots[i][j].heldItem = selectedItem;
                                        hasItemSelected = false;
                                        selectedItem = null;
                                    }
                                }
                            }
                        }
                    }
                    if(hasItemSelected) {
                        if(screenX >= ((Gdx.graphics.getWidth()/2 - InventoryScreen.INVENTORY_WIDTH/2) + 80) &&
                                screenX <= ((Gdx.graphics.getWidth()/2 - InventoryScreen.INVENTORY_WIDTH/2) + (80 + 32*3)) &&
                                (Gdx.graphics.getHeight() - screenY) >= ((Gdx.graphics.getHeight()/2 - InventoryScreen.INVENTORY_HEIGHT /2) + 140) &&
                                (Gdx.graphics.getHeight() - screenY <= (Gdx.graphics.getHeight()/2 + InventoryScreen.INVENTORY_HEIGHT /2) + (140 + 48*3))) {

                            if(selectedItem instanceof Food) {
                                ((Food) selectedItem).use();
                                selectedItem = null;
                                hasItemSelected = false;
                            }
                        }
                    }
                }
            }
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }
    }

}
