package com.elearning.service;

import com.elearning.model.CourseModule;
import com.elearning.model.Question;
import com.elearning.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizService {
    private List<Quiz> quizzes = new ArrayList<>();
    // stocare si questions pentru cautare ulterioara dupa id ??
    private static int nextQuizId;
    private static int nextQuestionId;

    public Quiz createQuiz(String title, CourseModule module) {
        Quiz quiz = new Quiz(++nextQuizId, title);
        module.addQuiz(quiz);
        quizzes.add(quiz);
        System.out.println("Quiz created: " + title + " in module " + module.getTitle());
        return quiz;
    }

    public void addQuestion(Quiz quiz, String text, String correctAnswer, List<String> options) {
        Question question = new Question(++nextQuestionId, text, correctAnswer, options);
        quiz.addQuestion(question);
        System.out.println("Question added to quiz: " + quiz.getTitle());
    }

    public double completeQuiz(Quiz quiz, List<String> answers) {
        List<Question> questions = quiz.getQuestions();

        if (answers.size() != questions.size()) {
            return 0;
        }

        int right = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).check(answers.get(i))) {
                right++;
            }
        }

        double score = (double) right / questions.size() * 100;
        System.out.println("Score: " + right + "/" + questions.size() + " (" + score + "%)");
        return score;
    }

    public void listQuestions(Quiz quiz) {
        System.out.println("Quiz: " + quiz.getTitle());
        for (Question q : quiz.getQuestions()) {
            System.out.println(" - " + q.getTitle());
            for (String o : q.getOptions()) {
                System.out.println("    * " + o);
            }
        }
    }
}
