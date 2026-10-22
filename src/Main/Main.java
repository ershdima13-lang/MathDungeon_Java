package Main;

import Entities.Entity;
import Entities.Player;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static Scanner in = new Scanner(System.in);
    public static Random random = new Random();

    public static Board board;

    void main() {
        System.out.println("Начать игру? (ДА или НЕТ)");
        List<String> yesAnswers = List.of("Да", "ДА", "да", "дА", "LF", "Lf", "lf", "lF");

        if (!yesAnswers.contains(in.nextLine())) {
            System.out.println("Печально :(");
            return;
        }

        board = new Board();
        Player player = board.getPlayer();
        Entity castle = board.getCastle();

        while (true)
        {
            board.turn();
            if (player.getHealth() <= 0)
            {
                System.out.println("Вы проиграли :(");
                return;
            }
            if (player.isOnPos(castle))
            {
                new Bossfight().start();
                return;
            }
        }
    }
}
