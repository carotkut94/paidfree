package com.death.paidfree;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class InterestingFragment extends Fragment {



    String url = "http://carotkut.net/play/getAll.php?token=8233669973";
    ProgressDialog pDialog;
    Realm realm;
    ShimmerRecyclerView recyclerView;
    RealmRecyclerAdapter realmRecyclerAdapter;

    public InterestingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = DBController.with(getActivity()).getRealm();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (ShimmerRecyclerView) view.findViewById(R.id.playDeals);
        recyclerView.setDemoLayoutReference(R.layout.deals_dummy_layout);
        if(AppStatus.getInstance(getActivity()).isOnline()){
            fillDataInDb();
        }
        else{
            showToast();
            if(DBController.with(getActivity()).hasUserResponse()>0) {
                realmRecyclerAdapter = new RealmRecyclerAdapter(DBController.with(getActivity()).getDeals(), getActivity());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(realmRecyclerAdapter);
            }
        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        Deals deals = realmRecyclerAdapter.getItem(position);
                        Intent intent = new Intent(getActivity(), GrabDeal.class);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(deals.getPoster()));
                        intent.putExtra("DESC", deals.getDescription());
                        intent.putExtra("TITLE", deals.getAdd_title());
                        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view,
                                view.getTransitionName()).toBundle();
                        getActivity().startActivity(intent,bundle);
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_interesting, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void fillDataInDb() {
//        pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.show();
        final JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("RESPONSE", response.toString());
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    if (!DBController.with(getActivity()).checkIfIdExists(object.getString("id"))) {
                                        realm.beginTransaction();
                                        Deals deals = realm.createObject(Deals.class);
                                        deals.setId(object.getString("id"));
                                        deals.setAdd_title(object.getString("add_title"));
                                        deals.setDescription(object.getString("description"));
                                        deals.setCategory(object.getString("category"));
                                        deals.setLink(object.getString("link"));
                                        deals.setPoster(object.getString("poster"));
                                        deals.setNew_price(object.getString("cPrice"));
                                        deals.setPrice(object.getString("oPrice"));
                                        deals.setAdd_time(System.currentTimeMillis());
                                        realm.commitTransaction();
                                        Log.e("Success", "Inserted");
                                    } else {
                                        Log.e("Already", "Inserted");
                                    }
                                    Log.e("GOT ", "GOT");
                                    realmRecyclerAdapter = new RealmRecyclerAdapter(DBController.with(getActivity()).getDeals(), getActivity());
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(realmRecyclerAdapter);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
//                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: BABMU");
//                pDialog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(req, "REQ2");
    }

    public void showToast()
    {
        Toast confirmLogin = new Toast(getActivity());
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout
                .custom_toast, null, false);
        ((TextView) v.findViewById(R.id.error)).setText("No Internet Connection!");
        // need to use app context here as the activity will be destroyed shortly
        v.findViewById(R.id.scrim).setBackground(ScrimUtil
                .makeCubicGradientScrimDrawable(
                        ContextCompat.getColor(getActivity(), R.color.scrim),
                        5, Gravity.BOTTOM));
        confirmLogin.setView(v);
        confirmLogin.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
        confirmLogin.setDuration(Toast.LENGTH_LONG);
        confirmLogin.show();
    }
}
