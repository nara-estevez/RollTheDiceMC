package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;

public abstract class RTDEffect {
    private int durationSeconds;
    private String name;
    private String description;
    private boolean isPositive;

    public abstract void apply(Player player);
    public abstract void remove(Player player);

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
    public void setIsPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

    // GETTERS
    public String getName() {
        return this.name;
    }
    public String getDescription() {
        return this.description;
    }
    public int getDurationSeconds() {
        return this.durationSeconds;
    }
    public boolean isPositive() {
        return this.isPositive;
    }
}
