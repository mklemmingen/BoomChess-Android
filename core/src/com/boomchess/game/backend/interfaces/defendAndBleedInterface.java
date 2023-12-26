package com.boomchess.game.backend.interfaces;

import com.boomchess.game.backend.Soldier;

public interface defendAndBleedInterface {
    /*
     * This interface is used to make sure that all the Soldier objects have a defendAndBleed method
     */
    int defendAndBleed(int damage, Soldier soldierAttack);
}
