package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

/**
 * This is a utility class to mutate an ItemStacks
 * ItemMeta. This class was made to reduce code by
 * extracting the ItemMeta and setting it inside the mutate
 * method.
 */
@UtilityClass
public final class MetaMutator {

	/**
	 * This method uses a Consumer to modify (mutate) an ItemStacks
	 * ItemMeta. This method extracts the ItemStacks ItemMeta and passes
	 * it to the consumer, then sets the ItemStacks ItemMeta to the modified ItemMeta.
	 *
	 * @param stack The stack to be modified
	 * @param metaConsumer The consumer representing the meta modification
	 */
	public void mutate(ItemStack stack, Consumer<ItemMeta> metaConsumer) {

		ItemMeta meta = stack.getItemMeta();
		metaConsumer.accept(meta);
		stack.setItemMeta(meta);
	}
}
