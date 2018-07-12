package net.albedo.bloodfallen.gui.gui.components.selectors;

import java.awt.Color;
import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.utils.RenderUtils;

public class KeybindButton extends SelectorButton{
	
	private Module mod;
//	private FontRenderer fontRenderer;
	private String provider;
//	private UnicodeFontRenderer text;
	
	public KeybindButton(Module data, SelectorSystem system) {
		super(data.getName(), system);
		this.mod = data;
//		text = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}
	
	@Override
	public void draw(int x, int y) {
		
		boolean mouseover = mouseOverButton(x, y, getX(), getY());
		boolean mouseOver2 = mouseOverButton(x + 14, y, getX() + 213, getY());
		
		if(mouseover){
			RenderUtils.drawRect(getX(), getY(), getX()+rect.getWidth(), getY()+getHeight(), 0x2FFFFFFF);
		}
		
		if (mouseOver2 && Mouse.isButtonDown(0)){
			mod.setKeybind(0);
		}

		
		if(isEnabled()){
//			drawDeleteButton();
//			fontRenderer.drawString(".", getX()+40, getY()+20, 0xFFCFCFCF);
			GuiManager.fr.drawStringWithShadow("KEY " + mod.getName(), getX()+40, getY()+8, mouseover? ColourType.HIGHLIGHT.getModifiedColour():ColourType.HIGHLIGHT.getColour());
			GuiManager.fr.drawString("Bound to " + mod.getName(), getX()+40, getY()+20, 0xFFCFCFCF);
			RenderUtils.drawBorder(getX() + 5, getY() + 5, getX() + 32, getY() + 32, 0xffFFFFFF, 0xffFFFFFF);;
//			GuiManager.fr.drawString(provider, getX()+3, getY()+26, 0xFFCFCFCF);
			
		}else{
//			drawDeleteButton();
//			fontRenderer.drawString(".", getX()+10000, getY()+20, 0xFFCFCFCF);
			GuiManager.fr.drawStringWithShadow("KEY " + mod.getName(), getX()+40, getY()+8, mouseover? ColourType.TEXT.getModifiedColour():ColourType.TEXT.getColour());
			GuiManager.fr.drawString("Bound to " + mod.getName(), getX()+40, getY()+20, 0xFFCFCFCF);
			
			RenderUtils.drawBorder(getX() + 5, getY() + 5, getX() + 32, getY() + 32, 0xffFFFFFF,0xffFFFFFF);
			
		}
		
	}
//	
//	public void drawDeleteButton(){
//		
//		  Gui.drawGradientRect(getX() + 200, getY() + 22, getX() + 213, getY() + 35, 0xff8A0808, 0xff3B0B0B);
//		  GuiUtils.drawRoundedBorder(getX() + 200, getY() + 22, getX() + 213, getY() + 35, 0xff000000);
//		
//		  GL11.glPushMatrix();
//	      Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("cube/Trash.png"));
//	      GL11.glTranslatef(getX() + getWidth() + 121 + 0.3F, getY() + 28 + 0.7F, 0.0F);
//	      Gui.drawScaledCustomSizeModalRect(-6, -6, 0.0F, 0.0F, 12, 12, 12, 12, 12.0F, 12.0F);
//	      GL11.glPopMatrix();
//	
//	}

	
//	@Override
//	public void mouseClicked(int x, int y) {
//		boolean mouseOver2 = mouseOverButton(x + 14, y, getX() + 213, getY());
//		if(mouseOverButton(x, y, getX(), getY()) && mouseOver2){
//			Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
//			mod.setKeybind(0);
//		} 
//	}

	
	@Override
	public String getText() {
		
		if(mod.getName() != ""){
			return super.getText() + " (" + mod.getKeyName() + ")";
		}
		return provider;
	}

	@Override
	public int getHeight() {
		return 36;
	}
	
	public Module getMod() {
		return mod;
	}
}