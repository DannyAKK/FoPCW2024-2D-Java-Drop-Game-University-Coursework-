package uk.ac.bradford.dropgame;

public class Player extends Entity {

    private final int maxEnergy;
    private int energy;
    private int itemsCollected = 0;

    public Player(int maxEnergy, int x, int y) {
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy;
        setPosition(x, y);
    }

    public void refillEnergy() {
        energy = maxEnergy;
    }
    
    public void changeEnergy(int amount) {
        energy += amount;
        if (energy > maxEnergy) {
            energy = maxEnergy;
        }
        if (energy < 0) {
            energy = 0;
        }
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void incrementItemsCollected() {
        itemsCollected++;
    }

    public int getItemsCollected() {
        return itemsCollected;
    }
}
