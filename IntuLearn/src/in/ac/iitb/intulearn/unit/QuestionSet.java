/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit;

import java.util.ArrayList;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 7:19:41 PM
 */
public abstract class QuestionSet {

    // TODO: Add difficultyLevel and answerExplanation.   
    public enum DifficultyLevel {
        EASY, NORMAL, DIFFICULT, VERY_DIFFICULT
    }

    public enum AnswerType {
        UNANSWERED, RIGHT, WRONG
    }

    public static class MultiChoiceQuestion {
        private final String questionText;
        private final String[] answerChoices;
        private final int[] correctAnswers;

        public MultiChoiceQuestion(String questionText, String[] answerChoices, int[] correctAnswers) {
            this.questionText = questionText;
            this.answerChoices = answerChoices;
            this.correctAnswers = correctAnswers;
        }

        public MultiChoiceQuestion(String questionText, String[] answerChoices, int correctAnswer) {
            this(questionText, answerChoices, new int[] { correctAnswer });
        }

        public String getQuestionText() {
            return questionText;
        }

        public String[] getAnswerChoices() {
            return answerChoices;
        }

        public int[] getCorrectAnswers() {
            return correctAnswers;
        }
    }

    public static class TrueFalseQuestion extends MultiChoiceQuestion {
        public TrueFalseQuestion(String questionText, boolean correctAnswer) {
            super(questionText, new String[] { "True", "False" }, correctAnswer ? 0 : 1);
        }
    }

    public abstract ArrayList<MultiChoiceQuestion> getAllQuestions();
}
