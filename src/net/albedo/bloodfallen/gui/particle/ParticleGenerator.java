package net.albedo.bloodfallen.gui.particle;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.wrappers.client.settings.GameSettings;
import net.albedo.bloodfallen.management.ScaledResolution;
import net.albedo.bloodfallen.tools.FontRENDERER;
import net.albedo.bloodfallen.utils.RenderUtils;

import org.lwjgl.opengl.GL11;


/**
 * Particle API This Api is free2use But u have to mention me.
 * 
 * @author Vitox
 * @version 3.0
 */

public class ParticleGenerator {

	public ArrayList<Particle> parts = new ArrayList();

	private Random random = new Random();
	public static int raindrops = 80;
	public static float alpha;
	public static float delay;
	public static int range = 60;
	private FontRENDERER fr;
	private ScaledResolution sr = new ScaledResolution(Albedo.getMinecraft().getGameSettings());

	public ParticleGenerator(int raindrops) {
		fr = new FontRENDERER(new Font("xd", 0, 30));
		for (int i = 0; i < raindrops; i++) {
			this.raindrops = raindrops;
			parts.add(new Particle(new Random().nextInt(sr.getScaledWidth()),
					new Random().nextInt(sr.getScaledHeight())));
		}
	}

	public void draw(int mouseX, int mouseY, float partialTicks) {
		for (Particle r : parts) {

			r.fall();
			r.interpolation();

				parts.stream()
						.filter(part -> (part.getX() > r.getX() && part.getX() - r.getX() < range
								&& r.getX() - part.getX() < range)
								&& (part.getY() > r.getY() && part.getY() - r.getY() < range
										|| r.getY() > part.getY() && r.getY() - part.getY() < range))
						.forEach(connectable -> r.connect(connectable.getX(), connectable.getY()));

			
				delay += 1;

				if (delay > 30) {
					alpha += 0.00009f;
					if (alpha >= 0.9f) {
						alpha -= 0.00009f;

					}
			RenderUtils.drawCircle(r.getX(), r.getY(), r.size, RenderUtils.changeAlpha(0xffFFFFFF, alpha));
		}
	}
}
}