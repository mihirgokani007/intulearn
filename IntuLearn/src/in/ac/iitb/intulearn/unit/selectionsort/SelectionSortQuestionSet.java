/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.selectionsort;

import in.ac.iitb.intulearn.unit.QuestionSet;

import java.util.ArrayList;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 7:17:33 PM
 */
public class SelectionSortQuestionSet extends QuestionSet {

    @Override
    public ArrayList<MultiChoiceQuestion> getAllQuestions() {
        return new ArrayList<QuestionSet.MultiChoiceQuestion>() {
            {

                add(new MultiChoiceQuestion(
                        "Worst case complexity of Selection sort is",
                        new String[] {
                                "O(n)",
                                "O(n^2)",
                                "O(n^3)",
                                "O(log n)"}, 1));

                add(new MultiChoiceQuestion(
                        "The Selection sort algorithm proceeds by",
                        new String[] {
                                "By finding the smallest element in the unsorted sublist",
                                "By finding the largest element in the unsorted sublist",
                                "Both of the above",
                                "None of the above" }, 3));

                add(new MultiChoiceQuestion(
                        "Selection Sort is",
                        new String[] {
                                "a Divide and Conquer Algorithm",
                                "Not an Online Algorithm",
                                "Greedy Algorithm",
                                "None of the above" }, 1));

                add(new MultiChoiceQuestion(
                        "Let S and I be the number of comparisons performed by Selection and Insertion sort respectively, then which of the following is always TRUE?",
                        new String[] {
                                "S >= I",
                                "S == I",
                                "S <= I"}, 2));

            }
        };
    }

}
