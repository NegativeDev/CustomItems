package dev.negativekb.customitems.core.registry;

import dev.negativekb.api.commands.Command;
import dev.negativekb.customitems.CustomItems;
import dev.negativekb.customitems.api.CustomItemManager;
import dev.negativekb.customitems.api.CustomItemsAPI;
import dev.negativekb.customitems.core.structure.CustomItem;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * RegistryType
 * One of the essential parts to registering a class
 * without needing to manually initialize it.
 * <p>
 * This will determine what type of registry this is.
 */
public enum RegistryType {

    // Declares the class as an Implementation class
    // which means it has some importance to the structure
    // of the project. Such as data, or object management.
    IMPLEMENTATION("Implementation", 12),

    // Declares the class a Listener
    // and once initializes will register it as
    // a bukkit listener.
    LISTENER("Listener", 11),

    // Declares the class a Command
    // and once it's initialized it will
    // register as a command and print out
    // in console.
    COMMAND("Command", 10),

    CUSTOM_ITEM("Custom Item", 3),

    // Declares the class as Other. Which has little
    // to no importance.
    OTHER("", 1);
    @Getter
    private final String name;
    @Getter
    private final int priority;

    /**
     * @param name     Name of the type. This will be printed to console when registered
     * @param priority Priority of registration. Highest to Lowest
     */
    RegistryType(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    /**
     * Registers the class. Supports special cases such as Listeners or Kits.
     *
     * @param clazz Class
     */
    @SneakyThrows
    public void register(Class<?> clazz) {
        CustomItems plugin = CustomItems.getInstance();
        CustomItemsAPI api = CustomItemsAPI.getInstance();

        switch (this) {
            case LISTENER -> {
                Bukkit.getPluginManager().registerEvents((Listener) clazz.getDeclaredConstructor().newInstance(), plugin);
            }

            case COMMAND -> {
                plugin.registerCommands((Command) clazz.getDeclaredConstructor().newInstance());
            }

            case CUSTOM_ITEM -> {
                CustomItemManager customItemManager = api.getCustomItemManager();
                CustomItem customItem = (CustomItem) clazz.getDeclaredConstructor().newInstance();
                customItemManager.registerCustomItem(customItem);

                Recipe recipe = customItem.getRecipe();
                if (recipe != null)
                    Bukkit.getServer().addRecipe(recipe);
            }

            default -> clazz.getDeclaredConstructor().newInstance();
        }
        send(clazz);
    }

    /**
     * Prints the log to console that the class has been initialized.
     *
     * @param clazz Class
     */
    private void send(Class<?> clazz) {
        String className = (getName().isEmpty() ? "" : " " + getName());
        System.out.println("[CustomItems] Registered" + className + " `" + clazz.getSimpleName() + "`");
    }
}
