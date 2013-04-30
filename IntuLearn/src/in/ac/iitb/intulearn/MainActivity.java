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
import in.ac.iitb.intulearn.unit.Unit.UnitInfo.SkillLevel;
import in.ac.iitb.intulearn.unit.binarysearch.BinarySearchUnit;
import in.ac.iitb.intulearn.unit.bubblesort.BubbleSortUnit;
import in.ac.iitb.intulearn.unit.insertionsort.InsertionSortUnit;
import in.ac.iitb.intulearn.unit.linearsearch.LinearSearchUnit;
import in.ac.iitb.intulearn.unit.selectionsort.SelectionSortUnit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

/**
 * @author: Mihir Gokani
 * @created: ??-Jan-2013 6:56:47 AM
 */
public class MainActivity extends ListActivity implements SearchView.OnQueryTextListener {

    private ListView mListView;
    private SearchView mSearchView;

    private ArrayList<Map<String, Object>> allItems = new ArrayList<Map<String, Object>>();
    private SimpleAdapter adapter;

    public MainActivity() {
        final Map<SkillLevel, Integer> skillLevelImageIds = new HashMap<SkillLevel, Integer>();
        skillLevelImageIds.put(SkillLevel.BASIC, R.drawable.ic_atom_1);
        skillLevelImageIds.put(SkillLevel.MODERATE, R.drawable.ic_atom_2);
        skillLevelImageIds.put(SkillLevel.ADVANCED, R.drawable.ic_atom_3);

        final Map<Unit.UnitInfo.Category, Integer> categoryImageIds = null;

        allItems.add(Unit.getInfoMap(LinearSearchUnit.class, skillLevelImageIds, categoryImageIds));
        allItems.add(Unit.getInfoMap(BinarySearchUnit.class, skillLevelImageIds, categoryImageIds));
        allItems.add(Unit.getInfoMap(BubbleSortUnit.class, skillLevelImageIds, categoryImageIds));
        allItems.add(Unit.getInfoMap(InsertionSortUnit.class, skillLevelImageIds, categoryImageIds));
        allItems.add(Unit.getInfoMap(SelectionSortUnit.class, skillLevelImageIds, categoryImageIds));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListView = getListView();

        final String[] from = { "name", "author", "skillLevel" };
        final int[] to = { android.R.id.title, android.R.id.text1, android.R.id.icon };
        final String[] filterable = { "name", "author", "category" };

        adapter = new UnitSimpleAdapter(this, allItems, R.layout.listitem_main, from, to, filterable);

        mListView.setAdapter(adapter);
        mListView.setTextFilterEnabled(true);

        // Show menu on top-right corner even when menu key is pressed
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem menuItemSearch = menu.findItem(R.id.menu_search);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menuItemSearch.getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setQueryHint(getString(R.string.menu_search));
        mSearchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_share:
                Resources resources = getResources();
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.str_label_share_body));
                intent.putExtra(Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name));
                startActivity(Intent.createChooser(intent, resources.getText(R.string.str_label_share_title)));
                return true;

            case R.id.menu_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.menu_user_guide:
                intent = new Intent(MainActivity.this, GenericActivity.class);
                intent.putExtra(GenericActivity.MODE_KEY, GenericActivity.Mode.URL);
                intent.putExtra(GenericActivity.URL_KEY, "file:///android_asset/UserGuide.html");
                intent.putExtra(GenericActivity.TITLE_ID_KEY, R.string.title_activity_user_guide);
                startActivity(intent);
                return true;

            case R.id.menu_about:
                intent = new Intent(MainActivity.this, GenericActivity.class);
                intent.putExtra(GenericActivity.MODE_KEY, GenericActivity.Mode.LAYOUT);
                intent.putExtra(GenericActivity.LAYOUT_ID_KEY, R.layout.activity_about);
                intent.putExtra(GenericActivity.TITLE_ID_KEY, R.string.title_activity_about);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(Unit.INTENT_KEY, (Class<? extends Unit>) allItems.get(position).get("this"));
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    public class UnitSimpleAdapter extends SimpleAdapter {

        private String[] mFilterable;

        private UnitSimpleFilter mFilter;
        private ArrayList<Map<String, Object>> mUnfilteredData;

        public UnitSimpleAdapter(Context context, ArrayList<Map<String, Object>> data, int viewResource, String[] from, int[] to) {
            this(context, data, viewResource, from, to, null);
        }

        public UnitSimpleAdapter(Context context, ArrayList<Map<String, Object>> data, int viewResource, String[] from, int[] to, String[] filterable) {
            super(context, data, viewResource, from, to);
            allItems = data;
            mFilterable = filterable == null ? from : filterable;
        }

        @Override
        public int getCount() {
            return allItems.size();
        }

        @Override
        public Object getItem(int position) {
            return allItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (mUnfilteredData != null) return super.getView(mUnfilteredData.indexOf(allItems.get(position)), convertView, parent);
            return super.getView(position, convertView, parent);
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new UnitSimpleFilter();
            }
            return mFilter;
        }

        @SuppressLint("DefaultLocale")
        private class UnitSimpleFilter extends Filter {

            @SuppressLint("DefaultLocale")
            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();

                if (mUnfilteredData == null) {
                    mUnfilteredData = new ArrayList<Map<String, Object>>(allItems);
                }

                if (prefix == null || prefix.length() == 0) {
                    ArrayList<Map<String, Object>> list = mUnfilteredData;
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = prefix.toString().toLowerCase();

                    ArrayList<Map<String, Object>> unfilteredValues = mUnfilteredData;
                    int count = unfilteredValues.size();

                    ArrayList<Map<String, Object>> newValues = new ArrayList<Map<String, Object>>(count);

                    for (int i = 0; i < count; i++) {
                        Map<String, Object> h = unfilteredValues.get(i);
                        if (h != null) {
                            boolean added = false;

                            for (String element : mFilterable) {
                                String[] words = h.get(element).toString().split("\\W");

                                for (String word : words) {
                                    if (word.toLowerCase().startsWith(prefixString)) {
                                        newValues.add(h);
                                        added = true;
                                        break;
                                    }
                                }

                                if (added) break;
                            }
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                allItems = (ArrayList<Map<String, Object>>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        }
    }
}
