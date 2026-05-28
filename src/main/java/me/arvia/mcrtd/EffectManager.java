package me.arvia.mcrtd;

import me.arvia.mcrtd.effects.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EffectManager {
    private final JavaPlugin plugin;
    public EffectManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // add to effects map with all the effects as they get made
    private final static Map<Integer, Supplier<RTDEffect>> effects;
    static {
        effects = new HashMap<>();
        effects.put(0, Vital::new);
        effects.put(1, IncreasedSpeed::new);
        effects.put(2, LowGravity::new);
        effects.put(3, LowHealth::new);
        effects.put(4, NoGravity::new);
        effects.put(5, StrongGravity::new);
    }

    public void rollEffect(Player player)
    {
        int option = (int) (Math.random() * effects.size());
        RTDEffect rolledEffect = effects.get(option).get();

        ChatColor color = (rolledEffect.isPositive())?ChatColor.GREEN:ChatColor.RED;

        player.sendMessage(color + rolledEffect.getName() + ": " + rolledEffect.getDescription()); // show effect to player
        rolledEffect.apply(player); // apply effect on player

        Bukkit.getScheduler().runTaskLater(plugin, () -> { // wait set time before removing effect
            if(player.isOnline()) {
                rolledEffect.remove(player);
            }
        }, rolledEffect.getDurationSeconds() * 20L);
    }
}
