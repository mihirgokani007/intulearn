/**
 * @author:  Mihir Gokani
 * @created: 10-Mar-2013
 */
package in.ac.iitb.intulearn.unit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.text.TextUtils;

/**
 * @author Mihir Gokani
 * @created 10-Mar-2013 1:20:48 AM
 */

public abstract class Unit {

    public static final String INTENT_KEY = "Unit";

    public static final int[] INITIAL_VALUES_ARRAY = new int[] { 30, 70, 20, 50, 60, 40, 10, 90, 80 };
    public static final List<Integer> INITIAL_VALUES_LIST = Collections.unmodifiableList(Arrays.asList(30, 70, 20, 50, 60, 40, 10, 90, 80));

    abstract public Class<? extends ExperimentView> getExperimentViewClass();

    abstract public Class<? extends QuestionSet> getQuestionSetClass();

    abstract public String getDscriptionAssetId();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface UnitInfo {

        public enum SkillLevel {
            BASIC, MODERATE, ADVANCED;

            private String description;

            private SkillLevel() {
                this("");
            }

            private SkillLevel(String description) {
                this.description = description;
            }

            @Override
            public String toString() {
                return description == null || description.isEmpty() ? super.toString() : description;
            }
        }

        public enum Category {
            UNDEFINED("Uncategorized"), ALGORITHMS_SEARCHING("Searching Algorithms"), ALGORITHMS_SORTING("Sorting Algorithms"), PROGRAMMING(
                    "General Programming");

            private String description;

            private Category() {
                this("");
            }

            private Category(String description) {
                this.description = description;
            }

            @Override
            public String toString() {
                return description == null || description.isEmpty() ? super.toString() : description;
            }
        }

        abstract public String name();

        abstract public Category category() default Category.UNDEFINED;

        abstract public SkillLevel skillLevel() default SkillLevel.MODERATE;

        abstract public String author() default "Mihir Gokani";

        abstract public long timestamp() default -1;

        abstract public String[] keywords() default { };
    }

    public final static <T extends Unit> T createInstance(Class<T> unitClass) throws InstantiationException, IllegalAccessException {
        return unitClass.newInstance();
    }

    public static Map<String, Object> getInfoMap(Class<? extends Unit> unitClass) {
        return getInfoMap(unitClass, null, null);
    }

    /**
     * NOTE: Either or both of skillLevelImageIds and categoryImageIds can be
     * null. If they are provided, they should not be used to filter data (Since
     * they are Integer values and not String).
     */
    public static Map<String, Object> getInfoMap(Class<? extends Unit> unitClass, Map<UnitInfo.SkillLevel, Integer> skillLevelImageIds,
            Map<UnitInfo.Category, Integer> categoryImageIds) {
        Object name, category, skillLevel, author, keywords;
        if (unitClass == null) {
            name = category = skillLevel = author = keywords = null;
        } else {
            UnitInfo unitMetadata = unitClass.getAnnotation(UnitInfo.class);
            UnitInfo.Category c = unitMetadata.category();
            UnitInfo.SkillLevel s = unitMetadata.skillLevel();

            name = unitMetadata.name();
            category = categoryImageIds != null && categoryImageIds.containsKey(c) ? categoryImageIds.get(c) : c.toString();
            skillLevel = skillLevelImageIds != null && skillLevelImageIds.containsKey(s) ? skillLevelImageIds.get(s) : s.toString();
            author = unitMetadata.author();
            keywords = TextUtils.join(", ", unitMetadata.keywords());
        }

        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("this", unitClass);
        map.put("name", name);
        map.put("category", category);
        map.put("skillLevel", skillLevel);
        map.put("author", author);
        map.put("keywords", keywords);
        return Collections.unmodifiableMap(map);
    }
}
