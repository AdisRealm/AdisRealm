package adiitya.adisrealm.gui;

import adiitya.adisrealm.AdisRealm;
import adiitya.adisrealm.utils.item.ItemBuilder;
import adiitya.adisrealm.utils.NameManager;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@UtilityClass
public final class ColorGUI {

	private static final ItemStack BACK_ITEM = new ItemBuilder(Material.ARROW).setName("Go back").build();
	private static final ItemStack SEPARATOR = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();

	public void show(Player p) {
		get().open(p);
	}

	public SmartInventory get() {
		return SmartInventory.builder()
				.closeable(true)
				.title("Name Color")
				.id("color")
				.size(6, 9)
				.parent(SettingsGUI.get())
				.provider(new ColorProvider())
				.build();
	}

	private static class ColorProvider implements InventoryProvider {

		@Override
		public void init(Player player, InventoryContents contents) {

			for (int i = 0; i < NameColor.values().length - 1; i++) {

				int y = i / 5;
				int x = (i % 5) + 2;

				NameColor color = NameColor.values()[i];
				ClickableItem item = getItem(player, color);
				contents.set(y, x, item);

			}

			contents.set(3, 4, getItem(player, NameColor.WHITE));

			contents.inventory().getParent()
					.ifPresent(inv -> contents.set(5, 4, ClickableItem.of(BACK_ITEM, e -> inv.open(player))));

			contents.fillRow(4, ClickableItem.of(SEPARATOR, e -> {}));
		}

		private void onClickColor(Player p, NameColor color, Inventory inv) {

			NameManager.setColor(p.getName(), color.color);

			for (int i = 0; i < 36; i++) {

				ItemStack item = inv.getItem(i);

				if (item == null)
					continue;

				Material mat = item.getType();
				NameColor c = NameColor.fromMaterial(mat);
				ItemBuilder b = new ItemBuilder(item).clearEnchants();

				if (NameManager.isColor(p.getName(), c.color))
					b.addEnchant(Enchantment.DURABILITY, 1);

				inv.setItem(i, b.build());
			}
		}

		private ClickableItem getItem(Player p, NameColor color) {

			ItemBuilder b = new ItemBuilder(color.mat)
					.addFlags(ItemFlag.HIDE_ENCHANTS)
					.setName(ChatColor.RESET + color.getName())
					.addLore(String.format("%s%s", color.color, p.getName()));

			if (NameManager.isColor(p.getName(), color.color))
				b.addEnchant(Enchantment.DURABILITY, 1);

			return ClickableItem.of(b.build(), e ->
					Bukkit.getScheduler().runTaskAsynchronously(AdisRealm.getInstance(), () ->
							onClickColor(p, color, e.getInventory())));
		}

		@Override
		public void update(Player player, InventoryContents contents) {
			// unused
		}
	}

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
			return Arrays.stream(NameColor.values())
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
}
