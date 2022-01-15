package dev.negativekb.customitems;

import dev.negativekb.api.BasePlugin;
import dev.negativekb.customitems.core.CoreInitializer;
import lombok.Getter;

public final class CustomItems extends BasePlugin {

    @Getter
    private static CustomItems instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        instance = this;

        new CoreInitializer();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
