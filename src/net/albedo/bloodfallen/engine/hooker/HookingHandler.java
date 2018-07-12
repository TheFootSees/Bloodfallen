package net.albedo.bloodfallen.engine.hooker;

import org.objectweb.asm.tree.MethodNode;


public interface HookingHandler {
	
	public void hook(MethodNode methodNode);
}
