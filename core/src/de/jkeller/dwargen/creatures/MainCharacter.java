package de.jkeller.dwargen.creatures;

import de.jkeller.dwargen.GameStates;

/**
 * Created by viking on 11/9/16. \[T]/
 */

//TODO: Think about what the player can do
public interface MainCharacter {

    void levelUp(int strengthUp, int speedUp, int intelligenceUp, int luckUp);
    void changeStat(Player.Stats stat, int amount);

}
