package dev.negativekb.customitems.customitems;

import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class SpecialBoneMeal extends CustomItem {

    public SpecialBoneMeal() {
        super("special-bone-meal", "&f&lSpecial Bone Meal", List.of(
                "&7Right-Clicking a Crop will instantly grow it."
        ), Material.BONE_MEAL);

        setInteractEvent(event -> {
            if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))
                return;

            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null)
               return;

            if (clickedBlock.getBlockData() instanceof Ageable crop) {
                if (crop.getAge() == crop.getMaximumAge()) {
                    event.setCancelled(true);
                    return;
                }

                crop.setAge(crop.getMaximumAge());
                clickedBlock.setBlockData(crop);

                ItemStack item = event.getItem();
                if (item == null) // Something went wrong.
                    return;

                Player player = event.getPlayer();
                PlayerInventory inventory = player.getInventory();

                int amount = item.getAmount();
                if (amount - 1 == 0) {
                    inventory.remove(item);
                    player.updateInventory();
                    return;
                }

                item.setAmount((amount - 1));
                player.updateInventory();
            }

        });
    }

    @Override
    public ShapedRecipe getRecipe() {
        ItemStack item = getItem();
        item.setAmount(4);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("special-bonemeal"), item);
        recipe.shape(
                " M ",
                "MBM",
                " M "
        );

        recipe.setIngredient('B', Material.BONE_MEAL);
        recipe.setIngredient('M', Material.BONE);

        return recipe;
    }
}
