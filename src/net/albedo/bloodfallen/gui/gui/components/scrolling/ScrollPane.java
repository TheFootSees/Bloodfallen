package net.albedo.bloodfallen.gui.gui.components.scrolling;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.gui.components.buttons.Button;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.utils.RenderUtils;

public class ScrollPane extends AbstractComponent implements IRectangle {

	protected List<AbstractComponent> components = new ArrayList<AbstractComponent>();
	protected int height, viewportHeight, dragged, mouseYOffset;
	private boolean dragging;

	private static int BAR_WIDTH = 4;
//	private FontRenderer text;

	public ScrollPane(int viewportHeight) {
		this.viewportHeight = viewportHeight;
//		text = new UnicodeFontRenderer(new Font("Roboto Regular", 0, 20));
	}

	@Override
	public void draw(int x, int y) {

		int barHeight = getBarHeight();
		// if(mouseOverBar(x + 50, y + 50, getX() + 50, getY() + 50,
		// barHeight)){
		if (mouseOverButton(x, y, getX(), getY())) {
			if (Mouse.hasWheel()) {
				int wheel = Mouse.getDWheel();
				if (wheel < -1) {
					dragged += 6;
				} else if (wheel > 1) {
					dragged -= 6;
				}

				if (dragged > viewportHeight - barHeight) {
					dragged = viewportHeight - barHeight;
				}

				if (dragged < 0) {
					dragged = 0;
				}
			}
		}

		if (dragging) {

			dragged = y - getY() + mouseYOffset;

			if (dragged > viewportHeight - barHeight) {
				dragged = viewportHeight - barHeight;
			}

			if (dragged < 0) {
				dragged = 0;
			}
		}

		RenderUtils.drawRect(getX() + rect.getWidth() - BAR_WIDTH - 1, getY(), getX() + rect.getWidth(),
				getY() + viewportHeight, 0x1FCFCFCF);

		RenderUtils.drawRect(getX() + rect.getWidth() - BAR_WIDTH - 1, getY() + dragged,
				getX() + rect.getWidth(), getY() + dragged + barHeight,
				0xFFFFFFFF);

		int drag = getScrollHeight(barHeight);

		RenderUtils.enableScissoring();
		RenderUtils.scissor(getX(), getY(), rect.getWidth(), viewportHeight);

		GL11.glTranslatef(0, -drag, 0);

		if (isWithinScrollPane(x, y, getX(), getY())) {
			for (AbstractComponent c : components) {
				c.draw(x, y + drag);
			}
			if (components.isEmpty()) {
				GuiManager.fr.drawString("Nothing found", getX() + 40, getY() + 20, -1);
//				addIcon("cube/close.png");
			}
		} else {
			for (AbstractComponent c : components) {
				c.draw(-999, -999);
			}
			if (components.isEmpty()) {
				GuiManager.fr.drawString("Nothing found", getX() + 40, getY() + 20, -1);
//				addIcon("cube/close.png");
			}
		}

		GL11.glTranslatef(0, drag, 0);

		RenderUtils.disableScissoring();
	}
//
//	public void addIcon(String icon) {
//
//		GL11.glPushMatrix();
//		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(icon));
//		GL11.glTranslatef(getX() + 20, getY() + 20 + 0.7F, 0.0F);
//		Gui.drawScaledCustomSizeModalRect(-6, -6, 0.0F, 0.0F, 24, 24, 24, 24, 24.0F, 24.0F);
//		GL11.glPopMatrix();
//	}

	public int getScrollHeight(int barHeight) {
		return (int) ((float) (dragged / (float) (viewportHeight - barHeight)) * (height - viewportHeight));
	}

	public boolean mouseOverBar(int x, int y, int cx, int cy, int barHeight) {
		return x >= cx + rect.getWidth() - BAR_WIDTH - 1 && x < cx + rect.getWidth() && y > cy + dragged
				&& y <= cy + dragged + barHeight;
	}

	public boolean mouseOverBarArea(int x, int y, int cx, int cy) {
		return x >= cx + rect.getWidth() - BAR_WIDTH - 1 && x <= cx + rect.getWidth() && y >= cy
				&& y <= cy + viewportHeight;
	}
	
	public boolean mouseOverButton(int x, int y, int cx, int cy){
		return x > cx && x < cx+rect.getWidth() && y > cy && y < cy+getHeight();
	}

	private boolean isWithinScrollPane(int x, int y, int cx, int cy) {
		return x > cx && x < cx + rect.getWidth() - BAR_WIDTH - 1 && y > cy && y < cy + viewportHeight;
	}

	@Override
	public void mouseClicked(int x, int y) {

		int barHeight = getBarHeight();

		if (mouseOverBar(x, y, getX(), getY(), barHeight)) {
			dragging = true;

			mouseYOffset = (getY() + dragged) - y;
		} else {

			int h = 0;
			int drag = getScrollHeight(barHeight);

			if (isWithinScrollPane(x, y, getX(), getY())) {
				for (AbstractComponent c : components) {
					c.mouseClicked(x, y + drag);
					h += c.getHeight();
				}
			} else {
				for (AbstractComponent c : components) {
					c.mouseClicked(-999, -999);
					h += c.getHeight();
				}
			}
		}
	}

	private int getBarHeight() {

		if (components.size() == 0) {
			return viewportHeight;
		}

		int h = 0;

		for (AbstractComponent comp : components) {
			h += comp.getHeight();
		}

		float contentRatio = viewportHeight / h;

		int barHeight = (int) (viewportHeight * contentRatio);

		if (barHeight < 20) {
			barHeight = 20;
		}

		if (barHeight > viewportHeight) {
			barHeight = viewportHeight;
		}

		return barHeight;
	}

	@Override
	public void keyPress(int key, char c) {
		for (AbstractComponent comp : components) {
			comp.keyPress(key, c);
		}
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		dragging = false;
	}

	@Override
	public int getHeight() {
		return viewportHeight;
	}

	@Override
	public int getWidth() {
		return rect.getWidth() - BAR_WIDTH - 1;
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

		height = 0;

		for (AbstractComponent component : components) {
			component.setY(height);
			height += component.getHeight();

		}
	}

	@Override
	public int getRectX() {
		return getX();
	}

	@Override
	public int getRectY() {
		return getY();
	}
}
