package Model;

import java.io.Serializable;
import java.util.Random;

public class RandomStrategyPlayer implements PlayerStrategyInterface, Serializable {
    @Override
    public String reinforce(String territoryClicked) {
        Random random = new Random();
        int armiesToReinforce ;
        int n = Game.getInstance().getCurrPlayer().armies + 1;
        //int n = 20 - 5 + 1;
        int i = random.nextInt() % n;
        armiesToReinforce =  i;


        Random random1 = new Random();
        Territory randomOwnedTerritory = Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random1.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));
        randomOwnedTerritory.addArmy(armiesToReinforce);
        Game.getInstance().getCurrPlayer().removeArmy(armiesToReinforce);
        System.out.println(armiesToReinforce+" armies reinforced to territory: "+ randomOwnedTerritory.getName());
        System.out.println("Armies left with the player: "+Game.getInstance().getCurrPlayer().armies);

        if((Game.getInstance().getGameTurn()+1) < Game.getInstance().getNumPlayers()){
            Game.getInstance().setGamePhase(2);
            Game.getInstance().setTurn(Game.getInstance().getGameTurn()+1);
        }
        else{
            Game.getInstance().setGamePhase(3);
            Game.getInstance().setTurn(0);
        }

        return "Random Player Reinforcement completed";
    }

    @Override
    public String fortify(String territoryClicked) {
        Random random = new Random();
        Territory randomSourceTerritory = Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));
        Territory randomDestinationTerritory ;

        while (true){
            randomDestinationTerritory =  Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));
            if(randomDestinationTerritory != randomSourceTerritory)
                break;
        }

        Random random1 = new Random();
        String armiesToMove ;
        int n = randomSourceTerritory.armies + 1;
        //int n = 20 - 5 + 1;
        int i = random.nextInt() % n;
        armiesToMove =  ""+i;

        Game.getInstance().fortification_source = randomSourceTerritory.getName();
        Game.getInstance().fortification_destination = randomDestinationTerritory.getName();
        Game.getInstance().getCurrPlayer().fortify(armiesToMove);
        System.out.println(armiesToMove+" armies moved from"+ Game.getInstance().fortification_source+" to "+Game.getInstance().fortification_destination);

        if(Game.getInstance().getGameTurn() == Game.getInstance().getNumPlayers()-1) {
            Game.getInstance().setGamePhase(2);
            Game.getInstance().setTurn(0);
        }
        else{
            Game.getInstance().setGamePhase(3);
            Game.getInstance().setTurn(Game.getInstance().getGameTurn()+1);
        }

        return "Random Player Fortification completed";
    }

    @Override
    public String attack(String territoryClicked) {
        System.out.println("Random Player Attack completed");

        Random random = new Random();
        int randomNoOfAttacks ;
        int n = 10 + 1;
        //int n = 20 - 5 + 1;
        int i = random.nextInt() % n;
        randomNoOfAttacks=  i;

        Territory randomAttackerTerritory = null;
        int count = 0;
        while(count < Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()){
            randomAttackerTerritory = Game.getInstance().getCurrPlayer().getTerritoriesOwned().get(random.nextInt(Game.getInstance().getCurrPlayer().getTerritoriesOwned().size()));
            if(randomAttackerTerritory.armies >2){
                Game.getInstance().setAlloutMode(true);
                Game.getInstance().setAttackerObj(randomAttackerTerritory);
                System.out.println("Random attacker territory selected: " +randomAttackerTerritory.getName());
                break;
            }
        }

        if(randomAttackerTerritory!=null){
            for(String territory:randomAttackerTerritory.getAdjacents()){
                Territory defenderTerrObj = Game.getInstance().gameMap.getTerritory(territory);
                if(defenderTerrObj.armies >1){
                    Game.getInstance().setAttackedObj(defenderTerrObj);
                    System.out.println("Random defender territory selected: " +defenderTerrObj.getName());
                    Game.getNewInstance().getCurrPlayer().attack("RandomStrategyAttackCalled");
                }

            }
        }

        Game.getInstance().setGamePhase(4);
        return "Random Player Attack completed";
    }
}
