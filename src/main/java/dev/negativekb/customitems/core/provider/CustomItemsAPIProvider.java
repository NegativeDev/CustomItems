package dev.negativekb.customitems.core.provider;

import dev.negativekb.customitems.api.CustomItemManager;
import dev.negativekb.customitems.api.CustomItemsAPI;
import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import org.jetbrains.annotations.NotNull;

@Registry(type = RegistryType.IMPLEMENTATION)
public class CustomItemsAPIProvider extends CustomItemsAPI {

    private final CustomItemManager customItemManager;
    public CustomItemsAPIProvider() {
        setInstance(this);

        customItemManager = new CustomItemManagerProvider();
    }

    @Override
    public @NotNull CustomItemManager getCustomItemManager() {
        return customItemManager;
    }
}
