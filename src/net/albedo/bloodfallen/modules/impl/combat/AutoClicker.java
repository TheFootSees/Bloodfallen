package net.albedo.bloodfallen.modules.impl.combat;

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
import net.albedo.bloodfallen.modules.values.SliderValue;


@ModuleInfo(name = "AutoClicker", desc = "Automatic clicks", category = Category.COMBAT)
public class AutoClicker extends Module {
	

	@ValueTarget
	private ToggleValue blockhit = new ToggleValue(this, "BlockHit", false);

	private TimeHelper timer = new TimeHelper();

	public static SliderValue cps;
	
	public AutoClicker() {
        super(new AbstractValue[]{
                cps = new SliderValue("cps", "clicker.cps", Albedo.valuesRegistry, 10, 1, 50, true)
        });
    }
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		
//		for(Module m : ModuleManager.modules){
//            if(Keyboard.getEventKey() == m.getKeybind()){
//                Albedo.getIrrlicht().getModuleManager().toggle(m);
//            }
//        }
		
		if (isToggled()) {
			Minecraft minecraft;
			EntityLivingBase entityLivingBase;
			if (Mouse.isButtonDown(0) && (blockhit.value || !Mouse.isButtonDown(1))
					&& (minecraft = Albedo.getMinecraft()).getGuiScreen().getHandle() == null
					&& timer.hasMSPassed(1000 / cps.getValue().longValue())) {
				minecraft.setLeftClickDelay(0);
				minecraft.clickMouse();
				timer.reset();
			}
		}
	}
}
