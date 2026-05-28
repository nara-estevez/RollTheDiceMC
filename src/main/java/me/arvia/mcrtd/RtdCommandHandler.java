package me.arvia.mcrtd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RtdCommandHandler implements CommandExecutor {
    private final CooldownManager cooldownManager;
    private final EffectManager effectManager;

    public RtdCommandHandler(CooldownManager cooldownManager, EffectManager effectManager) {
        this.cooldownManager = cooldownManager;
        this.effectManager = effectManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        // is player on cooldown?
        if (cooldownManager.isCooldownActive(player)) {
            player.sendMessage("You must wait " + cooldownManager.getSecondsRemaining(player) + " seconds before rolling the dice!");
            return true;
        }

        cooldownManager.startCooldown(player); // start cooldown for the rtd command
        effectManager.rollEffect(player); // roll effect and apply it to player

        return true;
    }
}
