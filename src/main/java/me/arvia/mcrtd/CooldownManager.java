package me.arvia.mcrtd;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final int cooldownSeconds;

    public CooldownManager(int cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
    }

    public void startCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    public void clearCooldown(Player player) {
        cooldowns.remove(player.getUniqueId());
    }

    public boolean isCooldownActive(Player player) {
        // if player has never been logged in hashmap, then theyre not on cooldown
        if (!cooldowns.containsKey(player.getUniqueId())) {
            return false;
        }

        long lastCooldown = cooldowns.get(player.getUniqueId());
        long currentTime = System.currentTimeMillis();
        long timeElapsed = (currentTime - lastCooldown) / 1000;

        return timeElapsed < cooldownSeconds; // not enough time has passed?
    }

    public long getSecondsRemaining(Player player) {
        long lastCooldown = cooldowns.get(player.getUniqueId());
        long currentTime = System.currentTimeMillis();
        long timeElapsed = (currentTime - lastCooldown) / 1000;

        return cooldownSeconds - timeElapsed;
    }
}
