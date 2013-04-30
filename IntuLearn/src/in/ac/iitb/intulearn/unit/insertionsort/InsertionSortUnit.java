/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.insertionsort;

import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.QuestionSet;
import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.Category;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.SkillLevel;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 1:57:37 AM
 */
@Unit.UnitInfo(name = "Insertion Sort", category = Category.ALGORITHMS_SORTING, author = "Mihir Gokani", skillLevel = SkillLevel.BASIC)
public class InsertionSortUnit extends Unit {

    @Override
    public Class<? extends ExperimentView> getExperimentViewClass() {
        return InsertionSortExperiment.class;
    }

    @Override
    public Class<? extends QuestionSet> getQuestionSetClass() {
        return InsertionSortQuestionSet.class;
    }

    @Override
    public String getDscriptionAssetId() {
        return "InsertionSortDescription.html";
    }

}
