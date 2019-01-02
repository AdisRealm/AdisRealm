package adiitya.adisrealm.utils.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class to ease the creation and modification of ItemStacks.
 * This class eases multiple actions including enchantment modification,
 * name modification, lore modification, and meta motification.
 */
public final class ItemBuilder {

	private final ItemStack stack;
	private List<String> lore = new ArrayList<>();

	/**
	 * Creates a new ItemBuilder instance from a material.
	 * A new ItemStack will be created with a size of 1.
	 *
	 * @param material The material for the ItemStack
	 */
	public ItemBuilder(Material material) {
		this.stack = new ItemStack(material);
	}

	/**
	 * Creates a new ItemBuilder instance from an existing ItemStack.
	 * All existing data in the ItemStack will be maintained, except
	 * for the data changed when using this instance.
	 *
	 * @param stack The stack to be modified
	 */
	public ItemBuilder(ItemStack stack) {
		this.stack = stack;
		this.lore = stack.getItemMeta().getLore();
	}

	/**
	 * Sets the name of the ItemStack
	 *
	 * @param name The new name
	 *
	 * @return The ItemBuilder
	 */
	public ItemBuilder setName(String name) {
		MetaMutator.mutate(stack, meta -> meta.setDisplayName(name));
		return this;
	}

	/**
	 * Add an enchantment to the ItemStack. This method uses
	 * {@link ItemStack#addUnsafeEnchantment(Enchantment, int)}
	 * to add the enchantment.
	 *
	 * @param ench The enchantment
	 * @param level The enchantment level
	 *
	 * @return The ItemBuilder
	 *
	 * @see ItemStack#addUnsafeEnchantment(Enchantment, int)
	 */
	public ItemBuilder addEnchant(Enchantment ench, int level) {
		stack.addUnsafeEnchantment(ench, level);
		return this;
	}

	/**
	 * This clears all Enchantments from the ItemStack
	 *
	 * @return The ItemBuilder
	 */
	public ItemBuilder clearEnchants() {
		MetaMutator.mutate(stack, this::removeEnchantments);
		return this;
	}

	/**
	 * This will add a line of lore to the ItemStack.
	 * Any lore added before this will appear above this lore.
	 *
	 * @param lore The lore to add
	 *
	 * @return The ItemBuilder
	 */
	public ItemBuilder addLore(String lore) {
		this.lore.add(lore);
		return this;
	}

	/**
	 * Adds the sepcified ItemFlags to the ItemStack
	 *
	 * @param flags The flag(s) to add
	 *
	 * @return The ItemBuilder
	 */
	public ItemBuilder addFlags(ItemFlag... flags) {
		MetaMutator.mutate(stack, meta -> meta.addItemFlags(flags));
		return this;
	}

	/**
	 * Builds the final ItemStack. This method will
	 * apply the lore to the ItemStack, then return it.
	 *
	 * @return The ItemStack
	 */
	public ItemStack build() {
		MetaMutator.mutate(stack, meta -> meta.setLore(lore));
		return stack;
	}

	private void removeEnchantments(ItemMeta meta) {
		meta.getEnchants().keySet().forEach(meta::removeEnchant);
	}
}
