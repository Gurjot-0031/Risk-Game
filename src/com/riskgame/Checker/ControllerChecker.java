package com.riskgame.Checker;

/**
 * This is the Interface for control checker and listener in the risk game.
 */
public interface ControllerChecker {
    /**
     * This method will be called when user selects number of players for the game
     *
     * @param numberOfPlayer
     */
    void onPlayerSelected(int numberOfPlayer);
}
