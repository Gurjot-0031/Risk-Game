package Model;

public class StrategyContextSetter {


    public PlayerStrategyInterface getStrategyObject(String strategyType) {
        PlayerStrategyInterface strategyObj = null;

        if(strategyType.equals("AGGRESSIVE"))
            strategyObj = new AggressiveStrategyPlayer();
        else if(strategyType.equals("BENEVOLENT"))
            strategyObj = new BenevolentStrategyPlayer();

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
