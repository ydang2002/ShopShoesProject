package com.nhuy.shopshoesproject.view.Activity.Customer;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.controller.Customer.LoginController;
import com.nhuy.shopshoesproject.models.PeopleModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressBar progressBar;
    private CircleImageView UserProfileImg;
    private CircleImageView ChangeProfileBtn;
    private TextView UserNameDrawer;
    private DrawerLayout mDrawerLayout;
    private LoginController loginController;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_ORDER = 2;
    private int currentFragment = FRAGMENT_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }
    private void initUI(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // code logic bắt sự kiện khi click váo item nav_view_admin
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        UserNameDrawer = hView.findViewById(R.id.username_drawer_admin);
        loginController = new LoginController(this);
        loginController.getProfileData(UserNameDrawer);
        replaceFagment(new FragmentHome());


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home_admin) {
            if (currentFragment != FRAGMENT_HOME) {
                replaceFagment(new FragmentHome());
                currentFragment = FRAGMENT_HOME;
            }
        }
        else if (id == R.id.nav_cart) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_order) {
            if (currentFragment != FRAGMENT_ORDER) {
                replaceFagment(new FragmentOrder());
                currentFragment = FRAGMENT_ORDER;
            }
        }
        else if (id == R.id.nav_bill) {
            Intent intent = new Intent(MainActivity.this, BillActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(MainActivity.this, CustomersLoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_account) {
            Intent intent = new Intent(MainActivity.this, UserManage.class);
            startActivity(intent);

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        if (currentFragment != FRAGMENT_HOME){
            currentFragment = FRAGMENT_HOME;
            replaceFagment(new FragmentHome());
        } else
        {
            super.onBackPressed();
        }
    }
    private void replaceFagment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame,fragment);
        fragmentTransaction.commit();
    }
}