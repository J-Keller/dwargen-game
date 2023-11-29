package de.jkeller.dwargen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jkeller.dwargen.screens.GameScreen;

/**
 * Created by viking on 10/22/16. \[T]/
 *
 * Class to manage the orthogonal camera
 */
public class CameraManager implements Disposable{

    /**scale of a pixel compared to a tile needed for the OrthogonalTiledMapRenderer*/
    float unitscale = 1/32f;
    float h = Gdx.graphics.getHeight();
    float w = Gdx.graphics.getWidth();
    final float CAMERA_HEIGHT = 32;
    final float CAMERA_WIDTH = 32 * (w / h);

    boolean isCenteredX;
    boolean isCenteredY;
    //trigger for isCentered test
    boolean moveCameraX = true;
    boolean moveCameraY = true;

    Vector2 dir = GameScreen.player.getVelocity();

    boolean trigger = false;

    /**The main camera (TODO: Focus on the player and move the map)*/
    OrthographicCamera mainCamera;
    /**Tiled map renderer renders the map provided by the LevelLoader*/
    OrthogonalTiledMapRenderer renderer;

    public CameraManager() {
        mainCamera = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        renderer = new OrthogonalTiledMapRenderer(LevelLoader.map, unitscale);
        mainCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        mainCamera.position.set(GameScreen.player.xPosMap /LevelLoader.TILESIZE, GameScreen.player.yPosMap /LevelLoader.TILESIZE, 0);
        mainCamera.update();
    }

    /**
     * Renders the map
     */
    public void renderMap() {
        renderer.render();
        renderer.setView(mainCamera);
    }

    public void moveMap() {
        mainCamera.position.set(GameScreen.player.xPosMap, GameScreen.player.yPosMap, 0);
        mainCamera.update();

        System.out.println("X: " + mainCamera.position.x + " Y: " + mainCamera.position.y);
        //System.out.println("X: " + GameScreen.player.xPosScreen + "; Y: " + GameScreen.player.yPosScreen);
    }

    private boolean isAtMapBorderX() {
        return (mainCamera.position.x < 30 || mainCamera.position.x > 170);
    }

    private boolean isAtMapBorderY() {
        return (mainCamera.position.y < 17 || mainCamera.position.y > 183);
    }

    /**
     * Deletes the renderer when the game is closed
     */
    @Override
    public void dispose() {
        renderer.dispose();
    }

}
