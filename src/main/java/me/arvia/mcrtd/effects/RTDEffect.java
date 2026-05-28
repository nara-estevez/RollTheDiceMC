package me.arvia.mcrtd.effects;

import org.bukkit.entity.Player;

public abstract class RTDEffect {
    private int durationSeconds;
    private String name;
    private String description;
    private boolean isPositive;

    public RTDEffect() {
    }

    void apply(Player player) {

    }

    void remove(Player player) {
    }

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
    String getName() {
        return this.name;
    }
    String getDescription() {
        return this.description;
    }
    int getDurationSeconds() {
        return this.durationSeconds;
    }
    boolean isPositive() {
        return this.isPositive;
    }
}
