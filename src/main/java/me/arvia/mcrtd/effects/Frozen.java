package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class Frozen extends RTDEffect {
    public Frozen() {
        setName("Frozen");
        setDescription("You are frozen for 20 seconds!");
        setDurationSeconds(20);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance movementSpeed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);

        if (movementSpeed == null || gravity == null) {
            return;
        }

        movementSpeed.setBaseValue(0);
        gravity.setBaseValue(1);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance movementSpeed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        AttributeInstance gravity = player.getAttribute(Attribute.GRAVITY);

        if (movementSpeed == null || gravity == null) {
            return;
        }

        movementSpeed.setBaseValue(0.1);
        gravity.setBaseValue(0.08);
    }
}
