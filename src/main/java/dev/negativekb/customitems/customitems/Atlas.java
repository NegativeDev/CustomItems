package dev.negativekb.customitems.customitems;

import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class Atlas extends CustomItem {

    private final List<Material> unbreakableBlocks;
    public Atlas() {
        super("atlas", "&b&lAtlas", List.of(
                "&7Mining a block will break a 3x3x3 area"
        ), Material.NETHERITE_PICKAXE);

        unbreakableBlocks = Arrays.asList(Material.BEDROCK, Material.CHEST, Material.ENDER_CHEST,
                Material.END_PORTAL, Material.NETHER_PORTAL, Material.END_PORTAL_FRAME,
                Material.BARRIER, Material.ENCHANTING_TABLE, Material.BOOKSHELF, Material.ANVIL);

        setBlockBreakEvent(event -> {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Block block = event.getBlock();

            event.setDropItems(false);
            attemptBreak(player, itemInMainHand, block);
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.NORTH));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.EAST));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.SOUTH));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.WEST));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.SOUTH_WEST));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.NORTH_WEST));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.NORTH_EAST));
            attemptBreak(player, itemInMainHand, block.getRelative(BlockFace.SOUTH_EAST));

            Block top = block.getRelative(BlockFace.UP);
            attemptBreak(player, itemInMainHand, top);
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.NORTH));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.EAST));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.SOUTH));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.WEST));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.SOUTH_WEST));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.NORTH_WEST));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.NORTH_EAST));
            attemptBreak(player, itemInMainHand, top.getRelative(BlockFace.SOUTH_EAST));

            Block bottom = block.getRelative(BlockFace.DOWN);
            attemptBreak(player, itemInMainHand, bottom);
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.NORTH));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.EAST));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.SOUTH));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.WEST));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.SOUTH_WEST));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.NORTH_WEST));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.NORTH_EAST));
            attemptBreak(player, itemInMainHand, bottom.getRelative(BlockFace.SOUTH_EAST));

        });
    }

    private void attemptBreak(Player player, ItemStack itemStack, Block block) {
        if (unbreakableBlocks.contains(block.getType()))
            return;

        block.getDrops(itemStack).forEach(item -> player.getInventory().addItem(item));
        block.setType(Material.AIR);
        Location location = block.getLocation();
        Objects.requireNonNull(location.getWorld()).playEffect(location,
                Effect.SMOKE, 1, 10);
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("atlas"), getItem());
        recipe.shape(
                "TTT",
                "TPT",
                "TTT"
        );

        recipe.setIngredient('T', Material.TNT);
        recipe.setIngredient('P', Material.NETHERITE_PICKAXE);

        return recipe;
    }

}
