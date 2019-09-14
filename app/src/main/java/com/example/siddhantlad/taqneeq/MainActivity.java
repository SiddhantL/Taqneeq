package com.example.siddhantlad.taqneeq;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
FirebaseAuth mAuth;
FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.headerText);
        TextView navEmail = (TextView) headerView.findViewById(R.id.textView_sub);
        navEmail.setText(user.getEmail());
        navUsername.setText(user.getDisplayName());

        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        MenuItem nav_score = menu.findItem(R.id.nav_camera);
        // set new title to the MenuItem
        nav_score.setTitle("Score");
        MenuItem nav_phone = menu.findItem(R.id.nav_gallery);
        // set new title to the MenuItem
        nav_phone.setTitle("Phone");
        MenuItem nav_year = menu.findItem(R.id.nav_slideshow);
        // set new title to the MenuItem
        nav_year.setTitle("Branch");
        MenuItem nav_course = menu.findItem(R.id.nav_manage);
        // set new title to the MenuItem
        nav_course.setTitle("Help");
        MenuItem nav_about_app = menu.findItem(R.id.nav_share);
        // set new title to the MenuItem
        nav_about_app.setTitle("About App");
        nav_about_app.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "Made By Siddhant Lad", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        MenuItem nav_logout = menu.findItem(R.id.nav_send);
        // set new title to the MenuItem
        nav_logout.setTitle("Logout");


        ArrayList<ModelClass> items = new ArrayList<>();
        ArrayList<ModelClass> items2 = new ArrayList<>();
        ArrayList<ModelClass> items3 = new ArrayList<>();
        CustomAdapter adapter = new CustomAdapter(this, items);
        CustomAdapter adapter2 = new CustomAdapter(this, items2);
        CustomAdapter adapter3 = new CustomAdapter(this, items3);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView recyclerView2 = findViewById(R.id.my_recycler_view2);
        RecyclerView recyclerView3 = findViewById(R.id.my_recycler_view3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
        recyclerView3.setAdapter(adapter);

// let's create 10 random items
        for (int i = 0; i < 10; i++) {
            items.add(new ModelClass(MyData.drawableArray[i], MyData.nameArray[i]));
            adapter.notifyDataSetChanged();
            items2.add(new ModelClass(MyData.drawableArray[i], MyData.nameArray[i]));
            adapter2.notifyDataSetChanged();
            items3.add(new ModelClass(MyData.drawableArray[i], MyData.nameArray[i]));
            adapter3.notifyDataSetChanged();
        }
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
            // Handle the camera action;
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
mAuth.signOut();
startActivity(new Intent(MainActivity.this,LoginActivity.class));
finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}