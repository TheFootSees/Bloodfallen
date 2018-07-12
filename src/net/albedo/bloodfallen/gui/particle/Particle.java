package net.albedo.bloodfallen.gui.particle;

import java.util.Random;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.wrappers.client.settings.GameSettings;
import net.albedo.bloodfallen.management.ScaledResolution;
import net.albedo.bloodfallen.utils.RenderUtils;

/**
 * Particle API 
 * This Api is free2use
 * But u have to mention me.
 * @author Vitox
 * @version 3.0
 */

public class Particle {
	
	public float x;
	public float y;
	public float size;
	public float speed;
	public float yspeed = new Random().nextFloat();
	public float xspeed = new Random().nextFloat();
	private int height;
	private int width;
	public static boolean everyside;
	private Random rndm = new Random();
	GameSettings gameSettings;
	
	public Particle(int x, int y){
		this.x = x;
		this.y = y;
		this.size = genRandom(0.9f, 1.1f);
	}
	
	float lint1 (float a, float b, float f) {
	    return (a * (1.0f - f)) + (b * f);
	}

	float lint2 (float a, float b, float f) {
	    return a + f * (b - a);
	}
	
	  public void connect(float x, float y) {
	        RenderUtils.connectPoints(getX(), getY(), x, y);
	    }
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void interpolation() {
		float a = 1.02e0f;
		float b = 1.0f;

		for (int n = 0; n <= 64; ++n) {

			float f = n / 64.0f;
			float p1 = lint1(a, b, f);
			float p2 = lint2(a, b, f);
			if (p1 != p2) {

				y -= f / 4;
				x -= f / 4;

			}
		}
	}

	public void fall(){
		ScaledResolution sr = new ScaledResolution(Albedo.getMinecraft().getGameSettings());
        y = (y + yspeed);
		
		x = (x + xspeed );
		
		if (y > sr.getScaledHeight()){
			y = 1;
			
		}
		
		if (x > sr.getScaledWidth()){
			x = 1;
			
		}		
		if (x < 1){
			x = sr.getScaledWidth();
		
		}
		if (y < 1){
			y = sr.getScaledHeight();
		}
	}
	
	public float genRandom(float min, float max) {
		return (float) (min + Math.random() * (max - min + 1.0F));
	}
	
	
}

