package dev.negativekb.customitems.customitems;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class VillagerCurer extends CustomItem {
    public VillagerCurer() {
        super("villager-curer", "&2&lVillager Curer", List.of(
                "&7Instantly cures the Zombie Villager you click."
        ), Material.GOLDEN_APPLE);

        setItemAttributes(itemStack -> {
            // Makes the item unstackable
            NBTItem nbtItem = new NBTItem(itemStack);
            nbtItem.setString("curer-id", UUID.randomUUID().toString());
            nbtItem.applyNBT(itemStack);
        });

        setInteractAtEntityEvent(event -> {
            Entity rightClicked = event.getRightClicked();
            if (!rightClicked.getType().equals(EntityType.ZOMBIE_VILLAGER))
                return;

            Location location = rightClicked.getLocation();
            rightClicked.remove();
            Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.VILLAGER);

            Player player = event.getPlayer();
            PlayerInventory inventory = player.getInventory();
            ItemStack itemInMainHand = inventory.getItemInMainHand();

            int amount = itemInMainHand.getAmount();
            if (amount - 1 == 0) {
                inventory.remove(itemInMainHand);
                player.updateInventory();
                return;
            }

            itemInMainHand.setAmount((amount - 1));
            player.updateInventory();
        });

        setConsumeEvent(event -> {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        });

    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("villager-curer"), getItem());
        recipe.shape(
                " A ",
                "AGA",
                " A "
        );

        recipe.setIngredient('A', Material.APPLE);
        recipe.setIngredient('G', Material.GOLDEN_APPLE);

        return recipe;
    }
}
