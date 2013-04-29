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

import in.ac.iitb.intulearn.unit.Unit;
import in.ac.iitb.intulearn.util.Common;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * @author: Mihir Gokani
 * @created: 14-Feb-2013 6:16:12 PM
 */
public class DescriptionFragment extends Fragment {

    private static final String ASSET_DIRECTORY = "descriptions";
    /* Note: Must specify trailing slash, REF: http://goo.gl/A7vSb */
    private static final String BASE_URL = Uri.fromFile(new File("android_asset", ASSET_DIRECTORY)).toString() + "/";

    private static final String SYNTAX_HIGHLIGHTER_CSS = "files/prism.css";
    private static final String DEFAULT_CSS = "files/default.css";
    private static final String JQUERY_JS = "files/jquery-latest.min.js";
    private static final String SYNTAX_HIGHLIGHTER_JS = "files/prism.js";
    private static final String DEFAULT_JS = "files/default.js";
    //@format:off
    private static final String TEMPLATE = "<html>\n<head>\n<title>%s</title>\n" +
		"<link rel='stylesheet' type='text/css' href='" + SYNTAX_HIGHLIGHTER_CSS + "' />\n" +
		"<link rel='stylesheet' type='text/css' href='" + DEFAULT_CSS + "' />\n" +
		"<script type='text/javascript' src='" + JQUERY_JS + "'></script>\n" +
		"<script type='text/javascript' src='" + SYNTAX_HIGHLIGHTER_JS + "'></script>\n" +
		"<script type='text/javascript' src='" + DEFAULT_JS + "'></script>\n" +
		"</head>\n\n<body>\n%s\n</body>\n</html>";
    //@format:on

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        
        WebView v = new WebView(inflater.getContext());
        v.getSettings().setJavaScriptEnabled(true);
        
        Class<? extends Unit> unitClass = (Class<? extends Unit>) getActivity().getIntent().getExtras().get(Unit.INTENT_KEY);
        String title = unitClass.getAnnotation(Unit.UnitInfo.class).name();
        String content;
        
        String errorContentUnavailable = getResources().getString(R.string.error_content_unavailable);
        
        try {
            Unit unit = Unit.createInstance(unitClass);
            String resource_path = new File(ASSET_DIRECTORY, unit.getDscriptionAssetId()).toString();
            content = Common.convertStreamToString(getActivity().getAssets().open(resource_path));
        } catch (java.lang.InstantiationException e) {
            content = errorContentUnavailable;
        } catch (IllegalAccessException e) {
            content = errorContentUnavailable;
        } catch (IOException e) {
            content = errorContentUnavailable;
        }

        v.loadDataWithBaseURL(BASE_URL, String.format(TEMPLATE, title, content), "text/html", null, null);
        return v;
    }
}
