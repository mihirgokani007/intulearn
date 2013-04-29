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

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * @author: Mihir Gokani
 * @created: ??-Jan-2013 8:16:27 AM
 */
public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialise the pager
        this.initialisePaging();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        // Set up the action bar.
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Show the Up button in the action bar.
        actionBar.setDisplayHomeAsUpEnabled(true);

        // REF: http://android-er.blogspot.in/2012/06/create-actionbar-in-tab-navigation-mode.html
        actionBar.addTab(actionBar.newTab().setText(R.string.title_fragment_description)
                .setTabListener(new CustomTabListener(this, DescriptionFragment.class.getName())));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_fragment_experiment)
                .setTabListener(new CustomTabListener(this, ExperimentFragment.class.getName())));
        actionBar.addTab(actionBar.newTab().setText(R.string.title_fragment_evaluate)
                .setTabListener(new CustomTabListener(this, EvaluateFragment.class.getName())));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomTabListener implements TabListener {
        private String mFragmentName;
        private Activity mActivity;

        public CustomTabListener(Activity activity, String fname) {
            mActivity = activity;
            mFragmentName = fname;
        }

        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            Fragment fragment = mActivity.getFragmentManager().findFragmentByTag(mFragmentName);

            if (fragment == null) {
                fragment = Fragment.instantiate(mActivity, mFragmentName);
                // REF: http://blog.stylingandroid.com/archives/1156
                ft.add(android.R.id.content, fragment, mFragmentName);
            } else ft.attach(fragment);
        }

        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            Fragment fragment = mActivity.getFragmentManager().findFragmentByTag(mFragmentName);

            if (fragment != null) ft.detach(fragment);
        }

        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {}
    }
}
