package net.aerh.xporter;

import net.aerh.xporter.command.XPWithdrawCommand;
import net.aerh.xporter.command.XPorterCommand;
import net.aerh.xporter.listener.BottleThrowListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class XPorterPlugin extends JavaPlugin {

    public static final DecimalFormat COMMA_SEPARATED_FORMAT = new DecimalFormat("#,###");

    private static XPorterPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new BottleThrowListener(), this);
        getCommand("xpwithdraw").setExecutor(new XPWithdrawCommand());
        getCommand("xporter").setExecutor(new XPorterCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static XPorterPlugin getPlugin() {
        return plugin;
    }
}
