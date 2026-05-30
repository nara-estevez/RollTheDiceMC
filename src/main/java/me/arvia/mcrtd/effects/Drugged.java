package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Drugged extends RTDEffect {
    public Drugged() {
        setName("Drugged");
        setDescription("Oww my head");
        setDurationSeconds(25);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) {
        PotionEffect nausea = new PotionEffect(PotionEffectType.NAUSEA, (getDurationSeconds() * 20), 1, true, false);
        player.addPotionEffect(nausea);
    }

    @Override
    public void remove(Player player) {
        if(player.hasPotionEffect(PotionEffectType.NAUSEA))
        {
            player.removePotionEffect(PotionEffectType.NAUSEA);
        }
    }
}
