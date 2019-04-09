package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import javax.swing.*;

import Controller.GameController;
import Controller.HomeController;
import Event.GameEvents;
import Event.MapEditorEvents;
import Model.Game;
//import javafx.stage.FileChooser;

public class HomeMenu implements Serializable {
    private HomeController objHomeController;

    private JMenuBar homeMenuBar;
    private HashMap<String, JMenu> homeMenus;

    public HomeMenu(HomeController objHomeController) {
        this.objHomeController = objHomeController;
        this.homeMenus = new HashMap<String, JMenu>();
    }

    public JMenuBar getMenuBar() {
        return this.homeMenuBar;
    }

    private void initMapEditorMenu(JMenuBar homeMenuBar) {
        JMenu loadGameMenu = new JMenu("Load Game");
        loadGameMenu.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int retValue = fileChooser.showOpenDialog(null);
                File selectedFile = null;
                if (retValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Game File Selected: " + selectedFile.getAbsolutePath());
                }

                Game.getNewInstance();
                Game.getNewInstance().loadSavedGame(selectedFile);
                GameController.getInstance();
                //Game.getInstance().loadSavedGame(selectedFile);
                //PhaseView.getInstance().loadFrame();
                PhaseView.getInstance().loadMap(Game.getInstance().getGameMap());
                return;
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        JMenu mapEditorMenu = new JMenu("MapEditor");
        mapEditorMenu.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");
                MapEditorEvents objEvent = new MapEditorEvents();
                objEvent.setEventInfo("MapEditor");
                objHomeController.eventTriggered(objEvent);
                return;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        homeMenuBar.add(loadGameMenu);
        homeMenuBar.add(mapEditorMenu);
        homeMenus.put("MapEditor", mapEditorMenu);
    }

    private void initFileMenu(JMenuBar homeMenuBar) {
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("New Game");
                JFrame configFrame = new JFrame("Config");
                JPanel configPanel = new JPanel();
                configFrame.setSize(600,600);
                configPanel.setSize(600, 600);
                configFrame.add(configPanel);


                Integer[] values = { 2, 3, 4, 5, 6 };
                Integer[] mapValues = { 1, 2, 3, 4, 5 };
                JLabel plLabel = new JLabel("Players #");
                //plLabel.setBounds(10,10,30,15);
                JComboBox<Integer> numPlayers = new JComboBox<Integer>(values);
                //numPlayers.setBounds(45, 10,30,15);

                JLabel mapLabel = new JLabel("Map #");
                //mapLabel.setBounds(10,30,30,15);
                JComboBox<Integer> numOfMaps = new JComboBox<Integer>(mapValues);
                //numOfMaps.setBounds(45,30,30,15);

                JLabel gameLabel = new JLabel("Game #");
                //gameLabel.setBounds(10,50,30,15);
                Integer[] gameValues = { 1, 2, 3, 4, 5 };
                JComboBox<Integer> numOfGames = new JComboBox<Integer>(gameValues);
                //numOfGames.setBounds(45,50,30,15);





                //Integer[] turns = { 1, 2, 3, 4, 5 };
                JTextField noOfTurns = new JTextField("Enter number of Turns");
                for (int i = 0; i < Integer.parseInt(numPlayers.getSelectedItem().toString()) ; i++) {
                    JLabel templabel = new JLabel("Player");
                }


                configPanel.add(plLabel);
                configPanel.add(numPlayers);

                configPanel.add(mapLabel);
                configPanel.add(numOfMaps);

                configPanel.add(gameLabel);
                configPanel.add(numOfGames);

                configPanel.add(noOfTurns);



                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);

                JButton loadMap = new JButton("Choose Map");
                loadMap.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int returnValue = fileChooser.showOpenDialog(null);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File[] selectedFiles = fileChooser.getSelectedFiles();
                            for(int i=0;i<selectedFiles.length;i++){
                                System.out.println("File Selected: " + selectedFiles[i].getAbsolutePath());
                            }

                        }
                    }
                });


                configPanel.add(loadMap);
               /* configPanel.add(loadMap2);
                configPanel.add(loadMap3);
                configPanel.add(loadMap4);
                configPanel.add(loadMap5);*/
                int result = JOptionPane.showConfirmDialog(null, configPanel, "Please Configure",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    if (fileChooser.getSelectedFiles() != null) {
                        String names = "";
                        String[] playerType = {"HUMAN", "AGGRESSIVE", "BENEVOLENT", "RANDOM", "CHEATER"};


                        String name = "";
                        String type = "";
                        for (int i = 0; i < Integer.parseInt(numPlayers.getSelectedItem().toString()); i++) {
                            //String name = JOptionPane.showInputDialog("Player " + i+1 + " name: ");
                            JTextField playerName = new JTextField();
                            JComboBox<String> typePlayers = new JComboBox<>(playerType);
                            Object[] message = {
                                    "Player " + (1+i) + " name: ", playerName,
                                    "Input value 2:", typePlayers,
                            };
                            int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);

                            if (option == JOptionPane.OK_OPTION)
                            {
                                name = playerName.getText();
                                type = typePlayers.getSelectedItem().toString();
                            }
                            if (name == null || name.length() < 1) {
                                System.out.println("Name cannot be empty. Try again");
                                System.out.println("*" + playerName + "*");
                                i--;
                                continue;
                            }
                            names += "," + name + "," + type;
                        }
                        for(int i=0;i<fileChooser.getSelectedFiles().length;i++){

                            for(int j=0; j<Integer.parseInt(numOfGames.getSelectedItem().toString()); j++){
                                GameEvents objEvent = new GameEvents();
                                objEvent.setEventInfo("New Game");
                                String eventData = numPlayers.getSelectedItem().toString() + ","
                                        + fileChooser.getSelectedFiles()[i].getAbsolutePath() + names+
                                        ","+noOfTurns.getText() +"," + numOfGames.getSelectedItem().toString()+
                                        ","+ fileChooser.getSelectedFiles().length;
                                objEvent.setEventData(eventData);

                                HomeController newHomeControllerObject = new HomeController();
                                Game.getNewInstance();
                                newHomeControllerObject.eventTriggered(objEvent);
                            }




                            /*try{
                                Thread.sleep(20000);
                            }
                            catch (InterruptedException E){
                                E.printStackTrace();
                            }*/
                        }

                    } else {
                        System.out.println("Cannot start new game. Invalid Configuration");
                    }
                }
            }
        });
        fileMenu.add(newGame);

        homeMenuBar.add(fileMenu);
        homeMenus.put("File", fileMenu);
    }

    public void initMenuBar() {
        homeMenuBar = new JMenuBar();
        initFileMenu(homeMenuBar);
        initMapEditorMenu(homeMenuBar);
    }

}
