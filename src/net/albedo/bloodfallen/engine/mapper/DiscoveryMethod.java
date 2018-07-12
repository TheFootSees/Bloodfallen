package net.albedo.bloodfallen.engine.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.albedo.bloodfallen.engine.wrappers.Wrapper;


@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DiscoveryMethod {
	
	public int checks() default Mapper.DEFAULT;

	
	public int modifiers() default 0;

	
	public Class<? extends Wrapper> declaring() default Wrapper.class;

	
	public String[] constants() default {};

	
	public int[] opcodes() default {};
}
