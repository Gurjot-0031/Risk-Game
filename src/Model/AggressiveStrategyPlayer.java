package Model;

import View.DiceRollView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AggressiveStrategyPlayer implements PlayerStrategyInterface {


    @Override
    public String reinforce(String territoryClicked) {
        Player curP = Game.getInstance().getCurrPlayer();
        System.out.println("aggressiVE player reinforcement starts");
        System.out.println("Player "+curP.getName()+" has "+curP.armies+" reinforcement armies");
        System.out.println("Strongest territory::"+getStrongestTerritory().getName()+" Armies::"+getStrongestTerritory().armies);

        getStrongestTerritory().armies = getStrongestTerritory().getArmies()+Game.getInstance().getCurrPlayer().getArmies();
        Game.getInstance().getCurrPlayer().setArmies(0);
        System.out.println("Strongest territory::"+getStrongestTerritory().getName()+" Updated Armies::"+getStrongestTerritory().armies);
        Game.getInstance().nextTurn();
        if(Game.getInstance().getGameTurn() == 0)
            Game.getInstance().nextPhase();

        return "Aggresive Player Reinforcement completed";
    }

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
                Game.getInstance().nextTurn();
                /*if(Game.getInstance().getGameTurn() == 0)
                    Game.getInstance().nextPhase();*/
            }
        }
        else{
            System.out.println("There is no territory with atleast 1 army");
            System.out.println("Next Player's turn");
            Game.getInstance().nextTurn();
            /*if(Game.getInstance().getGameTurn() == 0)
                Game.getInstance().nextPhase();*/
        }

        return null;
    }

    @Override
    public String attack(String territoryClicked) throws NullPointerException{
        //Attack will happen in all out mode
        Game.getInstance().setAlloutMode(true);
        Game.getInstance().getCurrPlayer().allOutInput = 0;

        Territory strongestTerritory = getStrongestTerritory();
        //System.out.println(strongestTerritory.getName());
        //Game.getInstance().setAttacker(strongestTerritory.getName());
        Game.getInstance().setAttackerObj(strongestTerritory);
        System.out.println("Attacker territory :"+Game.getInstance().getAttackerObj().getName());

        Iterator<Territory> territoryIterator = getNotOwnedAdjTerrObjects(getStrongestTerritory()).iterator();
        while(territoryIterator.hasNext()){
            Territory potentialDefender = territoryIterator.next();
            if(potentialDefender.getArmies()>1)
                Game.getInstance().setAttackedObj(potentialDefender);
            else {
                System.out.println("Adjacent territory "+potentialDefender.getName()+" does not have enough armies..");
                Game.getInstance().getCurrPlayer().diceThrowResults.add("Adjacent territory "+potentialDefender.getName()+" does not have enough armies..<br/>");
                continue ;
            }
            String prevOwner = potentialDefender.getOwner().getName();
            System.out.println("Defender territory :"+potentialDefender.getName());
            Game.getInstance().getCurrPlayer().attack(territoryClicked);
            if(Game.getInstance().getAttackerObj().getArmies() == 0){
                System.out.println("Attacker cannot attack anymore, armies left is 0 ");
                Game.getInstance().getCurrPlayer().diceThrowResults.add("Attacker cannot attack anymore, armies left is 0 <br/>");
                /*Game.getInstance().setAttacker(null);
                Game.getInstance().setAttacked(null);
                Game.getInstance().setAttackerObj(null);
                Game.getInstance().setAttackedObj(null);
                Game.getInstance().setNumOfDiceAttacker(-1);
                Game.getInstance().setNumOfDiceAttacked(-1);*/
            }
            else if(!prevOwner.equalsIgnoreCase(potentialDefender.getOwner().getName())){
                System.out.println("Defender "+potentialDefender.getName()+" conquered");
                if(territoryIterator.hasNext())
                    System.out.println("Next defender");
                else
                    System.out.println("Attacker does not have valid adjacent territories to attack");
                Game.getInstance().getCurrPlayer().diceThrowResults.add("Defender "+potentialDefender.getName()+" conquered<br/>");
                Game.getInstance().getCurrPlayer().diceThrowResults.add("Next Defender..<br/>");
                continue;
            }
            else {
                Game.getInstance().getCurrPlayer().attack(territoryClicked);
                System.out.println("Attack continues");
                Game.getInstance().getCurrPlayer().diceThrowResults.add("Attack Continues..<br/>");

            }
        }

        Game.getInstance().nextPhase();
        DiceRollView.getInstance().displayContent(Game.getInstance().getCurrPlayer().diceThrowResults);
        return null;
    }

    public Territory getStrongestTerritory(){
        int maxArmiesAndAdjacents = 0;
        //int maxNotOwnedAdjacents =0;
        Territory retTerr = null;

        for(Territory territory:Game.getInstance().getCurrPlayer().getTerritoriesOwned()){
            if(maxArmiesAndAdjacents < (territory.armies + getNotOwnedAdjacentsCount(territory))) {
                maxArmiesAndAdjacents = (territory.armies + getNotOwnedAdjacentsCount(territory));
                retTerr = territory;
            }
        }
        return retTerr;
    }

    public int getNotOwnedAdjacentsCount(Territory territory){
        int retVal = 0;
        for(String adj:territory.getAdjacents()){
            Territory adjObj = Game.getInstance().gameMap.getTerritory(adj);
            if(adjObj.getOwner() != territory.getOwner())
                retVal++;
        }
        return retVal;
    }
    public ArrayList<Territory> getNotOwnedAdjTerrObjects(Territory territory){
        ArrayList<Territory> retList = new ArrayList<>();
        for(String adj:territory.getAdjacents()){
            Territory adjObj = Game.getInstance().gameMap.getTerritory(adj);
            if(adjObj.getOwner() != territory.getOwner())
                retList.add(adjObj);
        }
        return retList;
    }
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
