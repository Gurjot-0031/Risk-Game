package Model;

import java.io.Serializable;

public class StrategyContextSetter implements Serializable {
/**
 *
 * This class is used to create the object of the particular strategy
 * based on the type of strategy set in the interface object
 */



    public PlayerStrategyInterface getStrategyObject(String strategyType) {
        PlayerStrategyInterface strategyObj = null;

        if(strategyType.equals("AGGRESSIVE"))
            strategyObj = new AggressiveStrategyPlayer();
        else if(strategyType.equals("BENEVOLENT"))
            strategyObj = new BenevolentStrategyPlayer();
        else if(strategyType.equals("CHEATER"))
            strategyObj = new CheaterStrategyPlayer();
        else if(strategyType.equals("RANDOM"))
            strategyObj = new RandomStrategyPlayer();
        return strategyObj;


    }



    /*public void setStrategy(PlayerStrategyInterface strategy) {
      this.strategy=strategy;
    }

    public String reinforce() {
        return this.strategy.reinforce(null);
    }

    public String attack() {
        return this.strategy.attack(null);
    }

    public String fortify() {
        return this.strategy.fortify(null);
    }*/
}
