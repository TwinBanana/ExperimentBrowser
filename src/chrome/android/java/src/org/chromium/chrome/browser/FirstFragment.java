package org.chromium.chrome.browser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.tab.Tab;
import org.chromium.chrome.browser.widget.TintedImageButton;

public class FirstFragment extends Fragment {

    private static final String TAG = "FirstFragment";

    TintedImageButton mBookmarkButton;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBookmarkButton();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_first, container, false);

        /* First Row */
        TintedImageButton mNewIncognitoTabButton = (TintedImageButton) view.findViewById(R.id.incognito_tab_button);
        mBookmarkButton = (TintedImageButton) view.findViewById(R.id.bookmark_button);
        updateBookmarkButton();
        TintedImageButton mDownloadsButton = (TintedImageButton) view.findViewById(R.id.downloads_button);

        /* Second Row */
        TintedImageButton mSettingsButton = (TintedImageButton) view.findViewById(R.id.settings_button);
        TintedImageButton mRecentTabsButton = (TintedImageButton) view.findViewById(R.id.recent_tabs_button);
        TintedImageButton mShareButton = (TintedImageButton) view.findViewById(R.id.share_button);

        mNewIncognitoTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO : make incognito tab
                ((ChromeTabbedActivity) getActivity()).clickNewIncognitoTab();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
//                ((ChromeTabbedActivity) getActivity()).clickNewTabOrIncognitoTab();
            }
        });

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO : make settings
                ((ChromeActivity) getActivity()).clickPreferences();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
            }
        });

        mRecentTabsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChromeTabbedActivity) getActivity()).clickRecentTabsButton();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
            }
        });

        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChromeActivity) getActivity()).clickShare();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
            }
        });

        mBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((ChromeTabbedActivity) getActivity()).getAppMenuPropertiesDelegate().u
                Log.d(TAG, "onClick: mBookmarkButton()");
                ((ChromeTabbedActivity) getActivity())
                        .addOrEditBookmark(((ChromeTabbedActivity)getActivity()).getActivityTab());
                updateBookmarkButton();
            }
        });

        mDownloadsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : check clickDownloadButton() OR openDownloadsView()
                ((ChromeTabbedActivity) getActivity()).openDownloadsView();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
            }
        });
        return view;
    }

    private void updateBookmarkButton(){
        Tab currentTab = ((ChromeTabbedActivity)getActivity()).getActivityTab();
        if (currentTab.getBookmarkId() != Tab.INVALID_BOOKMARK_ID) {
            mBookmarkButton.setImageResource(R.drawable.btn_star_filled);
        } else {
            mBookmarkButton.setImageResource(R.drawable.btn_star);
        }
    }
}
