package net.aerh.exporter.command;

import net.aerh.exporter.EXPorterPlugin;
import net.aerh.exporter.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class XPorterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp() || !sender.hasPermission("xporter.use")) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
            return true;
        }

        if (args.length < 1) {
            showHelp(sender, label);
            return true;
        }

        if (args[0].equals("help")) {
            showHelp(sender, label);
            return true;
        } else if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "You must specify a player and an amount of experience!");
                return true;
            }

            int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "You must specify a valid number!");
                return true;
            }

            if (amount <= 0) {
                sender.sendMessage(ChatColor.RED + "You can only give players a positive amount of experience!");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "That player is not online!");
                return true;
            }

            if (target.getInventory().firstEmpty() == -1) {
                sender.sendMessage(ChatColor.RED + "That player does not have enough space in their inventory!");
                return true;
            }

            ItemStack itemStack = Util.getExperienceBottle(null, amount);
            target.getInventory().addItem(itemStack);
            sender.sendMessage(ChatColor.GREEN + "Successfully gave " + ChatColor.YELLOW + target.getName()
                    + ChatColor.GREEN + " an Experience Bottle with " + ChatColor.YELLOW + EXPorterPlugin.COMMA_SEPARATED_FORMAT.format(amount) + ChatColor.GREEN + " stored experience!");
            return true;
        }

        return false;
    }

    private void showHelp(CommandSender sender, String label) {
        String help = ChatColor.DARK_AQUA + EXPorterPlugin.getPlugin().getDescription().getName() + " v" + EXPorterPlugin.getPlugin().getDescription().getVersion();

        help += "\n" + ChatColor.GRAY + "Commands:";
        help += "\n" + ChatColor.RESET + "  /" + label + " help - Show this help message";
        help += "\n" + ChatColor.RESET + "  /" + label + " give <player> <amount> - Give an Experience Bottle with the specified amount";
        help += "\n" + ChatColor.RESET + "  /xpwithdraw <amount> - Withdraw the specified amount of XP";

        sender.sendMessage(help);
    }
}
