package net.albedo.bloodfallen.gui.gui.components.selectors;

import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Mouse;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.gui.components.buttons.OptionsModButton;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleManager;
import net.albedo.bloodfallen.tools.FontRENDERER;
import net.albedo.bloodfallen.utils.RenderUtils;

public class ModuleButton extends SelectorButton {

	private Module mod;
	private boolean exist;
	private ModuleManager modManaer;
	private String provider;
   

	public ModuleButton(Module data, SelectorSystem system) {
		super(data.getName(), system);
		this.mod = data;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void draw(int x, int y) {

		boolean mouseover = mouseOverButton(x, y, getX(), getY());

		if (mouseover) {

			RenderUtils.drawRect(getX(), getY(), getX() + rect.getWidth(), getY() + getHeight(), 0x2FFFFFFF);
		}

		if (mod.isToggled()) {
			
			GuiManager.fr.drawStringWithShadow(getText(), getX() + 40, getY() + 6, mouseover ? ColourType.HIGHLIGHT.getModifiedColour() : ColourType.HIGHLIGHT.getColour());
			GuiManager.fr.drawString(mod.getDescription(), getX() + 40, getY() + 18, 0xFFCFCFCF);
//			text.drawString(provider, getX() + 3, getY() + 26, 0xFF8F8F8F);
			
			
			
			RenderUtils.drawRoundedBorder(getX() + 5, getY() + 5, getX() + 32, getY() + 32, 0xffFFFFFF);

			if (mod.hasOptions()) {
				GuiManager.fr.drawString("**", getX() + rect.getWidth() - 9, getY() + 10, 0x9FFFFFFF);
			}

		} else {
            GuiManager.fr.drawStringWithShadow(getText(), getX() + 40, getY() + 6, mouseover ? ColourType.TEXT.getModifiedColour() : ColourType.TEXT.getColour());
            GuiManager.fr.drawString(mod.getDescription(), getX() + 40, getY() + 18, 0xFFCFCFCF);
//			text.drawString(provider, getX() + 3, getY() + 26, 0xFF8F8F8F);
//
			if (mod.hasOptions()) {
				GuiManager.fr.drawString("**", getX() + rect.getWidth() - 9, getY() + 10, 0x9FFFFFFF);
			}
			RenderUtils.drawRoundedBorder(getX() + 5, getY() + 5, getX() + 32, getY() + 32, 0xffFFFFFF);

		}

	}

	public void initSettingsWindows(ModuleManager modManager) {
	    
		Window settings = new Window(mod.getName(), modManager, 125);
		if (mod.hasOptions()) {
			settings.addComponent(new OptionsModButton(mod));
			Albedo.getIrrlicht().getGui().windows.add(settings);
		
		}
	}

	
	@Override
	public void mouseClicked(int x, int y) {

		exist = !exist;
		if (mouseOverButton(x, y, getX(), getY())) {
			if (Mouse.isButtonDown(0)) {
//				Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
				mod.toggle();
			}
			if (Mouse.isButtonDown(1)) {
				if (exist){
				
					initSettingsWindows(modManaer);
				} else {
				    for (Window win : Albedo.getIrrlicht().getGui().windows){
				    	if (win.title.equals(mod.getName())){
				             if (mod.hasOptions()){
				            	 Albedo.getIrrlicht().getGui().windows.remove(win);
				             }
				    	}
				    }
				}
			}
		}
	}

	@Override
	public String getText() {
		return super.getText();

		
	}

	@Override
	public int getHeight() {
		return 36;
	}

	public Module getMod() {
		return mod;
	}
	
	
}
