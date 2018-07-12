package net.albedo.bloodfallen.saving;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleManager;


public class ModDataTask implements DataTask{

	@Override
	public String getFileName() {
		return "mods";
	}

	@Override
	public void read(Albedo client, JsonObject obj) {

		JsonArray arr = obj.get("mods").getAsJsonArray();

		for(JsonElement el : arr){

			JsonObject save = el.getAsJsonObject();

			JsonElement modElement = save.get("name");

			if(!modElement.isJsonNull()){
				
				Module mod = ModuleManager.getModuleByName(modElement.getAsString());
				
				if(mod != null){

					if(save.get("enabled").getAsBoolean()){
						ModuleManager.toggle(mod);
					}

					mod.setKeybind(save.get("keybind").getAsInt());
				}
			}
		}
		
		GuiManager.onModManagerChange();
	}

	@Override
	public void write(Albedo client, JsonObject obj) {
		
		JsonArray arr = new JsonArray();
		
		for(Module mod : ModuleManager.modules){
			JsonObject save = new JsonObject();
			save.addProperty("name", mod.getName());
			save.addProperty("enabled", mod.isToggled()? "true":"false");
			save.addProperty("keybind", mod.getKeybind());
			arr.add(save);
		}
		
		obj.add("mods", arr);
	}
	
}
