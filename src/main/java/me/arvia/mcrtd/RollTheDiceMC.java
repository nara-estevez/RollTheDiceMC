package me.arvia.mcrtd;

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

        getCommand("rtd").setExecutor(new RtdCommandHandler(cooldownManager, effectManager));

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onQuit(PlayerJoinEvent event) {
                cooldownManager.clearCooldown(event.getPlayer());
                effectManager.clearEffects(event.getPlayer());
            }
        }, RollTheDiceMC.this);
    }
}
