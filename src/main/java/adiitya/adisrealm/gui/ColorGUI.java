package adiitya.adisrealm.gui;

import adiitya.adisrealm.utils.MetaMutator;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

@UtilityClass
public final class ColorGUI {

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
			contents.set(5, 0, ClickableItem.of(getBackItem(), e -> contents.inventory().getParent().get().open(player)));
		}

		private ItemStack getBackItem() {

			ItemStack stack = new ItemStack(Material.ARROW, 1);
			MetaMutator.mutate(stack, meta -> meta.setDisplayName("Go back"));

			return stack;
		}

		private ClickableItem getItem(Player p, NameColor color) {

			ItemStack stack = new ItemStack(color.mat, 1);

			if (NameColorManager.isColor(p, color.color))
				stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);

			MetaMutator.mutate(stack, meta -> {
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				meta.setDisplayName(color.getName());
				meta.setLore(Collections.singletonList(String.format("%s%s", color.color.toString(), p.getName())));
			});

			return ClickableItem.of(stack, e -> NameColorManager.setColor(p.getUniqueId(), color.color));
		}

		@Override
		public void update(Player player, InventoryContents contents) {

		}
	}
}
