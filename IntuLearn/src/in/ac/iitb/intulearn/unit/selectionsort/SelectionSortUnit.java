/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.selectionsort;

import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.QuestionSet;
import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.Category;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.SkillLevel;

/**
 * @author Mihir Gokani
 * @created 16-Mar-2013 11:57:37 PM
 */
@Unit.UnitInfo(name = "Selection Sort", category = Category.ALGORITHMS_SORTING, author = "Snehalesh Mahale", skillLevel = SkillLevel.MODERATE)
public class SelectionSortUnit extends Unit {

    @Override
    public Class<? extends ExperimentView> getExperimentViewClass() {
        return SelectionSortExperiment.class;
    }

    @Override
    public Class<? extends QuestionSet> getQuestionSetClass() {
        return SelectionSortQuestionSet.class;
    }

    @Override
    public String getDscriptionAssetId() {
        return "SelectionSortDescription.html";
    }

}
