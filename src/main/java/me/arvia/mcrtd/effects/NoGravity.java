package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class NoGravity extends RTDEffect {
    public NoGravity() {
        setName("No Gravity");
        setDescription("Gravity is disabled for 25 seconds");
        setDurationSeconds(25);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);
        if (gravity == null) {
            return;
        }

        gravity.setBaseValue(0);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);
        if (gravity == null) {
            return;
        }

        gravity.setBaseValue(0.08);
    }
}
