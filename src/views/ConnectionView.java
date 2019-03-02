package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

public class ConnectionView {

    private JFrame frame;
    private JLabel info;
    private JTextField ip;
    private JButton button, next;

    private boolean isClient;
    private int width = 340;
    private int height = 340;

    public ConnectionView(boolean isClient) {
        this.isClient = isClient;

        frame = new JFrame();
        frame.setTitle(isClient ? "Cool Boiz -- Client" : "Cool Boiz -- Server");
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        info = new JLabel();
        info.setText(isClient ? "Connect to a partner" : "Host a server");
        info.setBounds(20, 20, width - 40, 20);
        info.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(info);

        if(isClient) {
            ip = new JTextField();
            ip.setBounds(20, 60, width - 40, 40);
            ip.setToolTipText("Enter your partners IP address");
            frame.add(ip);
        }

        button = new JButton(isClient ? "Connect to Server" : "Host Server");
        button.setBounds(20, 230, width - 100, 50);
        frame.add(button);

        // CLIENT
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(isClient) {
                        // Client
                    } else {
                        // Server
                    }
                } catch (Exception e) {
                    info.setText("Connection Error! Try again");
                    info.setForeground(Color.RED);
                }
            }
        });

        next = new JButton(">");
        next.setBounds(270, 230, 50, 50);
        frame.add(next);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
                new ConnectionView(!isClient);
            }
        });

        frame.setVisible(true);
    }

}
