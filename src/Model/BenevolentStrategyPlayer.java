package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class BenevolentStrategyPlayer implements PlayerStrategyInterface {
    @Override
    public String reinforce(String territoryClicked) {
        Iterator<Territory> territoryIterator = getWeakestTerritories().iterator();
        int i=1;
        System.out.println("Weakest territories::");
        while(territoryIterator.hasNext()) {
            Territory tempTerr = territoryIterator.next();
            System.out.println("Weakest territory " + i + " " + tempTerr.getName()+" armies: "+tempTerr.armies);
            i++;
        }

        while(Game.getInstance().getCurrPlayer().armies>0){
            Iterator<Territory> territoryIterator2 = getWeakestTerritories().iterator();
            //will reinforce the weakest territory first, then will update the weakest list again(code 2 lines above)
            Game.getInstance().getCurrPlayer().removeArmy(1);
            territoryIterator2.next().addArmy(1);
        }

        Iterator<Territory> territoryIterator3 = getWeakestTerritories().iterator();
        while(territoryIterator3.hasNext()) {
            Territory tempTerr = territoryIterator3.next();
            System.out.println("Territory " + tempTerr.getName()+" Updated armies: "+tempTerr.armies);
        }

        Game.getInstance().nextTurn();
        if(Game.getInstance().getGameTurn() == 0)
            Game.getInstance().nextPhase();
        return "Benevolent Reinforce completed";
    }

    @Override
    public String fortify(String territoryClicked) {
        System.out.println("Weakest territories, where fortifications need to be sent::");
        Iterator<Territory> territoryIterator = getWeakestTerritories().iterator();
        int i=1;
        System.out.println("Weakest territories::");
        while(territoryIterator.hasNext()) {
            Territory tempTerr = territoryIterator.next();
            System.out.println("Weakest territory " + i + " " + tempTerr.getName()+" armies: "+tempTerr.armies);
            i++;
        }

        for(Territory territory:getWeakestTerritories()){
            Game.getInstance().fortification_destination = territory.getName();
            Iterator<Territory> territoryIterator2 = getStrongestTerritories().iterator();
            while(territoryIterator2.hasNext()) {
                Territory tempTerr = territoryIterator2.next();
                if(territory.getAdjacents().contains(tempTerr)) {
                    Game.getInstance().fortification_source = tempTerr.getName();
                    Game.getInstance().getCurrPlayer().fortify("BenevolentPlayerFortifyCalled");
                    break;
                }
            }
        }

        for(Territory territory:Game.getInstance().getCurrPlayer().getTerritoriesOwned())
            System.out.println("Territory "+territory.getName()+" updated armies: "+territory.getArmies());

        Game.getInstance().nextTurn();
        if(Game.getInstance().getGameTurn() == 0)
            Game.getInstance().nextPhase();
        return "Benevolent Fortification completed";
    }

    @Override
    public String attack(String territoryClicked) {
        //Fortification phase will begin
        Game.getInstance().nextPhase();
        return "Benevolent Player: Attack skipped";
    }

    public ArrayList<Territory> getWeakestTerritories(){
        ArrayList<Territory> retList = Game.getInstance().getCurrPlayer().getTerritoriesOwned();
        /*for(Territory territory:Game.getInstance().getCurrPlayer().getTerritoriesOwned()){

        }*/
        Collections.sort(retList, new Comparator<Territory>() {
            @Override
            public int compare(Territory territory, Territory t1) {
                if(territory.armies==t1.armies)
                    return 0;
                else if(territory.armies < t1.armies)
                    return -1;
                else
                    return 1;
            }
        });

        return retList;

    }

    public ArrayList<Territory> getStrongestTerritories(){
        ArrayList<Territory> retList = Game.getInstance().getCurrPlayer().getTerritoriesOwned();

        Collections.sort(retList, new Comparator<Territory>() {
            @Override
            public int compare(Territory territory, Territory t1) {
                if(territory.armies==t1.armies)
                    return 0;
                else if(territory.armies < t1.armies)
                    return -1;
                else
                    return 1;
            }
        });
        Collections.reverse(retList);
        return retList;

    }


}