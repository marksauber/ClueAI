package se329.clue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.BoolRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import se329.clue.util.CardUtil;

public class NotepadActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static ArrayList<Boolean> tab1;
    private static ArrayList<Boolean> tab2;
    private static ArrayList<Boolean> tab3;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tab1 = new ArrayList<Boolean>();
        tab2 = new ArrayList<Boolean>();
        tab3 = new ArrayList<Boolean>();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SECTION_NAME = "section_name";
        private static final String ARG_NUM_OBJECTS = "num_objects";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String sectionName) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SECTION_NAME, sectionName);
            args.putInt(ARG_NUM_OBJECTS, getNumObjects(sectionNumber));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getArguments().getString(ARG_SECTION_NAME));

            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.list);
            int numButtons = (getArguments().getInt(ARG_NUM_OBJECTS));
            CardUtil util = new CardUtil(getContext());
            for(int i = 0; i < numButtons; i++){
                final CheckBox cb = new CheckBox(getActivity());
                cb.setId(i);
                final String sectionName = getArguments().getString(ARG_SECTION_NAME);
                addCheckboxName(util,sectionName,cb,i);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        saveCheckBoxData(sectionName,cb,isChecked);

                    }});
                setCheckboxState(sectionName,cb);
                ll.addView(cb);
            }
            return rootView;
        }

        private static int getNumObjects(int sectionNumber){
            switch (sectionNumber){
                case 1 :
                    return 6;
                case 2 :
                    return 6;
                case 3 :
                    return 9;
            }
            return -1;
        }
        private static void addCheckboxName(CardUtil util,String sectionName,CheckBox cb,int index){
            if(sectionName.equals("Suspects")){
                cb.setText(util.getSuspects().get(index));
                tab1.add(false);
            }else if(sectionName.equals("Weapons")){
                cb.setText(util.getWeapons().get(index));
                tab2.add(false);
            }else if(sectionName.equals(("Rooms"))){
                cb.setText(util.getRooms().get(index));
                tab3.add(false);
            }
        }
        private static void saveCheckBoxData(String sectionName,CheckBox cb,boolean isChecked){
            if(sectionName.equals("Suspects")){
                tab1.set(cb.getId(),isChecked);
            }else if(sectionName.equals("Weapons")){
                tab2.set(cb.getId(),isChecked);
            }else if(sectionName.equals(("Rooms"))){
                tab3.set(cb.getId(),isChecked);
            }
        }
        private static void setCheckboxState(String sectionName,CheckBox cb){
            if(sectionName.equals("Suspects")){
                cb.setChecked(tab1.get(cb.getId()));
            }else if(sectionName.equals("Weapons")){
                cb.setChecked(tab2.get(cb.getId()));
            }else if(sectionName.equals(("Rooms"))){
                cb.setChecked(tab3.get(cb.getId()));
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, (String) getPageTitle(position));
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Suspects";
                case 1:
                    return "Weapons";
                case 2:
                    return "Rooms";
            }
            return null;
        }
    }

    private boolean getBooleanFromSP(String key){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("CLUE", Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key, false);
        return value;
    }

    private void saveBooleanToSP(String key, boolean value){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("CLUE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}
