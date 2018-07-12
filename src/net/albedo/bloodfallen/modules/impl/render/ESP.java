package net.albedo.bloodfallen.modules.impl.render;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.Render2DEvent;
import net.albedo.bloodfallen.engine.hooker.impl.Render3DEvent;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.mapper.WrapperDelegationHandler;
import net.albedo.bloodfallen.engine.wrappers.client.entity.PlayerSp;
import net.albedo.bloodfallen.engine.wrappers.client.network.Packet;
import net.albedo.bloodfallen.engine.wrappers.client.network.server.S12Velocity;
import net.albedo.bloodfallen.engine.wrappers.client.renderer.entity.RenderManager;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityLivingBase;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityPlayer;
import net.albedo.bloodfallen.engine.wrappers.util.AxisAlignedBB;
import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;
import net.albedo.bloodfallen.tools.RenderUtils;

@ModuleInfo(name = "ESP", desc = "Makes hidden Entitys visible", category = Category.COMBAT)
public class ESP extends Module{
	
	
	@EventTarget
	public void onRender(Render3DEvent event) throws ClassNotFoundException {
		final Mapper mapper = Mapper.getInstance();	
		if (isToggled()) {
						
			for (Object obj : (Albedo.getMinecraft().getWorld().getLoadedEntityList())) {
					if (mapper.getMappedClass(EntityPlayer.class).isInstance(obj)) {
						if (!mapper.getMappedClass(PlayerSp.class).isInstance(obj)) {
						Entity p = WrapperDelegationHandler.createWrapperProxy(Entity.class, obj);
						RenderManager rm = Albedo.getMinecraft().getRenderManager();

						float r = Albedo.getMinecraft().getTimer().getRenderPartialTicks();

						double x = p.getPrevX() - (p.getPrevX() - p.getX()) * r - rm.getRenderPosX();
						double y = p.getPrevY() - (p.getPrevY() - p.getY()) * r - rm.getRenderPosY();
						double z = p.getPrevZ() - (p.getPrevZ() - p.getZ()) * r - rm.getRenderPosZ();

						GL11.glPushMatrix();

						GL11.glTranslated(x, y - 0.2D, z);
						GL11.glScalef(0.03F, 0.03F, 0.03F);
						GL11.glRotated(-Albedo.getMinecraft().getRenderManager().getPlayerViewY(), 0.0D, 1.0D, 0.0D);

						RenderUtils.drawRect(-20, -1, -26, 75, Color.BLACK.hashCode());
						RenderUtils.drawRect(-21, 0, -25, 74, -1);

						RenderUtils.drawRect(20, -1, 26, 75, Color.BLACK.hashCode());
						RenderUtils.drawRect(21, 0, 25, 74, -1);

						RenderUtils.drawRect(-20, -1, 21, 5, Color.BLACK.hashCode());
						RenderUtils.drawRect(-21, 0, 24, 4, -1);

						RenderUtils.drawRect(-20, 70, 21, 75, Color.BLACK.hashCode());
						RenderUtils.drawRect(-21, 71, 25, 74, -1);

						GL11.glPopMatrix();
					}
				}
			}
		}
	}

}
