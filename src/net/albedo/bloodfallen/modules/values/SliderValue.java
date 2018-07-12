package net.albedo.bloodfallen.modules.values;

import net.albedo.bloodfallen.gui.gui.AbstractComponent;
import net.albedo.bloodfallen.gui.gui.IRectangle;
import net.albedo.bloodfallen.gui.gui.components.Slider;

public class SliderValue extends AbstractValue<Double>{
	
	private double lowerBound;
	private double upperBound;
	private boolean rounded;
	
	public SliderValue(String name, String saveName, ValuesRegistry registry, double defaultValue, double lowerBound, double upperBound, boolean rounded) {
		super(name, saveName, registry, defaultValue);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.rounded = rounded;
	}
	
	public double getLowerBound() {
		return lowerBound;
	}
	
	public void setLowerBound(double lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	public double getUpperBound() {
		return upperBound;
	}
	
	public void setUpperBound(double upperBound) {
		this.upperBound = upperBound;
	}
	
	public boolean isRounded() {
		return rounded;
	}

	public void setRounded(boolean rounded) {
		this.rounded = rounded;
	}
	
	public void setValue(double value){
		
		if(rounded){
			value = (int)Math.round(value);
		}
		
		if(value > upperBound){
			value = upperBound;
		}
		
		if(value < lowerBound){
			value = lowerBound;
		}
		
		super.setValue(value);
	}
	
	@Override
	public AbstractComponent getComponent(IRectangle panel) {
		return new Slider(this);
	}
}
