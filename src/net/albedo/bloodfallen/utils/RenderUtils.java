package net.albedo.bloodfallen.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.management.ScaledResolution;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;


public class RenderUtils {
	// prevent construction :/
	private RenderUtils() {
	}

	
	public static void enable2D() {
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_LINE_SMOOTH);
	}

	
	public static void disable2D() {
		glDisable(GL_LINE_SMOOTH);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
	}

	
	public static void drawRect(double paramXStart, double paramYStart, double paramXEnd, double paramYEnd,
			int paramColor) {

		float alpha = (float) (paramColor >> 24 & 0xFF) / 255F;
		float red = (float) (paramColor >> 16 & 0xFF) / 255F;
		float green = (float) (paramColor >> 8 & 0xFF) / 255F;
		float blue = (float) (paramColor & 0xFF) / 255F;

		GL11.glClear(256);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(red, green, blue, alpha);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2d(paramXEnd, paramYStart);
		GL11.glVertex2d(paramXStart, paramYStart);
		GL11.glVertex2d(paramXStart, paramYEnd);
		GL11.glVertex2d(paramXEnd, paramYEnd);

		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glClear(256);
	}
	
	  public static void connectPoints(float xOne, float yOne, float xTwo, float yTwo) {
		  
	        GL11.glPushMatrix();
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glLineWidth(0.7f);
	        GL11.glBegin(GL11.GL_LINES);
	        GL11.glVertex2f(xOne, yOne);
	        GL11.glVertex2f(xTwo, yTwo);
	        GL11.glEnd();
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glPopMatrix();
	    }
	  
	  public static void drawCircle(float x, float y, float radius, int color) {
	        float alpha = (color >> 24 & 0xFF) / 255.0F;
	        float red = (color >> 16 & 0xFF) / 255.0F;
	        float green = (color >> 8 & 0xFF) / 255.0F;
	        float blue = (color & 0xFF) / 255.0F;

	        GL11.glColor4f(red, green, blue, alpha);
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        GL11.glPushMatrix();
	        GL11.glLineWidth(1F);
	        GL11.glBegin(GL11.GL_POLYGON);
	        for (int i = 0; i <= 360; i++)
	            GL11.glVertex2d(x + Math.sin(i * Math.PI / 180.0D) * radius, y + Math.cos(i * Math.PI / 180.0D) * radius);
	        GL11.glEnd();
	        GL11.glPopMatrix();
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glColor4f(1F, 1F, 1F, 1F);
	    }
	
	public static void drawRoundedRectBOTTOM(float x, float y, float x1, float y1, int insideC) {
		x *= 2;
		y *= 2;
		x1 *= 2;
		y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glPushMatrix();
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
		drawVLine(x, y, y1 - 2, insideC); // *
		drawVLine(x1 - 1, y, y1 - 2, insideC); // *
		drawHLine(x, x1 - 1, y, insideC); // *
		drawHLine(x + 2, x1 - 3, y1 - 1, insideC);
		// drawHLine(x + 1, x + 1, y + 1, borderC);
		// drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
		drawHLine(x1 - 2, x1 - 2, y1 - 2, insideC);
		drawHLine(x + 1, x + 1, y1 - 2, insideC);
		GL11.glPopMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	public static void drawRoundedRectTOP(float x, float y, float x1, float y1, int insideC) {
		x *= 2;
		y *= 2;
		x1 *= 2;
		y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawRect(x + 1, y + 1, x1 - 1, y1 - 1, insideC);
		drawVLine(x, y + 1, y1, insideC);
		drawVLine(x1 - 1, y + 1, y1, insideC);
		drawHLine(x + 2, x1 - 3, y, insideC);
		drawHLine(x, x1 - 1, y1 - 1, insideC);
		drawHLine(x + 1, x + 1, y + 1, insideC);
		drawHLine(x1 - 2, x1 - 2, y + 1, insideC);
		// drawHLine(x1 - 2, x1 - 2, y1 - 2, insideC);
		// drawHLine(x + 1, x + 1, y1 - 2, insideC);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}

	
	public static void drawBorder(float x, float y, float width, float height, float size, int color) {
		drawRect(x, y, width, size, color);
		drawRect(x, y, size, height, color);
		drawRect(x + width - size, y, size, height, color);
		drawRect(x, y + height - size, width, size, color);
	}

	public static void drawRoundedBorder(float x, float y, float x1, float y1, int borderC) {
		x *= 2;
		y *= 2;
		x1 *= 2;
		y1 *= 2;
		GL11.glScalef(0.5F, 0.5F, 0.5F);

		drawVLine(x, y + 1, y1 - 2, borderC);
		drawVLine(x1 - 1, y + 1, y1 - 2, borderC);
		drawHLine(x + 2, x1 - 3, y, borderC);
		drawHLine(x + 2, x1 - 3, y1 - 1, borderC);
		drawHLine(x + 1, x + 1, y + 1, borderC);
		drawHLine(x1 - 2, x1 - 2, y + 1, borderC);
		drawHLine(x1 - 2, x1 - 2, y1 - 2, borderC);
		drawHLine(x + 1, x + 1, y1 - 2, borderC);
		GL11.glScalef(2.0F, 2.0F, 2.0F);
	}
	
	public static void drawVLine(float x, float y, float y1, int colour) {
		if (y1 < y) {
			float var5 = y;
			y = y1;
			y1 = var5;
		}

		drawRect(x, y + 1, x + 1, y1, colour);
	}
	
	public static void drawHLine(float par1, float par2, float par3, int par4) {
		if (par2 < par1) {
			float var5 = par1;
			par1 = par2;
			par2 = var5;
		}

		drawRect(par1, par3, par2 + 1, par3 + 1, par4);
	}
	
	
	public static void drawBorderRect(float x, float y, float width, float height, float size, int color, int borderColor) {
		drawRect(x, y, width, height, color);
		drawBorder(x, y, width, height, size, borderColor);
	}

	
	public static void drawLine(float x, float y, float otherX, float otherY, int width, int color) {
		enable2D();
		glColor(color);
		glLineWidth(width);
		glBegin(GL_LINES);
		glVertex2f(x, y);
		glVertex2f(otherX, otherY);
		glEnd();
		disable2D();
	}

	
	public static int createTexture(BufferedImage bufferedImage) {
	
		final int pixels[] = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
		bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), pixels, 0, bufferedImage.getWidth());

		final ByteBuffer buffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);
		for (int y = 0; y < bufferedImage.getHeight(); y++)
			for (int x = 0; x < bufferedImage.getWidth(); x++) {
				final int pixel = pixels[y * bufferedImage.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		buffer.flip();

		final int texId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texId);
		
        
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return texId;
	}
	
	public static void drawRectWithTexture(float x, float y, float width, float height, float textureX, float textureY, float fullTextureWidth, float fullTextureHeight) {
		final float texX = textureX / fullTextureWidth;
		final float texY = textureY / fullTextureHeight;
		final float texWidth = width / fullTextureWidth;
		final float texHeight = height / fullTextureHeight;
		glEnable(GL_TEXTURE_2D);
		glBegin(GL_TRIANGLES);
		glTexCoord2f(texX + texWidth, texY);
		glVertex2d(x + width, y);
		glTexCoord2f(texX, texY);
		glVertex2d(x, y);
		glTexCoord2f(texX, texY + texHeight);
		glVertex2d(x, y + height);
		glTexCoord2f(texX, texY + texHeight);
		glVertex2d(x, y + height);
		glTexCoord2f(texX + texWidth, texY + texHeight);
		glVertex2d(x + width, y + height);
		glTexCoord2f(texX + texWidth, texY);
		glVertex2d(x + width, y);
		glEnd();
	}

	
	public static void glColor(int color) {
		glColor4f((color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f, (color >> 24 & 255) / 255f);
	}

	
	public static int getColor(int r, int g, int b, int a) {
		return (a << 24) + (r << 16) + (g << 8) + (b);
	}

	
	public static void prepareScissorBox(float top, float left, float bottom, float right) {
		ScaledResolution scaledResolution = new ScaledResolution(Albedo.getMinecraft().getGameSettings());
		glScissor((int) (top * scaledResolution.getScaleFactor()), (int) ((scaledResolution.getScaledHeight() - right) * scaledResolution.getScaleFactor()), (int) ((bottom - top) * scaledResolution.getScaleFactor()), (int) ((right - left) * scaledResolution.getScaleFactor()));
	}
	
	public static ScaledResolution getScaledResolution() {
		ScaledResolution sr = new ScaledResolution(Albedo.getMinecraft().getGameSettings());
		return sr;
	}

	public static void enableScissoring() {
		GL11.glEnable( GL11.GL_SCISSOR_TEST );
	}

	
	public static void scissor( final double x, final double y, final double w, final double h ) {
		final ScaledResolution sr = getScaledResolution();
		final int factor = sr.getScaleFactor();

		final double x2 = x + w, y2 = y + h;

		GL11.glScissor( ( int ) ( x * factor ), ( int ) ( ( sr.getScaledHeight() - y2 ) * factor ),
				( int ) ( ( x2 - x ) * factor ), ( int ) ( ( y2 - y ) * factor ) );
	}
	
	public static void disableScissoring() {
		GL11.glDisable( GL11.GL_SCISSOR_TEST );
	}

		public static int changeAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = ((float) 1 / 255) * c.getRed();
		float g = ((float) 1 / 255) * c.getGreen();
		float b = ((float) 1 / 255) * c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}
}
