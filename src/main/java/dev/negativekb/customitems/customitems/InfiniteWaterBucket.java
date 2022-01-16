package dev.negativekb.customitems.customitems;

import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class InfiniteWaterBucket extends CustomItem {

    public InfiniteWaterBucket() {
        super("infinite-water-bucket", "&b&lInfinite Water Bucket", List.of(
                "&7Automatically regenerate water after placement."
        ), Material.WATER_BUCKET);

        setItemAttributes(itemStack -> {
            // Add glowing
            ItemMeta itemMeta = itemStack.getItemMeta();
            assert itemMeta != null;
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.addEnchant(Enchantment.DURABILITY, 10, true);
            itemStack.setItemMeta(itemMeta);
        });

        setBucketEmptyEvent(event -> {
            event.setItemStack(getItem());
            Location location = event.getBlock().getLocation();

            Objects.requireNonNull(location.getWorld())
                    .playEffect(location, Effect.SMOKE, 1, 10);
        });
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("infinite-water-bucket"), getItem());
        recipe.shape(
                "BBB",
                "BBB",
                "BBB"
        );

        recipe.setIngredient('B', Material.WATER_BUCKET);
        return recipe;
    }
}
