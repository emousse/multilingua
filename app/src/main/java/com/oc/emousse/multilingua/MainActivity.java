package com.oc.emousse.multilingua;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.oc.emousse.multilingua.pref.UserShared;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserShared.getInstance(getApplicationContext()).checkLogin();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Initializing navigation view and setChecked first item
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationViewListener();
        navigationView.getMenu().getItem(0).setChecked(true);

        //Set drawer layout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setupDrawer();


    }

    private void navigationViewListener(){
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    // For rest of the options we just show a toast on click

                    case R.id.lessons:
                        Toast.makeText(getApplicationContext(),"Cours",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.quiz:
                        Toast.makeText(getApplicationContext(),"Quiz",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.logout:
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {

            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }
}
