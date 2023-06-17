package net.aerh.exporter.listener;

import net.aerh.exporter.EXPorterPlugin;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import static net.aerh.exporter.util.Util.STORED_XP;
import static net.aerh.exporter.util.Util.XP_KEY;


public class BottleThrowListener implements Listener {

    @EventHandler (ignoreCancelled = true)
    public void onBottleThrow(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof ThrownExpBottle bottle)) {
            return;
        }

        ItemMeta itemMeta = bottle.getItem().getItemMeta();
        if (itemMeta == null) {
            return;
        }

        if (itemMeta.getPersistentDataContainer().has(XP_KEY, PersistentDataType.INTEGER)) {
            bottle.setMetadata(STORED_XP, new FixedMetadataValue(EXPorterPlugin.getPlugin(), bottle.getItem().getItemMeta().getPersistentDataContainer().get(XP_KEY, PersistentDataType.INTEGER)));
        }
    }

    @EventHandler
    public void onBottleThrow(ExpBottleEvent event) {
        ThrownExpBottle bottle = event.getEntity();

        if (bottle.hasMetadata(STORED_XP)) {
            event.setExperience(bottle.getMetadata(STORED_XP).get(0).asInt());
        }
    }
}
