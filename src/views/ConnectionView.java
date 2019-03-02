package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Inet4Address;

public class ConnectionView {

    private JFrame frame;
    private JLabel label;
    private JTextField ip;
    private JButton button;
    private Inet4Address address;

    private int width = 340;
    private int height = 340;

    public ConnectionView() {
        frame = new JFrame();
        frame.setTitle("Cool Boiz");
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        label = new JLabel();
        label.setText("Connect to a partner");
        label.setBounds(20, 20, width - 40, 20);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(label);

        ip = new JTextField();
        ip.setBounds(20, 60, width - 40, 40);
        ip.setToolTipText("Enter your partners IP address");
        frame.add(ip);

        button = new JButton("Connect");
        button.setBounds(20, 230, width - 40, 50);
        frame.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Try to connect
            }
        });

        frame.setVisible(true);
    }

}
