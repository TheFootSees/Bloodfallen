package net.albedo.bloodfallen.tools;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.Map;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

public class FontRENDERER {
	private static final int TEXTURE_SIZE = 512;
	private Rectangle2D characterBounds[] = new Rectangle2D[256];
	private Font font;
	private int texture;
	private float maxHeight;
	private boolean freed;

	public FontRENDERER(Font font) {
		this.font = font;
		texture = generateTexture(font);
	}

	public int generateTexture(Font font) {
		final BufferedImage bufferedImage = new BufferedImage(TEXTURE_SIZE, TEXTURE_SIZE, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.setFont(font);
		final Map desktopHints = (Map) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
		if (desktopHints != null)
			graphics.setRenderingHints(desktopHints);
		final FontMetrics fontMetrics = graphics.getFontMetrics();
		float highestChar = 0, x = 0, y = 0;
		for (int i = 0; i < characterBounds.length; i++) {
			char c = (char) i;
			Rectangle2D rect = fontMetrics.getStringBounds(Character.toString(c), graphics);
			final float width = (float) rect.getWidth() + 2;
			if (x + width >= TEXTURE_SIZE) {
				y += highestChar;
				x = highestChar = 0;
			}
			if (rect.getHeight() > highestChar) {
				highestChar = (float) rect.getHeight();
				if (highestChar > maxHeight)
					maxHeight = highestChar;
			}
			graphics.drawString(Character.toString(c), x, y + fontMetrics.getAscent());
			characterBounds[i] = new Rectangle2D.Double(x, y, rect.getWidth() + 1, rect.getHeight());
			x += width;
		}
		freed = false;
		return createTexture(bufferedImage);
	}
	
	public int createTexture(BufferedImage bufferedImage) {
		
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

	/**
	 * Draws given character at given location.
	 *
	 * @param c
	 *            the character which is supposed to be drawn.
	 * @param x
	 *            the x position.
	 * @param y
	 *            the y position.
	 */
	public void drawChar(char c, float x, float y) {
		if (freed)
			return;
		RenderUtils.drawRectWithTexture(x, y, (float) characterBounds[c].getWidth(), (float) characterBounds[c].getHeight(), (float) characterBounds[c].getX(), (float) characterBounds[c].getY(), TEXTURE_SIZE, TEXTURE_SIZE);
	}

	/**
	 * Draws a string at given location.
	 *
	 * @param s
	 *            the string which is supposed to be drawn.
	 * @param x
	 *            the x position.
	 * @param y
	 *            the y position.
	 * @param color
	 *            the color used to draw the string.
	 */
	public void drawString(String s, float x, float y, int color) {
		if (freed)
			return;
		RenderUtils.glColor(color);
		glPushMatrix();
		glScaled(0.25, 0.25, 1);

		glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, texture);
		float posX = x * 4;
		for (char c : s.toCharArray()) {
			drawChar(c, posX, y * 4);
			posX += (float) characterBounds[c].getWidth();
		}
		RenderUtils.disable2D();
		glPopMatrix();
	}

	/**
	 * Draws a string at given location with a shadow.
	 *
	 * @param s
	 *            the string which is supposed to be drawn.
	 * @param x
	 *            the x position.
	 * @param y
	 *            the y position.
	 * @param color
	 *            the color used to draw the string.
	 *
	 * @see #drawString(String, float, float, int)
	 */
	public void drawStringWithShadow(String s, float x, float y, int color) {
		if (freed)
			return;
		drawString(s, x + 1, y + 1, 0xff000000);
		drawString(s, x, y, color);
	}

	/**
	 * @param c
	 *            the char of which the width should be returned.
	 *
	 * @return the characters width.
	 */
	public float getCharWidth(char c) {
		return (float) characterBounds[c].getWidth() * 0.25f;
	}

	/**
	 * @param c
	 *            the char of which the height should be returned.
	 *
	 * @return the characters height.
	 */
	public float getCharHeight(char c) {
		return (float) characterBounds[c].getHeight() * 0.25f;
	}

	/**
	 * @param s
	 *            the string of which the width should be returned.
	 *
	 * @return the strings width.
	 */
	public float getStringWidth(String s) {
		float size = 0;
		for (char c : s.toCharArray())
			size += getCharWidth(c);
		return size;
	}

	/**
	 * Trims the string provided to the width provided.
	 *
	 * @param s
	 *            the string which is supposed to trimmed.
	 * @param width
	 *            the width the string is supposed to be trimmed to.
	 *
	 * @return the string trimmed to the width provided.
	 */
	public String trimToWidth(String s, float width) {
		int i = s.length() - 1;
		for (float strWidth = getStringWidth(s); i >= 0; i--)
			if (strWidth > width)
				strWidth -= getCharWidth(s.charAt(i));
			else
				break;
		return s.substring(0, i + 1);
	}

	/**
	 * @param s
	 *            the string of which the dimensions for each character are
	 *            supposed to be returned;
	 *
	 * @return the dimensions of each character in the string.
	 */
	public float[][] getDimensions(String s) {
		float dimensions[][] = new float[s.length()][2];
		final char chars[] = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			dimensions[i][0] = getCharWidth(chars[i]);
			dimensions[i][1] = getCharHeight(chars[i]);
		}
		return dimensions;
	}

	/**
	 * @param s
	 *            the string of which the height should be returned.
	 *
	 * @return the strings height.
	 */
	public float getStringHeight(String s) {
		float highest = 0;
		for (char c : s.toCharArray()) {
			final float height = getCharHeight(c);
			if (height > highest)
				highest = height;
		}
		return highest;
	}

	/**
	 * Frees the fonts textures.
	 */
	public void free() {
		if (freed)
			return;
		glBindTexture(GL_TEXTURE_2D, 0);
		glDeleteTextures(texture);
		freed = true;
	}

	/**
	 * @return whether or not the textures were freed.
	 */
	public boolean isFreed() {
		return freed;
	}

	/**
	 * Loads a new font and also frees old one.
	 *
	 * @param font
	 *            the font which is supposed to be loaded.
	 */
	public void setFont(Font font) {
		this.font = font;
		free();
		this.texture = generateTexture(font);
	}

	/**
	 * @return the currently loaded font.
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @return the texture id for the font.
	 */
	public int getTexture() {
		return texture;
	}

	/**
	 * @return the max character height of the font.
	 */
	public float getMaxHeight() {
		return maxHeight / 4;
	}

	@Override
	public int hashCode() {
		return font.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this || (obj instanceof FontRENDERER && ((FontRENDERER) obj).font.equals(font));
	}

	@Override
	protected void finalize() throws Throwable {
		free();
		super.finalize();
	}
}
