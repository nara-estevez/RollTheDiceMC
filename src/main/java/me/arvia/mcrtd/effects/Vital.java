package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class Vital extends RTDEffect {
    public Vital() {
        setName("Vital");
        setDescription("Doubles your max health for 30 seconds");
        setDurationSeconds(30);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) { // Doubles the amount of player hearts
        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealth == null) { return; }

        maxHealth.setBaseValue(maxHealth.getBaseValue() * 2);
        player.setHealth(Math.min(player.getHealth() * 2, maxHealth.getBaseValue()));
    }

    @Override
    public void remove(Player player) {
        AttributeInstance maxHealth = player.getAttribute(Attribute.MAX_HEALTH);
        if (maxHealth == null) { return; }

        maxHealth.setBaseValue(maxHealth.getBaseValue() / 2);
        player.setHealth(Math.min(player.getHealth(), maxHealth.getBaseValue()));
    }
}
