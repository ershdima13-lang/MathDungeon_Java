package Main;

import Entities.Player;

import java.util.List;

public class Bossfight {
    private final int dragonMaxHealth = 50 * Main.board.getDifficulty();
    private int dragonHealth = dragonMaxHealth;
    private int playerHealth;

    private final Player player = Main.board.getPlayer();

    private final List<Abilities.Ability> abilities = List.of(
            new Abilities.BasicDamageAbility("Ударить мечом", "\uD83D\uDDE1️", 0),
            new Abilities.HealAbility("Восстановить магией здоровье", "✨", 3),
            new Abilities.StrongDamageAbility("Ударить мечом очень сильно", "⚔️", 5)
    );

    public void start()
    {
        System.out.println("Вы заходите в замок и видите паращего в небе дракона. Сразите его!");
        playerHealth = player.getHealth() * 20;
        while (true) {
            if (!turn()) break;
        }
    }

    public void damageDragon(int dmg)
    {
        dragonHealth -= dmg;
        if (dragonHealth < 0) dragonHealth = 0;
        System.out.println(STR."Вы нанесли \{dmg} урона! \uD83D\uDDE1️\uD83D\uDC09");
    }

    public void healPlayer(int amount)
    {
        playerHealth += amount;
        System.out.println(STR."Вы восстановили \{amount} здоровья! ✨");
    }

    private Boolean turn()
    {
        for (Abilities.Ability ability : abilities) ability.update();
        displayStats();
        displayOptions();
        getPlayerInput();
        if (dragonHealth <= 0)
        {
            System.out.println("Вы победили!!! 🎉🎉🎉");
            return false;
        }
        dragonTurn();
        if (playerHealth <= 0)
        {
            System.out.println("Вы проиграли... :(");
            return false;
        }
        return true;
    }

    private void displayOptions()
    {
        for (int i = 0; i < abilities.size(); i++)
        {
            Abilities.Ability ability = abilities.get(i);
            System.out.println(STR."\{i + 1}. \{ability.getName()} \{ability.getIcon()}"
                + STR."\{ability.getCooldown() > 0 ? STR."(перезарядка: \{ability.getCooldown()})" : ""}");
        }
    }

    private void displayStats()
    {
        System.out.println(STR."Ваше здоровье: \{playerHealth} (\{"❤️".repeat((int) Math.ceil((double) playerHealth / 20))})");
        System.out.println(STR."Здоровье дракона: \{dragonHealth} (\{"❤️".repeat((int) Math.ceil(((double) dragonHealth / dragonMaxHealth) * 5f))})");
    }

    private void getPlayerInput()
    {
        while (true) {
            int input = Main.in.nextInt() - 1;
            if (input >= 0 && input < abilities.size())
            {
                if (abilities.get(input).tryUse(this)) return;
            } else {
                if (input == -1) displayOptions();
                else System.out.println("Неизвестное действие, попробуйте ещё раз. Введите 0, чтобы вывести варианты.");
            }
        }
    }

    private void dragonTurn()
    {
        int dmg;
        if (Main.random.nextInt(1, 8 - Main.board.getDifficulty()) == 1)
        {
            System.out.println("Дракон готовит атаку огнём, уворачивайтесь!");
            if (new Challenges.DragonChallenge().get()) {
                System.out.println("Вы успешно увернулись! 💯");
                return;
            }
            dmg = 8 + Main.board.getDifficulty() * 2;
            System.out.println(STR."Вы не успели увернуться. Дракон нанёс огнём урон в \{dmg} ед.! 🔥");
        } else {
            dmg = Main.random.nextInt(2 * Main.board.getDifficulty(), 3 * Main.board.getDifficulty());
            System.out.println(STR."Дракон нанёс урон в \{dmg} ед.! 💥");
        }
        playerHealth -= dmg;
    }
}
