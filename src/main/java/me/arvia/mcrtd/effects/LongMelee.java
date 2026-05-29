package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class LongMelee extends RTDEffect {
    public LongMelee() {
        setName("Long Melee");
        setDescription("Your range is greatly increased for 30 seconds");
        setDurationSeconds(30);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance blockRange = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
        AttributeInstance entityRange = player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE);

        if (blockRange == null || entityRange == null) { return; }

        blockRange.setBaseValue(4.5 * 3);
        entityRange.setBaseValue(3 * 3);
    }

    @Override
    public void remove(Player player) {
        AttributeInstance blockRange = player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE);
        AttributeInstance entityRange = player.getAttribute(Attribute.ENTITY_INTERACTION_RANGE);

        if (blockRange == null || entityRange == null) { return; }

        blockRange.setBaseValue(4.5);
        entityRange.setBaseValue(3);
    }
}
