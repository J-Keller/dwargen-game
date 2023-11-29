package de.jkeller.dwargen.items.weapons;

import de.jkeller.dwargen.items.Equipable;
import de.jkeller.dwargen.items.Item;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * Abstract class to set standards for every Weapon-type in the game
 */
public abstract class Weapon extends Item implements Equipable {

    Weapon(int value, int weight, String filename) {
        super(value, weight, "weapons/" + filename);
    }

}
