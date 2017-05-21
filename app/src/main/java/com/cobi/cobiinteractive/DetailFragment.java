package com.cobi.cobiinteractive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
    final static String ARG_VERSION = "version";
    final static String ARG_API = "api";
    final static String ARG_RELEASED = "released";

    String mCurrentVersion;
    String mCurrentAPI;
    String mCurrentReleaseDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentVersion = savedInstanceState.getString(ARG_VERSION);
            mCurrentAPI = savedInstanceState.getString(ARG_API);
            mCurrentReleaseDate = savedInstanceState.getString(ARG_RELEASED);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            mCurrentVersion = args.getString(ARG_VERSION);
            mCurrentAPI = args.getString(ARG_API);
            mCurrentReleaseDate = args.getString(ARG_RELEASED);
            updateDetailView(mCurrentVersion, mCurrentAPI, mCurrentReleaseDate);
        }
    }

    public void updateDetailView(String version, String api, String released) {
        TextView versionView = (TextView) getActivity().findViewById(R.id.version);
        TextView apiView = (TextView) getActivity().findViewById(R.id.api);
        TextView releasedView = (TextView) getActivity().findViewById(R.id.released);

        versionView.setText(version);
        apiView.setText(api);
        releasedView.setText(released);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putString(ARG_VERSION, mCurrentVersion);
        outState.putString(ARG_API, mCurrentAPI);
        outState.putString(ARG_RELEASED, mCurrentReleaseDate);
    }
}
