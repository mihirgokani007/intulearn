/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.bubblesort;

import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.QuestionSet;
import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.Category;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.SkillLevel;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 1:57:37 AM
 */
@Unit.UnitInfo(name = "Bubble Sort", category = Category.ALGORITHMS_SORTING, author = "Pushpak Burange", skillLevel = SkillLevel.BASIC)
public class BubbleSortUnit extends Unit {

    @Override
    public Class<? extends ExperimentView> getExperimentViewClass() {
        return BubbleSortExperiment.class;
    }

    @Override
    public Class<? extends QuestionSet> getQuestionSetClass() {
        return BubbleSortQuestionSet.class;
    }

    @Override
    public String getDscriptionAssetId() {
        return "BubbleSortDescription.html";
    }

}
