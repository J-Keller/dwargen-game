package de.jkeller.dwargen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import de.jkeller.dwargen.CameraManager;
import de.jkeller.dwargen.GameStates;
import de.jkeller.dwargen.GameUI;
import de.jkeller.dwargen.LevelLoader;
import de.jkeller.dwargen.creatures.Creature;
import de.jkeller.dwargen.creatures.Monster;
import de.jkeller.dwargen.creatures.Player;
import de.jkeller.dwargen.items.Item;
import de.jkeller.dwargen.items.consumables.Food;
import de.jkeller.dwargen.items.weapons.Bow;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by viking on 11/13/16. \[T]/
 */
public class GameScreen extends AbstractScreen {

    private float stateTime;
    public static Player player;
    public static Monster orc;
    private Creature.Motion motionPlayer;
    private Creature.Motion motionMonster;
    public static Bow bow;
    GameUI ui;
    public static GameStates state = GameStates.RUNNING;
    //Fields used for Pause screen

    public static Stage pauseScreen;
    public static InventoryScreen inventoryScreen;
    CameraManager cam;
    public static Food potato;
    public static List<Item> items;

    /**
     * Method called on the start of the screen, loads stuff that is needed right at the beginning
     *  -Initializes Player
     *  -Sets Input Processor
     *  -Initializes Player Motion
     *  -Sets stateTime
     *  -Loads map
     *  -Initializes bow
     */
    @Override
    public void show() {
        inventoryScreen = new InventoryScreen();
        items = new ArrayList<Item>();
        player = new Player(2, "A", "B");
        player.xPosMap = 100;
        player.yPosMap = 100;
        orc = new Monster(2, "A", "B");
        Gdx.input.setInputProcessor(player.controls);
        motionPlayer = player.motion;
        motionMonster = orc.motion;
        stateTime = 0f;
        LevelLoader.loadMap();
        bow = new Bow(1, 2, "arrow.png");
        ui = new GameUI();
        createPauseScreen();
        inventoryScreen.createInventory();
        cam = new CameraManager();
        potato = new Food(2, 30, "potato.png", 0, 20, 0);
        items.add(potato);
    }

    /**
     * Render method that is called every frame (60 times per second)
     * 	-Clears background so that Animations, motion etc. work
     * 	-Sets updates stateTime
     * 	-Renders map
     * 	-Calls motion events like walking, and shooting
     */

    @Override
    public void render(float delta) {
        switch(state) {
            case PAUSED:
                pausedState();
                break;
            case INVENTORY:
                inventoryState();
                break;
            case RUNNING:
                runningState();
                break;
        }
    }

    /**
     * Gets executed when the game is paused
     */

    private void pausedState() {
        Gdx.input.setInputProcessor(pauseScreen);// Make the pauseScreen consume events
        pauseScreen.act();
        pauseScreen.draw();
    }

    /**
     * Gets executed when inventory is open
     */

    private void inventoryState() {
        if(!Gdx.input.getInputProcessor().equals(player.controls)) {
            Gdx.input.setInputProcessor(player.controls);
        }
        //TODO: Re-render background (map, creatures etc.)
        cam.renderMap();
        motionMonster.redraw();
        motionPlayer.redraw();
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getIsOnScreen() && !items.get(i).isPickedUp) {
                items.get(i).setScreenCoordinates();
                items.get(i).draw();
            }
        }
        inventoryScreen.renderInventory();
        if(player.hasItemSelected) {
            player.selectedItem.drawIconAt(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        }
        ui.drawPlayerStatusBars();
    }

    /**
     * Gets executed when game is running normally
     */

    private void runningState() {
        if(!Gdx.input.getInputProcessor().equals(player.controls)) {
            Gdx.input.setInputProcessor(player.controls);
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        cam.renderMap();
        cam.moveMap();
        motionPlayer.walk(stateTime);
        ui.drawPlayerStatusBars();
        ui.drawPlayerHitBox();

            /*if(bow.isShooting) {
                bow.shoot();
            }*/
        //TODO: Change removing items from list. This won't work right for bigger item lists
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getIsOnScreen() && !items.get(i).isPickedUp) {
                items.get(i).setScreenCoordinates();
                items.get(i).draw();
                ui.drawItemHitBox();
                items.get(i).pickUp();
                System.out.println(items.size());
            }
        }

        if (orc.isAlive) {
            orc.getToPlayer();
            orc.moveOnMap();
            if(orc.getIsOnScreen()) {
                orc.setScreenCoordinates();
                orc.moveOnScreen();
                motionMonster.walk(stateTime);
                ui.drawEnemyHealthBars();
                ui.drawEnemyHitBox();
            }

        }
        else spawnEnemy();

        if (bow.isShooting) {
            bow.shoot();
        }
    }

    /**
     * Spawns a new enemy when he dies
     */

    private void spawnEnemy() {
        orc = new Monster(2, "A", "B");
        motionMonster = orc.motion;
    }

    @Override
    public void dispose() {
        motionPlayer.dispose();
        if(orc.isAlive) motionMonster.dispose();
        LevelLoader.dispose();
        cam.dispose();
        ui.dispose();
        pauseScreen.dispose();
        for(InventoryScreen.InventorySlot[] sl : inventoryScreen.defaultSlots) {
            for(InventoryScreen.InventorySlot s : sl) {
                s.dispose();
            }
        }
        for(InventoryScreen.InventorySlot s : inventoryScreen.equipSlots) {
            s.dispose();
        }
        bow.dispose();
        potato.dispose();
    }

    /**
     * Creates the PauseScreen, together with input handling
     */

    private static void createPauseScreen() {
        pauseScreen = new Stage();

        createBasicSkin();
        TextButton newGameButton = new TextButton("Continue", skin); // Use the initialized skin
        newGameButton.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        newGameButton.addListener(new ClickListener(Input.Buttons.LEFT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Got it!");
                state = GameStates.RUNNING;
            }
        });
        pauseScreen.addActor(newGameButton);
        pauseScreen.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ESCAPE) {
                    state = GameStates.RUNNING;
                }
                return true;
            }
        });
    }

}
