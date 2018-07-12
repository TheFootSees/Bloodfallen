package net.albedo.bloodfallen.saving;

import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.modules.values.ValuesRegistry;


public class ClientSettingsTask implements DataTask{

	@Override
	public String getFileName() {
		return "settings";
	}

	@Override
	public void read(Albedo client, JsonObject obj) {
		
		ValuesRegistry vr = client.getValuesRegistry();
		
		Iterator<Map.Entry<String,JsonElement>> it = obj.entrySet().iterator();
		
		while(it.hasNext()){
			Map.Entry<String,JsonElement> entry = it.next();
			vr.getRegistry().put(entry.getKey(), getElementValue(entry.getValue()));
		}
	}

	@Override
	public void write(Albedo client, JsonObject obj) {
		
		ValuesRegistry vr = client.getValuesRegistry();
		
		Iterator<Map.Entry<String,Object>> it = vr.getRegistry().entrySet().iterator();
		
		while(it.hasNext()){
			
			Map.Entry<String,Object> entry = it.next();
			
			Object object = entry.getValue();
			
			if(object instanceof Integer){
				obj.addProperty(entry.getKey(), (Integer)object);
			}else if(object instanceof Float){
				obj.addProperty(entry.getKey(), (Float)object);
			}else if(object instanceof Double){
				obj.addProperty(entry.getKey(), (Double)object);
			}else if(object instanceof String){
				obj.addProperty(entry.getKey(), (String)object);
			}else if(object instanceof Boolean){
				obj.addProperty(entry.getKey(), (Boolean)object);
			}else{
				System.out.println("ERROR - property not recognized");
			}
		}
	}
	
	/**
	 * Gets the object value of a JsonElement because GSON doesn't let you do it normally
	 */
	private Object getElementValue(JsonElement el){
		
		JsonPrimitive p = el.getAsJsonPrimitive();
		
		if(p.isNumber()){
			return p.getAsDouble();
		}else if(p.isString()){
			return p.getAsString();
		}else if(p.isBoolean()){
			return p.getAsBoolean();
		}
		
		System.out.println("ERROR - JsonElement not recognized");
		
		return null;
	}
}
