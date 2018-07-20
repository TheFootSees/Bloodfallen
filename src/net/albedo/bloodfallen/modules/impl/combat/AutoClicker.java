package net.albedo.bloodfallen.modules.impl.combat;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.albedo.bloodfallen.Albedo;
import net.albedo.bloodfallen.engine.events.EventTarget;
import net.albedo.bloodfallen.engine.hooker.impl.Render2DEvent;
import net.albedo.bloodfallen.engine.hooker.impl.UpdateEvent;
import net.albedo.bloodfallen.engine.wrappers.client.Minecraft;
import net.albedo.bloodfallen.engine.wrappers.entity.EntityLivingBase;
import net.albedo.bloodfallen.gui.GuiManager;
import net.albedo.bloodfallen.management.TimeHelper;
import net.albedo.bloodfallen.management.values.ToggleValue;
import net.albedo.bloodfallen.management.values.ValueTarget;
import net.albedo.bloodfallen.modules.Category;
import net.albedo.bloodfallen.modules.Module;
import net.albedo.bloodfallen.modules.ModuleInfo;
import net.albedo.bloodfallen.modules.ModuleManager;
import net.albedo.bloodfallen.modules.values.AbstractValue;
import net.albedo.bloodfallen.modules.values.BooleanValue;
import net.albedo.bloodfallen.modules.values.SliderValue;


@ModuleInfo(name = "AutoClicker", desc = "Automatic clicks", category = Category.COMBAT)
public class AutoClicker extends Module {

	private static final Random RANDOM = new Random();
	
	private static final BooleanValue BLOCK_HIT_VALUE = new BooleanValue("BlockHit", "clicker.blockhit", Albedo.valuesRegistry, false);
	private static final BooleanValue RANDOMIZE_VALUE = new BooleanValue("Randomize", "clicker.randomize", Albedo.valuesRegistry, true);

	private final TimeHelper timer = new TimeHelper();

	public static SliderValue cpsValue;
	
	public AutoClicker() {
        super(new AbstractValue[]{
        		cpsValue = new SliderValue("cps", "clicker.cps", Albedo.valuesRegistry, 10, 1, 50, true),
        				BLOCK_HIT_VALUE,
        				RANDOMIZE_VALUE
        });
    }
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		
//		for(Module m : ModuleManager.modules){
//            if(Keyboard.getEventKey() == m.getKeybind()){
//                Albedo.getIrrlicht().getModuleManager().toggle(m);
//            }
//        }
		
		Minecraft minecraft;
		
		if (isToggled()) {
			
			long value = cpsValue.getValue().longValue();
			int bound = 4;
			long cps = RANDOMIZE_VALUE.getValue() ? value - bound / 2 + RANDOM.nextInt(bound + 1) : value;
			long time = 1000 / cps;
			if((minecraft = Albedo.getMinecraft()).getGuiScreen().getHandle() == null && timer.hasMSPassed(time)) {
				if (Mouse.isButtonDown(0) && (BLOCK_HIT_VALUE.getValue() || !Mouse.isButtonDown(1))){
					minecraft.setLeftClickDelay(0);
					minecraft.clickMouse();
				}
				timer.reset();
			}
			
		}
	}
}
