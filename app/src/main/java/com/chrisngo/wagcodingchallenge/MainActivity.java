package com.chrisngo.wagcodingchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chrisngo.wagcodingchallenge.domain.model.User;
import com.chrisngo.wagcodingchallenge.presenter.UserPresenter;
import com.chrisngo.wagcodingchallenge.util.DividerItemDecorator;
import com.chrisngo.wagcodingchallenge.view.activity.UserView;
import com.chrisngo.wagcodingchallenge.view.adapter.UserRecyclerViewAdapter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements UserView {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Convert to fragments
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecorator(getResources().getDrawable(R.drawable.divider, null)));
        UserPresenter userPresenter = new UserPresenter(MainActivity.this, this);
        userPresenter.fetchStackOverflowUsers();
    }

    @Override
    public void updateInfo(List<User> usersList) {
        recyclerView.setAdapter(new UserRecyclerViewAdapter(this, usersList,  getResources().getDimension(R.dimen.text_size)));
    }

//    private void addToParamsList(String param, String value, Map<String, String> params) {
//        params.put(param, value);
//    }
}
