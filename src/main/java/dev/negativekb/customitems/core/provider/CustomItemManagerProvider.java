package dev.negativekb.customitems.core.provider;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.negativekb.customitems.api.CustomItemManager;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CustomItemManagerProvider implements CustomItemManager {
    private final ArrayList<CustomItem> cache = new ArrayList<>();

    @Override
    public void registerCustomItem(@NotNull CustomItem customItem) {
        cache.add(customItem);
    }

    @Override
    public Optional<CustomItem> getCustomItem(@NotNull String name) {
        return cache.stream().filter(customItem -> customItem.getId().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public Optional<CustomItem> getCustomItem(@NotNull ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("custom-item") || !nbtItem.hasKey("custom-item-type"))
            return Optional.empty();

        Collection<CustomItem> customItems = getCustomItems();
        return customItems.stream().filter(customItem ->
                        customItem.getId().equalsIgnoreCase(nbtItem.getString("custom-item-type")))
                .findFirst();
    }

    @Override
    public Collection<CustomItem> getCustomItems() {
        return cache;
    }
}
