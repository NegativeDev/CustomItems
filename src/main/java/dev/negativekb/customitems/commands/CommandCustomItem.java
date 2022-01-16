package dev.negativekb.customitems.commands;

import dev.negativekb.api.commands.Command;
import dev.negativekb.api.commands.annotation.CommandInfo;
import dev.negativekb.customitems.api.CustomItemManager;
import dev.negativekb.customitems.api.CustomItemsAPI;
import dev.negativekb.customitems.core.Locale;
import dev.negativekb.customitems.core.registry.Registry;
import dev.negativekb.customitems.core.registry.RegistryType;
import dev.negativekb.customitems.core.structure.CustomItem;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Registry(type = RegistryType.COMMAND)
@CommandInfo(
        name = "customitem", aliases = {"customitems"}, permission = "customitems.admin",
        args = {"player", "name"}
)
public class CommandCustomItem extends Command {

    private final CustomItemManager customItemManager;
    public CommandCustomItem() {
        customItemManager = CustomItemsAPI.getInstance().getCustomItemManager();

        setTabComplete((sender, args) -> {
            if (args.length == 2) {
                Collection<CustomItem> customItems = customItemManager.getCustomItems();

                String lastWord = args[args.length - 1];

                List<CustomItem> collect = customItems.stream().filter(customItem ->
                        StringUtil.startsWithIgnoreCase(customItem.getId(), lastWord))
                        .collect(Collectors.toList());

                List<CustomItem> collect1 = customItems.stream().filter(customItem ->
                        (!collect.contains(customItem) &&
                        customItem.getId().toLowerCase().contains(lastWord.toLowerCase()))).collect(Collectors.toList());

                collect.addAll(collect1);

                List<String> stringValue = new ArrayList<>();
                collect.forEach(customItem -> stringValue.add(customItem.getId()));
                return stringValue;
            }

            return null;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Optional<Player> optionalPlayer = getPlayer(args[0]);

        if (optionalPlayer.isEmpty()) {
            Locale.INVALID_PLAYER.send(sender);
            return;
        }

        Player player = optionalPlayer.get();
        Optional<CustomItem> optionalItem = customItemManager.getCustomItem(args[1]);
        if (optionalItem.isEmpty()) {
            Locale.INVALID_ITEM.send(sender);
            return;
        }

        CustomItem customItem = optionalItem.get();
        int iterate = 1;
        try {
            iterate = Integer.parseInt(args[2]);
        } catch (Exception ignored) {
        }

        for (int i = 0; i < iterate; i++) {
            player.getInventory().addItem(customItem.getItem());
        }

        Locale.COMMAND_CUSTOMITEM_SUCCESS.replace("%amount%", iterate)
                .replace("%item%", customItem.getDisplayName())
                .replace("%player%", player.getName()).send(sender);
    }
}
