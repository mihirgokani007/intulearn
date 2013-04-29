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

import in.ac.iitb.intulearn.unit.QuestionSet;
import in.ac.iitb.intulearn.unit.Unit;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * @author: Mihir Gokani
 * @created: 13-Feb-2013 11:46:22 PM
 */
public class EvaluateFragment extends Fragment implements OnClickListener {

    private static final long MAX_PAUSE_DURATION = 10 * 60 * 1000;

    private Chronometer timer;
    private View overlay;
    private TextView text1, text2;
    private ViewFlipper viewFlipper;
    private Button btnStart, btnNext, btnPrev, btnStop;

    private long pauseTime;

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        Class<? extends Unit> unitClass = (Class<? extends Unit>) getActivity().getIntent().getExtras().get(Unit.INTENT_KEY);
        QuestionSet questionSet;
        try {
            Unit unit = Unit.createInstance(unitClass);
            Class<? extends QuestionSet> questionSetClass = unit.getQuestionSetClass();
            questionSet = questionSetClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_evaluate, container, false);

        viewFlipper = (ViewFlipper) layout.findViewById(R.id.questionSet);
        viewFlipper.removeAllViews();
        fillQuestions(viewFlipper, questionSet);

        overlay = layout.findViewById(R.id.overlay);
        text1 = (TextView) overlay.findViewById(android.R.id.text1);
        text2 = (TextView) overlay.findViewById(android.R.id.text2);
        setOverlayText("Evaluate Yourself!", "A timer will count the time once you start the quiz. You can pause at any time.");

        timer = (Chronometer) layout.findViewById(R.id.timer);

        btnStart = (Button) layout.findViewById(R.id.start);
        btnNext = (Button) layout.findViewById(R.id.next);
        btnPrev = (Button) layout.findViewById(R.id.previous);

        btnNext.setEnabled(false);
        btnPrev.setEnabled(false);

        btnStart.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);

        btnStop = (Button) overlay.findViewById(R.id.stop);
        btnStop.setOnClickListener(this);

        return layout;
    }

    private void fillQuestions(ViewFlipper viewFlipper, QuestionSet questionSet) {

        for (QuestionSet.MultiChoiceQuestion question : questionSet.getAllQuestions()) {
            View questionView = createQuestionView(question);
            viewFlipper.addView(questionView);
        }
    }

    private View createQuestionView(QuestionSet.MultiChoiceQuestion question) {
        Context context = getActivity();
        
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView questionText = new TextView(context);
        questionText.setText(question.getQuestionText());
        questionText.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Medium);
        layout.addView(questionText, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        RadioGroup radioGroup = new RadioGroup(context);
        RadioButton choice;

        for (String answerChoice : question.getAnswerChoices()) {
            choice = new RadioButton(context);
            choice.setText(answerChoice);
            radioGroup.addView(choice, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        choice = new RadioButton(context);
        choice.setText("Skip");
        radioGroup.addView(choice, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        radioGroup.setTag(R.id.correct_answers, radioGroup.getChildAt(question.getCorrectAnswers()[0]).getId());
        radioGroup.setTag(R.id.skip_radio_id, choice.getId());
        radioGroup.check(choice.getId());

        layout.addView(radioGroup, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        ScrollView scrollView = new ScrollView(context);
        scrollView.addView(layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        scrollView.setTag(radioGroup);
        return scrollView;
    }

    private boolean canResume() {
        return SystemClock.elapsedRealtime() - pauseTime < MAX_PAUSE_DURATION;
    }

    private void setOverlayText(String t1, String t2) {
        text1.setText(t1);
        text2.setText(t2);
    }

    public void start(boolean resume) {
        overlay.setVisibility(View.GONE);
        btnStart.setText(R.string.str_pause);
        fixEnabledStates();

        if (resume && canResume()) {
            timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - pauseTime));
        } else {
            timer.setBase(SystemClock.elapsedRealtime());
            pauseTime = 0;
        }
        timer.start();
    }

    public void pause() {
        timer.stop();
        pauseTime = SystemClock.elapsedRealtime();

        setOverlayText("Paused", timer.getText().toString());

        btnStart.setText(R.string.str_resume);
        btnStop.setVisibility(View.VISIBLE);

        btnNext.setEnabled(false);
        btnPrev.setEnabled(false);

        overlay.setVisibility(View.VISIBLE);
    }

    public void stop() {
        timer.stop();
        final int totalQuestions = viewFlipper.getChildCount();

        int attemptedCount = totalQuestions, correctCount = 0;
        for (int i = 0; i < totalQuestions; i++) {
            View viewFlipperChild = viewFlipper.getChildAt(i);
            RadioGroup radioGroup = (RadioGroup) viewFlipperChild.getTag();
            int checkedId = radioGroup.getCheckedRadioButtonId();
            if (radioGroup.getTag(R.id.skip_radio_id).equals(checkedId)) attemptedCount--;
            if (radioGroup.getTag(R.id.correct_answers).equals(checkedId)) correctCount++;
            radioGroup.check((Integer) radioGroup.getTag(R.id.skip_radio_id));
        }

        setOverlayText("Ended",
                String.format("%s\nTotal Questions: %d\nAttempted: %d\nCorrect: %d", timer.getText(), totalQuestions, attemptedCount, correctCount));

        btnStart.setText(R.string.str_start);
        btnStop.setVisibility(View.GONE);

        btnNext.setEnabled(false);
        btnPrev.setEnabled(false);

        overlay.setVisibility(View.VISIBLE);
        viewFlipper.setDisplayedChild(0);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnStart)) {
            if (overlay.getVisibility() == View.VISIBLE) start(true);
            else pause();

        } else if (v.equals(btnStop) || (v.equals(btnNext) && btnNext.getText().equals(getString(R.string.str_stop)))) {
            stop();

        } else {
            if (v.equals(btnPrev)) viewFlipper.showPrevious();
            else viewFlipper.showNext();

            fixEnabledStates();
        }
    }

    private void fixEnabledStates() {
        btnPrev.setEnabled(viewFlipper.getDisplayedChild() > 0);
        btnNext.setEnabled(viewFlipper.getDisplayedChild() < viewFlipper.getChildCount());

        if (viewFlipper.getDisplayedChild() == viewFlipper.getChildCount() - 1) {
            btnNext.setText(R.string.str_stop);
        } else {
            btnNext.setText(R.string.str_next);
        }
    }

}
