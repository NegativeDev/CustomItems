package dev.negativekb.customitems.core.structure;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.negativekb.api.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CustomItem {

    @Getter
    private final String id;
    private final String displayName;
    private final List<String> lore;
    private final Material material;

    // Events
    private Consumer<BlockBreakEvent> blockBreakEventConsumer;
    private Consumer<BlockPlaceEvent> blockPlaceEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damageByEntityEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damageEntityEventConsumer;
    private Consumer<PlayerJoinEvent> joinEventConsumer;
    private Consumer<PlayerQuitEvent> quitEventConsumer;
    private Consumer<PlayerInteractEvent> interactEventConsumer;
    private Consumer<PlayerTeleportEvent> teleportEventConsumer;
    private Consumer<FoodLevelChangeEvent> foodLevelChangeEventConsumer;

    public CustomItem(String id, String displayName, List<String> lore, Material material) {
        this.id = id;
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemBuilder(material).setName(displayName)
                .setLore(lore).build();

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("custom-item", true);
        nbtItem.setString("custom-item-type", this.id);
        nbtItem.applyNBT(item);
        return nbtItem.getItem();
    }

    public abstract ShapedRecipe getRecipe();


    // =======================================
    // LISTENERS
    // =======================================


    public void setBlockBreakEvent(Consumer<BlockBreakEvent> function) {
        this.blockBreakEventConsumer = function;
    }

    public void setBlockPlaceEvent(Consumer<BlockPlaceEvent> function) {
        this.blockPlaceEventConsumer = function;
    }

    public void setDamageByEntityEvent(Consumer<EntityDamageByEntityEvent> function) {
        this.damageByEntityEventConsumer = function;
    }

    public void setDamageEntityEvent(Consumer<EntityDamageByEntityEvent> function) {
        this.damageEntityEventConsumer = function;
    }

    public void setJoinEvent(Consumer<PlayerJoinEvent> function) {
        this.joinEventConsumer = function;
    }

    public void setQuitEvent(Consumer<PlayerQuitEvent> function) {
        this.quitEventConsumer = function;
    }

    public void setTeleportEvent(Consumer<PlayerTeleportEvent> function) {
        this.teleportEventConsumer = function;
    }

    public void setFoodLevelChangeEvent(Consumer<FoodLevelChangeEvent> function) {
        this.foodLevelChangeEventConsumer = function;
    }

    public void setInteractEvent(Consumer<PlayerInteractEvent> function) {
        this.interactEventConsumer = function;
    }

    public void onBlockBreak(BlockBreakEvent event) {
        Optional.ofNullable(blockBreakEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        Optional.ofNullable(blockPlaceEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Optional.ofNullable(damageByEntityEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
        Optional.ofNullable(damageEntityEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onJoin(PlayerJoinEvent event) {
        Optional.ofNullable(joinEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onQuit(PlayerQuitEvent event) {
        Optional.ofNullable(quitEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onTeleport(PlayerTeleportEvent event) {
        Optional.ofNullable(teleportEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onFoodChange(FoodLevelChangeEvent event) {
        Optional.ofNullable(foodLevelChangeEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onInteractEvent(PlayerInteractEvent event) {
        Optional.ofNullable(interactEventConsumer).ifPresent(function ->
                function.accept(event));
    }
}
