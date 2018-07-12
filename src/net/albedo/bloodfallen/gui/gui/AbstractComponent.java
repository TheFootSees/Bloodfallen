package net.albedo.bloodfallen.gui.gui;

public abstract class AbstractComponent {
	
	protected IRectangle rect;
	private int posY;
	
	public void draw(int x, int y){
		
	}
	
	public void keyPress(int key, char c){
		
	}
	
	public void mouseClicked(int x, int y){
		
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {
		
	}
	
	public void init(){
		
	}
	
	public void onGuiClosed(){
		
	}
	
	public void onModManagerChange(){
		
	}
	
	public int getWidth(){
		return 85;
	}
	
	public abstract int getHeight();
	
	public void setRectangle(IRectangle rect){
		this.rect = rect;
	}
	
	protected int getX(){
		return rect.getRectX();
	}
	
	protected int getY(){
		return rect.getRectY()+posY;
	}
	
	public void setY(int posY){
		this.posY = posY;
	}
}
