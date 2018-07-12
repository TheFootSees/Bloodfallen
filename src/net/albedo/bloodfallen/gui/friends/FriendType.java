package net.albedo.bloodfallen.gui.friends;

public enum FriendType {
	
	NEUTRAL("Neutral", null, false),
	FRIEND("Friend",null, true),
	ENEMY("Enemy", null, false),
	TEAM_MEMBER("Team", null, true);
	
	private String name;
	private String colour;
	private boolean protection;
	
	private FriendType(String name, String colour, boolean protection){
		this.name = name;
		this.colour = colour;
		this.protection = protection;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public String getColour() {
		return colour;
	}
	
	public boolean isProtected() {
		return protection;
	}
}
