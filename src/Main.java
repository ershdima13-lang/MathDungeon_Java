int step = 0;
int playerHealth = 5;
int boardSize = 5;

Entity player = new Entity(1 + boardSize / 2, 1 + boardSize / 2, "\uD83E\uDDD9");
Entity monsterSmall = new Entity(2, 1, "\uD83E\uDDDF");
Entity castle = new Entity(1, 1, "\uD83C\uDFF0");

Entity[] entities = { player, monsterSmall, castle };

String field = "";
Scanner in = new Scanner(System.in);

void main() {
    System.out.println("Начать игру? (ДА или НЕТ)");

    switch (in.nextLine()) {
        case "ДА":
            System.out.println("Ок");
            break;
        default:
            System.out.println("Печально :(");
            return;
    }

    while (playerHealth > 0 && !player.isOnPos(castle)) {
        drawField();
        return;
    }
}

void drawField()
{
    for (int y = 1; y < boardSize + 1; y++) {
        drawRaw(y);
    }
    drawLine();
}

void drawLine()
{
    for (int i = 0; i < boardSize; i++) System.out.print("+ —— ");
    System.out.println("+");
}

void drawRaw(int y)
{
    drawLine();
    for (int x = 1; x < boardSize + 1; x++) {
        System.out.print("| ");
        Boolean isEmpty = true;
        for (int i = 0; i < entities.length; i++) {
            if (entities[i].isOnPos(x, y)) {
                System.out.print(entities[i].character);
                isEmpty = false;
            }
        }
        if (isEmpty) System.out.print("  ");
        System.out.print(" ");
    }
    System.out.println("|");
}

class Entity
{
    public int x;
    public int y;
    public String character;

    public Entity(int pX, int pY, String pCharacter)
    {
        x = pX;
        y = pY;
        character = pCharacter;
    }

    public Boolean isOnPos(int pX, int pY) { return pX == x && pY == y; }
    public Boolean isOnPos(Entity entity) { return entity.x == x && entity.y == y; }
}
