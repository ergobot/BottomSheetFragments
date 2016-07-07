package com.example.android.bottomsheetfragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FirstFragment.OnFragmentInteractionListener,
        SecondFragment.OnFragmentInteractionListener{

    float mPreviousY = 0f;

    public CardView getToolbarContainer() {
        return toolbarContainer;
    }

    public void setToolbarContainer(CardView toolbarContainer) {
        this.toolbarContainer = toolbarContainer;
    }

    CardView toolbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarContainer = (CardView) findViewById(R.id.map_toolbar_container);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container,FirstFragment.newInstance("test1","test2"),FirstFragment.TAG).commit();
//        FirstFragment firstFragment = (FirstFragment)getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG);
//        ViewAnimationUtils.collapse(firstFragment.getBottomSheetImage());


        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState){
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d("bottomsheet", "STATE_COLLAPSED");
//                        fragmentManager.beginTransaction().replace(R.id.container,FirstFragment.newInstance("test1","test2"),FirstFragment.TAG).commit();
                        FloatingActionButton fab = ((FloatingActionButton)findViewById(R.id.fab));
//                        CoordinatorLayout coord = (CoordinatorLayout)fab.getParent();
                        fab.show();
                        FirstFragment firstFragment = (FirstFragment)getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG);
//                        firstFragment.getBottomSheetImage().animate().translationY(-firstFragment.getBottomSheetImage().getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        ViewAnimationUtils.collapse(firstFragment.getBottomSheetImage());
//   firstFragment.getBottomSheetImage().setVisibility(View.GONE);
                        showToolbar();
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d("bottomsheet", "STATE_DRAGGING");
                        ((FloatingActionButton)findViewById(R.id.fab)).hide();
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d("bottomsheet", "STATE_HIDDEN");
//                        FirstFragment firstFragment2 = (FirstFragment)getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG);
//                        ViewAnimationUtils.collapse(firstFragment2.getBottomSheetImage());
//                        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d("bottomsheet", "STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        ((FloatingActionButton)findViewById(R.id.fab)).hide();
                        FirstFragment firstFragment1 = (FirstFragment)getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG);
//                        firstFragment1.getBottomSheetImage().setVisibility(View.VISIBLE);
//                        firstFragment1.getBottomSheetImage().animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        ViewAnimationUtils.expand(firstFragment1.getBottomSheetImage(),MainActivity.this);
                        Log.d("bottomsheet", "STATE_EXPANDED");
                        hideToolbar();
//                        fragmentManager.beginTransaction().replace(R.id.container,SecondFragment.newInstance("test1","test2"),SecondFragment.TAG).commit();
                        break;
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                if(getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG) != null) {
                    Log.i("os", String.valueOf(slideOffset));

                    FirstFragment firstFragment = (FirstFragment)getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG);

//                    firstFragment.getBottomSheetImage();

//                    firstFragment.getBottomSheetImage().animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();

//                    float dVerticalScroll = slideOffset - mPreviousY;
//                    mPreviousY = slideOffset;
//
//                    going up
//                    if (dVerticalScroll <= 0 && firstFragment.getBottomSheetImage().getY() <= 0) {
//                        firstFragment.getBottomSheetImage().setY(0);
//                    }else {
//                        firstFragment.getBottomSheetImage().setY((int)(firstFragment.getBottomSheetImage().getY() + (slideOffset)));
//                    }


                }

            }
        });

        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    public void showToolbar() {
        CardView toolbar = this.getToolbarContainer();
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
    }

    public void hideToolbar() {
        CardView toolbar = this.getToolbarContainer();
        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
