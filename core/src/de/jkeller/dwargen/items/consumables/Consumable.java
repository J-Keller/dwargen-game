package de.jkeller.dwargen.items.consumables;

/**
 * Created by viking on 10/24/16. \[T]/
 *
 * This subclass of Item is used to provide the player with effects when consumed
 * It's the only Item-type that can be consumed
 */
public interface Consumable {

    /**
     * Gets called when the player uses the Item
     * Applies an effect to the player
     */
    void use();

}
