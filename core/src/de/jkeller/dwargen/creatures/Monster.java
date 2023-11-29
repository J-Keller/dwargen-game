package de.jkeller.dwargen.creatures;

import com.badlogic.gdx.math.Vector2;
import de.jkeller.dwargen.LevelLoader;
import de.jkeller.dwargen.items.Item;
import de.jkeller.dwargen.items.consumables.Food;
import de.jkeller.dwargen.screens.GameScreen;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * Subclass of Creature used to manage enemies
 */
public class Monster extends Creature implements Enemy {

    public Creature.Motion motion;
    public boolean isAlive;

    public Monster(int size, String name, String race) {
        super(size, name, race);
        motion = new Creature.Motion("orc.png");
        this.setHealth(200);
        this.xPosMap = 150;
        this.yPosMap = 120;
        isAlive = true;
    }

    @Override
    public void getToPlayer() {
        Vector2 dir = new Vector2();

        dir.x = GameScreen.player.getxPosMap() - this.xPosMap;
        dir.y = GameScreen.player.getyPosMap() - this.yPosMap;

        dir.setLength(1.5f);

        this.setVelocity(dir);
    }

    public void setScreenCoordinates() {
        this.xPosScreen = GameScreen.player.getxPosScreen() + (this.getxPosMap() - GameScreen.player.xPosMap) * LevelLoader.TILESIZE;
        this.yPosScreen = GameScreen.player.getyPosScreen() + (this.getyPosMap() - GameScreen.player.yPosMap) * LevelLoader.TILESIZE;

    }

    public void moveOnMap() {
        this.xPosMap += this.getVelocity().x/32;
        this.yPosMap += this.getVelocity().y/32;
    }

    public void moveOnScreen() {
        this.xPosScreen += this.getVelocity().x;
        this.yPosScreen += this.getVelocity().y;

        //Check if Monster hits the player
        if(this.xPosScreen >= GameScreen.player.xPosScreen && this.xPosScreen <= GameScreen.player.xPosScreen + Creature.BASE_WIDTH * GameScreen.player.SIZE &&
                this.yPosScreen >= GameScreen.player.yPosScreen && this.yPosScreen <= GameScreen.player.yPosScreen + Creature.BASE_HEIGHT * GameScreen.player.SIZE) {

            GameScreen.player.changeStat(Player.Stats.HEALTH, -20);
            this.xPosMap = GameScreen.player.xPosMap + 10;
            this.yPosMap = GameScreen.player.yPosMap + 10;

        }
    }

    /**
     * Gets called once the Monster has been hit by the player
     */
    @Override
    public void onHit() {
        if(isAlive) {
            this.setHealth(this.getHealth() - 50);
            if (this.getHealth() <= 0) onDeath();
        }
    }

    /**
     * Gets called when the Monster dies
     */
    @Override
    public void onDeath() {
        isAlive = false;
        motion.dispose();
        drop();
    }

    public void drop() {
        Item drop = new Food(2, 30, "potato.png", 0, 20, 0);
        drop.xPosMap = this.xPosMap;
        drop.yPosMap = this.yPosMap;
        GameScreen.items.add(drop);
    }

    public boolean getIsOnScreen() {
        System.out.println("X: " + this.xPosMap + " Y: " + this.yPosMap);
        return Math.abs(GameScreen.player.getxPosMap() - this.xPosMap) < 30 && Math.abs(GameScreen.player.getyPosMap() - this.getyPosMap()) < 30;
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
}
