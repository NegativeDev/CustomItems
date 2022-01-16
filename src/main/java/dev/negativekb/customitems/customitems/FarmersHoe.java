package dev.negativekb.customitems.customitems;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import dev.negativekb.customitems.menus.FarmersHoeTillEditorMenu;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

@Registry(type = RegistryType.CUSTOM_ITEM)
public class FarmersHoe extends CustomItem {

    private final List<Material> tillableBlocks;
    public FarmersHoe() {
        super("farmers-hoe", "&a&lFarmer's Hoe", Arrays.asList(
                "&7Breaking a crop will automatically collect the drops into",
                "&7your inventory and automatically replant the crop.",
                " ",
                "&aShift-Right-Clicking &7the air will open the Super-Till editor"
        ), Material.NETHERITE_HOE);

        tillableBlocks = Arrays.asList(Material.GRASS_BLOCK, Material.DIRT);

        setItemAttributes(itemStack -> {
            ItemMeta itemMeta = itemStack.getItemMeta();
            assert itemMeta != null;
            itemMeta.setUnbreakable(true);

            itemStack.setItemMeta(itemMeta);
        });

        setBlockBreakEvent(event -> {
            Player player = event.getPlayer();
            Block block = event.getBlock();
            if (block.getBlockData() instanceof Ageable crop) {
                event.setDropItems(false);
                event.setCancelled(true);
                if (crop.getAge() != crop.getMaximumAge())
                    return;

                Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
                drops.forEach(itemStack -> {
                    if (player.getInventory().firstEmpty() == -1) {
                        Objects.requireNonNull(player.getLocation().getWorld()).dropItem(block.getLocation(), itemStack);
                    } else {
                        player.getInventory().addItem(itemStack);
                    }
                });

                crop.setAge(0);
                block.setBlockData(crop);
            }
        });

        setInteractEvent(event -> {
            Action action = event.getAction();
            if (action.equals(Action.RIGHT_CLICK_AIR)) {
                Player player = event.getPlayer();
                ItemStack item = event.getItem();
                assert item != null;

                if (player.isSneaking())
                    new FarmersHoeTillEditorMenu(item).open(player);
            }

            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                ItemStack item = event.getItem();
                assert item != null;

                NBTItem nbtItem = new NBTItem(item);
                if (!nbtItem.hasKey("super-till") || !nbtItem.getBoolean("super-till"))
                    return;

                Block clickedBlock = event.getClickedBlock();
                assert clickedBlock != null;

                till(clickedBlock.getRelative(BlockFace.NORTH));
                till(clickedBlock.getRelative(BlockFace.EAST));
                till(clickedBlock.getRelative(BlockFace.SOUTH));
                till(clickedBlock.getRelative(BlockFace.WEST));
                till(clickedBlock.getRelative(BlockFace.SOUTH_WEST));
                till(clickedBlock.getRelative(BlockFace.NORTH_WEST));
                till(clickedBlock.getRelative(BlockFace.NORTH_EAST));
                till(clickedBlock.getRelative(BlockFace.SOUTH_EAST));
            }
        });


    }

    private void till(Block block) {
        if (!tillableBlocks.contains(block.getType()))
            return;

        boolean isAir = block.getRelative(BlockFace.UP).getType().equals(Material.AIR);
        if (!isAir)
            return;

        block.setType(Material.FARMLAND);
        Location location = block.getLocation();
        Objects.requireNonNull(location.getWorld()).playSound(location,
                Sound.ITEM_HOE_TILL, 1, 1);
    }



    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft("farmers-hoe"), getItem());
        recipe.shape(
                "PCP",
                "WHW",
                "PCP"
        );

        recipe.setIngredient('P', Material.POTATO);
        recipe.setIngredient('C', Material.CARROT);
        recipe.setIngredient('W', Material.WHEAT);
        recipe.setIngredient('H', Material.NETHERITE_HOE);

        return recipe;
    }
}
