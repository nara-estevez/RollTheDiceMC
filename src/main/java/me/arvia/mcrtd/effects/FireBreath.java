package me.arvia.mcrtd.effects;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FireBreath extends RTDEffect implements Listener {

    private static NamespacedKey fireballTag;
    private static boolean listenerRegistered = false;
    private static NamespacedKey activeEffect;

    // Players who have been given the effect but haven't fired yet
    private static final Set<UUID> pendingPlayers = new HashSet<>();

    public FireBreath() {
        setName("Fire Breath");
        setDescription("Attack in any direction to launch a fireball");
        setDurationSeconds(20);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) {
        if (!listenerRegistered) {
            fireballTag = new NamespacedKey(getPlugin(), "rtd_fireball");
            Bukkit.getPluginManager().registerEvents(this, getPlugin());
            listenerRegistered = true;
        }

        activeEffect = new NamespacedKey(getPlugin(), "activeEffect");
        // Mark player as pending — fireball launches on next left click
        pendingPlayers.add(player.getUniqueId());
    }

    @Override
    public void remove(Player player) {
        if (player.getPersistentDataContainer().has(activeEffect, PersistentDataType.INTEGER)) {
            player.getPersistentDataContainer().remove(activeEffect);
        }
        pendingPlayers.remove(player.getUniqueId());
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        // Left click only (air or block)
        if (event.getAction() != Action.LEFT_CLICK_AIR &&
                event.getAction() != Action.LEFT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        if (!pendingPlayers.remove(player.getUniqueId())) return;

        // Launch fireball in the direction the player is looking
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().normalize();

        Fireball fireball = player.getWorld().spawn(
                eyeLoc.clone().add(direction.clone().multiply(2)),
                Fireball.class
        );
        fireball.setShooter(player);
        fireball.setDirection(direction);
        fireball.setYield(2.0f);
        fireball.setIsIncendiary(false);

        fireball.getPersistentDataContainer().set(
                fireballTag,
                PersistentDataType.BYTE,
                (byte) 1
        );
    }

    @EventHandler
    public void onFireballExplode(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof Fireball fireball)) return;
        if (!fireball.getPersistentDataContainer().has(fireballTag, PersistentDataType.BYTE)) return;

        event.blockList().clear();

        Location explodeLoc = fireball.getLocation();
        double radius = 4.0;
        double maxDamage = 8.0;

        for (Entity entity : fireball.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof LivingEntity target)) continue;
            if (fireball.getShooter() instanceof Entity shooter && shooter.equals(target)) continue;

            double distance = target.getLocation().distance(explodeLoc);
            if (distance > radius) continue;

            double damage = maxDamage * (1.0 - (distance / radius));
            Vector knockback = target.getLocation().toVector()
                    .subtract(explodeLoc.toVector())
                    .normalize()
                    .multiply(1.5);

            target.damage(damage);
            target.setVelocity(target.getVelocity().add(knockback));
        }
    }
}