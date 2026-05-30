package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class InventoryShuffle extends RTDEffect {
    public InventoryShuffle() {
        setName("Inventory Shuffle");
        setDescription("Where did my items go?");
        setDurationSeconds(0);
        setIsPositive(false);
    }

    @Override
    public void apply(Player player) { // Doubles the amount of player hearts
        PlayerInventory inventory = player.getInventory();
        ItemStack[] contents = inventory.getContents();

        List<ItemStack> items = new ArrayList<>();
        Collections.addAll(items, contents);

        items.removeIf(Objects::isNull);

        Collections.shuffle(items);

        inventory.clear();
        for (int i = 0; i < items.size(); i++)
        {
            inventory.setItem(i, items.get(i));
        }
    }

    @Override
    public void remove(Player player) {
        // nothing
    }

}
