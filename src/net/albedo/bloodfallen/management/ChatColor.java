package net.albedo.bloodfallen.management;


public enum ChatColor {
	BLACK("§0", 0xff000000, 0xff000000), DARK_BLUE("§1", 0xff0000aa, 0xff00002a), DARK_GREEN("§2", 0xff00aa00, 0xff002a00), DARK_AQUA("§3", 0xff00aaaa, 0xff002a2a), DARK_RED("§4", 0xffaa0000, 0xff2a0000), DARK_PURPLE("§5", 0xffaa00aa, 0xff2a002a), GOLD("§6", 0xffffaa00, 0xff2a2a00), GRAY("§7", 0xffaaaaaa, 0xff2a2a2a), DARK_GRAY("§8", 0xff555555, 0xff151515), BLUE("§9", 0xff5555ff, 0xff15153f), GREEN("§a", 0xff55ff55, 0xff153f15), AQUA("§b", 0xff55ffff, 0xff153f3f), RED("§c", 0xffff5555, 0xff3f1515), LIGHT_PURPLE("§d", 0xffff55ff, 0xff3f153f), YELLOW("§e", 0xffffff55, 0xff3f3f15), WHITE("§f", 0xffffffff, 0xff3f3f3f);

	private final String formattingCode;

	private final int foregroundColor, backgroundColor;

	ChatColor(String formattingCode, int foregroundColor, int backgroundColor) {
		this.formattingCode = formattingCode;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	
	public static ChatColor getColor(String formattingCode) {
		final char c = formattingCode.charAt(1);
		return c >= 'a' ? ChatColor.values()[c - 'a'] : ChatColor.values()[c - '0'];
	}

	
	public int getForegroundColor() {
		return foregroundColor;
	}

	
	public int getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public String toString() {
		return formattingCode;
	}
}
