package views;

import game.helper.Questions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionManager {
    private static QuestionManager ourInstance = new QuestionManager();

    public static QuestionManager getInstance() {
        return ourInstance;
    }
    private Questions q = Questions.getInstance();

    private String answer = "";

    public String ask() {
        System.out.println("areweheeeeeeeeeeere");
        JFrame frame = new JFrame("Button Example");
        frame.setSize(1000, 400);

        //submit button
        JButton button=new JButton("Go!");
        button.setBounds(100,100,140, 40);

        //enter name label
        JLabel label = new JLabel();
        label.setText(q.getRandomQuestion());
        label.setBounds(10, 10, 300, 100);

        //textfield to enter name
        JTextField textfield = new JTextField();
        textfield.setBounds(180, 50, 170, 30);

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
                System.out.println(answer);
                frame.dispose();
            }
        });


        return answer;
    }

}
