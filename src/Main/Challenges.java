package Main;

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
            System.out.print(character + ": " + a + " + " + b + " = ");
            return Main.in.nextInt() == a + b;
        }
    }

    public static class SubtractionChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int b = Main.random.nextInt(minB, maxB);
            int a = b + Main.random.nextInt(minA, maxA);
            System.out.print(character + ": " + a + " - " + b + " = ");
            return Main.in.nextInt() == a - b;
        }
    }

    public static class MultiplicationChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int a = Main.random.nextInt(minA, maxA);
            int b = Main.random.nextInt(minB, maxB);
            System.out.print(character + ": " + a + " * " + b + " = ");
            return Main.in.nextInt() == a * b;
        }
    }

    public static class DivisionChallenge extends BinaryChallenge
    {
        @Override
        public Boolean get(String character, int minA, int maxA, int minB, int maxB) {
            int b = Main.random.nextInt(minB, maxB);
            int a = b * Main.random.nextInt(minA, maxA);
            System.out.print(character + ": " + a + " / " + b + " = ");
            return Main.in.nextInt() == a / b;
        }
    }

    public static class SqrtChallenge extends UnaryChallenge
    {
        @Override
        public Boolean get(String character, int min, int max) {
            int a = Main.random.nextInt(min, max);
            System.out.print(character + ": sqrt(" + a*a + ") = ");
            return Main.in.nextInt() == a;
        }
    }
}
