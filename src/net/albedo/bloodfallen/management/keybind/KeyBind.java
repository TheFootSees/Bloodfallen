package net.albedo.bloodfallen.management.keybind;

import org.lwjgl.input.Keyboard;


public class KeyBind {
	private KeyMask keyMask;
	private int key;

	public KeyBind(KeyMask keyMask, int key) {
		this.keyMask = keyMask;
		this.key = key;
	}

	
	public KeyMask getKeyMask() {
		return keyMask;
	}

	
	public int getKey() {
		return key;
	}

	
	public boolean isDown() {
		return keyMask.isDown() && Keyboard.isKeyDown(key);
	}

	
	public boolean isPressed() {
		// TODO: implement.
		throw new RuntimeException();
	}
}
