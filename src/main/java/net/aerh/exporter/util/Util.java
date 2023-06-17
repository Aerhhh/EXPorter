package net.aerh.exporter.util;

import net.aerh.exporter.EXPorterPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static net.aerh.exporter.EXPorterPlugin.COMMA_SEPARATED_FORMAT;

public class Util {

    public static final String STORED_XP = "stored_xp";
    public static final NamespacedKey XP_KEY = new NamespacedKey(EXPorterPlugin.getPlugin(), STORED_XP);
    private static final NamespacedKey UUID_KEY = new NamespacedKey(EXPorterPlugin.getPlugin(), "uuid");

    private Util() {
    }

    public static ItemStack getExperienceBottle(CommandSender sender, int amount) {
        ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(ChatColor.GREEN + "Experience Storage Bottle");

        List<String> lore = Arrays.asList(
                "",
                ChatColor.GRAY + (sender instanceof ConsoleCommandSender ? "Given" : "Stored") + " by " + sender.getName(),
                ChatColor.GRAY + "Amount: " + ChatColor.WHITE + COMMA_SEPARATED_FORMAT.format(amount),
                "",
                ChatColor.YELLOW + "Throw to pick up!"
        );

        itemMeta.setLore(lore);
        itemMeta.getPersistentDataContainer().set(XP_KEY, PersistentDataType.INTEGER, amount);
        itemMeta.getPersistentDataContainer().set(UUID_KEY, PersistentDataType.STRING, UUID.randomUUID().toString());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
