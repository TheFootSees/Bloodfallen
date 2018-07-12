package net.albedo.bloodfallen.gui.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.components.buttons.Button;
import net.albedo.bloodfallen.gui.gui.components.buttons.OptionsModButton;
import net.albedo.bloodfallen.gui.gui.components.scrolling.ScrollPane;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleManager;
import net.albedo.bloodfallen.tools.FontRENDERER;
import net.albedo.bloodfallen.utils.RenderUtils;

public class Window implements IRectangle {

	public String title;
	public int posX, posY, mouseXOffset, mouseYOffset;
	public boolean dragging, extended, pinned;
	protected List<AbstractComponent> components = new ArrayList<AbstractComponent>();
	public ArrayList<ModuleButton> moduleButton = new ArrayList<ModuleButton>();
	private Map<Font, FontRENDERER> fontRendererMap = new HashMap<>();
	private ModuleManager modManager;
	private Module m;

	public static int startX, startY;
	public static int endX, endY;
	public int openFade;
	public boolean done;
	public static float alpha;
	public static float delay;
	private boolean isSelected = false;
	public static boolean test = true;

	public static int TITLE_COMPONENT_SPACE = 2;
	public static int TITLE_SIZE = 12;

	public int minWidth;
	public int width;
	public int height;

	public Window(String title, ModuleManager modManager, int width) {
		this.title = title;
		this.width = width;
		this.minWidth = width;
		this.modManager = modManager;
		
	}

	public void renderWindow(int x, int y, boolean titleVisible) {

		if (Mouse.isButtonDown(2)) {

			endX = x;
			endY = y;
			RenderUtils.drawRect(startX, startY, x, y, RenderUtils.changeAlpha(0xff0431B4, 0.2f));

			if (startX >= posX + width && endX <= posX + width && startY >= height
					&& endY <= posY + height + TITLE_SIZE - 2) {

				System.out.println(title);
				isSelected = true;
				if (isSelected && extended) {
					RenderUtils.drawRect(posX, posY + height + 12, posX + width, posY - 4, 0xffFFFFFF);
				}
			}
		}

		if (!Mouse.isButtonDown(2)) {
			for (Window w : GuiManager.windows) {
			
				
				if (isSelected && extended) {
					RenderUtils.drawRect(posX, posY + height + 12, posX + width, posY - 4, 0xffFFFFFF);
				
				}
				if (isSelected && Mouse.isButtonDown(0)) {
	
					dragging = true;

				} else {
					if (Mouse.isButtonDown(1)) {
						isSelected = false;
					}
				}
			}
		}
		
		if (isSelected && dragging){
			posX = x + mouseXOffset;
		} else if (dragging){
			posX = x + mouseXOffset;
			posY = y + mouseYOffset;
		}
		
//		if (dragging) {
//			System.out.println("Dragging!");
//			posX = x + mouseXOffset;
//			posY = y + mouseYOffset;
//		}

		if (extended) {

			drawBodyRect(posX, posY + TITLE_SIZE + TITLE_COMPONENT_SPACE + 1, posX + width, (posY + TITLE_SIZE + TITLE_COMPONENT_SPACE + height), titleVisible);

			int elementY = posY + TITLE_SIZE + TITLE_COMPONENT_SPACE;
			for (AbstractComponent c : components) {

				c.draw(x, y);

				elementY += c.getHeight() + 1;
			}
			for (ModuleButton mb : moduleButton) {
				if (openFade > mb.getY() || done) {
					mb.draw(x, y);
				}
				elementY += mb.getHeight() + 1;
			}
		}

		if (titleVisible) {

			drawHeader(posX, posY, posX + width, posY + TITLE_SIZE);
			
			GuiManager.fr.drawStringWithShadow(title, posX + 20, posY - 7 + TITLE_SIZE / 2 , ColourType.TITLE_TEXT.getColour());
			
			if (hasExtensionButton()) {
				drawButton(posX + width - 10, posY + 2, posX + width - 2, posY + TITLE_SIZE - 2, extended);
			}

			// Toggle pinned
			if (hasPinnedButton()) {
				
				drawButton(posX + width - 22, posY + 2, posX + width - 14, posY + TITLE_SIZE - 2, pinned);
			}

		}

	}

