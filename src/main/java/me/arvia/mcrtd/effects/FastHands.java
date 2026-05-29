package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class FastHands extends RTDEffect {
    public FastHands() {
        setName("Fast Hands");
        setDescription("You instantly break blocks for 20 seconds");
        setDurationSeconds(20);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance breakSpeed = player.getAttribute(Attribute.BLOCK_BREAK_SPEED);
        if (breakSpeed == null) { return; }

        breakSpeed.setBaseValue(100);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance breakSpeed = player.getAttribute(Attribute.BLOCK_BREAK_SPEED);
        if (breakSpeed == null) { return; }

        breakSpeed.setBaseValue(1);
    }
}
