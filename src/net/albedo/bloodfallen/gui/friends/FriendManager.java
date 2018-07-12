package net.albedo.bloodfallen.gui.friends;

import java.util.HashMap;
import java.util.Map;

public class FriendManager {
	
	private Map<String,FriendType> friends;
	
	public FriendManager(){
		friends = new HashMap<String,FriendType>();
	}
	
	public FriendType getFriendType(String username){
		return friends.get(username);
	}
	
	public void setFriendType(String username, FriendType type){
				
		friends.remove(username);
		
		if(type != null && type != FriendType.NEUTRAL){
			friends.put(username, type);
		}
	}
}
