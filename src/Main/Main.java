package Main;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static int playerHealth = 5;

    public static Scanner in = new Scanner(System.in);
    public static Random random = new Random();

    public static Board board;

    void main() {
//        System.out.println("Начать игру? (ДА или НЕТ)");
//
//        if (!in.nextLine().equals("ДА")) {
//            System.out.println("Печально :(");
//            return;
//        }

        board = new Board();
        board.bossFight();

        while (true)
        {
            board.turn();
            if (playerHealth <= 0)
            {
                System.out.println("Вы проиграли :(");
                return;
            }
            if (board.player.isOnPos(board.castle))
            {
                board.bossFight();
                return;
            }
        }
    }
}
