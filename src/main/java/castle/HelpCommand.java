package castle;

public class HelpCommand extends Command {
    public HelpCommand(Game game) {
        super(game);
    }

    @Override
    public void execute(String[] args) {
        System.out.print("迷路了吗？你可以做的命令有：go bye help");
        System.out.println("如：\tgo east");

    }
}
