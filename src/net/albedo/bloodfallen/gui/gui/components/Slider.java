package net.albedo.bloodfallen.gui.gui.components;

import java.awt.Font;
import java.text.DecimalFormat;

import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.modules.values.SliderValue;
import net.albedo.bloodfallen.utils.RenderUtils;

public class Slider extends AbstractComponent{
	
	private static int BAR_WIDTH = 10;

	private SliderValue val;
	public int slide, mouseXOffset;
	protected int maxSlide;
	protected int minSlide;
	private boolean dragging;
//	private FontRenderer text;
	public static DecimalFormat sfThree = new DecimalFormat("#0.00");
	
	public Slider(SliderValue val) {
		this.val = val;
//		text = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}
	
	@Override
	public void draw(int x, int y) {
		
		if(dragging){
			
			slide = x + mouseXOffset;
			
			double factor = (slide - minSlide)/(double)(rect.getWidth());
			double range = val.getUpperBound() - val.getLowerBound();
			double addition = range*factor;
			
			val.setValue(addition+val.getLowerBound());
		}
		
		if (this.slide > maxSlide + rect.getWidth()) {
		      this.slide = maxSlide + rect.getWidth();
		    }
		    if (this.slide < minSlide) {
		      this.slide = minSlide;
		    }
		    

//		GuiUtils.drawRect(cx, cy, cx+panel.getWidth(), cy+getHeight(), 0x2FDFDFDF);
		RenderUtils.drawRect(getX(), getY(), getX() + slide, getY()+getHeight(), 0x7F9F9F9F);
		
		String displayString = val.getName()+": "+(val.isRounded()? val.getValue().intValue():sfThree.format(val.getValue()));
		
		GuiManager.fr.drawString(displayString, getX() + rect.getWidth()/2 - GuiManager.fr.getStringWidth(displayString)/2, getY() -1, 0xFFFFFFFF);
	}
	
	@Override
	public void mouseClicked(int x, int y) {
		if(isHovering(x, y)){
			dragging = true;
			mouseXOffset = slide - x;
		}
	}
	
	@Override
	public void onGuiClosed() {
		dragging = false;
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		dragging = false;
	}

	public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() + 36 && mouseY >= this.getY() + 1 && mouseY <= this.getY() + this.getHeight() - 1;
    }
	
	@Override
	public int getHeight() {
		return 12;
	}

}
