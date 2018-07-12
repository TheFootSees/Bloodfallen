package net.albedo.bloodfallen.gui.gui.components.buttons;

import net.albedo.bloodfallen.gui.gui.Window;

public class LinkButton extends Button{

	private String text;
	private String link;
	
	public LinkButton(String text, String link) {
		super();
		this.text = text;
		this.link = link;
	}

	@Override
	public boolean isCentered() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void toggle() {
//		ClientUtils.openBrowserWindow(link);
	}

	@Override
	public String getText() {
		return text;
	}

}
