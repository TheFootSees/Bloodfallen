package net.albedo.bloodfallen.gui.gui.hub;

import net.albedo.bloodfallen.utils.RenderUtils;

public class SquareCell {
	
	private String title;
	private int colour;
	private String contents;
	
	private static int[] colours = new int[]{
		0x5FFF0000,
		0x5F00FF00,
		0x5F0000FF,
		0x5F404000,
		0x5F004040,
		0x5F400040,
		0x5F202020,
		0x5F305010
	};
	
	public SquareCell(String title, int colour, String contents) {
		super();
		this.title = title;
		this.colour = colours[colour];
		this.contents = contents;
	}
	
	public void renderCell(SquareCell cell, ColourModifier colourModifier, int x, int y, int xPos, int yPos, int w, int h){
		
		if(mouseOverButton(x, y, xPos, yPos, xPos+w, yPos+h)){
			RenderUtils.drawRect(xPos, yPos, xPos+w, yPos+h, colourModifier.getDarkColourWithAlpha(cell.getColour()));
		}else{
			RenderUtils.drawRect(xPos, yPos, xPos+w, yPos+h, cell.getColour());
		}
		
		//GuiUtils.drawRect(xPos, yPos, xPos+w, yPos+11, 0x7F2B2B2B);
		
//		fontRenderer.drawStringWithShadow(cell.getTitle(), xPos + w/2 - fontRenderer.getStringWidth(cell.getTitle())/2, yPos + 2, 0xFFFFFFFF);
		
//		fontRenderer.drawStringWithShadow(cell.getContents(), xPos + 3, yPos + 14, w-3, 0xFFFFFFFF);
	}
	
	public boolean mouseOverButton(int x, int y, int bx, int by, int bx1, int by1){
		return x >= bx && x <= bx1 && y >= by && y <= by1;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
}
