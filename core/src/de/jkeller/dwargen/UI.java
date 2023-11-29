package de.jkeller.dwargen;

import java.util.Collections;

/**
 * Created by viking on 11/9/16. \[T]/
 */
public interface UI {

    //Used in game with standard settings
    //Visible when playing in level
    void drawPlayerStatusBars();
    void drawEnemyHealthBars();
    void drawMiniMap();

    //Visible in specific situations
    void showInventory();
    void showMenu();
    void showMap();
    void showQuestLog();
    void showStats();
    void showSkills();
    void showBestiary();
    void showWeaponCatalogue();
    void showArmorCatalogue();
    void showItemCatalogue();

    //Used in debug mode
    void drawPlayerHitBox();
    void drawEnemyHitBox();
    void drawItemHitBox();

}
