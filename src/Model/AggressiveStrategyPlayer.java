package Model;

import View.DiceRollView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * This class contains the implementation of the Aggressive
 * Strategy for reinforcement, attack and fortify methods
 */
public class AggressiveStrategyPlayer implements PlayerStrategyInterface,Serializable {


    /**
     * This method performs reinforcement for aggressive player
     * @param territoryClicked the territory selected
     * @return the completion message
     */
    @Override
    public String reinforce(String territoryClicked) {
        Player curP = Game.getInstance().getCurrPlayer();
        System.out.println("aggressiVE player reinforcement starts");
        //curP.addArmy(Game.getInstance().calcReinforcementArmies(curP.getId()));
        System.out.println("Player "+curP.getName()+" has "+curP.armies+" reinforcement armies");
        if(getStrongestTerritory() != null){
            System.out.println("Strongest territory::"+getStrongestTerritory().getName()+" Armies::"+getStrongestTerritory().armies);

            getStrongestTerritory().armies = getStrongestTerritory().getArmies()+Game.getInstance().getCurrPlayer().getArmies();
            Game.getInstance().getCurrPlayer().setArmies(0);
            System.out.println("Strongest territory::"+getStrongestTerritory().getName()+" Updated Armies::"+getStrongestTerritory().armies);
            Game.getInstance().nextTurn();
        }
        else{
            System.out.println("Strongest territory not found");
        }

        if(Game.getInstance().getGameTurn() == 0)
            Game.getInstance().nextPhase();

        return "Aggresive Player Reinforcement completed";
    }

    /**
     * This method performs fortification for aggressive player
     * @param territoryClicked the territory selected
     * @return the completion message
     */
    @Override
    public String fortify(String territoryClicked) {
        Territory territoryWithMaxArmies = null;
        int maxArmies = 0;
        for(Territory territory:Game.getInstance().getCurrPlayer().getTerritoriesOwned()){
            if(maxArmies < territory.armies) {
                maxArmies = territory.armies;
                territoryWithMaxArmies = territory;
            }
        }
        if(territoryWithMaxArmies != null){
            Game.getInstance().fortification_destination = territoryWithMaxArmies.name;
            for(Territory adjTerrObj:getOwnedAdjTerrObjects(territoryWithMaxArmies)){
                if(adjTerrObj.armies>0) {
                    Game.getInstance().fortification_source = adjTerrObj.name;
                    Game.getInstance().getCurrPlayer().fortify("ABC");
                }
            }
            if(Game.getInstance().fortification_source == null){
                System.out.println("No armies left in all the territories except "+territoryWithMaxArmies.getName());
                System.out.println("Next Player's turn");
                if(Game.getInstance().getGameTurn() == Game.getInstance().getNumPlayers()-1) {
                    Game.getInstance().setTurn(0);
                    Game.getInstance().setGamePhase(2);

                }
                else{
                    Game.getInstance().setTurn(Game.getInstance().getGameTurn()+1);
                    Game.getInstance().setGamePhase(3);
                }
                //Game.getInstance().nextTurn();
                /*if(Game.getInstance().getGameTurn() != Game.getInstance().getNumPlayers()-1)
                    Game.getInstance().setGamePhase(3); //Next player's attack will start*/
            }
        }
        else{
            System.out.println("There is no territory with atleast 1 army");
            System.out.println("Next Player's turn");
            Game.getInstance().nextTurn();
            /*if(Game.getInstance().getGameTurn() == 0)
                Game.getInstance().nextPhase();*/
            if(Game.getInstance().getGameTurn() != Game.getInstance().getNumPlayers()-1)
                Game.getInstance().setGamePhase(3); //Next player's attack will start
        }

        return null;
    }

