package net.albedo.bloodfallen.management.keybind;

import org.lwjgl.input.Keyboard;


public enum KeyMask {
	NONE(null), SHIFT(new int[] { Keyboard.KEY_LSHIFT, Keyboard.KEY_RSHIFT }), CONTROL(new int[] { Keyboard.KEY_LCONTROL, Keyboard.KEY_RCONTROL }), ALT(new int[] { Keyboard.KEY_LMENU, Keyboard.KEY_RMENU });

	private final int[] keys;

	KeyMask(int[] keys) {
		this.keys = keys;
	}

	
	public int[] getKeys() {
		return keys;
	}

	
	public boolean isDown() {
		if (keys != null) {
			for (int key : keys)
				if (Keyboard.isKeyDown(key))
					return true;
			return false;
		}
		return true;
	}
}
