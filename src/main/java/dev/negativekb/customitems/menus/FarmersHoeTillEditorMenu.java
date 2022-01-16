package dev.negativekb.customitems.menus;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.negativekb.api.gui.GUI;
import dev.negativekb.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FarmersHoeTillEditorMenu extends GUI {

    public FarmersHoeTillEditorMenu(ItemStack itemStack) {
        super("&a&lFarmer's Hoe Till Editor", 1);

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("super-till")) {
            nbtItem.setBoolean("super-till", false);
            nbtItem.applyNBT(itemStack);
        }

        setItemClickEvent(4, player -> {
            Boolean status = nbtItem.getBoolean("super-till");
            if (status) {
                return new ItemBuilder(Material.LIME_WOOL).setName("&a&lSuper Till")
                        .addLoreLine("&7When tilling land, it will till a 3x3")
                        .addLoreLine("&7area instead of the singular block")
                        .addLoreLine(" ")
                        .addLoreLine("&aThis setting is currently enabled!")
                        .build();
            } else {
                return new ItemBuilder(Material.RED_WOOL).setName("&c&lSuper Till")
                        .addLoreLine("&7When tilling land, it will till a 3x3")
                        .addLoreLine("&7area instead of the singular block")
                        .addLoreLine(" ")
                        .addLoreLine("&cThis setting is currently disabled!")
                        .build();
            }
        }, (player, event) -> {
            Boolean status = nbtItem.getBoolean("super-till");
            nbtItem.setBoolean("super-till", !status);
            nbtItem.applyNBT(itemStack);
            player.updateInventory();

            refresh(player);
        });
    }
}
