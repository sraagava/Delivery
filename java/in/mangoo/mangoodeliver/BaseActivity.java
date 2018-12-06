package in.mangoo.mangoodeliver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;


public class BaseActivity extends AppCompatActivity {


    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    {

        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_orders:
                          fragment = new OrdersFragment();
                          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                          return true;
                    case R.id.navigation_expense:
                          fragment = new ExpenseFragment();
                          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        return true;
                    case R.id.navigation_profile:
                          fragment = new ProfileFragment();
                          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
                        return true;
                }
                return false;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        fragment = new OrdersFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
