package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LuckySandvich extends RTDEffect {
    public LuckySandvich() {
        setName("Lucky Sandvich");
        setDescription("That vas delicious!");
        setDurationSeconds(30);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) { // 2 bars of yellow hearts
        PotionEffect absorption = new PotionEffect(PotionEffectType.ABSORPTION, (getDurationSeconds() * 20), 9, true, false);
        player.addPotionEffect(absorption);
    }

    @Override
    public void remove(Player player) {
        if (player.hasPotionEffect(PotionEffectType.ABSORPTION)) {
            player.removePotionEffect(PotionEffectType.ABSORPTION);
        }
    }
}
