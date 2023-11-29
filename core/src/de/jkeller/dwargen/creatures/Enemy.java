package de.jkeller.dwargen.creatures;

/**
 * Created by viking on 11/9/16. \[T]/
 */
public interface Enemy {

    void getToPlayer();
    void onHit();
    void onDeath();

}
