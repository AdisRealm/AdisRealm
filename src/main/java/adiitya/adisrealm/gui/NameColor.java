package adiitya.adisrealm.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public enum NameColor {

	BLACK(ChatColor.BLACK, Material.INK_SAC),
	DARK_BLUE(ChatColor.DARK_BLUE, Material.LAPIS_LAZULI),
	DARK_GREEN(ChatColor.DARK_GREEN, Material.CACTUS_GREEN),
	DARK_AQUA(ChatColor.DARK_AQUA, Material.CYAN_DYE),
	DARK_RED(ChatColor.DARK_RED, Material.REDSTONE),
	DARK_PURPLE(ChatColor.DARK_PURPLE, Material.PURPLE_DYE),
	GOLD(ChatColor.GOLD, Material.ORANGE_DYE),
	GRAY(ChatColor.GRAY, Material.LIGHT_GRAY_DYE),
	DARK_GRAY(ChatColor.DARK_GRAY, Material.GRAY_DYE),
	BLUE(ChatColor.BLUE, Material.LIGHT_BLUE_DYE),
	GREEN(ChatColor.GREEN, Material.LIME_DYE),
	AQUA(ChatColor.AQUA, Material.DIAMOND),
	RED(ChatColor.RED, Material.ROSE_RED),
	LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, Material.MAGENTA_DYE),
	YELLOW(ChatColor.YELLOW, Material.DANDELION_YELLOW),
	WHITE(ChatColor.WHITE, Material.BONE_MEAL);

	public final ChatColor color;
	public final Material mat;

	NameColor(ChatColor color, Material mat) {
		this.color = color;
		this.mat = mat;
	}

	public static NameColor fromMaterial(Material material) {
		return Arrays.stream(values())
				.filter(n -> n.mat.equals(material))
				.findAny()
				.orElse(NameColor.WHITE);
	}

	public String getName() {

		String name = color.name().toLowerCase().substring(1);
		name = color.name().substring(0, 1) + name;

		return name.replace("_", " ");
	}

	public String getFormattedName() {
		return color.toString() + getName();
	}
}
