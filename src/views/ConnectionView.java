package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ConnectionView {

    private JFrame frame;
    private JLabel info, info_ip;
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

        if(!isClient) {
            info_ip = new JLabel();
            String message;

            try {
                message = "Your IP: " + Inet4Address.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                message = "Can't find localhost!";
            }

            info_ip.setText(message);
            info_ip.setBounds(20, 60, width - 40, 40);
            info_ip.setFont(new Font("Arial", Font.PLAIN, 18));
            frame.add(info_ip);
        } else {
            ip = new JTextField();
            ip.setBounds(20, 60, width - 40, 40);
            ip.setToolTipText("Enter your partners IP address");
            ip.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent keyEvent) {
                    if(!ip.getText().matches("[0-9.]+")) ip.setText("");
                    button.setEnabled(ip.getText().length() > 7);
                }

                @Override
                public void keyPressed(KeyEvent keyEvent) { }

                @Override
                public void keyReleased(KeyEvent keyEvent) { }
            });
            frame.add(ip);
        }

        button = new JButton(isClient ? "Connect to Server" : "Host Server");
        button.setBounds(20, 230, width - 100, 50);
        button.setEnabled(!isClient);
        frame.add(button);

        // CLIENT
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if(isClient) {
                        // Client
                        new MainView(true, ip.getText());
                    } else {
                        // Server
                        new MainView(false, null);
                    } frame.dispose();
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
