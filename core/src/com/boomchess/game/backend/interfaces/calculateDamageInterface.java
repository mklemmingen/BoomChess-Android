package com.boomchess.game.backend.interfaces;

import com.boomchess.game.backend.Soldier;

public interface calculateDamageInterface {
    /*
     * This interface is used to make sure that all the Soldier objects have a calculateDamage method
     */
    int calculateDamage(Soldier soldierDefend);

    int getStandardHealth();
}
