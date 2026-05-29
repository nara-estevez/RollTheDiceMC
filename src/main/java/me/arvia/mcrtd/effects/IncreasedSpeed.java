package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class IncreasedSpeed extends RTDEffect {
    public IncreasedSpeed() {
        setName("Increased Speed");
        setDescription("Your speed is tripled for 20 seconds");
        setDurationSeconds(20);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance moveSpeed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (moveSpeed == null) { return; }

        moveSpeed.setBaseValue(0.7 * 3);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance moveSpeed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (moveSpeed == null) { return; }

        moveSpeed.setBaseValue(0.7);
    }
}
