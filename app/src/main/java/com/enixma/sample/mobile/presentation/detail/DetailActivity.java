package com.enixma.sample.mobile.presentation.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.enixma.sample.mobile.R;

/**
 * Created by nakarinj on 18/4/2018 AD.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String MOBILE = "mobile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_detail_activity);

        initToolbar();

        if(getIntent().hasExtra(MOBILE)) {
            DetailModel detailModel = getIntent().getParcelableExtra(MOBILE);
            addFragment(detailModel);
        } else{
            finish();
        }
    }

    private void initToolbar(){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void addFragment(DetailModel detailModel ){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_fragment_host, DetailFragment.newInstance(detailModel))
                .commit();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public static Intent getIntent(Context context){
        return new Intent(context, DetailActivity.class);
    }
}
