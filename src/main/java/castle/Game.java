package castle;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class Game {
    public static class Connection {
        public String from;
        public String to;
        public String direction;
    }

    private List<Room> rooms;
    private List<Connection> connections;
    private String currentRoomId;

    private Room currentRoom;
    private HashMap<String, Command> actions;

    private boolean stopped;

    public static Game load(String resourcePath) {
        //根据数据和框架分离的原则，把初始化rooms的数据放在了resource的config.json里面
        InputStream in = Game.class.getResourceAsStream(resourcePath);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Game game = objectMapper.readValue(in, Game.class);
            game.buildGame();
            in.close();
            return game;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Game() {
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public String getCurrentRoomId() {
        return currentRoomId;
    }

    public void setCurrentRoomId(String currentRoomId) {
        this.currentRoomId = currentRoomId;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        if (currentRoom == null) {
            currentRoomId = null;
        } else {
            currentRoomId = currentRoom.getId();
        }
    }

    public void buildGame() {
        if (rooms == null) {
            throw new IllegalStateException("缺少rooms");
        }
        if (connections == null) {
            throw new IllegalStateException("缺少connections");
        }

        //创建room id与room对象之间的map，方面下面构建connection时查找
        Map<String, Room> roomMap = new HashMap<String, Room>();
        for (Room room : rooms) {
            roomMap.put(room.getId(), room);
        }

        //根据config.json里的房间connection信息构建rooms之间的relationship
        for (Connection conn : connections) {
            Room fromRoom = roomMap.get(conn.from);
            Room toRoom = roomMap.get(conn.to);
            fromRoom.setExit(conn.direction, toRoom);
        }

        //设置当前房间，当前房间的设定也在config里面
        currentRoom = roomMap.get(currentRoomId);

        //设置游戏支持的actions
        setupActions();
    }

    private void setupActions() {
        this.actions = new HashMap<String, Command>();
        setAction("help", new HelpCommand(this));
        setAction("bye", new ByeCommand(this));
        setAction("go", new GoCommand(this));
    }

    private void setAction(String name, Command actionCommand) {
        actions.put(name, actionCommand);
    }

    public boolean isStopped() {
        return stopped;
    }

    public void stop() {
        stopped = true;
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

    public void play(String cmd, String[] args) {
        Command command = actions.get(cmd);
        if (command == null) {
            command = actions.get("help");
        }
        command.execute(args);
    }

    public static void main(String[] args) throws IOException {
        Game game = Game.load("/config.json");
        game.printWelcome();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!game.isStopped()) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }

            String[] parts = line.split(" ");
            String cmd = parts[0];
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);
            game.play(cmd, arguments);
        }

    }

}
