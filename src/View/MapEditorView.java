package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Controller.HomeController;
import Controller.MapController;
import Controller.MapEditorController;
import Event.IEvent;
import Event.MapEditorEvents;

public class MapEditorView {
	private static MapEditorController objController;
	private JFrame mapEditorFrame;
	private static MapEditorView instance;

	public static MapEditorView getInstance(MapEditorController objController) {
		if(instance == null) {
			instance = new MapEditorView(objController);
		}
		return instance;
	}

	public void loadFrame() {
		if(mapEditorFrame == null) {
			initFrame();
		}
		mapEditorFrame.setVisible(true);
	}

	private MapEditorView(MapEditorController objController) {
		MapEditorView.objController = objController;
	}

	public void initFrame() {
		mapEditorFrame = new JFrame("Map Editor");
		mapEditorFrame.setSize(1150, 800);
		mapEditorFrame.setResizable(false);
		mapEditorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mapEditorFrame.getContentPane().setBackground(Color.WHITE);
		mapEditorFrame.getContentPane().setLayout(null);

		JLabel label = new JLabel("Load Existing Map: ");
		mapEditorFrame.getContentPane().add(label);
		label.setBounds(10, 10, 150, 20);

		JButton btnBrowse = new JButton("Browse");
		mapEditorFrame.getContentPane().add(btnBrowse);
		btnBrowse.setBounds(140, 10, 100, 20);
		btnBrowse.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{
					final JComboBox<String> listOfTerritories = new JComboBox<String>();
					final JComboBox<String> listOfAdjacents = new JComboBox<String>();

					mapEditorFrame.getContentPane().add(listOfAdjacents);
					listOfAdjacents.setBounds(600, 70, 250, 20);

					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("File Selected: " + selectedFile.getAbsolutePath());
					MapEditorEvents objEvent = new MapEditorEvents();
					objEvent.setEventFile(selectedFile);
					objEvent.setEventInfo("MapEditorBrowse");
					objController.eventTriggered(objEvent);

					showEditButtons(listOfTerritories, listOfAdjacents, false);
				}
				return;
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});

		JLabel lblCreateMap = new JLabel("Author for New Map: ");
		mapEditorFrame.getContentPane().add(lblCreateMap);
		lblCreateMap.setBounds(350, 10, 170, 20);

		JTextField txtAuthor = new JTextField();
		mapEditorFrame.getContentPane().add(txtAuthor);
		txtAuthor.setBounds(490, 10, 100, 20);

		JLabel lblPath = new JLabel("Path for New Map: ");
		mapEditorFrame.getContentPane().add(lblPath);
		lblPath.setBounds(620, 10, 130, 20);

		JTextField txtPath = new JTextField();
		mapEditorFrame.getContentPane().add(txtPath);
		txtPath.setBounds(750, 10, 250, 20);

		JButton btnCreate = new JButton("Create Map");
		mapEditorFrame.getContentPane().add(btnCreate);
		btnCreate.setBounds(550, 35, 150, 20);
		btnCreate.addMouseListener(new MouseListener() {
			@Override public void mouseReleased(MouseEvent e) {}
			@Override public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtAuthor.getText().length() < 1 || txtPath.getText().length() < 1) {
					System.out.println("Please mention author name and path for new map");
					return;
				}
				final JComboBox<String> listOfTerritories = new JComboBox<String>();
				final JComboBox<String> listOfAdjacents = new JComboBox<String>();

				mapEditorFrame.getContentPane().add(listOfAdjacents);


				System.out.println("Creating New Map at: " + txtPath.getText());
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("MapEditorCreate");
				objEvent.setEventData(txtAuthor.getText() + "," + txtPath.getText());
				objController.eventTriggered(objEvent);

				showEditButtons(listOfTerritories, listOfAdjacents, true);

				return;
			}
			@Override public void mouseEntered(MouseEvent e) {}
			@Override public void mouseExited(MouseEvent e) {}
		});
	}

	public void showEditButtons(JComboBox<String> listOfTerritories, JComboBox<String> listOfAdjacents, boolean isNew) {

		String[] continents = MapController.getInstance().getContinentsArray();
		if(continents == null) {
			return;
		}

		JLabel label2 = new JLabel("List of continents");
		mapEditorFrame.getContentPane().add(label2);
		label2.setBounds(30, 60, 250, 20);

		JComboBox<String> listOfContinents = new JComboBox<String>(continents);
		mapEditorFrame.getContentPane().add(listOfContinents);

		listOfContinents.setBounds(30, 80, 250, 20);
		if(isNew == false)
			listOfContinents.setSelectedIndex(0);
		listOfContinents.addItemListener(new ItemListener() {
			@Override public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Object selectedContinent = e.getItem();
					try {
						String[] territories = MapController.getInstance().getTerritoriesArray(selectedContinent.toString());
						final DefaultComboBoxModel<String> territoriesModel = new DefaultComboBoxModel<String>(territories);
						listOfTerritories.setModel(territoriesModel);

						String tmpTerritory = territories[0];
						String[] tmpAdjacents = MapController.getInstance().getAdjacents(tmpTerritory);
						final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
						listOfAdjacents.setModel(adjacentModel);
					}
					catch(Exception eNC) {
						System.out.println("New Continent. Territory Information not loaded");
					}
				}
			}
		});

		JLabel label3 = new JLabel("List of territories");
		mapEditorFrame.getContentPane().add(label3);
		label3.setBounds(330, 60, 250, 20);



		mapEditorFrame.getContentPane().add(listOfTerritories);

		if(listOfContinents.getSelectedItem() != null) {
			String selectedContinent = listOfContinents.getSelectedItem().toString();
			String[] territories = MapController.getInstance().getTerritoriesArray(selectedContinent);
			DefaultComboBoxModel<String> territoriesModel = new DefaultComboBoxModel<String>(territories);
			listOfTerritories.setModel(territoriesModel);
		}
		listOfTerritories.setBounds(330, 80, 250, 20);
		listOfTerritories.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedTerritory = e.getItem().toString();
					String[] tmpAdjacents = MapController.getInstance().getAdjacents(selectedTerritory);
					final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
					listOfAdjacents.setModel(adjacentModel);
				}
			}
		});
		JLabel label4 = new JLabel("List of adjacent territories");
		mapEditorFrame.getContentPane().add(label4);
		label4.setBounds(640, 60, 250, 20);
		listOfAdjacents.setBounds(640, 80, 250, 20);

		if(listOfTerritories.getSelectedItem() != null) {
			String selectedTerritory = listOfTerritories.getSelectedItem().toString();
			String[] tmpAdjacents = MapController.getInstance().getAdjacents(selectedTerritory);
			final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
			listOfAdjacents.setModel(adjacentModel);
		}

		JLabel labelEnterContinent = new JLabel("Enter Continent name");
		mapEditorFrame.getContentPane().add(labelEnterContinent);
		labelEnterContinent.setBounds(100, 150, 350, 20);

		JLabel label5 = new JLabel("(Input format: Continent Name, Control value)");
		mapEditorFrame.getContentPane().add(label5);
		label5.setBounds(100, 190, 350, 20);
		JTextField inputContinent = new JTextField();

		mapEditorFrame.getContentPane().add(inputContinent);
		inputContinent.setBounds(100, 170, 250, 20);

		JButton btnAddContinent = new JButton("Add Continent");
		mapEditorFrame.getContentPane().add(btnAddContinent);
		btnAddContinent.setBounds(100, 350, 250, 20);
		btnAddContinent.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(inputContinent.getText().length() < 1) {
					System.out.println("Please enter valid continent name");
					return;
				}
				else if(inputContinent.getText().split(",").length < 2) {
					System.out.println("Please try again. Valid format is: <continent name>,<reward>");
					return;
				}
				else {
					try {
						Integer tmpCheck = Integer.parseInt(inputContinent.getText().split(",")[1]);
						if(tmpCheck < 0) {
							System.out.println("Continent reward cannot be less than 0");
							return;
						}
					}
					catch(NumberFormatException nfe) {
						System.out.println("Invalid reward number. Valid format is: <continent name>,<reward>");
						System.out.println(nfe);
						return;
					}
				}
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Add Continent");
				objEvent.setEventData(inputContinent.getText());
				objController.eventTriggered(objEvent);

				String[] continents = MapController.getInstance().getContinentsArray();
				final DefaultComboBoxModel<String> continentsModel = new DefaultComboBoxModel<String>(continents);
				listOfContinents.setModel(continentsModel);
			}
		});


		JButton btnDelContinent = new JButton("Delete Continent");
		mapEditorFrame.getContentPane().add(btnDelContinent);
		btnDelContinent.setBounds(100, 400, 250, 20);
		btnDelContinent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputContinent.getText().length() < 1) {
					System.out.println("Please enter valid continent name");
					return;
				}
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Delete Continent");
				objEvent.setEventData(inputContinent.getText());
				objController.eventTriggered(objEvent);

				String[] continents = MapController.getInstance().getContinentsArray();
				final DefaultComboBoxModel<String> continentsModel = new DefaultComboBoxModel<String>(continents);
				listOfContinents.setModel(continentsModel);
			}
		});

		JButton btnChangeReward = new JButton("Change Reward");
		mapEditorFrame.getContentPane().add(btnChangeReward);
		btnChangeReward.setBounds(100, 450, 250, 20);
		btnChangeReward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputContinent.getText().length() < 1) {
					System.out.println("Please enter valid continent name");
					return;
				}
				else if(inputContinent.getText().split(",").length < 2) {
					System.out.println("Please try again. Valid format is: <continent name>,<reward>");
					return;
				}
				else {
					try {
						Integer tmpCheck = Integer.parseInt(inputContinent.getText().split(",")[1]);
						if(tmpCheck < 0) {
							System.out.println("Continent reward cannot be less than 0");
							return;
						}
					}
					catch(NumberFormatException nfe) {
						System.out.println("Invalid reward number. Valid format is: <continent name>,<reward>");
						System.out.println(nfe);
						return;
					}
				}
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Change Reward");
				objEvent.setEventData(inputContinent.getText());
				objController.eventTriggered(objEvent);
			}
		});


		JLabel label6= new JLabel("Enter territory name");
		mapEditorFrame.getContentPane().add(label6);
		label6.setBounds(600, 150, 250, 20);
		JTextField inputTerritory = new JTextField();
		mapEditorFrame.getContentPane().add(inputTerritory);
		inputTerritory.setBounds(750, 150, 100, 20);

		JLabel label7= new JLabel("Enter X-coordinate");
		mapEditorFrame.getContentPane().add(label7);
		label7.setBounds(600, 190, 250, 20);
		JTextField inputTerritoryX = new JTextField();
		mapEditorFrame.getContentPane().add(inputTerritoryX);
		inputTerritoryX.setBounds(750, 190, 30, 20);

		JLabel label8= new JLabel("Enter Y-coordinate");
		mapEditorFrame.getContentPane().add(label8);
		label8.setBounds(600, 230, 250, 20);
		JTextField inputTerritoryY = new JTextField();
		mapEditorFrame.getContentPane().add(inputTerritoryY);
		inputTerritoryY.setBounds(750, 230, 30, 20);


		JLabel label9= new JLabel("Enter adjacent territories seperated by comma");
		mapEditorFrame.getContentPane().add(label9);
		label9.setBounds(600, 270, 330, 20);
		JTextField inputAdjacents = new JTextField();
		mapEditorFrame.getContentPane().add(inputAdjacents);
		inputAdjacents.setBounds(600, 290, 250, 20);


		JButton btnAddTerritory = new JButton("Add Territory");
		mapEditorFrame.getContentPane().add(btnAddTerritory);
		btnAddTerritory.setBounds(600, 350, 250, 20);
		btnAddTerritory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if((inputTerritory.getText().length() < 1) || (inputAdjacents.getText().length() < 1)) {
					System.out.println("Please fill all fields");
					return;
				}
				try {
					Integer x = Integer.parseInt(inputTerritoryX.getText());
					Integer y = Integer.parseInt(inputTerritoryY.getText());
					if(x < 0 || y < 0) {
						System.out.println("X and Y values cant be negative");
						return;
					}
				}
				catch(NumberFormatException nfe) {
					System.out.println(nfe);
					System.out.println("X and Y values must be integers");
					return;
				}

				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Add Territory");
				objEvent.setEventData(listOfContinents.getSelectedItem().toString() + "," +
						inputTerritory.getText() + "," + inputTerritoryX.getText() + "," +
						inputTerritoryY.getText() + "," + inputAdjacents.getText());
				objController.eventTriggered(objEvent);

				Object selectedContinent = listOfContinents.getSelectedItem().toString();
				String[] territories = MapController.getInstance().getTerritoriesArray(selectedContinent.toString());
				final DefaultComboBoxModel<String> territoriesModel = new DefaultComboBoxModel<String>(territories);
				listOfTerritories.setModel(territoriesModel);

				String tmpTerritory = territories[0];
				String[] tmpAdjacents = MapController.getInstance().getAdjacents(tmpTerritory);
				final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
				listOfAdjacents.setModel(adjacentModel);
			}
		});

		JButton btnDelTerritory = new JButton("Delete Territory");
		mapEditorFrame.getContentPane().add(btnDelTerritory);
		btnDelTerritory.setBounds(600, 400, 250, 20);
		btnDelTerritory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputTerritory.getText().length() < 1) {
					System.out.println("Please enter the territory name");
					return;
				}
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Delete Territory");
				objEvent.setEventData(listOfContinents.getSelectedItem().toString() + "," +
						inputTerritory.getText());
				objController.eventTriggered(objEvent);

				Object selectedContinent = listOfContinents.getSelectedItem().toString();
				String[] territories = MapController.getInstance().getTerritoriesArray(selectedContinent.toString());
				final DefaultComboBoxModel<String> territoriesModel = new DefaultComboBoxModel<String>(territories);
				listOfTerritories.setModel(territoriesModel);

				if(territories.length > 0) {
					String tmpTerritory = territories[0];
					String[] tmpAdjacents = MapController.getInstance().getAdjacents(tmpTerritory);
					final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
					listOfAdjacents.setModel(adjacentModel);
				}
			}
		});

		JButton btnAddAdjacent = new JButton("Add Adjacent");
		mapEditorFrame.getContentPane().add(btnAddAdjacent);
		btnAddAdjacent.setBounds(600, 450, 250, 20);
		btnAddAdjacent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputTerritory.getText().length() < 1) {
					System.out.println("Please enter the adjacent territory name");
					return;
				}
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Add Adjacent");
				objEvent.setEventData(listOfContinents.getSelectedItem().toString() + "," +
						listOfTerritories.getSelectedItem().toString() + "," +
						inputTerritory.getText());
				objController.eventTriggered(objEvent);

				String tmpTerritory = listOfTerritories.getSelectedItem().toString();
				String[] tmpAdjacents = MapController.getInstance().getAdjacents(tmpTerritory);
				final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
				listOfAdjacents.setModel(adjacentModel);
			}
		});

		JButton btnRemoveAdjacent = new JButton("Remove Adjacent");
		mapEditorFrame.getContentPane().add(btnRemoveAdjacent);
		btnRemoveAdjacent.setBounds(600, 500, 250, 20);
		btnRemoveAdjacent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputTerritory.getText().length() < 1) {
					System.out.println("Please enter the adjacent territory name");
					return;
				}
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Delete Adjacent");
				objEvent.setEventData(listOfContinents.getSelectedItem().toString() + "," +
						listOfTerritories.getSelectedItem().toString() + "," +
						inputTerritory.getText());
				objController.eventTriggered(objEvent);

				String tmpTerritory = listOfTerritories.getSelectedItem().toString();
				String[] tmpAdjacents = MapController.getInstance().getAdjacents(tmpTerritory);
				final DefaultComboBoxModel<String> adjacentModel = new DefaultComboBoxModel<String>(tmpAdjacents);
				listOfAdjacents.setModel(adjacentModel);
			}
		});

		JButton btnSaveChanges = new JButton("Save Map");
		mapEditorFrame.getContentPane().add(btnSaveChanges);
		btnSaveChanges.setBounds(350, 580, 250, 30);
		btnSaveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MapEditorEvents objEvent = new MapEditorEvents();
				objEvent.setEventInfo("Save Map");
				objController.eventTriggered(objEvent);
			}
		});
	}

}
