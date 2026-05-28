package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;

public class LowHealth extends RTDEffect {
    public LowHealth() {
        setName("Low Health");
        setDescription("Go down to half a heart");
        setDurationSeconds(0);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        player.setHealth(1);
    }

    @Override
    public void remove(Player player) {
        // does nothing
    }
}