    /**
     * This method performs attack for aggressive player
     * @param territoryClicked the territory selected
     * @return the completion message
     */
    @Override
    public String attack(String territoryClicked) throws NullPointerException{
        //Attack will happen in all out mode
        Game.getInstance().setAlloutMode(true);
        Game.getInstance().getCurrPlayer().allOutInput = 0;

        Territory strongestTerritory = getStrongestTerritory();
        //System.out.println(strongestTerritory.getName());
        //Game.getInstance().setAttacker(strongestTerritory.getName());
        if(strongestTerritory!=null) {
            Game.getInstance().setAttackerObj(strongestTerritory);
            System.out.println("Current Player:" + Game.getInstance().getCurrPlayer().getName());
            System.out.println("Attacker territory :" + Game.getInstance().getAttackerObj().getName());

            Iterator<Territory> territoryIterator = getNotOwnedAdjTerrObjects(getStrongestTerritory()).iterator();
            if (territoryIterator.hasNext()) {
                while (territoryIterator.hasNext()) {
                    Territory potentialDefender = territoryIterator.next();
                    if (potentialDefender.getArmies() > 1)
                        Game.getInstance().setAttackedObj(potentialDefender);
                    else {
                        System.out.println("Adjacent territory " + potentialDefender.getName() + " does not have enough armies..");
                        Game.getInstance().getCurrPlayer().diceThrowResults.add("Adjacent territory " + potentialDefender.getName() + " does not have enough armies..<br/>");
                        //Game.getInstance().nextTurn();
                        //System.out.println("CONTINUE AGGR");
                        System.out.println("Attacker do not have valid adjacents to attack");
                        System.out.println("GAME TURN:" + Game.getInstance().getGameTurn());

                        if (Game.getInstance().getGameTurn() == Game.getInstance().getNumPlayers() - 1) {
                            System.out.println("PL ID:" + Game.getInstance().getCurrPlayer().getId());
                            Game.getInstance().setTurn(0);
                            Game.getInstance().setGamePhase(2);

                        } else {
                            Game.getInstance().setTurn(Game.getInstance().getGameTurn() + 1);
                            Game.getInstance().setGamePhase(3);
                        }
                        continue;
                    }
                    String prevOwner = potentialDefender.getOwner().getName();
                    System.out.println("Defender territory :" + potentialDefender.getName());
                    System.out.println("Attack started");
                    if (Game.getInstance().getAttackerObj().getArmies() == 1) {
                        System.out.println("Attacker cannot attack anymore, armies left is 0 ");
                        Game.getInstance().getCurrPlayer().diceThrowResults.add("Attacker cannot attack anymore, armies left is 0 <br/>");
                        if (Game.getInstance().getGameTurn() == Game.getInstance().getNumPlayers() - 1) {
                            Game.getInstance().setTurn(0);
                            Game.getInstance().setGamePhase(2);

                        } else {
                            Game.getInstance().setTurn(Game.getInstance().getGameTurn() + 1);
                            Game.getInstance().setGamePhase(3);
                        }
                        //Game.getInstance().nextTurn();
                        break;
                /*Game.getInstance().setAttacker(null);
                Game.getInstance().setAttacked(null);
                Game.getInstance().setAttackerObj(null);
                Game.getInstance().setAttackedObj(null);
                Game.getInstance().setNumOfDiceAttacker(-1);
                Game.getInstance().setNumOfDiceAttacked(-1);*/
                    } else if (!prevOwner.equalsIgnoreCase(potentialDefender.getOwner().getName())) {
                        System.out.println("Defender " + potentialDefender.getName() + " conquered");
                        if (territoryIterator.hasNext())
                            System.out.println("Next defender");
                        else {
                            System.out.println("Attacker does not have valid adjacent territories to attack");
                            Game.getInstance().nextTurn();
                            break;
                        }
                        Game.getInstance().getCurrPlayer().diceThrowResults.add("Defender " + potentialDefender.getName() + " conquered<br/>");
                        Game.getInstance().getCurrPlayer().diceThrowResults.add("Next Defender..<br/>");
                        continue;
                    } else {
                        System.out.println("Attack continues");
                        Game.getInstance().getCurrPlayer().attack(territoryClicked);
                        Game.getInstance().getCurrPlayer().diceThrowResults.add("Attack Continues..<br/>");

                    }
                }
            } else {
                System.out.println("Attacker do not have valid adjacents to attack");
                if (Game.getInstance().getCurrPlayer().getId() == Game.getInstance().getNumPlayers() - 1) {
                    Game.getInstance().setGamePhase(2);
                    Game.getInstance().setTurn(0);
                } else {
                    Game.getInstance().setTurn(Game.getInstance().getGameTurn() + 1);
                    Game.getInstance().setGamePhase(3);
                }

            }
        }
        else{
            System.out.println("In aggressive strategy attack, no owned territory found");
        }
        //After a player's attack finishes, it goes to next player's attack
        /*if(!territoryIterator.hasNext()) {
            //Game.getInstance().nextTurn();
            Game.getInstance().setGamePhase(3);
        }*/
        /*if(Game.getInstance().getCurrPlayer().getId() == (Game.getInstance().players.size()-1))
            Game.getInstance().nextPhase();*/
        DiceRollView.getInstance().displayContent(Game.getInstance().getCurrPlayer().diceThrowResults);
        return null;
    }

