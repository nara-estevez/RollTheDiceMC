package me.arvia.mcrtd.effects;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

public class FastHands extends RTDEffect {
    public FastHands() {
        setName("Fast Hands");
        setDescription("You break blocks 3x as fast for 20 seconds");
        setDurationSeconds(20);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) {
        AttributeInstance miningEfficiency = player.getAttribute(Attribute.MINING_EFFICIENCY);
        if (miningEfficiency == null) { return; }

        miningEfficiency.setBaseValue(3); // default value is 0 so cant multiply
    }

    @Override
    public void remove(Player player) {
        AttributeInstance miningEfficiency = player.getAttribute(Attribute.MINING_EFFICIENCY);
        if (miningEfficiency == null) { return; }

        miningEfficiency.setBaseValue(0);
    }
}
