package net.albedo.bloodfallen.utils;

import java.awt.*;
import org.lwjgl.opengl.*;

public class GuiUtil
{
    public static void drawRect(final float x1, final float y1, final float x2, final float y2, final float x3, final float y3, final float x4, final float y4, final Color col) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        setColor(col);
        GL11.glBegin(7);
        GL11.glVertex2d((double)x4, (double)y4);
        GL11.glVertex2d((double)x3, (double)y3);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x3, (double)y3);
        GL11.glVertex2d((double)x4, (double)y4);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1, final int outer, final int inner) {
        drawRect(x, y, x2, y2, inner);
        final float f = (outer >> 24 & 0xFF) / 255.0f;
        final float f2 = (outer >> 16 & 0xFF) / 255.0f;
        final float f3 = (outer >> 8 & 0xFF) / 255.0f;
        final float f4 = (outer & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public static void drawBorder(final double x, final double y, final double x2, final double y2, final float l1, final int outer) {
        final float f = (outer >> 24 & 0xFF) / 255.0f;
        final float f2 = (outer >> 16 & 0xFF) / 255.0f;
        final float f3 = (outer >> 8 & 0xFF) / 255.0f;
        final float f4 = (outer & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public static void setColor(final Color c) {
        GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);
    }
    
    public static void drawRect(final float g, final float h, final float i, final float j, final int col1) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void drawIcon(final float x, final float y, final int size, final String resourceLocation) {
        GL11.glPushMatrix();
//        GL11.glBindTexture(3553, ManagerTexture.instance.getTextureNearest("/assets/" + resourceLocation));
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glEnable(6406);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glTranslatef(x, y, 0.0f);
        drawScaledRect(0, 0, 0.0f, 0.0f, size, size, size, size, size, size);
        GL11.glDisable(6406);
        GL11.glDisable(2896);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static void drawScaledRect(final int x, final int y, final float u, final float v, final int uWidth, final int vHeight, final int width, final int height, final float tileWidth, final float tileHeight) {
//        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
    }
    
    public static Color rainbow(final long offset, final float fade) {
        final float hue = (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0f, 1.0f))), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
    
    public static void draw(final float x, final float y, final float w, final float h) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        int i = 1;
        for (float x2 = 0.0f; x2 <= w; x2 += 0.1f) {
            i *= 500;
            setColor(rainbow(i, 1.0f));
            GL11.glBegin(0);
            GL11.glVertex2d((double)(x + x2), (double)y);
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        for (float y2 = h; y2 >= 0.0f; y2 -= 0.1f) {
            i *= 500;
            setColor(rainbow(i, 1.0f));
            GL11.glBegin(0);
            GL11.glVertex2d((double)(x + w), (double)(y + y2));
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        for (float x2 = 0.0f; x2 <= w; x2 += 0.1f) {
            i *= 500;
            setColor(rainbow(i, 1.0f));
            GL11.glBegin(0);
            GL11.glVertex2d((double)(x + x2), (double)(y + h));
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        for (float y2 = h; y2 >= 0.0f; y2 -= 0.1f) {
            i *= 500;
            setColor(rainbow(i, 1.0f));
            GL11.glBegin(0);
            GL11.glVertex2d((double)x, (double)(y + y2));
            GL11.glEnd();
            GL11.glPopMatrix();
        }
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static void draw(final double x, final double y, final double w, final double h) {
        draw((float)x, (float)y, (float)w, (float)h);
    }
}