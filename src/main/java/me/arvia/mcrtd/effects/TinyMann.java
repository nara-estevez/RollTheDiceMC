package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class TinyMann extends RTDEffect {
    public TinyMann() {
        setName("Tiny Mann");
        setDescription("You become tiny for 20 seconds");
        setDurationSeconds(20);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance scale = player.getAttribute(Attribute.SCALE);
        AttributeInstance speed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (scale == null || speed == null) {
            return;
        }

        scale.setBaseValue(0.1);
        speed.setBaseValue(0.7 * 2);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance scale = player.getAttribute(Attribute.SCALE);
        AttributeInstance speed = player.getAttribute(Attribute.MOVEMENT_SPEED);
        if (scale == null || speed == null) {
            return;
        }

        scale.setBaseValue(1);
        speed.setBaseValue(0.7);
    }

}
