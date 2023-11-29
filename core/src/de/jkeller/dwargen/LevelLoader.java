package de.jkeller.dwargen;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by viking on 10/22/16. \[T]/
 *
 * Class that manages the different levels in the game
 */

//TODO: Make non-static to allow different maps with different spawn points (not needed in prototype version)
//TODO: Make non-static to implement disposable
public class LevelLoader {

    //Tiled map tile size of used tileset
    public static final int TILESIZE = 32;

    //Fields needed to load a map
    private static TmxMapLoader mapLoader = new TmxMapLoader();
    static TiledMap map;
    public static TiledMapTileLayer collisionLayer;

    /**
     * Method that loads a map and its collision layer
     */
    public static void loadMap() {
        map = mapLoader.load("levels/camtest.tmx");
        collisionLayer = (TiledMapTileLayer) map.getLayers().get("collision_layer");
    }

    /**
     * Deletes map
     */
    public static void dispose() {
        map.dispose();
    }

    /**
     * Inner class that manages spawn points of maps
     */
    public static class Spawn {
        public static int xPos = 100;
        public static int yPos = 100;
    }

}
