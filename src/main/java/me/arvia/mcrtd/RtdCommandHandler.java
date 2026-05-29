package me.arvia.mcrtd;

import org.bukkit.Bukkit;
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

    private void handleRTD(Player player)
    {
        // is player on cooldown?
        if (cooldownManager.isCooldownActive(player)) {
            player.sendMessage("[RTD] You must wait " + cooldownManager.getSecondsRemaining(player) + " seconds before rolling the dice!");
            return;
        }

        cooldownManager.startCooldown(player); // start cooldown for the rtd command
        effectManager.rollEffect(player); // roll effect and apply it to player
    }

    private void handleRTDClear(Player player)
    {
        cooldownManager.clearCooldown(player);
        effectManager.clearEffects(player);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        switch (command.getName()) {
            case "rtd":
                if (!(sender instanceof Player player))
                {
                    sender.sendMessage("[RTD] Only players can execute this command.");
                    return true;
                }

                handleRTD(player);
                break;
            case "rtd-clear":
                if (!sender.isOp())
                {
                    sender.sendMessage("[RTD] You must be a server operator to run this command.");
                    return true;
                }
                if (args.length < 1)
                {
                    sender.sendMessage("[RTD] You need to specify a player name.");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null)
                {
                    sender.sendMessage("[RTD] Player was not found or is offline. Check the spelling.");
                    return true;
                }

                handleRTDClear(target);
                break;
            case "rtd-set-cooldown":
                if (!sender.isOp())
                {
                    sender.sendMessage("[RTD] You must be a server operator to run this command.");
                    return true;
                }
                if (args.length < 1)
                {
                    sender.sendMessage("[RTD] You must specify the length of the cooldown in seconds.");
                    return true;
                }

                try {
                    cooldownManager.setCooldownSeconds(Integer.parseInt(args[0]));
                } catch (Exception e) {
                    sender.sendMessage("[RTD] Invalid cooldown in seconds.");
                }
                break;
        }

        return true;
    }
}
