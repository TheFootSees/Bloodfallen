package net.albedo.bloodfallen.engine.hooker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.albedo.bloodfallen.engine.events.Event;
import net.albedo.bloodfallen.engine.events.impl.LocalVariableEvent;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HookingMethod {
	
	public Class<? extends Event> value();

	
	public int flags() default Hooker.DEFAULT | Hooker.BEFORE;

	
	public int[] opcodes() default {};

	
	public int[] indices() default {};

	
	public int[] overwrite() default {};
}
