package com.chrisngo.wagcodingchallenge.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chrisngo.wagcodingchallenge.R;
import com.chrisngo.wagcodingchallenge.domain.model.User;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.text.NumberFormat;
import java.util.List;



public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder>{
    private List<User> userData;
    private float textSize;
    private static final String TAG = UserRecyclerViewAdapter.class.getSimpleName();
    private Context context;

    public UserRecyclerViewAdapter(Context context, List<User> userData, float textSize) {
        this.userData = userData;
        this.textSize = textSize;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Log.d(TAG, "Setting views");
        final User user = userData.get(position);
        NumberFormat nf = NumberFormat.getInstance();
        holder.username.setText(user.getDisplayName());
        holder.reputation.setText(nf.format(user.getReputation()));
        holder.goldBadges.setText(String.format("%d", user.getBadgeCounts().getGold()));
        holder.silverBadges.setText(String.format("%d", user.getBadgeCounts().getSilver()));
        holder.bronzeBadges.setText(String.format("%d", user.getBadgeCounts().getBronze()));
        final String imageUri = user.getProfileImage();
        holder.usersLayout.setOnClickListener(new View.OnClickListener() {
            // Open user's stackoverflow profile on click
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(user.getLink()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        loadImage(holder, imageUri);
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
        return new ViewHolder(view);
    }


    // Load image using Picasso to handle caching. If device is offline, load from cache.
    // If cached load fails try one more time.
    private void loadImage(final ViewHolder holder, final String imageUri) {
        if(imageUri!=null) {
            Picasso.get()
                    .load(imageUri)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.gravatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            //Retry online if cache failed
                            Picasso.get()
                                    .load(imageUri)
                                    .into(holder.gravatar, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Log.v(TAG, "Picasso loaded image");
                                            holder.progressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e(TAG, "Picasso could not fetch image");
                                        }
                                    });
                        }
                    });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, reputation, goldBadges, silverBadges, bronzeBadges;
        ImageView gravatar;
        ProgressBar progressBar;
        ConstraintLayout usersLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            reputation = (TextView) itemView.findViewById(R.id.reputation);
            goldBadges = (TextView) itemView.findViewById(R.id.gold_badges);
            silverBadges = (TextView) itemView.findViewById(R.id.silver_badges);
            bronzeBadges = (TextView) itemView.findViewById(R.id.bronze_badges);
            usersLayout = (ConstraintLayout) itemView.findViewById(R.id.usersLayout);
            username.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    textSize);
            reputation.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    textSize);
            goldBadges.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    textSize);
            silverBadges.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    textSize);
            bronzeBadges.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    textSize);
            gravatar = (ImageView) itemView.findViewById(R.id.gravatar);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }
}
