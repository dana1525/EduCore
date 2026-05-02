package com.elearning.service;
import com.elearning.model.Question;
import com.elearning.model.Quiz;
import com.elearning.model.QuizResult;
import com.elearning.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizResultService {
    private Map<Integer, List<QuizResult>> resultsByStudent = new HashMap<>(); // cheia - student id, valoarea - lista de rezultate
    private static int nextResultId;

    public QuizResult completeQuiz(Quiz quiz, Student student, List<String> answers) {
        List<Question> questions = quiz.getQuestions();
        if (answers.size() != questions.size()) {
            throw new IllegalArgumentException("Wrong number of answers.");
        }

        int right = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (questions.get(i).check(answers.get(i))) {
                right++;
            }
        }

        double score = (double) right / questions.size() * 100;
        QuizResult result = new QuizResult(++nextResultId, student, quiz, score);
        // daca studentul nu are inca nicio intrare in map, cream automat lista noua pentru el si adaugam rezultatul
        if (!resultsByStudent.containsKey(student.getId())) {
            resultsByStudent.put(student.getId(), new ArrayList<>());
        }
        resultsByStudent.get(student.getId()).add(result);
        System.out.println(student.getName() + " scored: " + right + "/" + questions.size() + " (" + score + "%)");
        return result;
    }

    public double getBestScore(Student student, Quiz quiz) {
        List<QuizResult> studentResults = resultsByStudent.get(student.getId());
        if (studentResults == null) {
            return 0;
        }
        double best = 0;
        for (QuizResult r : studentResults) {
            if (r.getQuiz().getId() == quiz.getId() && r.getScore() > best) {
                best = r.getScore();
            }
        }
        return best;
    }
}
