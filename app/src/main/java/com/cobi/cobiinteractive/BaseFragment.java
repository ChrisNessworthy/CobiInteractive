package com.cobi.cobiinteractive;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cobi.cobiinteractive.adapter.AndroidVersionAdapter;
import com.cobi.cobiinteractive.classes.AndroidVersionArray;
import com.cobi.cobiinteractive.classes.AndroidVersionObject;
import com.cobi.cobiinteractive.network.ApiHelper;
import com.cobi.cobiinteractive.network.URLConstants;
import com.cobi.cobiinteractive.utility.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BaseFragment extends Fragment {
    OnBaseObjectSelectedListener mCallback;

    ListView androidVersionsList;

    ArrayList<AndroidVersionObject> androidVersionObjects;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnBaseObjectSelectedListener {
        /** Called by BaseFragment when a list item is selected */
        public void onDetailSelected(String version, String api, String released);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        androidVersionsList = (ListView) getActivity().findViewById(R.id.androidVersionsList);

        fetchBaseData();
    }

    @Override
    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.detail_fragment) != null) {
//            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnBaseObjectSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBaseObjectSelectedListener");
        }
    }

    public void loadListView() {

        final AndroidVersionAdapter adapter = new AndroidVersionAdapter(androidVersionObjects, this.getContext());
        androidVersionsList.setAdapter(adapter);

        androidVersionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                AndroidVersionObject selectedItem = adapter.getItem(position);

                String version = "";
                String api = "";
                String released = "";

                if (selectedItem.getVersion() != null) {
                    version = selectedItem.getVersion();
                }
                if (selectedItem.getApi() != null) {
                    api = selectedItem.getApi();
                }
                if (selectedItem.getReleased() != null) {
                    released = selectedItem.getReleased();
                }

                // Notify the parent activity of selected item
                mCallback.onDetailSelected(version, api, released);

                // Set the item as checked to be highlighted when in two-pane layout
                androidVersionsList.setItemChecked(position, true);
            }
        });
    }

    //Calls the API to get all the base data
    public void fetchBaseData() {
        StringRequest zapperBaseObjectCall = ApiHelper.buildApiStringCall(
                Request.Method.GET,
                URLConstants.URLS.BASE_URL(),
                null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        AndroidVersionArray obj = gson.fromJson(response, AndroidVersionArray.class);
                        androidVersionObjects = obj.getVersions();
                        loadListView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
        zapperBaseObjectCall.setTag(URLConstants.CALL_COBI_API);
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(zapperBaseObjectCall);
    }
}
