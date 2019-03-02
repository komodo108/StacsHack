package views;

import game.IPlayer;
import game.render.Render;
import listener.KeyListen;
import network.CorruptedPacketException;
import network.IPacket;
import network.Packet;
import network.types.HelloPacket;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class MainView extends Observable implements Observer {
    private boolean canUpdate = false;
    private JFrame frame;
    private Render render;
    private KeyListen listen;
    private String ip;
    private boolean mode;

    private int width = 700;
    private int height = 700;

    private Socket s;
    private IPlayer icebreaker, helper;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeFrame() {
        render = new Render();
        render.setBounds(0, 0, width, height);

        frame = new JFrame("Cool Boiz");
        frame.setSize(width, height);
        frame.setResizable(false);

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
                            if(packet instanceof HelloPacket) { }
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                render.paintComponents(render.getGraphics());
                render.repaint();
            }
        });
    }
}