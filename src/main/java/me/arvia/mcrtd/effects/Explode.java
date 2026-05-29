package me.arvia.mcrtd.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class Explode extends RTDEffect implements Listener {
    private NamespacedKey tntTag;

    public Explode() {
        setName("Explode");
        setDescription("Run!");
        setDurationSeconds(2);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        World world = player.getWorld();
        Bukkit.getPluginManager().registerEvents(this, getPlugin()); // listen for explosions
        TNTPrimed tnt = world.spawn(player.getLocation(), TNTPrimed.class);
        tnt.setFuseTicks(40); // 2 seconds
        // tag tnt so listener can check
        tntTag = new NamespacedKey(getPlugin(), "rtd_tnt");
        tnt.getPersistentDataContainer().set(
                tntTag,
                PersistentDataType.BYTE,
                (byte) 1
        );
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof TNTPrimed tnt)) return;
        if (!tnt.getPersistentDataContainer().has(
                tntTag,
                PersistentDataType.BYTE)) return;

        // stop blocks from breaking
        event.blockList().clear();

        // damage players manually
        Location explodeLoc = tnt.getLocation();
        double radius = 5.0;
        double maxDamage = 20.0;

        for (Entity entity : tnt.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player target)) continue;

            double distance = target.getLocation().distance(explodeLoc);
            if (distance > radius) continue;

            double damage = maxDamage * (1.0 - (distance / radius)); // damage falloff

            // knockback vector
            Vector knockback = target.getLocation().toVector()
                    .subtract(explodeLoc.toVector())
                    .normalize()
                    .multiply(2.0); // knockback strength

            target.damage(damage);
            target.setVelocity(target.getVelocity().add(knockback));
        }
    }

    @Override
    public void remove(Player player) {
        // nothing, tnt just blows up
    }


}
