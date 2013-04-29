/**
 ***** BEGIN GPL LICENSE BLOCK *****
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, write to:
 * 
 * the Free Software Foundation Inc. 51 Franklin Street, Fifth Floor Boston, MA
 * 02110-1301, USA
 * 
 * or go online at: http://www.gnu.org/licenses/ to view license options.
 * 
 * Original Author: Mihir Gokani (http://mihirgokani.in)
 * 
 ***** END GPL LICENCE BLOCK *****
 */
package in.ac.iitb.intulearn;

import in.ac.iitb.intulearn.unit.ExperimentListener;
import in.ac.iitb.intulearn.unit.ExperimentView;
import in.ac.iitb.intulearn.unit.Unit;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * @author:  Mihir Gokani
 * @created: 15-Feb-2013 7:14:47 AM
 */
public class ExperimentFragment extends Fragment {

    private ProgressBar mProgressBar;
    private ImageButton playButton;

    private ExperimentView experiment;

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Class<? extends Unit> unitClass = (Class<? extends Unit>) getActivity().getIntent().getExtras().get(Unit.INTENT_KEY);
        try {
            Unit unit = Unit.createInstance(unitClass);
            Class<? extends ExperimentView> experimentClass = unit.getExperimentViewClass();
            Constructor<? extends ExperimentView> constructor = experimentClass.getConstructor(Context.class);
            experiment = constructor.newInstance(container.getContext());
        } catch (java.lang.InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (NoSuchMethodException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }

        experiment.createAnimation();
        experiment.events.addExperimentListener(new ExperimentListenerAdapterImpl());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_experiment, container, false);

        HorizontalScrollView content = (HorizontalScrollView) layout.findViewById(R.id.content);
        content.addView(experiment, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        mProgressBar = (ProgressBar) layout.findViewById(R.id.progress);

        playButton = (ImageButton) layout.findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            private boolean alreadyStarted = false;

            @Override
            public void onClick(View v) {
                if (experiment.isAnimationRunning()) {
                    experiment.pauseAnimation();
                } else {
                    if (alreadyStarted || true) {
                        experiment.initExperiment();
                        experiment.createAnimation();
                    }
                    mProgressBar.setMax(experiment.getStepsCount());
                    experiment.playAnimation();
                    alreadyStarted = true;
                }
            }
        });

        return layout;
    }

    private class ExperimentListenerAdapterImpl extends ExperimentListener.Adapter {
        @Override
        public void onExperimentStart(ExperimentView experiment, boolean resume) {

            playButton.setImageResource(R.drawable.ic_light_av_pause);
        }

        @Override
        public void onExperimentPause(ExperimentView experiment) {

            playButton.setImageResource(R.drawable.ic_light_av_replay);
        }

        @Override
        public void onExperimentEnd(ExperimentView experiment) {

            onExperimentPause(experiment); // Or, move to evaluate! Or, to the
            // next experiment!
        }

        @Override
        public void onCurrentStepChanged(ExperimentView experiment, int oldStep, int newStep) {

            mProgressBar.setProgress(newStep);
            mProgressBar.setSecondaryProgress(newStep + 1);
        }
    }
}
