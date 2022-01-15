package dev.negativekb.customitems.api;

import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface CustomItemManager {

    void registerCustomItem(@NotNull CustomItem customItem);

    Optional<CustomItem> getCustomItem(@NotNull String name);

    Optional<CustomItem> getCustomItem(@NotNull ItemStack itemStack);

    Collection<CustomItem> getCustomItems();

}
