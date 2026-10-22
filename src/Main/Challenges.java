package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Challenges {
    public abstract static class Challenge
    {
        public abstract Boolean get();
    }

    public abstract static class BinaryChallenge extends Challenge
    {
        public abstract Boolean get(String character, int minA, int maxA, int minB, int maxB);
        public Boolean get() {
            return false;
        }
        public Boolean get(String character, int a, int b) {
            return get(character, a, b, a, b);
        }
    }

    public abstract static class UnaryChallenge extends Challenge
    {
        public Boolean get() {
            return false;
        }
        public abstract Boolean get(String character, int min, int max);
    }

    public static class AdditionChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int a = Main.random.nextInt(minA, maxA);
            int b = Main.random.nextInt(minB, maxB);
            System.out.print(STR."\{character}: \{a} + \{b} = ");
            return Main.in.nextInt() == a + b;
        }
    }

    public static class SubtractionChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int b = Main.random.nextInt(minB, maxB);
            int a = b + Main.random.nextInt(minA, maxA);
            System.out.print(STR."\{character}: \{a} - \{b} = ");
            return Main.in.nextInt() == a - b;
        }
    }

    public static class MultiplicationChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int a = Main.random.nextInt(minA, maxA);
            int b = Main.random.nextInt(minB, maxB);
            System.out.print(STR."\{character}: \{a} * \{b} = ");
            return Main.in.nextInt() == a * b;
        }
    }

    public static class DivisionChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int b = Main.random.nextInt(minB, maxB);
            int a = b * Main.random.nextInt(minA, maxA);
            System.out.print(STR."\{character}: \{a} / \{b} = ");
            return Main.in.nextInt() == a / b;
        }
    }

    public static class DragonChallenge extends Challenge
    {
        // Чтож, я попытался всё сделать чисто...

        private final List<String> icons = new ArrayList<>(List.of("🛡️", "⚔️", "🔥", "💥", "🎯"));

        @Override
        public Boolean get() {
            Collections.shuffle(icons, Main.random);

            List<String> ops = List.of("+", "*", "-");
            String firstOp = ops.get(Main.random.nextInt(ops.size()));
            String secondOp = ops.get(Main.random.nextInt(ops.size()));

            int a = Main.random.nextInt(5, 3 + Main.board.getDifficulty() * 3);
            int b = Main.random.nextInt(3, 2 + Main.board.getDifficulty() * 2);

            String aIcon = icons.get(0);
            String bIcon = icons.get(1);
            String answerIcon = icons.get(2);

            int answer = Main.random.nextInt(5, 5 + Main.board.getDifficulty() * 2);

            System.out.print(STR."""
                    \{aIcon} = \{a}
                    \{getOperationString(aIcon, bIcon, firstOp)} = \{getOperationResult(a, b, firstOp)}
                    \{getOperationString(STR."(\{getOperationString(bIcon, answerIcon, "*")})", aIcon,secondOp)} = \{getOperationResult(b * answer, a, secondOp)}
                    \{answerIcon} =""");
            System.out.print(" ");

            return Main.in.nextInt() == answer;
        }

        private String getOperationString(String a, String b, String op)
        {
            boolean swap = !op.equals("-") && Main.random.nextBoolean();

            String l = swap ? b : a;
            String r = swap ? a : b;

            return STR."\{l} \{op} \{r}";
        }

        private int getOperationResult(int a, int b, String op)
        {
            switch (op) {
                case "+" -> {
                    return a + b;
                }
                case "*" -> {
                    return a * b;
                }
                default -> {
                    return a - b;
                }
            }
        }
    }

    public static class SqrtChallenge extends UnaryChallenge
    {
        @Override
        public Boolean get(String character, int min, int max) {
            int a = Main.random.nextInt(min, max);
            System.out.print(STR."\{character}: sqrt(\{a * a}) = ");
            return Main.in.nextInt() == a;
        }
    }
}
