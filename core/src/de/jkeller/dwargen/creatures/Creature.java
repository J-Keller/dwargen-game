package de.jkeller.dwargen.creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jkeller.dwargen.GameEntity;
import de.jkeller.dwargen.LevelLoader;
import de.jkeller.dwargen.items.Equipable;
import de.jkeller.dwargen.items.Item;
import de.jkeller.dwargen.screens.GameScreen;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * Superclass for all Creature-types setting standards that apply for all subclasses.
 * All creature Stats, Animations ans Inventories are managed here
 */
public abstract class Creature extends GameEntity {

    public static final float BASE_WIDTH = 32;
    public static final float BASE_HEIGHT = 48;

    //live stats
    private int health;
    private int mana;
    private int stamina;

    //level based cap for live stats
    private int maxHealth = 200;
    private int maxMana = 100;
    private int maxStamina = 100;

    //level based attributes
    private int strength;
    private int speed = 2;
    private int intelligence;
    private int luck;

    //race based attributes, used for detection purposes
    private int vision;
    private int hearing;

    //equipment based attributes
    private int dmg;
    private int armor;

    //Level increases when enough xp have been earned to gain a level up
    private int lvl;
    //Will increase when quests are finished, monsters are slain etc
    private int exp;

    //Velocities in x and y direction to determine walking direction
    private Vector2 velocity = new Vector2();

    //Name of the creature
    private final String NAME;
    //Race of the creature which determines vision, hearing and other stats
    private final String RACE;

    //Creatures size used to scale the animation etc
    public final float SIZE;

    //Constructor to "give birth" to a creature by giving it a name, race and size
    protected Creature(int size, String name, String race) {
        this.SIZE = size;
        this.NAME = name;
        this.RACE = race;

        xPosScreen = Gdx.graphics.getWidth()/2 - 16;
        yPosScreen = Gdx.graphics.getHeight()/2 - 24;

    }

    /* Getters and setters
     * TODO: Check which of those are actually needed
     * */

    public int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    protected void setMana(int mana) {
        this.mana = mana;
    }

    public int getStamina() {
        return stamina;
    }

    protected void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    protected void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    protected void setMaxStamina(int maxStamina) {
        this.maxStamina = maxStamina;
    }

    protected int getStrength() {
        return strength;
    }

    protected void setStrength(int strength) {
        this.strength = strength;
    }

    protected int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected int getIntelligence() {
        return intelligence;
    }

    protected void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    protected int getLuck() {
        return luck;
    }

    protected void setLuck(int luck) {
        this.luck = luck;
    }

    protected int getDmg() {
        return dmg;
    }

    protected void setDmg(int dmg) {
        this.dmg = dmg;
    }

    protected int getArmor() {
        return armor;
    }

    protected void setArmor(int armor) {
        this.armor = armor;
    }

    protected int getLvl() {
        return lvl;
    }

    protected void setLvl(int lvl) {
        this.lvl = lvl;
    }

    protected int getExp() {
        return exp;
    }

    protected void setExp(int exp) {
        this.exp = exp;
    }

    public float getxPosMap() {
        return xPosMap;
    }

    public float getyPosMap() {
        return yPosMap;
    }

    public float getxPosScreen() {
        return xPosScreen;
    }

    public float getyPosScreen() {
        return yPosScreen;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    protected void setVelocity(float x, float y) {
        this.velocity.x = x;
        this.velocity.y = y;
        this.velocity.setLength(speed);
    }

    protected void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
        this.velocity.setLength(speed);
    }


    /**
     * Creates animation for the Creature
     * TODO: Make subclasses for different animations
     */

    public class CharacterAnimation implements Disposable {

        private final String FILENAME;

        /**
         * standardised constants for creature animations consisting of 3 columns and 4 rows of texture regions
         * Columns:
         *  -Left: Standing
         *  -Middle: Right leg in front
         *  -Right: Left leg in front
         */
        private static final int FRAME_COLS = 3;

        /**
         * Rows:
         *  -First: Facing downwards
         *  -Second: Facing upwards
         *  -Third: Facing Left
         *  -Fourth: Facing Right
         */
        private static final int FRAME_ROWS = 4;

        //Constants to make it easier to point to each direction when making an animation
        public static final int DOWN = 0;
        public static final int UP = 1;
        public static final int LEFT = 2;
        public static final int RIGHT = 3;
        public static final int STANDING = 4;

        //One AnimationManager for each possible direction (No diagonal animations)
        private Animation walkDown;
        private Animation walkUp;
        private Animation walkLeft;
        private Animation walkRight;

        //Textures used for animations
        Texture walkSheet;
        TextureRegion[] walkDownFrames;
        TextureRegion[] walkUpFrames;
        TextureRegion[] walkLeftFrames;
        TextureRegion[] walkRightFrames;

        //Directions the player could be facing when standing
        public TextureRegion faceDown;
        TextureRegion faceUp;
        TextureRegion faceLeft;
        TextureRegion faceRight;

        /**
         * Constructor for animation giving the path to the SpriteSheet
         * @param filename
         * Filename of the SpriteSheet
         */
        CharacterAnimation(String filename) {
            this.FILENAME = filename;
        }

        /**
         * Creates walk animations for each direction and assigns them to the corresponding variables
         */

