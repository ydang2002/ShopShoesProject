package com.nhuy.shopshoesproject.view.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nhuy.shopshoesproject.R;
import com.nhuy.shopshoesproject.view.Activity.Admin.Bill.CustomerBillActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Brand.HiddenBrandActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Brand.NewBrandActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Brand.ViewAllBrandActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Category.HiddenCategoryActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Category.NewCategoryActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Category.ViewCategoryActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Order.AllCustomerOrderActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.HiddenProduct;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.NewProductActivity;
import com.nhuy.shopshoesproject.view.Activity.Admin.Product.ViewAllProductActivity;
import com.nhuy.shopshoesproject.view.Activity.Customer.CustomersLoginActivity;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // code logic bắt sự kiện khi click váo item nav_view_admin
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home_admin) {
            Intent intent = new Intent(getApplicationContext(), ViewAllProductActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_add_product) {
            Intent intent = new Intent(getApplicationContext(), NewProductActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            Intent intent = new Intent(AdminHomeActivity.this, CustomersLoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Logout successful", Toast.LENGTH_SHORT).show();
        }
//        else if (id == R.id.nav_customer_statistic) {
//            Intent intent = new Intent(AdminHome.this, statisticActivity.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_add_category) {
            Intent intent = new Intent(AdminHomeActivity.this, NewCategoryActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_view_category) {
            Intent intent = new Intent(AdminHomeActivity.this, ViewCategoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add_brand) {
            Intent intent = new Intent(AdminHomeActivity.this, NewBrandActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_view_brand) {
            Intent intent = new Intent(AdminHomeActivity.this, ViewAllBrandActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_hidden_product) {
            Intent intent = new Intent(AdminHomeActivity.this, HiddenProduct.class);
            startActivity(intent);
        } else if (id == R.id.nav_hidden_brand) {
            Intent intent = new Intent(AdminHomeActivity.this, HiddenBrandActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_customer_order) {
            Intent intent = new Intent(AdminHomeActivity.this, AllCustomerOrderActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_customer_bill) {
            Intent intent = new Intent(AdminHomeActivity.this, CustomerBillActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_hidden_category) {
            Intent intent = new Intent(AdminHomeActivity.this, HiddenCategoryActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout_admin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}