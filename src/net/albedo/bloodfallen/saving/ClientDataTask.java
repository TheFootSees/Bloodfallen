package net.albedo.bloodfallen.saving;

import com.google.gson.JsonObject;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.GuiManager;


public class ClientDataTask implements DataTask {

    @Override
    public String getFileName() {
        return "client";
    }

    @Override
    public void read(Albedo client, JsonObject obj) {
        GuiManager.opened = obj.get("opened-gui").getAsBoolean();

    }

    @Override
    public void write(Albedo client, JsonObject obj) {
        obj.addProperty("opened-gui", GuiManager.opened);

    }
}
