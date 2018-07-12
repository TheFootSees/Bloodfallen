package net.albedo.bloodfallen.gui;

import javassist.util.proxy.ProxyFactory;
import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.mapper.WrapperDelegationHandler;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiIngame;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiScreen;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.gui.components.ColourPicker;
import net.albedo.bloodfallen.gui.gui.components.SearchableTextField;
import net.albedo.bloodfallen.gui.gui.components.scrolling.ModScrollPane;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorSystem;
import net.albedo.bloodfallen.gui.particle.ParticleGenerator;
import net.albedo.bloodfallen.management.values.SliderValue;
import net.albedo.bloodfallen.management.values.ToggleValue;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.ModuleManager;
import net.albedo.bloodfallen.tools.FontRENDERER;
import net.albedo.bloodfallen.tools.RenderUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GuiIngameManager {
	public GuiIngameManager() {
		EventManager.register(this);
	}
	
	public GuiIngame createGuiScreen() {
		try {
			final ProxyFactory proxyFactory = new ProxyFactory();
			final Mapper mapper = Mapper.getInstance();
			proxyFactory.setSuperclass(mapper.getMappedClass(GuiIngame.class));

			    final GuiIngame guiScreen = new GuiIngame() {
				private int width, height;


				@Override
				public void renderGameOverlay(float partialTicks) {
					RenderUtils.drawRect(0, 0, 20, 30, 0xffFFFFFF);
					
				}


				@Override
				public Object getHandle() {
					return null;
				}
			};

			return WrapperDelegationHandler.createWrapperProxy(GuiIngame.class, proxyFactory.create(null, null, (Object proxy, Method thisMethod, Method proceed, Object[] args) -> {
				for (Method method : GuiIngame.class.getDeclaredMethods())
					if (mapper.getMappedMethod(method).equals(thisMethod)) {
						final Class types[] = method.getParameterTypes();
						final Object handledArgs[] = new Object[args.length];
						for (int i = 0; i < args.length; i++)
							handledArgs[i] = Wrapper.class.isAssignableFrom(types[i]) ? WrapperDelegationHandler.createWrapperProxy((Class<? extends Wrapper>) types[i], args[i]) : args[i];
						return method.invoke(guiScreen, handledArgs);
					}
				return null;
			}));
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		final Minecraft minecraft = Albedo.getMinecraft();
		if (minecraft.getGuiScreen().getHandle() == null && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			createGuiScreen();
		}
	}

}
