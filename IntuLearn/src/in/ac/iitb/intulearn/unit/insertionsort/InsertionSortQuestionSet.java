/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.insertionsort;

import in.ac.iitb.intulearn.unit.QuestionSet;

import java.util.ArrayList;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 7:17:33 PM
 */
public class InsertionSortQuestionSet extends QuestionSet {

    @Override
    public ArrayList<MultiChoiceQuestion> getAllQuestions() {
        return new ArrayList<QuestionSet.MultiChoiceQuestion>() {
            {

                add(new MultiChoiceQuestion(
                        "Insertion Sort is a simple __________, a comparison sort in which the sorted array (or list) is built one entry at a time.", 
                        new String[] {
                                "Sorting algorithm",
                                "Searching algorithm",
                                "Cartesian tree",
                                "Euclidean algorithm"}, 0));

                add(new MultiChoiceQuestion(
                        "Which of the following is TRUE with respect to Insertion Sort?",
                        new String[] {
                                "It inserts the Fibonacci sequence into arrays",
                                "It selects all the elements in an array out of order and sorts in one step",
                                "It is a comparison sort which removes elements out of order and inserts into the correct position",
                        "None of the above" }, 1));
                
                add(new MultiChoiceQuestion(
                        "What operation does the Insertion Sort use to move numbers from the unsorted section to the sorted section of the list?",
                        new String[] {
                                "Finding the minimum value",
                                "Swapping",
                                "Finding out an pivot value",
                                "None of the above" }, 1));

                add(new MultiChoiceQuestion(
                        "How many loops are there usually in an simple Insertion Sort?",
                        new String[] {
                                "One",
                                "Two",
                                "Three",
                                "Four"}, 2));
                
                add(new TrueFalseQuestion("Insertion Sort can only be made to sort elements into ascending order.", false));
            }
        };
    }

}
