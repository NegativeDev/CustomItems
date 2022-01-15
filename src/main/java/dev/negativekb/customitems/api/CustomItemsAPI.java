package dev.negativekb.customitems.api;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

public abstract class CustomItemsAPI {

    @Getter @Setter
    private static CustomItemsAPI instance;

    @NotNull
    public abstract CustomItemManager getCustomItemManager();
}
