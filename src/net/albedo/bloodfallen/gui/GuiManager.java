package net.albedo.bloodfallen.gui;

import javassist.util.proxy.ProxyFactory;
import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.Render2DEvent;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.engine.mapper.WrapperDelegationHandler;
import net.albedo.bloodfallen.engine.wrappers.Wrapper;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.client.gui.GuiScreen;
import net.albedo.bloodfallen.gui.gui.ColourType;
import net.albedo.bloodfallen.gui.gui.Window;
import net.albedo.bloodfallen.gui.gui.components.ColourPicker;
import net.albedo.bloodfallen.gui.gui.components.SearchableTextField;
import net.albedo.bloodfallen.gui.gui.components.scrolling.KeyScrollPane;
import net.albedo.bloodfallen.gui.gui.components.scrolling.ModScrollPane;
import net.albedo.bloodfallen.gui.gui.components.selectors.KeybindButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.ModuleButton;
import net.albedo.bloodfallen.gui.gui.components.selectors.SelectorSystem;
import net.albedo.bloodfallen.gui.gui.hub.keybind.KeybindSetButton;
import net.albedo.bloodfallen.gui.particle.ParticleGenerator;
import net.albedo.bloodfallen.management.ScaledResolution;
import net.albedo.bloodfallen.management.values.SliderValue;
import net.albedo.bloodfallen.management.values.ToggleValue;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleManager;
import net.albedo.bloodfallen.tools.FontRENDERER;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GuiManager {
	public static List<Window> windows = new CopyOnWriteArrayList<Window>();
	public static boolean opened;
    private Window win;
    private ParticleGenerator rain;
    public static FontRENDERER fr, bloody;
	
	// prevent construction :/
	public GuiManager(ModuleManager modManager) {
		EventManager.register(this);
		initModWindows(modManager);
		initColoursWindow(modManager);
//		initBindWindows(modManager);
		moveWindows();
	}

	public static void initModWindows(ModuleManager modManager) {

		Window win;
		ModScrollPane scroll;
		SelectorSystem<ModuleButton> modSystem = new SelectorSystem<ModuleButton>();

		windows.add(win = new Window("Mods", modManager, 320));
		win.addComponent(scroll = new ModScrollPane(240, modSystem));
		win.addComponent(new SearchableTextField("Search", scroll));
	}
	
	public void initBindWindows(ModuleManager modManager) {

		Window win;
		KeyScrollPane scroll;

		SelectorSystem<KeybindButton> keySystem = new SelectorSystem<KeybindButton>();
		KeybindSetButton setButton = new KeybindSetButton(keySystem);

		windows.add(win = new Window("Binds", modManager, 220));
		win.addComponent(scroll = new KeyScrollPane(130, keySystem));
		win.addComponent(new KeybindSetButton(keySystem));
		win.addComponent(new SearchableTextField("Search", scroll));

	}
	
	private void initColoursWindow(ModuleManager modManager) {

		Window colours = new Window("Colours", modManager, 85);

		for (ColourType colourType : ColourType.values()) {
			colours.addComponent(new ColourPicker(colourType, this));
		}

		windows.add(colours);
	}
	
	private void moveWindows() {

		int line = 0;

		for (Window win : windows) {
			win.posX = 3;
			win.posY = line++ * 20;
		}
	}
	
	public static void onModManagerChange() {

		for (Window win : windows) {
			win.onModManagerChange();
		}
	}
	
	public GuiScreen createGuiScreen() {
		try {
			final ProxyFactory proxyFactory = new ProxyFactory();
			final Mapper mapper = Mapper.getInstance();
			proxyFactory.setSuperclass(mapper.getMappedClass(GuiScreen.class));

			    final GuiScreen guiScreen = new GuiScreen() {
				private int width, height;


				@Override
				public void drawScreen(int x, int y, float f) {
					rain.draw(x, y, f);
					draw(x, y, f);
				}

				@Override
				public void handleInput() {
					if (Mouse.isCreated())
						while (Mouse.next()) {
							final int x = Mouse.getEventX() * width / Display.getWidth(), y = height - Mouse.getEventY() * height / Display.getHeight() - 1, button = Mouse.getEventButton();
							if (Mouse.getEventButtonState())
								mouseClicked(x, y, button);
							else if (button != -1)
								mouseReleased(x, y, button);
						}

					if (Keyboard.isCreated())
						while (Keyboard.next())
							if (Keyboard.getEventKeyState())
								keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
				}

				@Override
				public void onUpdate() {
					update();
				}

				@Override
				public void onClose() {
					rain.alpha = 0;
			    	rain.delay = 0;

				}

				@Override
				public void initGui(Minecraft minecraft, int width, int height) {
					Keyboard.enableRepeatEvents(true);
					this.width = width;
					this.height = height;
					rain = new ParticleGenerator(ParticleGenerator.raindrops);
					fr = new FontRENDERER(new Font("Roboto Regular", 0, 40));
					bloody = new FontRENDERER(new Font("Horrorfind", 0, 70));
				}

				@Override
				public void resize(Minecraft minecraft, int width, int height) {
//					initGui(minecraft, width, height);
				}

				@Override
				public boolean shouldPauseGame() {
					return false;
				}

				@Override
				public Object getHandle() {
					return this;
				}
			};
			
			return WrapperDelegationHandler.createWrapperProxy(GuiScreen.class, proxyFactory.create(null, null, (Object proxy, Method thisMethod, Method proceed, Object[] args) -> {
				for (Method method : GuiScreen.class.getDeclaredMethods())
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
		Display.setTitle("Bloodfallen");
		final Minecraft minecraft = Albedo.getMinecraft();
		if (minecraft.getGuiScreen().getHandle() == null && Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
			minecraft.displayGuiScreen(createGuiScreen());
	}

	@EventTarget
	public void onRenderBloodfallen(Render2DEvent event) {
		try {
			GuiManager.bloody.drawStringWithShadow(Albedo.BLOODFALLEN, 2, 2, 0xff8A0808);
		} catch (Exception e) {

		}
	}

	@EventTarget
	public void onRenderArrayList(Render2DEvent event) {
		try {
			int y = 0;
			for (Module m : ModuleManager.modules) {
				if (m.isToggled()) {
					ScaledResolution scaledResolution = new ScaledResolution(Albedo.getMinecraft().getGameSettings());
					int sr = scaledResolution.getScaledWidth();
					GuiManager.fr.drawStringWithShadow(m.getName(),
							scaledResolution.getScaledWidth() - GuiManager.fr.getStringWidth(m.getName()), y,
							0xffFFFFFF);
					y += 10;
				}
			}
		} catch (Exception e) {

		}
	}
	
	public void draw(int mouseX, int mouseY, float partialTicks) {
		for (Window w : windows) {
			w.renderWindow(mouseX, mouseY, true);
		}
		
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		for (Window w : windows) {
			w.mouseClicked(mouseX, mouseY);
		}
	}

	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for (Window w : windows) {
			w.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}

	public void keyTyped(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			Albedo.getMinecraft().displayGuiScreen(null);
			return;
		}

		for (Window w : windows) {
			w.keyPress(typedChar, keyCode);
		}
	}


	public void update() {

	}

	public static void setDragging(Window dragging) {

		for (Window win : windows) {
			if (win != dragging) {
				win.dragging = false;
			}
		}

		windows.remove(dragging);
		windows.add(windows.size(), dragging);
	}

	public static Window getWindowByName(String name) {

		for (Window w : windows) {
			if (w.title.equals(name)) {
				return w;
			}
		}

		return null;
	}
}
