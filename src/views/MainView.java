package views;

import game.IPlayer;
import game.Player;
import game.Tile;
import game.helper.Direction;
import game.helper.Position;
import game.items.ItemManager;
import game.map.Map;
import game.render.Render;
import listener.KeyListen;
import network.CorruptedPacketException;
import network.IPacket;
import network.Packet;
import network.types.*;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class MainView extends Observable implements Observer {
    public static boolean canUpdate = false;
    private JFrame frame;
    private Render render;
    private KeyListen listen;
    private String ip;
    private boolean mode;

    private int width = 700;
    private int height = 700;

    private Socket s;
    private IPlayer icebreaker, helper;
    private ItemManager manager;
    private Map map;
    private BufferedReader read;
    private BufferedWriter write;

    public MainView(boolean mode, String ip) {
        this.ip = ip;
        this.mode = mode;

        if(mode) setupClient();
        else setupServer();
        makeFrame();
        gameloop();
    }

    public void setupClient() {
        try {
            s = new Socket(ip, 2727);
            write = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            read = new BufferedReader(new InputStreamReader(s.getInputStream()));

            map = Map.getInstance();
            manager = ItemManager.getInstance();

            icebreaker = new Player(new Position(1, 1), Direction.DOWN, write);
            helper = new Player(new Position(2, 1), Direction.DOWN, null);
            icebreaker.addUpdater(this);

            listen = new KeyListen(icebreaker);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setupServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(2727);
            s = serverSocket.accept();
            write = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            read = new BufferedReader(new InputStreamReader(s.getInputStream()));

            map = Map.getInstance();
            manager = ItemManager.getInstance();

            icebreaker = new Player(new Position(2, 1), Direction.DOWN, null);
            helper = new Player(new Position(1, 1), Direction.DOWN, write);
            icebreaker.addUpdater(this);

            listen = new KeyListen(icebreaker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeFrame() {
        frame = new JFrame("Cool Boiz");
        frame.setSize(width, height);
        frame.setResizable(false);

        if(mode) render = new Render(icebreaker, helper, map, manager, mode);
        else render = new Render(helper, icebreaker, map, manager, mode);
        render.setBounds(0, 0, width, height);

        frame.add(render);

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.addKeyListener(listen);
        frame.repaint();

        addObserver(this);
    }

    public void gameloop() {
        Timer readTimer = new Timer();
        readTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ExecutorService service = Executors.newSingleThreadExecutor();
                try {
                    service.submit(() -> {
                        try {
                            IPacket packet = Packet.readNextPacket(read);
                            System.out.println("Received a packet of type " + packet.getType());

                            // TODO: Packets
                            if(packet instanceof HelloPacket) {
                                /* Do nothing */
                            } else if(packet instanceof ItemRemovePacket) {
                                manager.deleteItemAt(((ItemRemovePacket) packet).getItemPosition());
                            } else if(packet instanceof MapPacket) {
                                MapPacket mapPacket = (MapPacket) packet;
                                map.updateTileAt(mapPacket.getPosition(), mapPacket.getTile());

                                /* TODO: Store questions */
                            } else if(packet instanceof PlayerPacket) {
                                PlayerPacket playerPacket = (PlayerPacket) packet;
                                if(mode) {
                                    helper = playerPacket.getPlayer();
                                    render.setOther(helper);
                                } else {
                                    icebreaker = playerPacket.getPlayer();
                                    render.setOther(icebreaker);
                                }
                            } else if(packet instanceof WinPacket) {
                                WinPacket winPacket = (WinPacket) packet;
                                if(winPacket.getWin()) {
                                    System.out.println("You Win!!");
                                } else {
                                    System.out.println("You lose ;-;");
                                } System.exit(0);
                            }
                        } catch (IOException | CorruptedPacketException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            System.err.println("Received null packet!!");
                            System.exit(3);
                        }
                    }).get(10, TimeUnit.SECONDS);
                    service.shutdown();
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 50);

        Timer renderTimer = new Timer();
        renderTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(canUpdate) {
                    setChanged();
                    notifyObservers();

                    canUpdate = false;
                } else {
                    canUpdate = true;
                }
            }
        }, 0, 50);

        Timer helloTimer = new Timer();
        helloTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    write.write(new HelloPacket().asPacket());
                    write.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
    }

    @Override
    public void update(Observable observable, Object o) {
        SwingUtilities.invokeLater(() -> {
            render.paintComponents(render.getGraphics());
            render.repaint();
        });
    }
}
