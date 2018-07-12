package net.albedo.bloodfallen.modules.values;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.buttons.BooleanButton;

public class BooleanValue extends AbstractValue<Boolean>{

	public BooleanValue(String name, String saveName, ValuesRegistry registry, Boolean defaultValue) {
		super(name, saveName, registry, defaultValue);
	}

	@Override
	public AbstractComponent getComponent(IRectangle panel) {
		return new BooleanButton(this);
	}
}
