package me.arvia.mcrtd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RtdCommandHandler implements CommandExecutor {
    private final CooldownManager cooldownManager;
    private final EffectManager effectManager;
    private final MessageManager messageManager;

    public RtdCommandHandler(CooldownManager cooldownManager, EffectManager effectManager, MessageManager messageManager) {
        this.cooldownManager = cooldownManager;
        this.effectManager = effectManager;
        this.messageManager = messageManager;
    }

    private void handleRTD(Player player, int option)
    {
        // is player on cooldown?
        if (cooldownManager.isCooldownActive(player)) {
            messageManager.sendString(player, "You must wait " + cooldownManager.getSecondsRemaining(player) + " seconds before rolling the dice!");
            return;
        }

        cooldownManager.startCooldown(player); // start cooldown for the rtd command
        option = (option == -1) ? (int) (Math.random() * effectManager.getEffectsAmount()) : option; // if no option given, roll a random one
        effectManager.rollEffect(player, option); // roll effect and apply it to player
    }

    private void handleRTDClear(Player player)
    {
        cooldownManager.clearCooldown(player);
        effectManager.clearEffects(player);

        messageManager.sendString(player, "Your effects have been cleared by an operator.");
    }

    private boolean doesPlayerExist(Player player)
    {
        return (player != null && player.isOnline());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        switch (command.getName()) {
            case "rtd":
                if (!(sender instanceof Player player))
                {
                    messageManager.sendString(sender, "Only players can execute this command.");
                    return true;
                }

                int rtdOption = -1;
                if (args.length > 0 && player.isOp()) // if sender is operator and tries to force effect, allow them
                {
                    rtdOption = Integer.parseInt(args[0]);
                    if (args.length > 1) { // if also sends name of a player
                        String playerName = args[1];
                        Player target = Bukkit.getPlayer(playerName);
                        if (doesPlayerExist(target))
                        {
                            player = target;
                        }
                    }
                }

                handleRTD(player, rtdOption);
                break;
            case "rtd-clear":
                if (!sender.isOp())
                {
                    messageManager.sendString(sender, "You do not have permission to execute this command.");
                    return true;
                }
                if (args.length < 1)
                {
                    messageManager.sendString(sender, "You need to specify a player name.");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[0]);
                if (!doesPlayerExist(target))
                {
                    messageManager.sendString(sender, "That player is not online or does not exist.");
                    return true;
                }

                handleRTDClear(target);
                messageManager.sendString(sender, "Effects on " + target.getName() + " have been cleared.");
                break;
            case "rtd-set-cooldown":
                if (!sender.isOp())
                {
                    messageManager.sendString(sender, "You do not have permission to execute this command.");
                    return true;
                }
                if (args.length < 1)
                {
                    messageManager.sendString(sender, "You must specify the length of the cooldown in seconds.");
                    return true;
                }

                int cooldown;
                try {
                    cooldown = Integer.parseInt(args[0]);
                } catch (Exception e) {
                    messageManager.sendString(sender, "Invalid cooldown in seconds.");
                    return true;
                }
                cooldownManager.setCooldownSeconds(cooldown);
                messageManager.sendString(sender, "Cooldown set to " + args[0] + " seconds.");

                if (cooldown < 40) {
                    messageManager.sendString(sender, "WARNING: Cooldown timer is less than 40 seconds. This may lead to RTD effects overlapping and breaking. Consider setting a higher cooldown.", ChatColor.YELLOW);
                }
                break;
        }

        return true;
    }
}
