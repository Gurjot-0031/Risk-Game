package View;
import Controller.GameController;
import Model.Continent;
import Model.Game;
import Model.Player;
import Model.Territory;
import View.PhaseView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class WorldDominationView implements Observer {

    private static WorldDominationView instance;
    JLabel worldDominationViewLabel =new JLabel();
    ArrayList<Territory> territoriesList ;
    String[] continentList ;

    public void initWorldDomnationView(){

        worldDominationViewLabel.setBounds(1024,0,500,768);
        JFrame frameFromPhase = PhaseView.getInstance().getGameFrame();
        JPanel worldDominationViewPanel = new JPanel();
        worldDominationViewPanel.setBounds(1024,0,500,768);
        worldDominationViewPanel.add(worldDominationViewLabel);
        frameFromPhase.add(worldDominationViewPanel);

        Game.getInstance().addObserver(this);

    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof Game){
            String label="<html><head><h1>Map Domination</h1></head><br/> ";

            territoriesList = Game.getInstance().getGameMap().getTerritories();

            for (int i=0 ;i<Game.getInstance().getNumPlayers();i++){
                int tempTerritoryCount = 0;
                float percentage = 0;

                for(Territory territory:territoriesList){
                    if(territory.getOwner()==Game.getInstance().getPlayerById(i)){
                        tempTerritoryCount +=1;
                    }
                }
                percentage = (float) 100*tempTerritoryCount/Game.getInstance().getGameMap().getTerritories().size();

                label = label+ Game.getInstance().getPlayerById(i).getName()+" owns "+
                        percentage +" % territories and owns "+ PhaseView.getInstance().curPArmies+ "<br/>";
                worldDominationViewLabel.setText(label);
            }
            worldDominationViewLabel.setText(label+"</html>");

            /*Continenet domination starts here...
            continentList = Game.getInstance().getGameMap().getContinentsArray();

            for(String continent:continentList){
                int tempTerritoryCount = 0;
                float percentage = 0;

                    for(String territory: Game.getInstance().getGameMap().getTerritoriesArray(continent)){
                        if(territory == continent.)
                    }
                }
                percentage = (float) 100*tempTerritoryCount/Game.getInstance().getGameMap().getTerritories().size();

                label = label+ Game.getInstance().getPlayerById(i).getName()+" owns "+
                        percentage +" % territories and owns "+ PhaseView.getInstance().curPArmies+ "<br/>";
                worldDominationViewLabel.setText(label);
                */

        }

        else if(o instanceof GameController){

        }
    }

    public static WorldDominationView getInstance() {
        if(instance == null) {
            instance = new WorldDominationView();
        }
        return instance;
    }
}
