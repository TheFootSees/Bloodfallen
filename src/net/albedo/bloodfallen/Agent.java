package net.albedo.bloodfallen;

import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.albedo.bloodfallen.engine.exceptions.MappingException;
import net.albedo.bloodfallen.engine.hooker.Hooker;
import net.albedo.bloodfallen.engine.mapper.Mapper;
import net.albedo.bloodfallen.launcher.Launcher;
import net.albedo.bloodfallen.management.GameConfig;


public class Agent {
	public static void premain(String args, Instrumentation instrumentation) {
		agentmain(args, instrumentation);
	}

	public static void agentmain(String args, Instrumentation instrumentation) {
		final Map<String, String[]> agentParameters = new HashMap<>();
		for (String s : args.split(",")) {
			final String[] values = s.split("=");
			if (values.length < 2)
				continue;
			agentParameters.put(values[0], Arrays.copyOfRange(values, 1, values.length));
		}
		
		try {
			Albedo.bootstrap(new GameConfig(agentParameters.get("version")[0], agentParameters.get("gameDir")[0], agentParameters.get("assetsDir")[0], agentParameters.get("main")[0]));
			Mapper.getInstance().generate();
			Hooker.createHooker().hook(instrumentation);
		} catch (MappingException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