	protected void drawButton(int x, int y, int x1, int y1, boolean enabled) {
		RenderUtils.drawRect(x, y, x1, y1, enabled ? 0x9FFFFFFF : 0x4FFFFFFF);
	}

	protected void drawHeader(int x, int y, int x1, int y1) {
		RenderUtils.drawRoundedRectTOP(posX, posY - 5, posX + width, posY + TITLE_SIZE + 3, ColourType.BORDER.getModifiedColour());
	}

	protected void drawBodyRect(int x, int y, int x1, int y1, boolean titleVisible) {
		RenderUtils.drawRoundedRectBOTTOM(x, y, x1, y1, 0x90000000);
	}

	public void mouseClicked(int x, int y) {

		if (hasExtensionButton() && mouseOverToggleExtension(x, y)) {
			onExtensionToggle();
			done = false;
		} else if (hasPinnedButton() && mouseOverTogglePinned(x, y)) {
			onPinnedToggle();
		} else if (mouseOverTitle(x, y)) {

			dragging = true;

			mouseXOffset = posX - x;
			mouseYOffset = posY - y;

			Albedo.getIrrlicht().gui.setDragging(this);
		}

		if (extended) {

			this.openFade = posY + 19;
			for (AbstractComponent c : components) {
				c.mouseClicked(x, y);

			}

			for (ModuleButton mb : moduleButton) {
				mb.mouseClicked(x, y);

			}
			return;
		} else {
		}
	}

	public void onGuiClosed() {

		this.dragging = false;

		for (AbstractComponent comp : components) {
			comp.onGuiClosed();
		}
	}

	public void onModManagerChange() {

		for (AbstractComponent comp : components) {
			comp.onModManagerChange();
		}
	}

	public void keyPress(char c, int key) {

		for (AbstractComponent comp : components) {
			comp.keyPress(key, c);
		}
	}

	public boolean hasExtensionButton() {
		return true;
	}

	public boolean hasPinnedButton() {
		return true;
	}

	public void onExtensionToggle() {
		extended = !extended;
		
		doClickSound();
	}

	public void onPinnedToggle() {
		pinned = !pinned;
		doClickSound();
		
	}

	protected void doClickSound() {
//		Client.getClient().getMinecraft().thePlayer.playSound("random.click", 1, 1);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {
		dragging = false;
		if (extended) {
			for (AbstractComponent c : components) {
				c.mouseReleased(mouseX, mouseY, state);
			}
		}
	}
	
	public boolean mouseOverTitle(int x, int y) {
		return x >= posX && x <= posX + width && y >= posY && y <= posY + TITLE_SIZE + TITLE_COMPONENT_SPACE;
	}

	public boolean mouseOverToggleExtension(int x, int y) {
		return x >= posX + width - 10 && x <= posX + width - 2 && y >= posY + 2 && y <= posY + TITLE_SIZE - 2;
	}

	public boolean mouseOverTogglePinned(int x, int y) {
		return x >= posX + width - 22 && x <= posX + width - 14 && y >= posY + 2 && y <= posY + TITLE_SIZE - 2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ModuleManager getModManager() {
		return modManager;
	}

	public void setModManager(ModuleManager modManager) {
		this.modManager = modManager;
	}

	public void addComponent(AbstractComponent c) {
		this.components.add(c);
		c.setRectangle(this);
		c.init();
		updateSize();
	}

	public void addComponentRaw(AbstractComponent c) {
		this.components.add(c);
		c.setRectangle(this);
		c.init();
	}
	
	@Override
	public void updateSize() {

		this.height = 0;
		this.width = minWidth;

		for (AbstractComponent component : components) {

			component.setY(height + TITLE_SIZE);
			height += component.getHeight();

			if (component.getWidth() > this.width) {
				this.width = component.getWidth();
			}
		}
	}

	@Override
	public int getRectX() {
		return posX;
	}

	@Override
	public int getRectY() {
		return posY + TITLE_COMPONENT_SPACE;
	}

}
