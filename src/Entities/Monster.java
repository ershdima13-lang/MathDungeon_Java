package Entities;

import Main.Main;
import Main.Challenges;

import java.util.List;
import java.util.function.Supplier;

public class Monster extends Entity {
    public Monster(int pX, int pY) {
        super(pX, pY, "\uD83E\uDDDF");
    }
    public Monster(int pX, int pY, String pCharacter) { super(pX, pY, pCharacter); }

    List<Supplier<Boolean>> challenges = List.of(
        () -> new Challenges.AdditionChallenge().get(this.character, 1, 20),
        () -> new Challenges.SubtractionChallenge().get(this.character, 1, 20),
        () -> new Challenges.MultiplicationChallenge().get(this.character, 1, 10),
        () -> new Challenges.DivisionChallenge().get(this.character, 1, 10));

    public Boolean challengePlayer()
    {
        Boolean success = challenges.get(Main.random.nextInt(challenges.size())).get();
        if (success) System.out.println("Верно!");
        else System.out.println("Неверно!");
        return success;
    }
}
