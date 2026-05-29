package me.arvia.mcrtd.effects;

import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;

public class Explode extends RTDEffect implements Listener {
    public Explode() {
        setName("Explode");
        setDescription("Run!");
        setDurationSeconds(2);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        World world = player.getWorld();
        TNTPrimed tnt = world.spawn(player.getLocation(), TNTPrimed.class);
        tnt.setFuseTicks(40); // 2 seconds
        // Tag it so the listener knows it's yours
        tnt.getPersistentDataContainer().set(
                new NamespacedKey(this.getPlugin(), "rtd_tnt"),
                PersistentDataType.BYTE,
                (byte) 1
        );
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed tnt)) return;

        if (tnt.getPersistentDataContainer().has(new NamespacedKey(this.getPlugin(), "rtd_tnt"), PersistentDataType.BYTE)) {
            event.blockList().clear(); // cancels block damage
        }
    }

    @Override
    public void remove(Player player) {
        // nothing, tnt just blows up
    }


}
