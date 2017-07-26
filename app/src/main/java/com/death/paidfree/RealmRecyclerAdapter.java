package com.death.paidfree;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * Created by deathcode on 20/07/17.
 */

public class RealmRecyclerAdapter extends ShimmerRecyclerView.Adapter<RealmRecyclerAdapter.ViewHolder> {


    RealmResults<Deals> deals;
    Context context;
    class ViewHolder extends ShimmerRecyclerView.ViewHolder{

        ImageView banner;
        TextView appName;
        public ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            banner = (ImageView) itemView.findViewById(R.id.banner);
            appName = (TextView) itemView.findViewById(R.id.appname);

        }
    }

    RealmRecyclerAdapter(RealmResults<Deals> deal, Context context)
    {
        deals = deal;
        this.context = context;
    }

    @Override
    public RealmRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.deals_layout, parent, false);
        return new ViewHolder(itemView);
    }


    public Deals getItem(int position) {
        return deals.get(position);
    }

    @Override
    public void onBindViewHolder(RealmRecyclerAdapter.ViewHolder holder, int position) {
        Picasso.with(context).load(deals.get(position).getPoster()).into(holder.banner);
        holder.appName.setText(deals.get(position).getAdd_title());
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }
}
