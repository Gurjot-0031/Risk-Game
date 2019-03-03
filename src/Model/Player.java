package Model;

import java.awt.Color;

public class Player {
	private int id;
	private String name;
	private Color color;
	int armies;
	
	public Player(int Id, String name, Color color) {
		this.setId(Id);
		this.setColor(color);
		this.setName(name);
		this.armies = 0;
	}
	
	public int getArmies() {
		return this.armies;
	}
	
	public void setArmies(int armies) {
		this.armies = armies;
	}
	
	public boolean removeArmy(int num) {
		if(num > this.armies) {
			System.out.println("Army removal Failed");
			return false;
		}
		this.armies -= num;
		return true;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
}
