package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;

public class Hungry extends RTDEffect {
    public Hungry() {
        setName("Hungry");
        setDescription("You could go for some food");
        setDurationSeconds(0);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        player.setFoodLevel(1);
    }

    @Override
    public void remove(Player player) {
        // nothing
    }
}
