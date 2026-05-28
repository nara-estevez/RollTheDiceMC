package me.arvia.mcrtd;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class RollTheDiceMC extends JavaPlugin {
    @Override
    public void onEnable() {
        final CooldownManager cooldownManager = new CooldownManager(40);
        final EffectManager effectManager = new EffectManager(this);

        getCommand("rtd").setExecutor(new RtdCommandHandler(cooldownManager, effectManager));

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onQuit(PlayerJoinEvent event) {
                cooldownManager.clearCooldown(event.getPlayer());
            }
        }, RollTheDiceMC.this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
