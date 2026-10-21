package Entities;

public class Entity {
    private int x;
    private int y;
    public String character;

    public Entity(int pX, int pY, String pCharacter)
    {
        x = pX;
        y = pY;
        character = pCharacter;
    }

    public Boolean isOnPos(int pX, int pY) { return pX == x && pY == y; }
    public Boolean isOnPos(Entity entity) { return entity.x == x && entity.y == y; }
    public void SetPos(int newX, int newY)
    {
        x = newX;
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
