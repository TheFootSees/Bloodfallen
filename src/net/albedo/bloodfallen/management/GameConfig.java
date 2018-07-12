package net.albedo.bloodfallen.management;

import java.io.File;


public class GameConfig {
	public final String version;
	public final File gameDir, assetDir;
	public final Class main;

	public GameConfig(String version, String gameDir, String assetDir, String main) throws ClassNotFoundException {
		this.version = version;
		this.gameDir = new File(gameDir);
		this.assetDir = new File(assetDir);
		this.main = Class.forName(main);
	}
}