        public void createWalkAnimation() {
            walkSheet = new Texture(Gdx.files.internal("characters/" + FILENAME));
            TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);
            walkDownFrames = new TextureRegion[FRAME_COLS];
            walkUpFrames = new TextureRegion[FRAME_COLS];
            walkLeftFrames = new TextureRegion[FRAME_COLS];
            walkRightFrames = new TextureRegion[FRAME_COLS];
            faceDown = tmp[DOWN][0];
            faceUp = tmp[UP][0];
            faceLeft = tmp[LEFT][0];
            faceRight = tmp[RIGHT][0];

            for (int i = 0; i < FRAME_ROWS; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    switch(i) {
                        case DOWN:
                            walkDownFrames[j] = tmp[i][j];
                            break;
                        case UP:
                            walkUpFrames[j] = tmp[i][j];
                            break;
                        case LEFT:
                            walkLeftFrames[j] = tmp[i][j];
                            break;
                        case RIGHT:
                            walkRightFrames[j] = tmp[i][j];
                            break;
                    }
                }
            }

            walkDown = new Animation(0.2f, walkDownFrames);
            walkUp = new Animation(0.2f, walkUpFrames);
            walkLeft = new Animation(0.2f, walkLeftFrames);
            walkRight = new Animation(0.2f, walkRightFrames);

        }

        @Override
        public void dispose() {
            walkSheet.dispose();
        }
    }

    /**
     * Handles Creature's motions over the screen and executes animations created in CharacterAnimation class
     */

    public class Motion implements Disposable {

        SpriteBatch batch;
        public CharacterAnimation animation;

        /**
         * Initializes AnimationManager and SpriteBatch to draw the AnimationManager on
         */
        Motion(String filename) {
            animation = new CharacterAnimation(filename);
            animation.createWalkAnimation();
            batch = new SpriteBatch();
        }

        /**
         * Executes the walk AnimationManager
         */

        public void walk(float stateTime) {
            Animation<TextureRegion> walk = null;
            int xTile = (int) xPosMap / LevelLoader.TILESIZE;
            int yTile = (int) yPosMap / LevelLoader.TILESIZE;

            if(velocity.y > 0) walk = animation.walkUp;
            if(velocity.x > 0) walk = animation.walkRight;
            if(velocity.y < 0) walk = animation.walkDown;
            if(velocity.x < 0) walk = animation.walkLeft;

            batch.begin();
            if(walk != null) batch.draw(walk.getKeyFrame(stateTime, true), xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
            else batch.draw(animation.faceDown, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
            batch.end();

            //TODO: Optimize collision detection for walking right
            //TODO: Add collision detection with other creature entities
            if(LevelLoader.collisionLayer.getCell(xTile, (int)((yPosMap + velocity.y)/LevelLoader.TILESIZE)) == null) yPosMap += (velocity.y * speed) / LevelLoader.TILESIZE;
            if(LevelLoader.collisionLayer.getCell((int)((xPosMap + velocity.x)/LevelLoader.TILESIZE), yTile) == null) xPosMap += (velocity.x * speed) / LevelLoader.TILESIZE;
            //System.out.println(xTile + "   " + yTile);

        }

        /**
         * Redraws the creature, so it is possible to keep everything visible in other screens
         */

        public void redraw() {
            //TODO: Fix bug where creature always faces down
            batch.begin();

            if(velocity.y > 0) batch.draw(animation.faceUp, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
            if(velocity.x > 0) batch.draw(animation.faceRight, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
            if(velocity.y < 0) batch.draw(animation.faceLeft, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
            if(velocity.x < 0) batch.draw(animation.faceDown, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);

            batch.end();
        }

        /**
         * Disposes the SpriteBatch when not needed anymore
         */
        @Override
        public void dispose() {
            batch.dispose();
            animation.dispose();
        }

    }

    /**
     * Inventory for the Creature used for trading and looting
     */

    public class Inventory {

        //Number of Inventory slots
        int size;

        //Number of used Inventory slots
        int usedSize;

        //Content of the Inventory
        public int[] inventoryValues = new int[3 * 9];
        public Item[] inventoryItems = new Item[3 * 9];

        /**
         * Adds an Item to the inventory
         * @param item
         * The item that will be added
         */
        public void add(Item item) {
            int[] places = new int[2];
            places[0] = GameScreen.inventoryScreen.findFreeSlot()[0];
            places[1] = GameScreen.inventoryScreen.findFreeSlot()[1];
            System.out.println(places[0] + "  " + places[1]);
            GameScreen.inventoryScreen.defaultSlots[places[0]][places[1]].isOccupied = true;
            GameScreen.inventoryScreen.defaultSlots[places[0]][places[1]].heldItem = item;
        }

        /**
         * Removes an Item from the Inventory
         * @param item
         * The Item that will be removed
         */
        public void remove(Item item) {
            int[] places = new int[2];
            places[0] = GameScreen.inventoryScreen.findItem(item)[0];
            places[1] = GameScreen.inventoryScreen.findItem(item)[1];
            System.out.println(places[0] + "  " + places[1]);
            GameScreen.inventoryScreen.defaultSlots[places[0]][places[1]].isOccupied = false;
        }

        /**
         * Equips an Equipable item
         * @param item
         * The item that will be Equipped
         */
        public void equip(Equipable item) {

        }

    }
}
