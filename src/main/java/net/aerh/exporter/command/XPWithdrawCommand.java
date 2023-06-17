package net.aerh.exporter.command;

import net.aerh.exporter.util.ExperienceUtil;
import net.aerh.exporter.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static net.aerh.exporter.EXPorterPlugin.COMMA_SEPARATED_FORMAT;

public class XPWithdrawCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!commandSender.hasPermission("exporter.withdraw")) {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "You must specify an amount of experience to withdraw!");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "You must specify a valid number!");
            return true;
        }

        if (amount <= 0) {
            player.sendMessage(ChatColor.RED + "You can only withdraw a positive amount of experience!");
            return true;
        }

        if (ExperienceUtil.getExp(player) < amount) {
            player.sendMessage(ChatColor.RED + "You do not have enough experience to withdraw that amount!");
            return true;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ChatColor.RED + "You do not have enough space in your inventory!");
            return true;
        }

        ItemStack itemStack = Util.getExperienceBottle(player, amount);
        ExperienceUtil.changeExp(player, -amount);
        player.sendMessage(ChatColor.GREEN + "You withdrew " + ChatColor.YELLOW + COMMA_SEPARATED_FORMAT.format(amount) + ChatColor.GREEN + " experience!");
        player.getInventory().addItem(itemStack);

        return true;
    }
}
