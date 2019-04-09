package Model;

import View.PhaseView;

import javax.swing.*;
import java.io.Serializable;

/**
 *
 * This class contains the implementation of the Cheater
 * Strategy for reinforcement, attack and fortify methods
 */
public class CheaterStrategyPlayer implements PlayerStrategyInterface, Serializable {

    /**
     * This method performs reinforcement for cheater player
     * @param territoryClicked the territory selected
     * @return the completion message
     */
    @Override
    public String reinforce(String territoryClicked) {
        Player curP = Game.getInstance().getCurrPlayer();
        System.out.println("Cheater player reinforcement starts");

        for(Territory territory:curP.getTerritoriesOwned()){
            System.out.println();
            System.out.println(territory.getName()+" has "+territory.getArmies());
            territory.addArmy(territory.armies);
            System.out.println(territory.getName()+" : Updated armies "+territory.getArmies());
            System.out.println();
        }

        if((Game.getInstance().getGameTurn()+1) < Game.getInstance().getNumPlayers()){
            Game.getInstance().setGamePhase(2);
            Game.getInstance().setTurn(Game.getInstance().getGameTurn()+1);
        }
        else{
            Game.getInstance().setGamePhase(3);
            Game.getInstance().setTurn(0);
        }

        return "Cheater Player Reinforcement completed";
    }

    /**
     * This method performs fortification for cheater player
     * @param territoryClicked the territory selected
     * @return the completion message
     */
    @Override
    public String fortify(String territoryClicked) {
        Player curP = Game.getInstance().getCurrPlayer();
        System.out.println("Cheater player fortification starts");

        for(Territory territory:curP.getTerritoriesOwned()){
            System.out.println();
            System.out.println(territory.getName()+" has "+territory.getArmies());
            for(String territory1:territory.getAdjacents()){
                Territory adjTerr = Game.getInstance().gameMap.getTerritory(territory1);
                if(!adjTerr.getOwner().getName().equalsIgnoreCase(territory.getOwner().getName())){
                    territory.addArmy(territory.armies);
                    System.out.println(territory.getName()+" : Updated armies "+territory.getArmies());
                    break;
                }
            }
            System.out.println();
        }


        if(Game.getInstance().getGameTurn() == Game.getInstance().getNumPlayers()-1) {
            Game.getInstance().setGamePhase(2);
            Game.getInstance().setTurn(0);
        }
        else{
            Game.getInstance().setGamePhase(3);
            Game.getInstance().setTurn(Game.getInstance().getGameTurn()+1);
        }
        return "Cheater Player fortification completed";
    }

    /**
     * This method performs attack for cheater player
     * @param territoryClicked the territory selected
     * @return the completion message
     */
    @Override
    public String attack(String territoryClicked) {
        Player curP = Game.getInstance().getCurrPlayer();
        System.out.println("Cheater player attack starts");


        System.out.println("Current Player"+curP.getName());
        for(Territory territory:curP.getTerritoriesOwned()){
            System.out.println();
            System.out.println(territory.getName()+" adjacents::");
            for(String territory1:territory.getAdjacents()){
                Territory adjObj = Game.getInstance().gameMap.getTerritory(territory1);
                System.out.println("Territory "+adjObj.getName()+"- current owner:: "+adjObj.getOwner().getName());
                adjObj.setOwner(curP);

                System.out.println("Territory "+adjObj.getName()+"- new owner:: "+adjObj.getOwner().getName());

                for(JButton btn: PhaseView.getInstance().getBtnTerritories().values()){
                    if(btn.getText().equalsIgnoreCase(adjObj.getName())){
                        btn.setBackground(Game.getInstance().getCurrPlayer().getColor());
                    }
                }
            }
        }


        Game.getInstance().setGamePhase(4);

        return "Cheater Player Attack completed";
    }
}
