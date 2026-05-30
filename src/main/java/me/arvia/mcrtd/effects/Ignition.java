package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;

public class Ignition extends RTDEffect {
    public Ignition() {
        setName("Ignition");
        setDescription("You get set on fire!");
        setDurationSeconds(0);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        player.setFireTicks(200); // 200 ticks equivalent to 10 seconds
    }

    @Override
    public void remove(Player player) {
        // do nothing, fire runs out on its own
    }
}
