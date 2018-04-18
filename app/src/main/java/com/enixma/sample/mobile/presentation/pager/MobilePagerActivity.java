package com.enixma.sample.mobile.presentation.pager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.enixma.sample.mobile.R;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class MobilePagerActivity extends AppCompatActivity {

    private MobilePagerFragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mobile_pager_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addPagerFragment();
    }

    private void addPagerFragment(){
        fragment = new MobilePagerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pager_fragment_host, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pager_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_sort) {
            fragment.displaySortOptions();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static Intent getIntent(Context context){
        return new Intent(context, MobilePagerActivity.class);
    }

}
