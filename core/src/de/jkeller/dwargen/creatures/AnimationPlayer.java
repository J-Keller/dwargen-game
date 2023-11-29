package de.jkeller.dwargen.creatures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class AnimationPlayer implements Disposable {

    private SpriteBatch batch;
    private AnimationManager animation;

    /**
     * Initializes AnimationManager and SpriteBatch to draw the AnimationManager on
     */
    AnimationPlayer(AnimationManager animationManager) {
        animation = animationManager;
        animation.createWalkAnimation();
        batch = new SpriteBatch();
    }

    /**
     * Executes the walk AnimationManager
     */

    @SuppressWarnings("all")
    public void walk(float stateTime, WalkAnimations direction, int x, int y, int size) {
        Animation<TextureRegion> walk = null;

        switch(direction) {
            case UP:
                walk = animation.walkUp;
                break;
            case RIGHT:
                walk = animation.walkRight;
                break;
            case DOWN:
                walk = animation.walkDown;
                break;
            case LEFT:
                walk = animation.walkLeft;
                break;
            case STANDING:
                walk = animation.walkDown;
                break;
            default:
                walk = animation.walkDown;
                break;
        }

        batch.begin();
        batch.draw(walk.getKeyFrame(stateTime, true), x, y, size * NewCreature.BASE_WIDTH, size * NewCreature.BASE_HEIGHT);
        batch.end();

    }

    /**
     * Redraws the creature, so it is possible to keep everything visible in other screens
     */
    /*@SuppressWarnings("all")
    public void redraw() {
        //TODO: Fix bug where creature always faces down
        batch.begin();

        if(velocity.y > 0) batch.draw(animation.faceUp, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
        if(velocity.x > 0) batch.draw(animation.faceRight, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
        if(velocity.y < 0) batch.draw(animation.faceLeft, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);
        if(velocity.x < 0) batch.draw(animation.faceDown, xPosScreen, yPosScreen, SIZE * BASE_WIDTH, SIZE * BASE_HEIGHT);

        batch.end();
    }*/

    /**
     * Disposes the SpriteBatch when not needed anymore
     */
    @Override
    public void dispose() {
        batch.dispose();
        animation.dispose();
    }

}
