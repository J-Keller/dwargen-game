package de.jkeller.dwargen.items.consumables;

import de.jkeller.dwargen.creatures.Player;
import de.jkeller.dwargen.items.Item;
import de.jkeller.dwargen.screens.GameScreen;

/**
 * Created by viking on 12/21/16. \[T]/
 */
public class Food extends Item implements Consumable {

    public final int STAMINA_REG;
    public final int HEALTH_REG;
    public final int MANA_REG;

    public Food(int value, int weight, String filename, int staminaReg, int healthReg, int manaReg) {
        super(value, weight, "consumables/" + filename);
        this.STAMINA_REG = staminaReg;
        this.HEALTH_REG = healthReg;
        this.MANA_REG = manaReg;
    }

    @Override
    public void use() {
        //TODO: Make item change status of user
        System.out.println("Item has been used");

        GameScreen.player.changeStat(Player.Stats.HEALTH, HEALTH_REG);
        GameScreen.player.changeStat(Player.Stats.STAMINA, STAMINA_REG);
        GameScreen.player.changeStat(Player.Stats.MANA, MANA_REG);
    }

}
