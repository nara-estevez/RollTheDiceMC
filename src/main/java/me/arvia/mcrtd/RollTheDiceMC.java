package me.arvia.mcrtd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RollTheDiceMC extends JavaPlugin {
    static CooldownManager cooldownManager;
    static EffectManager effectManager;
    static MessageManager messageManager;

    @Override
    public void onEnable() {
        cooldownManager = new CooldownManager(40);
        messageManager = new MessageManager(Bukkit.getServer());
        effectManager = new EffectManager(this, messageManager);
        getLogger().info(ChatColor.GREEN + "[RollTheDiceMC] Enabled");

        RtdCommandHandler commandHandler = new RtdCommandHandler(cooldownManager, effectManager, messageManager);
        getCommand("rtd").setExecutor(commandHandler);
        getCommand("rtd-clear").setExecutor(commandHandler);
        getCommand("rtd-set-cooldown").setExecutor(commandHandler);

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onQuit(PlayerJoinEvent event) {
                cooldownManager.clearCooldown(event.getPlayer());
                effectManager.clearEffects(event.getPlayer());
            }

            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                cooldownManager.startCooldown(event.getPlayer()); // cooldown on join before you can rtd
            }
        }, RollTheDiceMC.this);
    }
}
