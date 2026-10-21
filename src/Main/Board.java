package Main;

import Entities.BigMonster;
import Entities.Entity;
import Entities.Monster;
import Entities.Player;

import java.util.ArrayList;
import java.util.List;

public class Board {
    int step = 0;
    int size;

    int difficulty;

    Entity castle;
    Player player;
    List<Entity> entities;

    public Board()
    {
        System.out.print("Введите сложность (1 - 3): ");
        difficulty = Math.clamp(Main.in.nextInt(), 1, 3);
        System.out.println("Сложность: " + difficulty + "\n");

        size = 3 + (difficulty - 1) * 2;
        player = new Player(Main.random.nextInt(1, size), size, "\uD83E\uDDD9");
        castle = new Entity(Main.random.nextInt(1, size), 1, "\uD83C\uDFF0");
        entities = new ArrayList<>(List.of(player, castle));

        int x;
        int y;

        while (true)
        {
            x = Main.random.nextInt(1, size);
            y = Main.random.nextInt(1, size);

            if (!isAnyoneAtPos(x, y)){
                entities.add(new BigMonster(x, y));
                break;
            }
        }

        for (int i = 0; i < (difficulty + 2) * size; i++) {
            x = Main.random.nextInt(1, size);
            y = Main.random.nextInt(1, size);

            if (!isAnyoneAtPos(x, y)) entities.add(new Monster(x, y));
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void turn()
    {
        step++;
        draw();
        displayStats();
        handlePlayerMovement();
    }

    public void bossFight()
    {
        new Bossfight().start(this);
    }

    private Boolean isAnyoneAtPos(int x, int y)
    {
        for (Entity entity : entities)
        {
            if (entity.isOnPos(x, y)) return true;
        }
        return false;
    }

    private Entity getEntityAtPoint(int x, int y)
    {
        for (Entity entity : entities)
        {
            if (entity.isOnPos(x, y)) return entity;
        }
        return null;
    }

    private void draw()
    {
        for (int y = 1; y < size + 1; y++) {
            drawRaw(y);
        }
        drawLine();
    }

    private void drawLine()
    {
        for (int i = 0; i < size; i++) System.out.print("* —— ");
        System.out.println("*");
    }

    private void drawRaw(int y)
    {
        drawLine();
        for (int x = 1; x < size + 1; x++) {
            System.out.print("| ");
            drawTile(x, y);
        }
        System.out.println("|");
    }

    private void drawTile(int x, int y)
    {
        String tile = "  ";
        for (Entity entity : entities) {
            if (entity.isOnPos(x, y)) {
                tile = entity.character;
                break;
            }
        }
        System.out.print(tile + " ");
    }

    private void displayStats()
    {
        System.out.println("Жизней: " + "❤️".repeat(player.getHealth()) + "\nХод: " + step + ", X: " + player.getX() + ", Y: " + player.getY());
    }

    private void handlePlayerMovement()
    {
        System.out.println("Введите новую позицию игрока (только на 1 клетку по x или по y)");
        System.out.print("X: ");
        int x = Main.in.nextInt();
        System.out.print("Y: ");
        int y = Main.in.nextInt();

        boolean isMoveValid = Math.abs(x - player.getX()) + Math.abs(player.getY() - y) == 1;
        if (x < 1 || x > size || y < 1 || y > size) isMoveValid = false;

        if (isMoveValid) {
            if (isAnyoneAtPos(x, y))
            {
                Entity entity = getEntityAtPoint(x, y);
                if (entity instanceof Monster monster)
                {
                    if (!monster.challengePlayer()) player.damage(1);
                    entities.remove(entity);
                }
            }
            player.SetPos(x, y);
        } else {
            System.out.println("Неправильный ход, попробуйте ещё раз!");
            handlePlayerMovement();
        }
    }
}
