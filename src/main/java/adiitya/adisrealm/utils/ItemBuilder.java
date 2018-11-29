package adiitya.adisrealm.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ItemBuilder {

	private ItemStack stack;
	private List<String> lore = new ArrayList<>();

	public ItemBuilder(Material material) {
		this.stack = new ItemStack(material);
	}

	public ItemBuilder setName(String name) {
		MetaMutator.mutate(stack, meta -> meta.setDisplayName(name));
		return this;
	}

	public ItemBuilder addEnchant(Enchantment ench, int level) {
		stack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemBuilder addLore(String lore) {
		this.lore.add(lore);
		return this;
	}

	public ItemBuilder addFlags(ItemFlag... flags) {
		MetaMutator.mutate(stack, meta -> meta.addItemFlags(flags));
		return this;
	}

	public ItemStack build() {
		MetaMutator.mutate(stack, meta -> meta.setLore(lore));
		return stack;
	}
}
