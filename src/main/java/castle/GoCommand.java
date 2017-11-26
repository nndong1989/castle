package castle;

public class GoCommand extends Command {

    public GoCommand(Game game) {
        super(game);
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            System.out.println("参数错误");
            return;
        }

        String direction = args[0];
        Room currentRoom = game.getCurrentRoom();
        Room nextRoom = currentRoom.gotoRoom(direction);

        if (nextRoom == null) {
            System.out.println("那里没有门！");
        } else {
            currentRoom = nextRoom;
            game.setCurrentRoom(currentRoom);
            System.out.println("你在" + currentRoom);
            System.out.print("出口有: ");
            currentRoom.printExitInfo();
            System.out.println();
        }
    }
}
