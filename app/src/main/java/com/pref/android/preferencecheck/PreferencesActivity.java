package com.pref.android.preferencecheck;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.List;

public class PreferencesActivity extends PreferenceActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {

    TextView m_Title;
    boolean mSinglePane;
    private PreferenceScreen m_prefScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        boolean hidingHeaders = onIsHidingHeaders();
        mSinglePane = hidingHeaders || !onIsMultiPane();
        LayoutInflater li = this.getLayoutInflater();
        View actionBarView = li.inflate(R.layout.action_bar_center_title_with_back, null);
        m_Title = actionBarView.findViewById(R.id.title);
        if(null != m_Title)
        {
            m_Title.setText("Settings");
        }

        super.onCreate(savedInstanceState);


        getActionBar().setCustomView(actionBarView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        invalidateHeaders();
        getListView().setCacheColorHint(Color.TRANSPARENT);
    }

    public void setFragmentPreferenceScreen(PreferenceScreen preferenceScreen)
    {
        m_prefScreen = preferenceScreen;
    }

    public void setSharedPreferencesChangeListener()
    {
        if(m_prefScreen != null)
        {
            SharedPreferences prefs = m_prefScreen.getSharedPreferences();
            if(null != prefs)
            {
                prefs.registerOnSharedPreferenceChangeListener(this);
            }
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        Header about = new Header();
        about.fragment = About.NAME;
        about.titleRes = R.string.aboutPreferenceTitle;
        target.add(about);

        Header options = new Header();
        options.fragment = Options.NAME;
        options.titleRes = R.string.optionsPreferenceTitle;
        target.add(options);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    void removeSharedPreferencesChangeListener()
    {
        if(m_prefScreen != null)
        {
            SharedPreferences prefs = m_prefScreen.getSharedPreferences();
            if(null != prefs)
            {
                prefs.unregisterOnSharedPreferenceChangeListener(this);
            }
        }
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        return About.NAME.equals(fragmentName)
                || Options.NAME.equals(fragmentName);
    }

    public static class About extends PreferenceFragment
    {
            private PreferencesActivity m_prefsActivity;

            public static final String NAME = "com.pref.android.preferencecheck.PreferencesActivity$About";

            @Override
            public void onAttach(Activity activity)
            {
                super.onAttach(activity);
                m_prefsActivity = (PreferencesActivity) activity;
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState)
            {
                try
                {
                    super.onActivityCreated(savedInstanceState);
                }
                catch(RuntimeException e)
                {
                    // Do nothing
                }
            }

            @Override
            public void onResume()
            {
                super.onResume();
                // First set the preference screen to this fragment
                m_prefsActivity.setFragmentPreferenceScreen(getPreferenceScreen());
                m_prefsActivity.setSharedPreferencesChangeListener();
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
            {
                View v = inflater.inflate(R.layout.aboutdialog, null);
                TextView tv = v.findViewById(R.id.aboutText);
                if(tv != null)
                {
                    tv.setText("Version 1");
                }

                TextView copyRightTv =  v.findViewById(R.id.copyRightDesc);
                if(copyRightTv != null)
                {
                    copyRightTv.setText("Copyright Description");
                }

                TextView copyRightLink = v.findViewById(R.id.copyRightLink);
                copyRightLink.setMovementMethod(LinkMovementMethod.getInstance());

                return v;
            }

            @Override
            public void onPause()
            {
                super.onPause();
                m_prefsActivity.removeSharedPreferencesChangeListener();
            }
        }

    public static class Options extends PreferenceFragment
    {
        private PreferencesActivity m_prefsActivity;

        public static final String NAME = "com.pref.android.preferencecheck.PreferencesActivity$Options";

        @Override
        public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            m_prefsActivity = (PreferencesActivity) activity;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState)
        {
            try
            {
                super.onActivityCreated(savedInstanceState);
            }
            catch(RuntimeException e)
            {
                // Do nothing
            }
        }

        @Override
        public void onResume()
        {
            super.onResume();
            // First set the preference screen to this fragment
            m_prefsActivity.setFragmentPreferenceScreen(getPreferenceScreen());
            m_prefsActivity.setSharedPreferencesChangeListener();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View v = inflater.inflate(R.layout.aboutdialog, null);
            TextView tv = v.findViewById(R.id.aboutText);
            if(tv != null)
            {
                tv.setText("Version Number");
            }

            TextView copyRightTv =  v.findViewById(R.id.copyRightDesc);
            if(copyRightTv != null)
            {
                copyRightTv.setText("Copyright Description");
            }

            TextView copyRightLink = v.findViewById(R.id.copyRightLink);
            copyRightLink.setMovementMethod(LinkMovementMethod.getInstance());

            return v;
        }

        @Override
        public void onPause()
        {
            super.onPause();
            m_prefsActivity.removeSharedPreferencesChangeListener();
        }
    }
}

