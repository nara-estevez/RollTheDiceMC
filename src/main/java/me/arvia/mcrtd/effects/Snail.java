package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class Snail extends RTDEffect {
    public Snail() {
        setName("Snail");
        setDescription("Your speed is decreased for 20 seconds");
        setDurationSeconds(20);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance moveSpeed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (moveSpeed == null) { return; }

        moveSpeed.setBaseValue(0.1 / 4);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance moveSpeed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (moveSpeed == null) { return; }

        moveSpeed.setBaseValue(0.1);
    }
}
