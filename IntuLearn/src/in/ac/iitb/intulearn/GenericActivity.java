package in.ac.iitb.intulearn;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TableLayout.LayoutParams;

public class GenericActivity extends Activity {

    enum Mode {
        URL, LAYOUT
    }

    public static final String MODE_KEY = "mode_key";
    public static final String TITLE_KEY = "title_key";
    public static final String TITLE_ID_KEY = "title_id_key";
    public static final String LAYOUT_ID_KEY = "layout_id_key";
    public static final String URL_KEY = "layout_id_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(MODE_KEY)) {
            if (extras.containsKey(TITLE_ID_KEY)) setTitle(extras.getInt(TITLE_ID_KEY));
            else if (extras.containsKey(TITLE_KEY)) setTitle(extras.getString(TITLE_KEY));

            switch (Mode.valueOf(extras.get(MODE_KEY).toString())) {
                case LAYOUT:
                    setContentView(extras.getInt(LAYOUT_ID_KEY, R.layout.activity_generic));
                    break;
                case URL:
                    WebView content = new WebView(this);
                    content.loadUrl(extras.getString(URL_KEY));
                    setContentView(content, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                    break;
            }
        } else {
            setContentView(R.layout.activity_generic);
            return;
        }
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
