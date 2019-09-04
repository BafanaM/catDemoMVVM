package com.example.listdatausingmvvm.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.listdatausingmvvm.R;
import com.example.listdatausingmvvm.database.AppDatabase;
import com.example.listdatausingmvvm.database.CatDataEntry;
import com.example.listdatausingmvvm.databinding.ActivityMainBinding;
import com.example.listdatausingmvvm.model.CatDto;
import com.example.listdatausingmvvm.model.CatListViewModel;

public class MainActivity extends AppCompatActivity implements CatListAdapter.ItemOnclickListener {

    private CatListAdapter catListAdapter;
    private AppDatabase appDatabase;
    private ProgressDialog catsProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(this);
        catsProgressDialog = new ProgressDialog(this);
//        showProgressDialog();
        catListAdapter = new CatListAdapter(this::onItemClicked);
        binding.catListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.catListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.catListRecyclerView.setAdapter(catListAdapter);
        setUpViewModelData();
    }

    @Override
    public void onItemClicked(CatDataEntry catDataEntry) {
        Intent intent = new Intent(this, CatDetailsActivity.class);
        intent.putExtra("cat_entry", getMappedCatDtoFromDb(catDataEntry));
        startActivity(intent);
    }

    private void setUpViewModelData() {
        CatListViewModel viewModel = ViewModelProviders.of(this).get(CatListViewModel.class);
        if (isNetworkConnectionAvailable()) {
            viewModel.getCatListLiveData().observe(this, catListAdapter::setCatListItems);
        } else {
            showCustomDialog(getString(R.string.network_error));
        }

        dismissProgressDialog();
    }

    public void showProgressDialog() {
        if (catsProgressDialog != null) {
            catsProgressDialog.setTitle(getString(R.string.places_loading));
            catsProgressDialog.show();
            catsProgressDialog.setMessage(getString(R.string.please_wait_message));
            catsProgressDialog.setIndeterminate(true);
        }
    }

    public void dismissProgressDialog() {
        catsProgressDialog.dismiss();
    }

    public void showCustomDialog(String titleText) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titleText);
        builder.setIcon(R.drawable.ic_error_black_24dp);
        builder.setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CatListViewModel viewModel = ViewModelProviders.of(MainActivity.this).get(CatListViewModel.class);
                viewModel.getCatListLiveData().observe(MainActivity.this, catListAdapter::setCatListItems);
                dialog.dismiss();
            }
        })
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setUpViewModelData();
                    }
                });
        builder.show().setCancelable(false);
    }

    boolean isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }

    public CatDto getMappedCatDtoFromDb(CatDataEntry catDataEntry) {
        CatDto catDto = new CatDto();
        catDto.setDescription(catDataEntry.getDescription());
        catDto.setImageId(catDataEntry.getId());
        catDto.setImageUrl(catDataEntry.getImageUrl());
        catDto.setTitle(catDataEntry.getTitle());
        return catDto;
    }
}
