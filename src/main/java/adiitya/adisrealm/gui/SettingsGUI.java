package adiitya.adisrealm.gui;

import adiitya.adisrealm.utils.MetaMutator;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@UtilityClass
public final class SettingsGUI {

	public void show(Player p) {
		get().open(p);
	}

	public SmartInventory get() {
		return SmartInventory.builder()
				.closeable(true)
				.id("settings")
				.title("Settings")
				.size(1, 5)
				.type(InventoryType.HOPPER)
				.provider(new SettingsProvider())
				.build();
	}

	private static class SettingsProvider implements InventoryProvider {

		@Override
		public void init(Player player, InventoryContents contents) {

			ItemStack stack = new ItemStack(Material.NAME_TAG);
			MetaMutator.mutate(stack, meta -> meta.setDisplayName("Name color"));

			ClickableItem color = ClickableItem.of(stack, e -> ColorGUI.show(player));
			contents.add(color);
		}

		@Override
		public void update(Player player, InventoryContents contents) {

		}
	}
}
