package Model;

/**
 *
 * It is the interface for different strategies which should
 * execute the methods defined in the interface respectively.
 */
public interface PlayerStrategyInterface {
    public String reinforce(String territoryClicked);

    public String fortify(String territoryClicked);

    public String attack(String territoryClicked);




}
