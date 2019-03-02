package Model;

import java.util.ArrayList;

public class Territory {
	String name;
	int x;
	int y;
	ArrayList<String> adjacents;
	String owner;
	
	public Territory(String name, int x, int y, ArrayList<String> adjacents) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.adjacents = adjacents;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public ArrayList<String> getAdjacents() {
		return this.adjacents;
	}
	
	public boolean checkAdjacent(String adjacent) {
		if(this.adjacents.contains(adjacent)) {
			return true;
		}
		return false;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void removeAdjacent(String adjacent) {
		if(this.adjacents.contains(adjacent)) {
			this.adjacents.remove(adjacent);
		}
	}
	
	public boolean addAdjacent(String adjacent) {
		if(this.adjacents.contains(adjacent)) {
			return false;
		}
		this.adjacents.add(adjacent);
		return true;
	}
}
