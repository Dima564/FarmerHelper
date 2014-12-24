package com.kpi.kovalenkodima.farmerhelper.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.kpi.kovalenkodima.farmerhelper.fragments.PlantsFragment;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.fragments.QualificationsFragment;
import com.kpi.kovalenkodima.farmerhelper.fragments.WorkersFragment;
import com.kpi.kovalenkodima.farmerhelper.fragments.FieldsFragment;
import com.kpi.kovalenkodima.farmerhelper.fragments.NavigationDrawerFragment;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final int FRAG_FIELDS = 0;
    public static final int FRAG_PLANTS = 1;
    public static final int FRAG_WORKERS = 2;
    public static final int FRAG_QUALIFICATIONS = 3;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = new FieldsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        switch (position) {
            case FRAG_FIELDS:
                fragment = new FieldsFragment();
                break;
            case FRAG_PLANTS:
                fragment = new PlantsFragment();
                break;
            case FRAG_WORKERS:
                fragment = new WorkersFragment();
                break;
            case FRAG_QUALIFICATIONS:
                fragment = new QualificationsFragment();
                break;
            default:
                fragment = new FieldsFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.container,fragment).commit();
    }
}
