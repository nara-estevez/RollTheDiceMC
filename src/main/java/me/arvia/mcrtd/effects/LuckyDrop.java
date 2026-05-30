package me.arvia.mcrtd.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LuckyDrop extends RTDEffect{
    public LuckyDrop() {
        setName("Lucky Drop");
        setDescription("You found a diamond block!");
        setDurationSeconds(0);
        setIsPositive(true);
    }

    @Override
    public void apply(Player player) { // gives player diamond block
        ItemStack item = new ItemStack(Material.DIAMOND_BLOCK, 1);
        player.getInventory().addItem(item);
    }

    @Override
    public void remove(Player player) {
        // nothing
    }
}
