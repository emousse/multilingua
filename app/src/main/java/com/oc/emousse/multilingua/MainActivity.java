package com.oc.emousse.multilingua;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.oc.emousse.multilingua.database.Rendezvous;
import com.oc.emousse.multilingua.pref.UserShared;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initiate Realm
        Realm.init(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check if user logged in
        //UserShared.getInstance(getApplicationContext()).checkLogin();

        if(UserShared.getInstance(getApplicationContext()).isLoggedIn()){
            //send close action to all subscribers
            final Intent close = new Intent("close");
            LocalBroadcastManager.getInstance(this).sendBroadcast(close);

            //Initializing toolbar
            Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
            setSupportActionBar(toolbar);

            //Initializing navigation view and setChecked first item
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //Set drawer layout
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();

            //On ajoute un fragment par défaut via la méthode "replace".
            changeFragment(LessonFragment.class, true);
        }
        else {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            // Closing all the Activities
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the Realm instance.
        //_realm.close();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.lessons)
        {
            //on ajoute les autres fragments via la méthode "add"
            changeFragment(LessonFragment.class, true);
        }
        else if (id == R.id.quiz)
        {
            //on ajoute les autres fragments via la méthode "add"
            changeFragment(QuizzFragment.class, true);
        }
        else if (id == R.id.rdv)
        {
            //on ajoute les autres fragments via la méthode "add"
            changeFragment(RendezvousFragment.class, true);
        }
        else if (id == R.id.logout)
        {
            UserShared.getInstance(getApplicationContext()).logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Class<? extends Fragment> fragmentClass, boolean isFirst)
    {
        try
        {
            final Fragment fragment = fragmentClass.newInstance();
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // We can also animate the changing of fragment.
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            // Replace current fragment by the new one.
            fragmentTransaction.replace(R.id.fragmentContainer, fragment);
            // Null on the back stack to return on the previous fragment when user
            // press on back button.
            fragmentTransaction.addToBackStack(null);

            // Commit changes.
            fragmentTransaction.commit();
        }
        catch (Exception exception)
        {

        }
    }
}
