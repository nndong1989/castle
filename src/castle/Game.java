package castle;

import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private HashMap<String, Handler> actions;

    public Game() {
        this.actions = new HashMap<String, Handler>();
        createRooms();
        setAction("bye", new ByeHandler(this));
    }

    private void setAction(String name, Handler actionHandler) {
        actions.put(name, actionHandler);
    }

    private void createRooms() {
        Room outside, lobby, pub, study, bedroom;

        //	制造房间
        outside = new Room("城堡外");
        lobby = new Room("大堂");
        pub = new Room("小酒吧");
        study = new Room("书房");
        bedroom = new Room("卧室");


        //	初始化房间的出口
        outside.setExit("south", lobby);
        outside.setExit("west", study);
        outside.setExit("east", pub);
        lobby.setExit("east", outside);
        pub.setExit("south", outside);
        study.setExit("north", outside);
        study.setExit("south", bedroom);
        bedroom.setExit("east", study);

        currentRoom = outside;  //	从城堡门外开始
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("欢迎来到城堡！");
        System.out.println("这是一个超级无聊的游戏。");
        System.out.println("如果需要帮助，请输入 'help' 。");
        System.out.println();
        System.out.println("现在你在" + currentRoom);
        System.out.print("出口有：");
        currentRoom.printRoomDesc();
        System.out.println();
    }

    // 以下为用户命令

    private void printHelp() {
        System.out.print("迷路了吗？你可以做的命令有：go bye help");
        System.out.println("如：\tgo east");
    }

    private void goRoom(String direction) {
        Room nextRoom = null;

        nextRoom = currentRoom.gotoRoom(direction);

        if (nextRoom == null) {
            System.out.println("那里没有门！");
        } else {
            currentRoom = nextRoom;
            System.out.println("你在" + currentRoom);
            System.out.print("出口有: ");
            currentRoom.printExitInfo();
            System.out.println();
        }
    }

    public void play() {
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        String[] words = line.split(" ");

        actions.get(words[0]).doCMD();
    }

    public static void main(String[] args) {

        Game game = new Game();
        game.printWelcome();

        while (true) {
            game.play();
        }

//        System.out.println("感谢您的光临。再见！");
    }

}
