package game.helper;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Questions {
    private static Questions ourInstance = new Questions();
    public static Questions getInstance() {
        return ourInstance;
    }

    private File question_file = new File("../res/questions.txt");
    private ArrayList<String> questions = new ArrayList<>();
    private ArrayList seen = new ArrayList();

    private Questions() {
        try {
            BufferedReader read = new BufferedReader(new FileReader(question_file));
            String line;

            while((line = read.readLine()) != null) {
                questions.add(line);
            } read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getRandomQuestion() {
        Random random = new Random();
        int index = random.nextInt(questions.size());
        int asked = 0;

        while(!seen.contains(index) && asked < questions.size() / 2) {
            index = random.nextInt(questions.size());
        } seen.add(index);
        return questions.get(index);
    }
}
