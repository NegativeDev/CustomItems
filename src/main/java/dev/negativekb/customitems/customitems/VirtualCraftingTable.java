package dev.negativekb.customitems.customitems;

import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class VirtualCraftingTable extends CustomItem {
    public VirtualCraftingTable() {
        super("virtual-crafting-table", "&a&lVirtual Crafting Table", List.of(
                "&7Right-Clicking will open a virtual Crafting Table"
        ), Material.CRAFTING_TABLE);

        setInteractEvent(event -> {
            Action action = event.getAction();
            if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
                event.setCancelled(true);

                Player player = event.getPlayer();
                player.openWorkbench(player.getLocation(), true);
            }
        });
    }

    @Override
    public ShapedRecipe getRecipe() {
        ItemStack item = getItem();

        // Add glowing
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
        item.setItemMeta(itemMeta);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("virtual-crafting-table"), item);
        recipe.shape(
                " M ",
                "MBM",
                " M "
        );

        recipe.setIngredient('B', Material.CRAFTING_TABLE);
        recipe.setIngredient('M', Material.BOOK);

        return recipe;
    }
}
