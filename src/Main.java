int step = 0;
int playerHealth = 5;
int boardSize;
int difficulty;

Scanner in = new Scanner(System.in);
Random random = new Random();

Entity player, castle;
List<Entity> entities;

void main() {
    System.out.println("Начать игру? (ДА или НЕТ)");

    if (!in.nextLine().equals("ДА")) {
        System.out.println("Печально :(");
        return;
    }

    System.out.print("Введите сложность (1 - 3): ");
    difficulty = Math.clamp(in.nextInt(), 1, 3);
    System.out.println("Сложность: " + difficulty + "\n");

    generateLevel();

    Move();
}

void Move()
{
    step++;
    drawField();
    DisplayStats();
    handlePlayerMovement();

    if (playerHealth <= 0)
    {
        System.out.println("Вы проиграли :(");
        return;
    }
    if (player.isOnPos(castle))
    {
        System.out.println("Вы выиграли! :D");
        return;
    }

    Move();
}

void handlePlayerMovement()
{
    System.out.println("Введите новую позицию игрока (только на 1 клетку по x или по y)");
    System.out.print("X: ");
    int x = in.nextInt();
    System.out.print("Y: ");
    int y = in.nextInt();

    boolean isMoveValid = Math.abs(x - player.x) + Math.abs(player.y - y) == 1;
    if (x < 1 || x > boardSize || y < 1 || y > boardSize) isMoveValid = false;

    if (isMoveValid) {
        if (isAnyoneAtPos(x, y))
        {
            Entity entity = getEntityAtPoint(x, y);
            if (entity instanceof Monster monster)
            {
                if (!monster.challengePlayer()) playerHealth--;
                entities.remove(entity);
            }
        }
        player.SetPos(x, y);
    } else {
        System.out.println("Неправильный ход, попробуйте ещё раз!");
        handlePlayerMovement();
    }
}

void DisplayStats()
{
    System.out.println("Жизней: " + "❤️".repeat(playerHealth) + "\nХод: " + step + ", X: " + player.x + ", Y: " + player.y);
}

void generateLevel()
{
    boardSize = 3 + (difficulty - 1) * 2;
    player = new Entity(random.nextInt(1, boardSize), boardSize, "\uD83E\uDDD9");
    castle = new Entity(random.nextInt(1, boardSize), 1, "\uD83C\uDFF0");
    entities = new ArrayList<>(List.of(player, castle));

    int x;
    int y;

    while (true)
    {
        x = random.nextInt(1, boardSize);
        y = random.nextInt(1, boardSize);

        if (!isAnyoneAtPos(x, y)){
            entities.add(new BigMonster(x, y));
            break;
        }
    }

    for (int i = 0; i < (difficulty + 2) * boardSize; i++) {
        x = random.nextInt(1, boardSize);
        y = random.nextInt(1, boardSize);

        if (!isAnyoneAtPos(x, y)) entities.add(new Monster(x, y));
    }
}

Boolean isAnyoneAtPos(int x, int y)
{
    for (Entity entity : entities)
    {
        if (entity.isOnPos(x, y)) return true;
    }
    return false;
}

Entity getEntityAtPoint(int x, int y)
{
    for (Entity entity : entities)
    {
        if (entity.isOnPos(x, y)) return entity;
    }
    return null;
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
    for (int i = 0; i < boardSize; i++) System.out.print("* —— ");
    System.out.println("*");
}

void drawRaw(int y)
{
    drawLine();
    for (int x = 1; x < boardSize + 1; x++) {
        System.out.print("| ");
        DrawTile(x, y);
    }
    System.out.println("|");
}

void DrawTile(int x, int y)
{
    String tile = "  ";
    for (Entity entity : entities) {
        if (entity.isOnPos(x, y)) {
            tile = entity.character;
            break;
        }
    }
    System.out.print(tile + " ");
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
    public void SetPos(int newX, int newY)
    {
        x = newX;
        y = newY;
    }
}

class Monster extends Entity
{
    public Monster(int pX, int pY) {
        super(pX, pY, "\uD83E\uDDDF");
    }
    public Monster(int pX, int pY, String pCharacter) { super(pX, pY, pCharacter); }

    List<Supplier<Boolean>> challenges = List.of(
            () -> {
                int a = random.nextInt(1, 20);
                int b = random.nextInt(1, 20);
                System.out.print(this.character + ": " + a + " + " + b + " = ");
                return in.nextInt() == a + b;
            },
            () -> {
                int b = random.nextInt(1, 20);
                int a = b + random.nextInt(1, 20);
                System.out.print(this.character + ": " + a + " - " + b + " = ");
                return in.nextInt() == a - b;
            },
            () -> {
                int a = random.nextInt(1, 10);
                int b = random.nextInt(1, 10);
                System.out.print(this.character + ": " + a + " * " + b + " = ");
                return in.nextInt() == a * b;
            },
            () -> {
                int b = random.nextInt(1, 10);
                int a = b * random.nextInt(1, 10);
                System.out.print(this.character + ": " + a + " / " + b + " = ");
                return in.nextInt() == a / b;
            });

    public Boolean challengePlayer()
    {
        Boolean success = challenges.get(random.nextInt(challenges.size())).get();
        if (success) System.out.println("Верно!");
        else System.out.println("Неверно!");
        return success;
    }
}

class BigMonster extends Monster
{
    public BigMonster(int pX, int pY) {
        super(pX, pY, "\uD83D\uDC09");

        challenges = List.of(
                () -> {
                    int a = random.nextInt(1, 25 * difficulty);
                    int b = random.nextInt(1, 25 * difficulty);
                    System.out.print(this.character + ": " + a + " * " + b + " = ");
                    return in.nextInt() == a * b;
                },
                () -> {
                    int b = random.nextInt(10 * difficulty, 10 * (difficulty + 1));
                    int a = b * random.nextInt(10, 30);
                    System.out.print(this.character + ": " + a + " / " + b + " = ");
                    return in.nextInt() == a / b;
                },
                () -> {
                    int a = random.nextInt(3 + difficulty, 5 + difficulty + 3);
                    System.out.print(this.character + ": sqrt(" + a*a + ") = ");
                    return in.nextInt() == a;
                });
    }

    public Boolean challengePlayer()
    {
        Boolean success = super.challengePlayer();
        if (!success) playerHealth -= 2;
        else playerHealth += 1;
        return success;
    }
}
