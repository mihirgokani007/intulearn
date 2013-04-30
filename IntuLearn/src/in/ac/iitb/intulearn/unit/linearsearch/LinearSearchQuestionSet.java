/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.linearsearch;

import in.ac.iitb.intulearn.unit.QuestionSet;

import java.util.ArrayList;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 7:17:33 PM
 */
public class LinearSearchQuestionSet extends QuestionSet {

    @Override
    public ArrayList<MultiChoiceQuestion> getAllQuestions() {
        return new ArrayList<QuestionSet.MultiChoiceQuestion>() {
            {
                add(new MultiChoiceQuestion("The \"running time\" of an algorithm refers to...", new String[] {
                        "How much time it takes for the program to run",
                        "The number of steps it takes to complete with respect to the size of the input",
                        "How much time it takes to write the program", }, 1));

                add(new TrueFalseQuestion(
                        "Maximum number of comparisons the linear search performs in searching a value in an array of size N is N.", true));

                add(new MultiChoiceQuestion("Linear search is the the simplest search algorithm; it is a special case of __________.",
                        new String[] { "Minimax", "Computer chess", "Brute-force search", "Eight queens puzzle" }, 2));

                add(new MultiChoiceQuestion(
                        "For linear search, the \"best case scenario\" (when the algorithm completes with the fewest number of steps) is when...",
                        new String[] { "The first item equals the key", "The middle item equals the key", "The last item equals the key",
                                "The data does not contain the key" }, 0));

                add(new MultiChoiceQuestion(
                        "For linear search, the \"worst case scenario\" (when the algorithm takes the most number of steps) is when...",
                        new String[] { "The first item equals the key", "The middle item equals the key", "The last item equals the key",
                                "The data does not contain the key" }, 3));
            }
        };
    }

}
