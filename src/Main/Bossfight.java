package Main;

import java.util.List;

public class Bossfight {
    private int dragonHealth = 100;
    private int playerHealth;

    private final List<Abilities.Ability> abilities = List.of(
            new Abilities.BasicDamageAbility("Ударить мечом", "\uD83D\uDDE1️", 0),
            new Abilities.HealAbility("Восстановить магией злоровье", "✨", 3)
    );

    public void start(Board board)
    {
        System.out.println("Вы заходите в замок. Вас встречает уже знакомый Вам дракон. Сразите его!");
        playerHealth = board.player.getHealth() * 20;
        while (true) turn();
    }

    public void damageDragon(int dmg)
    {
        dragonHealth -= dmg;
        if (dragonHealth < 0) dragonHealth = 0;
        System.out.println("Вы нанесли " + dmg + " урона! \uD83D\uDDE1️\uD83D\uDC09");
    }

    public void healPlayer(int amount)
    {
        playerHealth += amount;
        System.out.println("Вы восстановили " + amount + " здоровья! ✨");
    }

    private void turn()
    {
        getPlayerInput();
        for (Abilities.Ability ability : abilities) ability.update();
    }

    private void displayOptions()
    {
        for (int i = 0; i < abilities.size(); i++)
        {
            Abilities.Ability ability = abilities.get(i);
            System.out.println(String.format("%s. %s %s", i + 1, ability.getName(), ability.getIcon()) +
                    ((ability.getCooldown() > 0) ? String.format(" (перезарядка: %s)", ability.getCooldown()) : ""));
        }
    }

    private void getPlayerInput()
    {
        displayOptions();
        while (true) {
            int input = Main.in.nextInt() - 1;
            if (input >= 0 && input < abilities.size())
            {
                abilities.get(input).tryUse(this);
                return;
            } else {
                if (input == -1) displayOptions();
                else System.out.println("Неизвестное действие, попробуйте ещё раз. Введите 0, чтобы вывести варианты.");
            }
        }
    }
}
