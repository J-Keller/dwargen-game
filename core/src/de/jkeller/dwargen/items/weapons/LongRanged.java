package de.jkeller.dwargen.items.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.jkeller.dwargen.creatures.Player;
import de.jkeller.dwargen.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viking on 11/10/16. \[T]/
 * Subclass of Weapon
 *
 * Particularities for this Weapon-type:
 *  -Long ranged
 *  -Needs Ammunition
 */

public abstract class LongRanged extends Weapon{

    /**Needed to check if an projectile has been shot and is still in the air, so the player can't shoot too rapidly (Flag for next projectile)*/
    public boolean isShooting = false;
    /**Maximum range in pixels the bow can shoot the projectile*/
    private float range = 500;
    /**Fields needed to draw the projectile*/

    /**Loaded projectile*/
    List<Projectile> projectiles  = new ArrayList<Projectile>();

    LongRanged(int value, int weight, String filename) {
        super(value, weight, filename);
    }


    /**
     * Used to create an projectile entity depending on the type of ammo the player loaded to the bow
     */
    private class Projectile {
        SpriteBatch batch;
        Texture projectileTexture;
        /**The target the player has been aiming to*/
        public float targetX;
        public float targetY;
        /**The Direction tha projectile is travelling*/
        private Vector2 direction;
        private float travelledDistance;
        /**Position where the projectile is drawn*/
        float xPos;
        float yPos;
        /**Position of the arrows hit box*/
        float xHit;
        float yHit;

        /**
         * Constructor initializes the Texture (TODO: Set file as constructor parameter when more ammo types are available)
         * and sets the Position as well as the hit box
         * @param player
         * The player who uses the bow
         */
        Projectile(Player player, float targetX, float targetY) {
            projectileTexture = new Texture("items/weapons/arrow.png");
            xPos = player.getxPosScreen();
            yPos = player.getyPosScreen();
            xHit = xPos + 32;
            yHit = yPos + 32;
            this.targetX = targetX;
            this.targetY = targetY;
            travelledDistance = 0;
            batch = new SpriteBatch();
        }

        /**
         * Checks whether the projectile has hit something or has reached maximum range
         * TODO: Add check for object collision (walls, houses etc.) (new Tiled Map Layer)
         * @return
         * True if is the projectile has reached its final destination
         */
        public boolean checkHit() {

            //Travelled projectile distance exceeds range
            if (travelledDistance > range) return true;
            //Checks if projectile is out of bounds
            if (xPos > Gdx.graphics.getWidth() || xPos < 0 || yPos > Gdx.graphics.getHeight() || yPos < 0) return true;
            //Checks if projectile has hit a monster
            if ((xHit > GameScreen.orc.xPosScreen) && (xHit < GameScreen.orc.xPosScreen + 60) && (yHit > GameScreen.orc.yPosScreen && yHit < GameScreen.orc.yPosScreen + 100)) {
                GameScreen.orc.onHit();
                return true;
            }
            return false;
        }

        /**
         * Deletes the Texture and SpriteBatch as well as resetting the isShooting flag
         */
        public void dispose(int i) {
            projectileTexture.dispose();
            batch.dispose();
            projectiles.remove(i);
        }
    }

    /**
     * Loads a new projectile to shoot by creating a new SpriteBatch to draw on and a new projectile
     * It also sets the direction the projectile will be travelling when shot
     * TODO: Initialize range in Bow Constructor
     * @param player
     * The player who uses the bow
     */
    public void createArrow(Player player, float targetX, float targetY) {
        projectiles.add(new Projectile(player, targetX, targetY));
        projectiles.get(projectiles.size() - 1).direction = new Vector2();
        setDirection(projectiles.get(projectiles.size() - 1));
        for(int i = 0; i <= projectiles.size() - 1; i++) System.out.println(" " + i);
    }

    /**
     * Sets the direction Vectors x and y parameters and the length to determine speed
     */
    private void setDirection(Projectile projectile) {
        System.out.println("TX: " + projectile.targetX + "  TY: " + projectile.targetY);
        projectile.direction.set(projectile.targetX - projectile.xHit, projectile.targetY - projectile.yHit);
        projectile.direction.setLength(10);
    }

    /**
     * Executes the shooting "animation" by updating position and hit box position and drawing the projectile the the screen
     */
    public void shoot() {
        int i = 0;
        for(Projectile projectile : projectiles) {
            if (projectile.checkHit()) {
                System.out.println(i);
                projectile.dispose(i);
                break;
            }
            projectile.batch.begin();
            projectile.batch.draw(new TextureRegion(projectile.projectileTexture), projectile.xPos, projectile.yPos, 16, 16, 32, 32, 1, 1, projectile.direction.angle() + 45, true);
            projectile.batch.end();
            projectile.xPos += projectile.direction.x;
            projectile.yPos += projectile.direction.y;
            projectile.travelledDistance += 5;
            projectile.xHit += projectile.direction.x;
            projectile.yHit += projectile.direction.y;
            i++;
        }
    }

}
