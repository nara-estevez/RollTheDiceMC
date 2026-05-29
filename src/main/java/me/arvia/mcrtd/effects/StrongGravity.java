package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class StrongGravity extends RTDEffect {
    public StrongGravity() {
        setName("Strong Gravity");
        setDescription("Gravity is twice as strong for 30 seconds");
        setDurationSeconds(30);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);
        if (gravity == null) {
            return;
        }

        gravity.setBaseValue(0.08 * 2);
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
