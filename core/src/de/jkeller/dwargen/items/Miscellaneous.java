package de.jkeller.dwargen.items;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * Item subclass for stuff that can't be applied to anything else
 */
public class Miscellaneous extends Item {

    Miscellaneous(int value, int weight, String filename) {
        super(value, weight, "weapons/" + filename);
    }

}
