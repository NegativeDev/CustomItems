package dev.negativekb.customitems.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.negativekb.customitems.api.CustomItemManager;
import dev.negativekb.customitems.api.CustomItemsAPI;
import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

@Registry(type = RegistryType.LISTENER)
public class CustomItemListener implements Listener {

    private final CustomItemManager customItemManager;
    private final RegionContainer container;
    private final WorldGuardPlugin worldGuardPlugin;

    public CustomItemListener() {
        customItemManager = CustomItemsAPI.getInstance().getCustomItemManager();
        worldGuardPlugin = WorldGuardPlugin.inst();
        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType().equals(Material.AIR))
            return;

        Location location = event.getBlock().getLocation();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(location));
        LocalPlayer localPlayer = worldGuardPlugin.wrapPlayer(player);
        if (!set.testState(localPlayer, Flags.BLOCK_BREAK))
            return;

        customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onBlockBreak(event));
    }

    @EventHandler
    public void onBlockBreak(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType().equals(Material.AIR))
            return;

        Location location = event.getBlock().getLocation();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(location));
        LocalPlayer localPlayer = worldGuardPlugin.wrapPlayer(player);
        if (!set.testState(localPlayer, Flags.BLOCK_PLACE))
            return;

        customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onBlockPlace(event));
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        HumanEntity entity = event.getEntity();
        ItemStack item = entity.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR))
            return;

        customItemManager.getCustomItem(item).ifPresent(customItem ->
                customItem.onFoodChange(event));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() == Material.AIR)
            return;

        customItemManager.getCustomItem(item).ifPresent(customItem ->
                customItem.onInteractEvent(event));
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR)
            return;

        customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onTeleport(event));

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR)
            return;

        customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onJoin(event));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.AIR)
            return;

        customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                customItem.onQuit(event));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player player) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (itemInMainHand.getType() == Material.AIR)
                return;

            customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                    customItem.onEntityDamageByEntity(event));
        }

        Entity damager = event.getDamager();
        if (damager instanceof Player player) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            if (itemInMainHand.getType() == Material.AIR)
                return;

            customItemManager.getCustomItem(itemInMainHand).ifPresent(customItem ->
                    customItem.onEntityDamageEntity(event));
        }
    }

}
