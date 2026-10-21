package Entities;

public class Player extends Entity {
    private int health = 5;

    public Player(int pX, int pY, String pCharacter) {
        super(pX, pY, pCharacter);
    }

    public int getHealth() {
        return health;
    }

    public void damage(int dmg)
    {
        health -= dmg;
        if (health < 0) health = 0;
    }
}
