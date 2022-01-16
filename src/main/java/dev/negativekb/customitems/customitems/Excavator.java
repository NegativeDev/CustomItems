package dev.negativekb.customitems.customitems;

import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class Excavator extends CustomItem {

    private final List<Material> unbreakableBlocks;
    public Excavator() {
        super("excavator", "&9&lExcavator", List.of(
                "&7When digging, a 5x5 area will be dug out."
        ), Material.NETHERITE_SHOVEL);

        unbreakableBlocks = Arrays.asList(Material.BEDROCK, Material.CHEST, Material.ENDER_CHEST,
                Material.END_PORTAL, Material.NETHER_PORTAL, Material.END_PORTAL_FRAME,
                Material.BARRIER, Material.ENCHANTING_TABLE, Material.BOOKSHELF, Material.ANVIL);

        setBlockBreakEvent(event -> {
            Block block = event.getBlock();

            event.setDropItems(false);

            getNearbyBlocks(block.getLocation(), 5).stream()
                    .filter(block1 -> !unbreakableBlocks.contains(block1.getType()))
                    .forEach(block1 -> {
                        block1.setType(Material.AIR, true);
                        Location location = block1.getLocation();
                        Objects.requireNonNull(location.getWorld()).playEffect(location,
                                Effect.SMOKE, 1, 10);
                    });
        });
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("excavator"), getItem());
        recipe.shape(
                "TTT",
                "TPT",
                "TTT"
        );

        recipe.setIngredient('T', Material.TNT);
        recipe.setIngredient('P', Material.NETHERITE_SHOVEL);

        return recipe;
    }

    public List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                Block blockAt = Objects.requireNonNull(location.getWorld()).getBlockAt(x, location.getBlockY(), z);
                if (blockAt.getType().equals(Material.AIR))
                    continue;

                blocks.add(location.getWorld().getBlockAt(x, location.getBlockY(), z));
            }
        }
        return blocks;
    }
}
