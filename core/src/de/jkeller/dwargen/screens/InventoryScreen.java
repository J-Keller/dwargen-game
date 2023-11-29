package de.jkeller.dwargen.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import de.jkeller.dwargen.items.Item;

/**
 * Created by viking on 2/14/17. \[T]/
 */
public class InventoryScreen {

    /**
     * Creates the Inventory
     * TODO: Put into GameUI class
     */

    static ShapeRenderer shape;
    public static final float INVENTORY_WIDTH = 370;
    public static final float INVENTORY_HEIGHT = 300;
    public static final int INVENTORY_ROWS = 3;
    public static final int INVENTORY_COLUMNS = 9;
    static final int SLOT_MARGIN = 8;
    public InventorySlot[][] defaultSlots = new InventorySlot[INVENTORY_ROWS][INVENTORY_COLUMNS];
    InventorySlot[] equipSlots = new InventorySlot[4];
    static SpriteBatch icon;

    public void createInventory() {
        shape = new ShapeRenderer();
        icon = new SpriteBatch();
        shape.setAutoShapeType(true);

        for(int i = 0; i < INVENTORY_ROWS; i++) {
            for(int j = 0; j < INVENTORY_COLUMNS; j++) {

                defaultSlots[i][j] = new InventorySlot(j * InventorySlot.SIDE_LENGTH + j * SLOT_MARGIN, i * InventorySlot.SIDE_LENGTH + i * SLOT_MARGIN);

            }
        }

        for(int i = 0; i < 4; i++) {

            equipSlots[i] = new InventorySlot(0, i * InventorySlot.SIDE_LENGTH + i * SLOT_MARGIN + 130);

        }
    }

    public void renderInventory() {
        shape.begin();
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.GRAY);
        shape.rect(Gdx.graphics.getWidth()/2 - INVENTORY_WIDTH /2, Gdx.graphics.getHeight()/2 - INVENTORY_HEIGHT /2, INVENTORY_WIDTH, INVENTORY_HEIGHT);
        shape.end();
        for(InventorySlot[] s : defaultSlots) {
            for(InventorySlot sl : s) {
                sl.drawSlot();
            }
        }

        for(InventorySlot s : equipSlots) {
            s.drawSlot();
        }

        //Player View
        shape.begin();
        shape.set(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.DARK_GRAY);
        shape.rect((Gdx.graphics.getWidth()/2 - INVENTORY_WIDTH/2) + 80, (Gdx.graphics.getHeight()/2 - INVENTORY_HEIGHT/2) + 140, 32*3, 48*3);
        shape.end();
        icon.begin();
        icon.draw(GameScreen.player.motion.animation.faceDown, Gdx.graphics.getWidth()/2 - INVENTORY_WIDTH /2 + 80, Gdx.graphics.getHeight()/2 - INVENTORY_HEIGHT /2 + 140, 32*3, 48*3);
        icon.end();
    }

    public int[] findFreeSlot() {
        int[] places = new int[2];
        for(int i = 0; i < INVENTORY_ROWS; i++) {
            for(int j = 0; j < INVENTORY_COLUMNS; j++) {
                if(!defaultSlots[i][j].isOccupied) {
                    places[0] = i;
                    places[1] = j;
                    return places;
                }
            }
        }
        return places;
    }

    public int[] findItem(Item item) {
        int[] places = new int[2];
        for(int i = 0; i < INVENTORY_ROWS; i++) {
            for(int j = 0; j < INVENTORY_COLUMNS; j++) {
                if(defaultSlots[i][j].heldItem.equals(item)) {
                    places[0] = i;
                    places[1] = j;
                    return places;
                }
            }
        }
        return places;
    }

    public class InventorySlot implements Disposable {

        public static final int SIDE_LENGTH = 32;

        //Fields for graphics
        public float xPos;
        public float yPos;
        ShapeRenderer slotShape;
        SpriteBatch slotIcon;

        //Fields needed for logic
        public boolean isOccupied;
        //Item in that particular slot
        public Item heldItem;
        //Quantity of this Item
        int numberOfItems;

        InventorySlot(float xPos, float yPos) {
            this.xPos = xPos + Gdx.graphics.getWidth()/2 - INVENTORY_WIDTH /2 + 8;
            this.yPos = yPos + Gdx.graphics.getHeight()/2 - INVENTORY_HEIGHT /2 + 8;
            slotShape = new ShapeRenderer();
            slotIcon = new SpriteBatch();
            slotShape.setAutoShapeType(true);
        }

        void drawSlot() {
            slotShape.begin();
            slotShape.setColor(Color.DARK_GRAY);
            slotShape.set(ShapeRenderer.ShapeType.Filled);
            slotShape.rect(xPos, yPos, 32, 32);
            slotShape.end();

            if(isOccupied) {
                slotIcon.begin();
                slotIcon.draw(heldItem.icon, xPos, yPos, 32, 32);
                slotIcon.end();
            }
        }

        boolean getIsOccupied() {
            return isOccupied;
        }

        Item getHeldItem() {
            return heldItem;
        }

        @Override
        public void dispose() {
            slotShape.dispose();
            slotIcon.dispose();
        }
    }

}
