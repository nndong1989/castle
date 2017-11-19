package castle;

public class ByeHandler extends Handler {
    public ByeHandler(Game game) {
        super(game);
    }

    @Override
    public void doCMD() {
        System.out.println("bye bye!");
        System.exit(0);
    }
}