    /**
     * This method searches the territory with maximum adjacents and
     * then makes the territory with highest armies as the strongest one.
     * @return the Strongest Terrritory object
     */
    public Territory getStrongestTerritory(){
        int maxArmiesAndAdjacents = 0;
        //int maxNotOwnedAdjacents =0;
        Territory retTerr = null;

        ArrayList<Territory> territories = new ArrayList<>();
        for(Territory territory:Game.getInstance().getCurrPlayer().getTerritoriesOwned()){
            for(String territory1:territory.getAdjacents()){
                Territory terrObj = Game.getInstance().gameMap.getTerritory(territory1);
                if(!terrObj.getOwner().getName().equalsIgnoreCase(territory.getOwner().getName()))
                    territories.add(territory);
            }
        }

        for(Territory territory:territories){
            if(maxArmiesAndAdjacents < (territory.armies + getNotOwnedAdjacentsCount(territory))) {
                maxArmiesAndAdjacents = (territory.armies + getNotOwnedAdjacentsCount(territory));
                retTerr = territory;
            }
        }
        return retTerr;
    }

    /**
     *
     * This method counts the number of adjacent terrritory that are not owned by current player
     * @param territory the territory object
     * @return count of the adjacents not owned by current player
     */
    public int getNotOwnedAdjacentsCount(Territory territory){
        int retVal = 0;
        for(String adj:territory.getAdjacents()){
            Territory adjObj = Game.getInstance().gameMap.getTerritory(adj);
            if(adjObj.getOwner() != territory.getOwner()) {
                if (adjObj.getArmies() > 0)
                    retVal++;
            }
        }
        return retVal;
    }

    /**
     *
     * This method returns the arraylist of type territory not owned by current player
     * @param territory the territory object
     * @return a list of object (of type territory) of territories not owned by current player
     */
    public ArrayList<Territory> getNotOwnedAdjTerrObjects(Territory territory){
        ArrayList<Territory> retList = new ArrayList<>();
        for(String adj:territory.getAdjacents()){
            Territory adjObj = Game.getInstance().gameMap.getTerritory(adj);
            if(adjObj.getOwner() != territory.getOwner())
                retList.add(adjObj);
        }
        return retList;
    }

    /**
     *
     * This method returns the arraylist of type territory owned by current player
     * @param territory the territory object
     * @return a list of object (of type territory) of territories owned by current player
     */
    public ArrayList<Territory> getOwnedAdjTerrObjects(Territory territory){
        ArrayList<Territory> retList = new ArrayList<>();
        for(String adj:territory.getAdjacents()){
            Territory adjObj = Game.getInstance().gameMap.getTerritory(adj);
            if(adjObj.getOwner() == territory.getOwner())
                retList.add(adjObj);
        }
        return retList;
    }
}
