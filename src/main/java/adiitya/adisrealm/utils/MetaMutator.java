package adiitya.adisrealm.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public final class MetaMutator {

	public static void mutate(ItemStack stack, Consumer<ItemMeta> metaConsumer) {

		ItemMeta meta = stack.getItemMeta();
		metaConsumer.accept(meta);
		stack.setItemMeta(meta);
	}
}
