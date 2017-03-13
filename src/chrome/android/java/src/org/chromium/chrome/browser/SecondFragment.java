package org.chromium.chrome.browser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.widget.TintedImageButton;

public class SecondFragment extends Fragment {

    private static final String TAG = "SecondFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_second, container, false);

                /* First Row */
        TintedImageButton mFindInPageButton = (TintedImageButton) view.findViewById(R.id.find_in_page_button);
        TintedImageButton mAddHomescreenButton = (TintedImageButton) view.findViewById(R.id.add_homescreen_button);
        TintedImageButton mHistoryButton = (TintedImageButton) view.findViewById(R.id.history_button);

        mFindInPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChromeTabbedActivity) getActivity()).clickFindInPage();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
            }
        });

        mAddHomescreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChromeActivity) getActivity()).clickAddToHomescreen();
            }
        });

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChromeActivity) getActivity()).clickOpenHistoryMenu();
                ((ChromiumBottomSheetFragment) getParentFragment()).popDown();
            }
        });

        return view;
    }
}
