package net.albedo.bloodfallen.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.modules.impl.combat.Aimbot;
import net.albedo.bloodfallen.modules.impl.combat.AutoClicker;
import net.albedo.bloodfallen.modules.impl.combat.Velocity;
import net.albedo.bloodfallen.modules.impl.misc.FastPlace;
import net.albedo.bloodfallen.modules.impl.misc.Timer;
import net.albedo.bloodfallen.modules.impl.misc.Uninject;
import net.albedo.bloodfallen.modules.impl.movement.Fly;
import net.albedo.bloodfallen.modules.impl.movement.NoFall;
import net.albedo.bloodfallen.modules.impl.movement.Speed;
import net.albedo.bloodfallen.modules.impl.render.ESP;


public class ModuleManager {
	public static List<Module> modules = new CopyOnWriteArrayList<Module>();

	// prevent construction :/
	public ModuleManager() {

		
		modules.add(new AutoClicker());	
		modules.add(new Velocity());
		modules.add(new FastPlace());
		modules.add(new Aimbot());
		modules.add(new Speed());
		modules.add(new ESP());
		modules.add(new Fly());
		modules.add(new Uninject());
	
		for (Module m : modules) {
			initMod(m);
			EventManager.register(m);
		}

	}

	private void initMod(Module m) {
		if (m.getClass().isAnnotationPresent(ModuleInfo.class)) {
			ModuleInfo details = m.getClass().getAnnotation(ModuleInfo.class);
			m.setKeybind(details.defaultKey());
			m.setName(details.name());
			m.setDescription(details.desc());

		}
	}

	public static void toggle(Module m) {

		if (m.isToggled()) {
			m.setToggled(false);
//			EventManager.call(new Eventre(m));
			m.onDisable();
		} else {
			m.setToggled(true);
//			EventManager.call(new EventEnabled(m));
			m.onEnable();
		}
		Albedo.getIrrlicht().getGui().onModManagerChange();
	}

	public Module getModule(Class ModuleClass) {
		for (Module m : modules) {
			if (m.getClass().equals(ModuleClass)) {
				return m;
			}
		}

		return null;
	}

	public static Module getModuleByName(String name) {

		for (Module m : modules) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}

		return null;
	}
}