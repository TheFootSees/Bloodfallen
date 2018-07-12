package net.albedo.bloodfallen;

import com.sun.management.OperatingSystemMXBean;

import net.albedo.bloodfallen.engine.events.EventManager;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.Render2DEvent;
import net.albedo.bloodfallen.engine.hooker.impl.SwapBuffersEvent;
import net.albedo.bloodfallen.engine.mapper.WrapperDelegationHandler;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.entity.Entity;
import net.albedo.bloodfallen.gui.GuiIngameManager;
import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.gui.friends.FriendManager;
import net.albedo.bloodfallen.launcher.rmi.IPerformanceCharts;
import net.albedo.bloodfallen.launcher.rmi.IRmiManager;
import net.albedo.bloodfallen.launcher.rmi.impl.RmiManager;
import net.albedo.bloodfallen.management.GameConfig;
import net.albedo.bloodfallen.management.ScaledResolution;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleManager;
import net.albedo.bloodfallen.modules.values.ValuesRegistry;
import net.albedo.bloodfallen.saving.FileManager;
import net.albedo.bloodfallen.tools.FontRENDERER;
import net.albedo.bloodfallen.utils.LoggerFactory;
import net.albedo.bloodfallen.utils.MathUtils;
import net.albedo.bloodfallen.utils.minecraftutils.MathHelper;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Albedo {
	
	public static final String NAME = "Bloodfallen", VERSION = "1.2.2-alpha";

	public static String BLOODFALLEN = "Bloodfallen";
	
	private static Albedo instance;

	private Logger logger = LoggerFactory.getLogger(Albedo.class);
	private GameConfig gameConfig;
	private ModuleManager moduleManager;
	public GuiManager gui;
	public static FontRENDERER fr;
	private FileManager fileManager;
	public GuiIngameManager gim;
	public static ValuesRegistry valuesRegistry;
    public static String mcVersion;
	private FriendManager friendManager;
	private int frameCounter;

	
	private Albedo(GameConfig gameConfig) {
		instance = this;
		this.gameConfig = gameConfig;
		logger.log(Level.INFO, "Detected version: " + gameConfig.version);
		mcVersion = gameConfig.version;
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				((IRmiManager) RmiManager.getRMI(RmiManager.IDENTIFIER)).shutdown();
				logger.log(Level.INFO, "Shutting down...");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}, NAME + " - ShutdownHook"));

		EventManager.register(this);

	   final Thread performanceThread = new Thread(() -> {
            try
            {
                for(;;)
                {
                    final IPerformanceCharts performanceCharts = (IPerformanceCharts) RmiManager.getRMI("PerformanceCharts");

                    final Runtime runtime = Runtime.getRuntime();
                    final int usedMemory = (int)((runtime.totalMemory() - runtime.freeMemory()) / 1000_000);
                    performanceCharts.update(IPerformanceCharts.Type.RAM, usedMemory + "MB /" + (runtime.maxMemory() / 1000_000) + "MB", usedMemory);

                    final OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                    performanceCharts.update(IPerformanceCharts.Type.CPU_LOAD, MathUtils.roundClean(operatingSystemMXBean.getProcessCpuLoad() * 100, 2) + "%", (int)(operatingSystemMXBean.getProcessCpuLoad() * 10000));

                    final int fps = frameCounter * (1000 / 75);
                    performanceCharts.update(IPerformanceCharts.Type.FPS, fps + "FPS", fps);
                    frameCounter = 0;
                    Thread.sleep(75);
                }
            }
            catch (InterruptedException | RemoteException e)
            {
                e.printStackTrace();
            }
        }, NAME + " - PerformanceThread");
        performanceThread.setDaemon(true);
        performanceThread.start();
        
        valuesRegistry = new ValuesRegistry();
        fileManager = new FileManager(this);
        fileManager.loadClientSettings();
        
        fileManager.loadSecondarySettings();
        
        addShutdownHook();
        
		moduleManager = new ModuleManager();
		
		gui = new GuiManager(moduleManager);
		
		gim = new GuiIngameManager();
		
	}
	
	
	public void destroy(){
		EventManager.unregister(this);
	}

	public void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                fileManager.save();
            }
        });
    }
	
	public GuiManager getGui() {
		return gui;
	}
	
	@EventTarget
	public void onSwapBuffers(SwapBuffersEvent event) {
		frameCounter++;
	}

	public ValuesRegistry getValuesRegistry() {
		return valuesRegistry;
	}

	public static void bootstrap(GameConfig gameConfig) {
		new Albedo(gameConfig);
	}
	
	public static Albedo getIrrlicht() {
		return instance;
	}

	
	public static Minecraft getMinecraft() {
		return WrapperDelegationHandler.createWrapperProxy(Minecraft.class, null).getMinecraft();
	}

	public ModuleManager getModuleManager() {
		return moduleManager;
	}

	public FriendManager getFriendManager() {
		return friendManager;
	}

	public static String getFullDir() {

		File file = new File(System.getProperty("user.home"), "Fallen");
		if (!file.exists()) {
			file.mkdirs();
		}
		return System.getProperty("user.home") + "/Fallen";
	}
	
	public float getDistanceToEntity(Entity entityIn) {
		float var2 = (float) (Albedo.getMinecraft().getPlayer().getX() - entityIn.getX());
		float var3 = (float) (Albedo.getMinecraft().getPlayer().getY() - entityIn.getY());
		float var4 = (float) (Albedo.getMinecraft().getPlayer().getZ() - entityIn.getZ());
		return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
	}
}
