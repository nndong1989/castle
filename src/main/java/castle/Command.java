package castle;

public abstract class Command {
    protected Game game;

    public Command(Game game) {
        this.game = game;
    }

    public abstract void execute(String[] args);
}
