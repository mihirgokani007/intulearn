/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.binarysearch;

import in.ac.iitb.intulearn.unit.QuestionSet;

import java.util.ArrayList;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 7:17:33 PM
 */
public class BinarySearchQuestionSet extends QuestionSet {

    @Override
    public ArrayList<MultiChoiceQuestion> getAllQuestions() {
        return new ArrayList<QuestionSet.MultiChoiceQuestion>() {
            {

                add(new MultiChoiceQuestion(
                        "Which of the following is FALSE?",
                        new String[] {
                                "Binary search and linear search have the same best-case performance",
                                "Binary search and linear search have the same worst-case performance",
                                "Linear search requires no special constraint on the array",
                                "Binary search requires the array is sorted" }, 1));

                add(new TrueFalseQuestion("The binary search does not work unless the array is sorted.", true));

                add(new TrueFalseQuestion("The binary search terminates when the variables low becomes equal to high.", false));

                add(new MultiChoiceQuestion(
                        "For binary search, the \"best case scenario\" (when the algorithm completes with the fewest number of steps) is when...",
                        new String[] { 
                                "The first item equals the key", 
                                "The middle item equals the key", 
                                "The last item equals the key",
                                "The data does not contain the key" }, 1));

                add(new MultiChoiceQuestion(
                        "For binary search, the \"worst case scenario\" (when the algorithm takes the most number of steps) is when...",
                        new String[] { 
                                "The first item equals the key", 
                                "The middle item equals the key", 
                                "The last item equals the key",
                                "The data does not contain the key" }, 3));
            }
        };
    }

}
