package in.ac.iitb.intulearn.unit;

import java.util.EventListener;

import android.animation.Animator;

/**
 * Implementors of this interface can add themselves as update listeners to get
 * notifications about events related to the {@link ExperimentView}.
 * Notifications indicate experiment related events, such as change in the
 * experiment steps or indicate progress of the experiment such as start or end
 * of an experiment.
 * 
 * @author Mihir Gokani
 * @created 9-Feb-2013 09:28:35 PM
 * 
 */
public interface ExperimentListener extends EventListener {

    /**
     * Notifies the change in the current step of the experiment
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     * @param oldIndex
     *            Index of current step before it is changed
     * @param newIndex
     *            Index of current step after it is changed
     */
    abstract public void onCurrentStepChanged(ExperimentView experiment, int oldIndex, int newIndex);

    /**
     * Called after an {@link Animator} is added or removed
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     * @param oldCount
     *            Total number of steps before the number of steps are changed
     * @param newCount
     *            Total number of steps after the number of steps are changed
     */
    abstract public void onStepCountChanged(ExperimentView experiment, int oldCount, int newCount);

    /**
     * Notifies the change in the progress of the experiment. Consequently, it
     * is called for each frame of the animation.
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     * @param progress
     *            Time elapsed in milliseconds
     */
    abstract public void onProgressChanged(ExperimentView experiment, long progress);

    /**
     * Notifies the change in the total progress duration of the experiment.
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     * @param totalDuration
     *            Total duration time in milliseconds
     */
    abstract public void onTotalDurationChanged(ExperimentView experiment, long totalDuration);

    /**
     * Notifies the start or resume of the animation.
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     * @param resume
     *            True if experiment is resumed from a paused state, False
     *            otherwise
     */
    abstract public void onExperimentStart(ExperimentView experiment, boolean resumed);

    /**
     * Notifies the pause of the animation
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     */
    abstract public void onExperimentPause(ExperimentView experiment);

    /**
     * Notifies the end of the animation
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     */
    abstract public void onExperimentEnd(ExperimentView experiment);

    /**
     * Notifies when an element is clicked
     * 
     * @param experiment
     *            A reference to the {@link ExperimentView} object which fired
     *            the event
     * 
     * @param element
     *            A reference to the {@link ShapeHolder} object which fired the
     *            event
     */
    abstract public void onElementClicked(ExperimentView experiment, ShapeHolder element);

    
    /**
     * This adapter class provides empty implementations of the methods from
     * {@link ExperimentListener}. Any custom listener that cares only about a
     * subset of the methods of this listener can simply subclass this adapter class
     * instead of implementing the interface directly.
     * 
     * @author Mihir Gokani
     * @created 16-Feb-2013 8:44:25 PM
     */
    public static class Adapter implements ExperimentListener {

        @Override
        public void onCurrentStepChanged(ExperimentView experiment, int oldIndex, int newIndex) {
        }

        @Override
        public void onStepCountChanged(ExperimentView experiment, int oldCount, int newCount) {
        }

        @Override
        public void onProgressChanged(ExperimentView experiment, long progress) {
        }

        @Override
        public void onTotalDurationChanged(ExperimentView experiment, long totalDuration) {
        }

        @Override
        public void onExperimentStart(ExperimentView experiment, boolean resumed) {
        }

        @Override
        public void onExperimentPause(ExperimentView experiment) {
        }

        @Override
        public void onExperimentEnd(ExperimentView experiment) {
        }

        @Override
        public void onElementClicked(ExperimentView experiment, ShapeHolder element) {
        }

    }
}
