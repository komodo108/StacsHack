package game;

import game.helper.Questions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class QuestionManager {
    private static QuestionManager ourInstance = new QuestionManager();

    public static QuestionManager getInstance() {
        return ourInstance;
    }

    private JFrame frame;
    private JLabel info, info_ip;
    private JTextField ansField;
    private JButton button, next;
    private Questions q = Questions.getInstance();
    private String answer;

    private boolean isClient;
    private int width = 340;
    private int height = 340;

    public String ask() {
        frame = new JFrame();
        frame.setTitle("Question");
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        info = new JLabel();
        info.setText(q.getRandomQuestion());
        info.setBounds(20, 20, width - 40, 20);
        info.setFont(new Font("Arial", Font.PLAIN, 18));
        frame.add(info);

        ansField = new JTextField();
        ansField.setBounds(20, 60, width - 40, 40);
        ansField.setToolTipText("Enter your ansField:");
        ansField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
                if (!ansField.getText().matches("[1-9.]+")) ansField.setText("");
                button.setEnabled(ansField.getText().length() > 7);
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
        frame.add(ansField);

        button = new JButton("Submit Answer");
        button.setBounds(20, 230, width - 100, 50);
        button.setEnabled(!isClient);
        frame.add(button);

        // CLIENT
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answer = ansField.getText();
                frame.dispose();
            }

        });

        return answer;
    }

}
