package castle;

public class ByeCommand extends Command {
    public ByeCommand(Game game) {
        super(game);
    }

    @Override
    public void execute(String[] args) {
        System.out.println("感谢您的光临。再见！");
        game.stop();
    }
}
