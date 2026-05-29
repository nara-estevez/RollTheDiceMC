package me.arvia.mcrtd.effects;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Paranoia extends RTDEffect {
    private List<Sound> sounds;

    public Paranoia() {
        setName("Paranoia");
        setDescription("What was that?");
        setDurationSeconds(35);
        setIsPositive(false);

        sounds = new ArrayList<>();
        sounds.add(Sound.BLOCK_NOTE_BLOCK_IMITATE_CREEPER);
        sounds.add(Sound.ENTITY_CREEPER_PRIMED);
        sounds.add(Sound.AMBIENT_CAVE);
        sounds.add(Sound.ENTITY_WARDEN_HEARTBEAT);
        sounds.add(Sound.BLOCK_GRASS_STEP);
        sounds.add(Sound.BLOCK_STONE_STEP);
        sounds.add(Sound.BLOCK_DEEPSLATE_STEP);
        sounds.add(Sound.BLOCK_BASALT_STEP);
    }

    private void scheduleSound(Player player)
    {
        NamespacedKey activeEffect = new NamespacedKey(this.getPlugin(), "activeEffect");

        Bukkit.getScheduler().runTaskLater(this.getPlugin(), () -> {
            if (!player.getPersistentDataContainer().has(activeEffect, PersistentDataType.INTEGER))
            {
                return;
            }
            int soundId = (int) (Math.random() * sounds.size());
            player.playSound(player.getLocation(), sounds.get(soundId), 1, 1);
            scheduleSound(player); // schedule next sound
        }, (1 + (int)(Math.random() * 6)) * 20L);
    }

    @Override
    public void apply(Player player) {
        scheduleSound(player);
    }

    @Override
    public void remove(Player player) {
        // nothing
    }
}
