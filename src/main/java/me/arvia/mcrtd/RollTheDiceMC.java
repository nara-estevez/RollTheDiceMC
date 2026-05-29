package me.arvia.mcrtd;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RollTheDiceMC extends JavaPlugin {
    static CooldownManager cooldownManager;
    static EffectManager effectManager;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager(40);
        effectManager = new EffectManager(this);
        getLogger().info(ChatColor.GREEN + "[RollTheDiceMC] Enabled");

        RtdCommandHandler commandHandler = new RtdCommandHandler(cooldownManager, effectManager);
        getCommand("rtd").setExecutor(commandHandler);
        getCommand("rtd-clear").setExecutor(commandHandler);

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onQuit(PlayerJoinEvent event) {
                cooldownManager.clearCooldown(event.getPlayer());
                effectManager.clearEffects(event.getPlayer());
            }
        }, RollTheDiceMC.this);
    }
}
