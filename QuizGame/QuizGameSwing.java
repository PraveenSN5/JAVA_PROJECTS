import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class QuizGameSwing {
    
    static class Question {
        String questionText;
        String[] options;
        int correctAnswer;
        
        public Question(String text, String[] choices, int correct) {
            questionText = text;
            options = choices;
            correctAnswer = correct;
        }
    }
    
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    
    private JFrame frame;
    private JLabel questionLabel;
    private JLabel scoreLabel;
    private JLabel feedbackLabel;
    private ButtonGroup optionsGroup;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    
    public QuizGameSwing() {
        initializeQuestions();
        createGUI();
        showQuestion(questions.get(currentQuestionIndex));
    }
    
    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question(
            "What is the capital of France?",
            new String[]{"London", "Paris", "Berlin", "Madrid"},
            1 // Paris is index 1
        ));
        
        questions.add(new Question(
            "Which of these is not a Java keyword?",
            new String[]{"class", "interface", "method", "public"},
            2 // method is not a keyword
        ));
        
        questions.add(new Question(
            "What is the result of 5 + 7 * 3?",
            new String[]{"36", "26", "22", "16"},
            1 // 7*3=21 +5=26
        ));
    }
    
    private void createGUI() {
        frame = new JFrame("Quiz Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        
        // Question panel
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionPanel.add(questionLabel);
        questionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Options panel
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        
        questionPanel.add(optionsPanel);
        questionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Submit button
        submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitListener());
        questionPanel.add(submitButton);
        
        // Status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: 0/" + questions.size());
        feedbackLabel = new JLabel(" ");
        statusPanel.add(scoreLabel, BorderLayout.WEST);
        statusPanel.add(feedbackLabel, BorderLayout.EAST);
        
        frame.add(questionPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    private void showQuestion(Question question) {
        questionLabel.setText("Question " + (currentQuestionIndex + 1) + ": " + question.questionText);
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(question.options[i]);
            optionButtons[i].setSelected(false);
        }
        
        feedbackLabel.setText(" ");
    }
    
    private class SubmitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = -1;
            for (int i = 0; i < 4; i++) {
                if (optionButtons[i].isSelected()) {
                    selectedIndex = i;
                    break;
                }
            }
            
            if (selectedIndex == -1) {
                feedbackLabel.setText("Please select an answer!");
                return;
            }
            
            Question currentQuestion = questions.get(currentQuestionIndex);
            if (selectedIndex == currentQuestion.correctAnswer) {
                score++;
                feedbackLabel.setText("Correct!");
            } else {
                feedbackLabel.setText("Incorrect! Correct answer was: " + 
                    currentQuestion.options[currentQuestion.correctAnswer]);
            }
            
            scoreLabel.setText("Score: " + score + "/" + questions.size());
            
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                showQuestion(questions.get(currentQuestionIndex));
            } else {
                JOptionPane.showMessageDialog(frame,
                    "Quiz completed!\nYour final score: " + score + "/" + questions.size(),
                    "Results",
                    JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizGameSwing());
    }
}