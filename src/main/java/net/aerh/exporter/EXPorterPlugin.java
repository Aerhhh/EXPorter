package net.aerh.exporter;

import net.aerh.exporter.command.XPWithdrawCommand;
import net.aerh.exporter.command.EXPorterCommand;
import net.aerh.exporter.listener.BottleThrowListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

public final class EXPorterPlugin extends JavaPlugin {

    public static final DecimalFormat COMMA_SEPARATED_FORMAT = new DecimalFormat("#,###");

    private static EXPorterPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new BottleThrowListener(), this);
        getCommand("xpwithdraw").setExecutor(new XPWithdrawCommand());
        getCommand("exporter").setExecutor(new EXPorterCommand());
    }

    public static EXPorterPlugin getPlugin() {
        return plugin;
    }
}
