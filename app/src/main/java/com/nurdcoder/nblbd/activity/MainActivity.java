package com.nurdcoder.nblbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewFlipper;

import com.nurdcoder.nblbd.R;
import com.nurdcoder.nblbd.fragment.AboutUsFragment;
import com.nurdcoder.nblbd.fragment.CSRFragment;
import com.nurdcoder.nblbd.fragment.CareerFragment;
import com.nurdcoder.nblbd.fragment.FinancialStatusFragment;
import com.nurdcoder.nblbd.fragment.NewsEventsFragment;
import com.nurdcoder.nblbd.fragment.OverseasOperationFragment;
import com.nurdcoder.nblbd.fragment.ProductsServicesFragment;
import com.nurdcoder.nblbd.fragment.ServiceLocationFragment;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_PRODUCTS_SERVICES = "products_services";
    private static final String TAG_FINANCIAL_STATUS = "financial_status";
    private static final String TAG_SERVICE_LOCATION = "service_location";
    private static final String TAG_OVERSEAS_OPERATION = "overseas_operation";
    private static final String TAG_ABOUT_US = "about_us";
    private static final String TAG_CSR = "csr";
    private static final String TAG_CAREER = "career";
    private static final String TAG_NEWS_EVENTS = "news_events";
    public static String CURRENT_TAG = TAG_PRODUCTS_SERVICES;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private ViewFlipper viewFlipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        viewFlipper = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        viewFlipper.startFlipping();
        // Next screen comes in from left.
        viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
        // Current screen goes out from right.
        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);
        viewFlipper.setFlipInterval(3000);

        activityTitles = getResources().getStringArray(R.array.drawer_menu_item_activity_titles);

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PRODUCTS_SERVICES;
            loadHomeFragment();
        }
    }

    @Override
    void setContentView() {

    }

    @Override
    void setupActionBar() {

    }

    @Override
    void initializeEditTextComponents() {

    }

    @Override
    void initializeButtonComponents() {

    }

    @Override
    void initializeTextViewComponents() {

    }

    @Override
    void initializeImageViewComponents() {

    }

    @Override
    void initializeOtherViewComponents() {

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
        overridePendingTransition(R.anim.trans_top_in,
                R.anim.trans_top_out);
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

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main_menu content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

//        // refresh toolbar menu
//        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // ProductsServices
                ProductsServicesFragment productsServicesFragment = new ProductsServicesFragment();
                return productsServicesFragment;
            case 1:
                // FinancialStatus
                FinancialStatusFragment financialStatusFragment = new FinancialStatusFragment();
                return financialStatusFragment;
            case 2:
                // ServiceLocation
                ServiceLocationFragment serviceLocationFragment = new ServiceLocationFragment();
                return serviceLocationFragment;
            case 3:
                // OverseasOperation
                OverseasOperationFragment overseasOperationFragment = new OverseasOperationFragment();
                return overseasOperationFragment;
            case 4:
                // AboutUs
                AboutUsFragment aboutUsFragment = new AboutUsFragment();
                return aboutUsFragment;
            case 5:
                // CSR
                CSRFragment csrFragment = new CSRFragment();
                return csrFragment;
            case 6:
                // Career
                CareerFragment careerFragment = new CareerFragment();
                return careerFragment;
            case 7:
                // NewsEvents
                NewsEventsFragment newsEventsFragment = new NewsEventsFragment();
                return newsEventsFragment;
            default:
                return new ProductsServicesFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main_menu content with ContentFragment Which is our Inbox View;
                    case R.id.drawer_menu_product_services:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_PRODUCTS_SERVICES;
                        break;
                    case R.id.drawer_menu_financial_status:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FINANCIAL_STATUS;
                        break;
                    case R.id.drawer_menu_service_location:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_SERVICE_LOCATION;
                        break;
                    case R.id.drawer_menu_overseas_operation:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_OVERSEAS_OPERATION;
                        break;
                    case R.id.drawer_menu_about_us:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_ABOUT_US;
                        break;
                    case R.id.drawer_menu_csr:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_CSR;
                        break;
                    case R.id.drawer_menu_career:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_CAREER;
                        break;
                    case R.id.drawer_menu_news_events:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_NEWS_EVENTS;
                        break;

                    case R.id.drawer_menu_contact_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(MainActivity.this, ContactActivity.class));
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                        drawer.closeDrawers();
                        return true;
                    case R.id.drawer_menu_daily_exchange_rate:
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        intent.putExtra("title", getResources().getString(R.string.drawer_menu_daily_exchange_rate_text));
                        intent.putExtra("content", getResources().getStringArray(R.array.daily_exchange_rate_content));
                        startActivity(intent);
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                        drawer.closeDrawers();
                        return true;
                    case R.id.drawer_menu_complain_cell:
                        Intent intent0 = new Intent(MainActivity.this, ListActivity.class);
                        intent0.putExtra("title", getResources().getString(R.string.drawer_menu_complain_cell_text));
                        intent0.putExtra("content", getResources().getStringArray(R.array.complain_cell_content));
                        startActivity(intent0);
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_PRODUCTS_SERVICES;
                loadHomeFragment();
                return;
            }
        }

        exitFromApp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(this);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(null) && !query.isEmpty()) {
                    try {
                        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                        intent.putExtra("title", getResources().getString(R.string.main_menu_search_text));
                        intent.putExtra("url", getResources().getString(R.string.main_menu_search_url));
                        startActivity(intent);
                        overridePendingTransition(R.anim.trans_left_in,
                                R.anim.trans_left_out);
                    } catch (NullPointerException ex) {
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals(null) && !newText.isEmpty()) {
                    try {

                    } catch (NullPointerException ex) {
                    }
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.main_menu_web_mail) {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("title", getResources().getString(R.string.main_menu_web_mail_text));
            intent.putExtra("url", getResources().getString(R.string.main_menu_web_mail_url));
            startActivity(intent);
            overridePendingTransition(R.anim.trans_left_in,
                    R.anim.trans_left_out);
            return true;
        } else if (id == R.id.main_menu_about_developer) {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            intent.putExtra("title", getResources().getString(R.string.main_menu_about_developer_text));
            intent.putExtra("content", getResources().getStringArray(R.array.about_developer_content));
            startActivity(intent);
            overridePendingTransition(R.anim.trans_left_in,
                    R.anim.trans_left_out);
            return true;
        } else if (id == R.id.main_menu_exit_app) {
            exitFromApp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void exitFromApp() {
        final SweetAlertDialog sDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_MODIFIED_TYPE);
        sDialog.setTitleText(getResources().getString(R.string.title_exit_dialog));
        sDialog.setTitle("sDialog");
        sDialog.setConfirmText(getResources().getString(R.string.dialog_confirm_text));
        sDialog.setCancelText(getResources().getString(R.string.dialog_cancel_text));
        sDialog.setCancelable(false);
        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog mDialog) {
                mDialog.dismiss();
                exitThisWithAnimation();
            }
        });
        sDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog mDialog) {
                mDialog.cancel();
            }
        });
        sDialog.show();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }
}
