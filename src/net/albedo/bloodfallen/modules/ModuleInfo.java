package net.albedo.bloodfallen.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
	
	String name();
	String desc() default "";
	int defaultKey() default -1;
	
	Category category();
}
