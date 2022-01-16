package dev.negativekb.customitems.customitems;

import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class VirtualEnderChest extends CustomItem {
    public VirtualEnderChest() {
        super("virtual-ender-chest", "&5&lVirtual Ender Chest", List.of(
                "&7Right-Clicking will open a virtual Ender Chest"
        ), Material.ENDER_CHEST);

        setItemAttributes(itemStack -> {
            // Add glowing
            ItemMeta itemMeta = itemStack.getItemMeta();
            assert itemMeta != null;
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
            itemStack.setItemMeta(itemMeta);
        });

        setInteractEvent(event -> {
            Action action = event.getAction();
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);

                Player player = event.getPlayer();
                player.openInventory(player.getEnderChest());
                player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
            }
        });
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("virtual-ender-chest"), getItem());
        recipe.shape(
                "SSS",
                "OCO",
                "OEO"
        );

        recipe.setIngredient('S', Material.SHULKER_SHELL);
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('E', Material.ENDER_EYE);
        recipe.setIngredient('C', Material.ENDER_CHEST);

        return recipe;
    }
}
