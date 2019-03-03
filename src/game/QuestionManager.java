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

    private String answer = "";

    public String ask() {
        System.out.println("areweheeeeeeeeeeere");
        Questions q = Questions.getInstance();
        JFrame frame = new JFrame("Button Example");

        //submit button
        JButton button=new JButton("Submit");
        button.setBounds(100,100,140, 40);

        //enter name label
        JLabel label = new JLabel();
        label.setText(q.getRandomQuestion());
        label.setBounds(10, 10, 100, 100);

        //textfield to enter name
        JTextField textfield= new JTextField();
        textfield.setBounds(110, 50, 130, 30);

        //add to frame
        frame.add(textfield);
        frame.add(label);
        frame.add(button);
        frame.setSize(300,300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //action listener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                answer = textfield.getText();
                frame.dispose();
            }
        });

        System.out.println(answer);

        return answer;
    }

}
