package net.albedo.bloodfallen.saving;

import com.google.gson.JsonObject;

import net.albedo.bloodfallen.Albedo;

public interface DataTask {
		
	public String getFileName();
	
	public void read(Albedo client, JsonObject obj);
	
	public void write(Albedo client, JsonObject obj);
}
