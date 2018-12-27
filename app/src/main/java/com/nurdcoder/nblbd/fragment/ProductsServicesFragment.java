package com.nurdcoder.nblbd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nurdcoder.nblbd.R;

/**
 * Created by Ratan on 7/27/2015.
 */
public class ProductsServicesFragment extends Fragment {

    private TabLayout mParentTabLayout;
    private ViewPager mParentViewPager;
    private int mTabItemsCount = 6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.fragment_products_services, null);
        mParentTabLayout = (TabLayout) x.findViewById(R.id.fragment_products_services_parent_tl);
        mParentViewPager = (ViewPager) x.findViewById(R.id.fragment_products_services_parent_vp);

        /**
         *Set an Apater for the View Pager
         */
        mParentViewPager.setAdapter(new ProductsServicesAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        mParentTabLayout.post(new Runnable() {
            @Override
            public void run() {
                mParentTabLayout.setupWithViewPager(mParentViewPager);
            }
        });

        return x;

    }

    class ProductsServicesAdapter extends FragmentPagerAdapter {

        public ProductsServicesAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DepositProductsFragment();
                case 1:
                    return new CreditProductsFragment();
                case 2:
                    return new CardsFragment();
                case 3:
                    return new ScheduleofChargesFragment();
                case 4:
                    return new DownloadFormsFragment();
                case 5:
                    return new UsefulLinkSiteFragment();
            }
            return null;
        }

        @Override
        public int getCount() {

            return mTabItemsCount;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Deposit Products";
                case 1:
                    return "Credit Products";
                case 2:
                    return "Cards";
                case 3:
                    return "Schedule of Charges";
                case 4:
                    return "Download Forms";
                case 5:
                    return "Useful Link Site";
            }
            return null;
        }
    }

}
