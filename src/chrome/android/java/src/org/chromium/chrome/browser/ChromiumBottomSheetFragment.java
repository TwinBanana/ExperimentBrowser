// Made by Bel. 29.01.2017
package org.chromium.chrome.browser;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.chromium.base.metrics.RecordUserAction;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.chrome.browser.widget.TintedImageButton;

import static android.content.Context.WINDOW_SERVICE;

public class ChromiumBottomSheetFragment extends BottomSheetDialogFragment {

    public static final String TAG = "Deb_bottom_sheet_menu";
    private View view;

    private WindowManager windowManager;
    private View bottomNavigationMenu;

    private TintedImageButton mBackButton;
    private TintedImageButton mCloseMenuButton;
    private TintedImageButton mForwardButton;

    private final int windowManagerHeight = 48;

    public ChromiumBottomSheetFragment(){}

    public static ChromiumBottomSheetFragment getInstance(){
        final ChromiumBottomSheetFragment chromiumBottomSheetFragment = new ChromiumBottomSheetFragment();
        final Bundle args = new Bundle();
        chromiumBottomSheetFragment.setArguments(args);
        return chromiumBottomSheetFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.bottom_sheet_menu, null);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.bottomSheet_viewPager);
        viewPager.setAdapter(new BottomSheetPagerAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return view;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        final float scale = getContext().getResources().getDisplayMetrics().density;
        final int height = (int) (windowManagerHeight * scale + 0.5f);

        final WindowManager.LayoutParams p = new WindowManager.LayoutParams(
                // Shrink the window to wrap the content rather than filling the screen
                WindowManager.LayoutParams.MATCH_PARENT,
                height,
                // Display it on top of other application windows, but only for the current user
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                // Make the underlying application window visible through any transparent parts
                PixelFormat.TRANSLUCENT);

        // Define the position of the window within the screen
        p.gravity = Gravity.BOTTOM | Gravity.CENTER;

        windowManager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        windowManager.addView(bottomNavigationMenu, p);

//        view = View.inflate(getContext(), R.layout.bottom_sheet_menu, null);
////        dialog.setContentView(view);
//        final CoordinatorLayout.LayoutParams layoutParams =
//                (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
//        final CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
//
//        if (behavior != null && behavior instanceof BottomSheetBehavior) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        TODO : make an image position representation

        /* Setting the Theme for the FragmentDialog to set the max HEIGHT */
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog);
        bottomNavigationMenu = View.inflate(getContext(), R.layout.bottom_navigation, null);

        mBackButton = (TintedImageButton) bottomNavigationMenu.findViewById(R.id.bottom_nav_back_button);
        mCloseMenuButton = (TintedImageButton) bottomNavigationMenu.findViewById(R.id.bottom_nav_close_button);
        mForwardButton = (TintedImageButton) bottomNavigationMenu.findViewById(R.id.bottom_nav_forward_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Tab currentTab = ((ChromeTabbedActivity)getActivity()).getActivityTab();
                if (currentTab == null) {
                    org.chromium.base.Log.i(TAG, "mForwardButton onClick() currentTab == null. return");
                    return;
                }

                if (currentTab.canGoForward()) {
                    currentTab.goForward();
                    RecordUserAction.record("MobileMenuForward");
                    RecordUserAction.record("MobileTabClobbered");
                }
            }
        });

        mCloseMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDown();
            }
        });

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        try {
            if(bottomNavigationMenu != null) {
                windowManager.removeViewImmediate(bottomNavigationMenu);
            }
        }catch (Exception e){
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setCancelable(boolean cancelable) {
        final Dialog dialog = getDialog();
        final View touchOutsideView = dialog.getWindow().getDecorView()
                .findViewById(android.support.design.R.id.touch_outside);
        final View bottomSheetView = dialog.getWindow().getDecorView()
                .findViewById(android.support.design.R.id.design_bottom_sheet);

        if (cancelable) {
            touchOutsideView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog.isShowing()) {
                        dialog.cancel();
                    }
                }
            });
            BottomSheetBehavior.from(bottomSheetView).setHideable(true);
        } else {
            touchOutsideView.setOnClickListener(null);
            BottomSheetBehavior.from(bottomSheetView).setHideable(false);
        }
    }

    private class BottomSheetPagerAdapter extends FragmentPagerAdapter{

        public BottomSheetPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem: " + position);
            switch (position){
                case 0: {
                    return new FirstFragment();
                }
                case 1: {
                    return new SecondFragment();
                }
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public void popDown(){
        if(bottomNavigationMenu != null) {
            windowManager.removeViewImmediate(bottomNavigationMenu);
        }
        dismiss();
    }

}
