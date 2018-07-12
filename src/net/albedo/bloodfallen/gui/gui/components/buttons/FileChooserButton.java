package net.albedo.bloodfallen.gui.gui.components.buttons;

import java.io.File;

import javax.swing.JFileChooser;

import net.albedo.bloodfallen.gui.gui.Window;

public class FileChooserButton extends Button{

	private File file;
	private String text;
	
	public FileChooserButton(String text) {
		super();
		this.text = text;
	}

	@Override
	public boolean isCentered() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return file != null;
	}

	@Override
	public void toggle() {
		
		JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION){
			file = fc.getSelectedFile();
		}
	}

	@Override
	public String getText() {
		return file != null? text + ": " + file.getName() : "Select " + text + " File";
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
