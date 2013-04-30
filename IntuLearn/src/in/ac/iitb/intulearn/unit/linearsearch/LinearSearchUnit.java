/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.linearsearch;

import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.QuestionSet;
import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.Category;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.SkillLevel;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 1:57:37 AM
 */
@Unit.UnitInfo(name = "Linear Search", category = Category.ALGORITHMS_SEARCHING, author = "Mihir Gokani", skillLevel = SkillLevel.BASIC)
public class LinearSearchUnit extends Unit {

    @Override
    public Class<? extends ExperimentView> getExperimentViewClass() {
        return LinearSearchExperiment.class;
    }

    @Override
    public Class<? extends QuestionSet> getQuestionSetClass() {
        return LinearSearchQuestionSet.class;
    }

    @Override
    public String getDscriptionAssetId() {
        return "LinearSearchDescription.html";
    }

}
