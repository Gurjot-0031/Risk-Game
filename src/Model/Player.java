package Model;

import java.awt.Color;

public class Player {
	private int id;
	private String name;
	private Color color;
	
	public Player(int Id, String name, Color color) {
		this.setId(Id);
		this.setColor(color);
		this.setName(name);
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
