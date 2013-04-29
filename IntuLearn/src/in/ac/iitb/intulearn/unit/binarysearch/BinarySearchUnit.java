/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit.binarysearch;

import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.QuestionSet;
import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.Category;
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.SkillLevel;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 1:57:37 AM
 */
@Unit.UnitInfo(name = "Binary Search", category = Category.ALGORITHMS_SEARCHING, author = "Mihir Gokani", skillLevel = SkillLevel.MODERATE)
public class BinarySearchUnit extends Unit {

    @Override
    public Class<? extends ExperimentView> getExperimentViewClass() {
        return BinarySearchExperiment.class;
    }

    @Override
    public Class<? extends QuestionSet> getQuestionSetClass() {
        return BinarySearchQuestionSet.class;
    }

    @Override
    public String getDscriptionAssetId() {
        return "BinarySearchDescription.html";
    }

}
