package de.jkeller.dwargen;

import com.badlogic.gdx.utils.Disposable;

/**
 * Created by viking on 12/22/16. \[T]/
 *
 * Base Class for all Game entities
 */
//TODO: rewrite Code to implement this -> target is to have a list of entities that get updated in the loop
public abstract class GameEntity implements Disposable {

    public float xPosMap;
    public float yPosMap;

    public float xPosScreen;
    public float yPosScreen;

    public boolean isVisible;
    public boolean isOnScreen;

    public abstract void create();
    public abstract void update();

    public abstract void dispose();

}
