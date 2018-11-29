package adiitya.adisrealm.gui;

import adiitya.adisrealm.utils.ItemBuilder;
import adiitya.adisrealm.utils.NameColorManager;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class ColorGUI {

	public static final ItemStack BACK_ITEM = new ItemBuilder(Material.ARROW).setName("Go back").build();

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

				int y = (i / 5) + 1;
				int x = (i % 5) + 2;

				NameColor color = NameColor.values()[i];
				ClickableItem item = getItem(player, color);
				contents.set(y, x, item);

			}

			contents.set(4, 4, getItem(player, NameColor.WHITE));

			contents.inventory().getParent()
					.ifPresent(inv -> contents.set(5, 0, ClickableItem.of(BACK_ITEM, e -> inv.open(player))));
		}

		private ClickableItem getItem(Player p, NameColor color) {

			ItemBuilder b = new ItemBuilder(color.mat)
					.addFlags(ItemFlag.HIDE_ENCHANTS)
					.setName(color.getName())
					.addLore(String.format("%s%s", color.color.toString(), p.getName()));

			if (NameColorManager.isColor(p, color.color))
				b.addEnchant(Enchantment.DURABILITY, 1);

			return ClickableItem.of(b.build(), e -> NameColorManager.setColor(p.getUniqueId(), color.color));
		}

		@Override
		public void update(Player player, InventoryContents contents) {

		}
	}
}
