package dev.negativekb.customitems.core.structure;

import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.negativekb.api.util.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class CustomItem {

    @Getter
    private final String id;
    @Getter
    private final String displayName;
    private final List<String> lore;
    private final Material material;

    // Custom Attributes
    private Consumer<ItemStack> itemAttributesConsumer;

    // Events
    private Consumer<BlockBreakEvent> blockBreakEventConsumer;
    private Consumer<BlockPlaceEvent> blockPlaceEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damageByEntityEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damageEntityEventConsumer;
    private Consumer<PlayerJoinEvent> joinEventConsumer;
    private Consumer<PlayerQuitEvent> quitEventConsumer;
    private Consumer<PlayerInteractEvent> interactEventConsumer;
    private Consumer<PlayerInteractAtEntityEvent> interactAtEntityEventConsumer;
    private Consumer<PlayerTeleportEvent> teleportEventConsumer;
    private Consumer<FoodLevelChangeEvent> foodLevelChangeEventConsumer;
    private Consumer<PlayerBucketEmptyEvent> bucketEmptyEventConsumer;
    private Consumer<PlayerItemConsumeEvent> consumeEventConsumer;

    public CustomItem(String id, String displayName, List<String> lore, Material material) {
        this.id = id;
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemBuilder(material).setName(displayName)
                .setLore(lore).build();

        // Adds extra attributes set by developers
        Optional.ofNullable(itemAttributesConsumer)
                .ifPresent(function -> function.accept(item));

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("custom-item", true);
        nbtItem.setString("custom-item-type", this.id);
        nbtItem.applyNBT(item);
        return nbtItem.getItem();
    }

    public abstract Recipe getRecipe();

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

    public void setBucketEmptyEvent(Consumer<PlayerBucketEmptyEvent> function) {
        this.bucketEmptyEventConsumer = function;
    }

    public void setInteractAtEntityEvent(Consumer<PlayerInteractAtEntityEvent> function) {
        this.interactAtEntityEventConsumer = function;
    }

    public void setConsumeEvent(Consumer<PlayerItemConsumeEvent> function) {
        this.consumeEventConsumer = function;
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

    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Optional.ofNullable(bucketEmptyEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        Optional.ofNullable(interactAtEntityEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void onConsume(PlayerItemConsumeEvent event) {
        Optional.ofNullable(consumeEventConsumer).ifPresent(function ->
                function.accept(event));
    }

    public void setItemAttributes(Consumer<ItemStack> function) {
        this.itemAttributesConsumer = function;
    }
}
