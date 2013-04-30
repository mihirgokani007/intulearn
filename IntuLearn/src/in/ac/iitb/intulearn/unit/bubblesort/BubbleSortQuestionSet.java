/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.bubblesort;

import in.ac.iitb.intulearn.unit.QuestionSet;

import java.util.ArrayList;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 7:17:33 PM
 */
public class BubbleSortQuestionSet extends QuestionSet {

    @Override
    public ArrayList<MultiChoiceQuestion> getAllQuestions() {
        return new ArrayList<QuestionSet.MultiChoiceQuestion>() {
            {

                add(new MultiChoiceQuestion("Worst case complexity of Bubble sort is", new String[] { "O(n)", "O(n^2)", "O(n^3)", "O(log n)" }, 1));

                add(new MultiChoiceQuestion("Which of the following is TRUE with respect to Bubble Sort?", new String[] { "Adjacent elements are NOT swapped",
                        "Only adjacent elements are swapped", "Only terminal elements are swapped", "Elements are NOT swapped" }, 1));

                add(new MultiChoiceQuestion("Which of the following is true about bubble sort when compared to Insertion Sort?", new String[] {
                        "Bubble sort requires at least twice as many writes as insertion sort and twice as many cache misses",
                        "Bubble sort requires at half as many writes as insertion sort and half as many cache misses" }, 0));
            }
        };
    }

}
