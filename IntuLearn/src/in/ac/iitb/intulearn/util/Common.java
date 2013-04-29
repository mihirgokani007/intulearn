package in.ac.iitb.intulearn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.View;

public abstract class Common {

    private static final String KEY_FIRST_RUN = "firstRun";

    /**
     * Check if this is the first run
     * 
     * @return returns true, if this is the first run
     */
    public boolean getFirstRun(SharedPreferences mPreferences) {
        return mPreferences.getBoolean(KEY_FIRST_RUN, true);
    }

    /**
     * Store the first run
     */
    public void setFirstRunCompleted(SharedPreferences mPreferences) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(KEY_FIRST_RUN, false);
        editor.commit();
    }

    final static public class ViewInvalidator implements ValueAnimator.AnimatorUpdateListener {

        private View view;

        public ViewInvalidator(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            view.invalidate();
        }
    }

    /**
     * REF: http://stackoverflow.com/a/2683771/155813
     */
    static public class DensityManager {

        private static DensityManager instance = null;
        private DisplayMetrics displayMetrics;

        private DensityManager(Context context) {
            displayMetrics = context.getResources().getDisplayMetrics();
        }

        static public DensityManager getInstance(Context context) {
            if (instance == null) {
                instance = new DensityManager(context);
            }
            return instance;
        }

        public DisplayMetrics getDisplayMetrics() {
            return displayMetrics;
        }

        public int DIPToPixels(float dip) {
            return (int) (0.5 + (dip * displayMetrics.density));
        }

        public int pixelsToDIP(float pixels) {
            return (int) (0.5 + (pixels / displayMetrics.density));
        }

        public int SPToPixels(float sp) {
            return (int) (0.5 + (sp * displayMetrics.scaledDensity));
        }

        public int pixelsToSP(float pixels) {
            return (int) (0.5 + (pixels / displayMetrics.scaledDensity));
        }
    }

    // final static public class Constants {
    // final static public String TAG_EXPERIMENT = "Experiment";
    // final static public String TAG_EVALUATE = "Evaluate";
    // }

    final static public String convertStreamToString(InputStream in) throws IOException {

        StringWriter writer = new StringWriter();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        char[] buffer = new char[in.available()];

        reader.read(buffer);
        writer.write(buffer);

        return writer.toString();
    }
}
