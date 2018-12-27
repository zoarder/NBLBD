package com.nurdcoder.nblbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurdcoder.nblbd.R;

/**
 * Created by ZOARDER AL MUKTADIR on 11/21/2016.
 */

public class BlogDetailsActivity extends BaseActivity {

    private CollapsingToolbarLayout mParentCollapsingToolbar;
    private ImageView mHeaderImageView;
    private TextView mBlogDescriptionTextView;
    private TextView mBlogDateTimeTextView;
    String title;
    int image;
    String content;
    boolean hasExpandedTitle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void setContentView() {
        setContentView(R.layout.activity_blog_details);
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");
            image = getIntent().getIntExtra("image", 0);
            hasExpandedTitle = getIntent().getBooleanExtra("hasExpandedTitle", true);
        }
    }

    @Override
    void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_blog_details_parent_tb);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    void initializeEditTextComponents() {
    }

    @Override
    void initializeButtonComponents() {
    }

    @Override
    void initializeTextViewComponents() {
        mBlogDescriptionTextView = (TextView) findViewById(R.id.activity_blog_details_content_layout_blog_description_tv);
        mBlogDescriptionTextView.setText(Html.fromHtml(content));
        mBlogDateTimeTextView = (TextView) findViewById(R.id.activity_blog_details_content_layout_blog_date_time_tv);
    }

    @Override
    void initializeImageViewComponents() {
        mHeaderImageView = (ImageView) findViewById(R.id.activity_blog_details_header_ib);
        mHeaderImageView.setImageResource(image);
    }

    @Override
    void initializeOtherViewComponents() {
        mParentCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.activity_blog_details_parent_ctl);
        if (!hasExpandedTitle) {
            mParentCollapsingToolbar.setTitle(" ");
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.activity_blog_details_parent_abl);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        mParentCollapsingToolbar.setTitle(title);
                        isShow = true;
                    } else if (isShow) {
                        mParentCollapsingToolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                        isShow = false;
                    }
                }
            });
        } else {
            mParentCollapsingToolbar.setTitle(title);
        }
    }

    @Override
    void initializeViewComponentsEventListeners() {
    }

    @Override
    void removeViewComponentsEventListeners() {
    }

    @Override
    void exitThisWithAnimation() {
        finish();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_out);
    }

    @Override
    void startNextWithAnimation(Intent intent, boolean isFinish) {
        if (isFinish) {
            finish();
        }
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in,
                R.anim.trans_left_out);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitThisWithAnimation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        exitThisWithAnimation();
    }
}