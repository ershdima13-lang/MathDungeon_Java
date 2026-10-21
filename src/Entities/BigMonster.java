package Entities;

import Main.Main;
import Main.Challenges;

import java.util.List;

public class BigMonster extends Monster {
    public BigMonster(int pX, int pY) {
        super(pX, pY, "\uD83D\uDC09");

        challenges = List.of(
            () -> new Challenges.MultiplicationChallenge().get(this.character, 1, 25 * Main.board.getDifficulty()),
            () -> new Challenges.DivisionChallenge().get(this.character, 10, 30,
                10 * Main.board.getDifficulty(), 10 * (Main.board.getDifficulty() + 1)),
            () -> new Challenges.SqrtChallenge().get(this.character, 3 + Main.board.getDifficulty(), 5 + Main.board.getDifficulty() + 3)
        );
    }

    public Boolean challengePlayer()
    {
        Boolean success = super.challengePlayer();
        if (!success) Main.playerHealth -= 2;
        else Main.playerHealth += 1;
        return success;
    }
}
