package me.arvia.mcrtd;

import me.arvia.mcrtd.effects.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EffectManager {
    private final NamespacedKey activeEffect;
    private final JavaPlugin plugin;
    private final MessageManager messageManager;

    public EffectManager(JavaPlugin plugin, MessageManager messageManager) {
        this.plugin = plugin;
        activeEffect = new NamespacedKey(plugin, "activeEffect");
        this.messageManager = messageManager;
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
        effects.put(6, FastHands::new);
        effects.put(7, TinyMann::new);
        effects.put(8, Ignition::new);
        effects.put(9, Snail::new);
        effects.put(10, LongMelee::new);
        effects.put(11, Paranoia::new);
        effects.put(12, Explode::new);
        effects.put(13, Frozen::new);
        effects.put(14, FireBreath::new);
        effects.put(15, LuckySandvich::new);
        effects.put(16, InventoryShuffle::new);
        effects.put(17, Hungry::new);
        effects.put(18, Drugged::new);
        effects.put(19, LuckyDrop::new);
    }

    public void rollEffect(Player player, int option)
    {
        RTDEffect rolledEffect = effects.get(option).get();
        rolledEffect.setPlugin(plugin); // some effects need plugin to work

        boolean effectIsInstant = (rolledEffect.getDurationSeconds() == 0);
        if (!effectIsInstant)
        {
            player.getPersistentDataContainer().set(activeEffect, PersistentDataType.INTEGER, option);
        }

        ChatColor color;
        if (rolledEffect.isPositive())
        {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 1);
            color = ChatColor.GREEN;
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_TRUMPET_OXIDIZED, 1, 1);
            color = ChatColor.RED;
        }

        messageManager.sendString(player, rolledEffect.getName() + ": " + rolledEffect.getDescription(), color);

        rolledEffect.apply(player); // apply effect on player
        messageManager.broadcastString(player.getName() + " has rolled " + rolledEffect.getName(), color);

        if (!effectIsInstant)
        {
            Bukkit.getScheduler().runTaskLater(plugin, () -> { // wait set time before removing effect
                if(player.isOnline() && player.getPersistentDataContainer().has(activeEffect, PersistentDataType.INTEGER)) {
                    rolledEffect.remove(player);
                    player.getPersistentDataContainer().remove(activeEffect);
                    messageManager.broadcastString(player.getName() + "'s perk has worn off.");
                }
            }, rolledEffect.getDurationSeconds() * 20L);
        }
    }

    public void clearEffects(Player player)
    {
        if (player.getPersistentDataContainer().has(activeEffect, PersistentDataType.INTEGER)) {
            Integer playerEffectIndex = player.getPersistentDataContainer().get(activeEffect, PersistentDataType.INTEGER);
            Bukkit.getScheduler().cancelTasks(plugin);

            effects.get(playerEffectIndex).get().remove(player);

            player.getPersistentDataContainer().remove(activeEffect);
        }
    }

    public int getEffectsAmount()
    {
        return effects.size();
    }
}
