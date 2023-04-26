package fr.naruse.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemUtils {

    public static ItemStack buildItem(ItemStack itemStack, boolean enchant) {
        return buildItem(itemStack.getType(), itemStack.getAmount(), itemStack.getItemMeta().getDisplayName(), enchant, null);
    }

    public static ItemStack buildItem(ItemStack itemStack, String name, boolean enchant) {
        return buildItem(itemStack.getType(), itemStack.getAmount(), name, enchant, null);
    }

    public static ItemStack buildItem(ItemStack itemStack, int amount, String name, boolean enchant) {
        return buildItem(itemStack.getType(), amount, name, enchant, null);
    }

    public static ItemStack buildItem(Material material, boolean enchant) {
        return buildItem(material, 1, "", enchant, null);
    }

    public static ItemStack buildItem(Material material, String name, boolean enchant) {
        return buildItem(material, 1, name, enchant, null);
    }

    public static ItemStack buildItem(Material material, int amount, String name, boolean enchant) {
        return buildItem(material, amount, name, enchant, null);
    }

    public static ItemStack buildItem(Material material, int amount, String name, boolean enchant, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        if (enchant) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
        }

        if (lore != null) {
            meta.setLore(lore);
        }

        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS});
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
