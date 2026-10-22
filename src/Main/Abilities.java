package Main;

import java.util.List;
import java.util.function.Supplier;

public class Abilities {
    public abstract static class Ability {
        protected String name;
        protected String icon;
        protected int cooldown;
        protected int currentCooldown;
        protected List<Supplier<Boolean>> challenges;

        public Ability(String name, String icon, int cooldown)
        {
            this.name = name;
            this.icon = icon;
            this.cooldown = cooldown;
        }

        public int getCooldown() {
            return currentCooldown;
        }
        public String getName() {
            return name;
        }
        public String getIcon() {
            return icon;
        }

        public Boolean tryUse(Bossfight bossfight) {
            if (currentCooldown > 0)
            {
                System.out.println(STR."Способность на перезарядке! (\{currentCooldown})");
                return false;
            }
            currentCooldown = cooldown + 1;
            if (challenge()) {
                use(bossfight);
            }
            else System.out.println("У Вас не вышло использовать эту способность :(");
            return true;
        }

        public void update()
        {
            if (currentCooldown > 0) currentCooldown--;
        }

        protected Boolean challenge() {
            return challenges.get(Main.random.nextInt(challenges.size())).get();
        }

        protected abstract void use(Bossfight bossfight);
    }

    public static class BasicDamageAbility extends Ability {
        public BasicDamageAbility(String name, String icon, int cooldown) {
            super(name, icon, cooldown);
            challenges = List.of(
                    () -> new Challenges.AdditionChallenge().get(icon, 5 * Main.board.getDifficulty(), 10 * Main.board.getDifficulty()),
                    () -> new Challenges.SubtractionChallenge().get(icon, 5 * Main.board.getDifficulty() , 15 * Main.board.getDifficulty()),
                    () -> new Challenges.MultiplicationChallenge().get(icon, 2 * Main.board.getDifficulty(), 5 * Main.board.getDifficulty())
            );
        }

        @Override
        protected void use(Bossfight bossfight) {
            int dmg = Main.random.nextInt(8, 12);
            bossfight.damageDragon(dmg);
        }
    }

    public static class HealAbility extends Ability {
        public HealAbility(String name, String icon, int cooldown) {
            super(name, icon, cooldown);
            challenges = List.of(
                    () -> new Challenges.SubtractionChallenge().get(icon, Main.board.getDifficulty() * 10, Main.board.getDifficulty() * 20),
                    () -> new Challenges.MultiplicationChallenge().get(icon, 3 + Main.board.getDifficulty() * 3, 5 + Main.board.getDifficulty() * 3),
                    () -> new Challenges.DivisionChallenge().get(icon, Main.board.getDifficulty() * 5, Main.board.getDifficulty() * 10)
            );
        }

        @Override
        protected void use(Bossfight bossfight) {
            int heal = Main.random.nextInt(8, 12);
            bossfight.healPlayer(heal);
        }
    }

    public static class StrongDamageAbility extends Ability {

        public StrongDamageAbility(String name, String icon, int cooldown) {
            super(name, icon, cooldown);
            challenges = List.of(
                    () -> new Challenges.SubtractionChallenge().get(icon, 10, Main.board.getDifficulty() * 50),
                    () -> new Challenges.DivisionChallenge().get(icon, 10 + Main.board.getDifficulty() * 5, 10 + Main.board.getDifficulty() * 8),
                    () -> new Challenges.SqrtChallenge().get(icon, 5 + Main.board.getDifficulty() * 3, Main.board.getDifficulty() * 8)
            );
        }

        @Override
        protected void use(Bossfight bossfight) {
            if (Main.random.nextInt(1,3) != 1)
            {
                int dmg = Main.random.nextInt(15, 20);
                bossfight.damageDragon(dmg);
            } else System.out.println("Вы промахнулись! \uD83D\uDCA2");
        }
    }
}
