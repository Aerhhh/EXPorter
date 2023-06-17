package net.aerh.xporter.listener;

import net.aerh.xporter.XPorterPlugin;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import static net.aerh.xporter.util.Util.STORED_XP;
import static net.aerh.xporter.util.Util.XP_KEY;


public class BottleThrowListener implements Listener {

    @EventHandler
    public void onBottleThrow(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();

        if (!(projectile instanceof ThrownExpBottle)) {
            return;
        }

        ThrownExpBottle bottle = (ThrownExpBottle) projectile;

        if (bottle.getItem().getItemMeta() == null) {
            return;
        }

        if (bottle.getItem().getItemMeta().getPersistentDataContainer().has(XP_KEY, PersistentDataType.INTEGER)) {
            projectile.setMetadata(STORED_XP, new FixedMetadataValue(XPorterPlugin.getPlugin(), bottle.getItem().getItemMeta().getPersistentDataContainer().get(XP_KEY, PersistentDataType.INTEGER)));
        }
    }

    @EventHandler
    public void onBottleThrow(ExpBottleEvent event) {
        ThrownExpBottle bottle = event.getEntity();

        if (bottle.hasMetadata(STORED_XP)) {
            event.setExperience(event.getEntity().getMetadata(STORED_XP).get(0).asInt());
        }
    }
}
