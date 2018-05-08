package com.smartalgorithms.tousidestest.Home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.smartalgorithms.tousidestest.Constants;
import com.smartalgorithms.tousidestest.Helpers.DialogHelper;
import com.smartalgorithms.tousidestest.Helpers.FileHelper;
import com.smartalgorithms.tousidestest.Helpers.LoggingHelper;
import com.smartalgorithms.tousidestest.Helpers.ResourcesHelper;
import com.smartalgorithms.tousidestest.R;

import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {
    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.rlyt_select_input) RelativeLayout rlyt_select_input;
    @BindView(R.id.iv_select_input) ImageView iv_select_input;
    @BindView(R.id.tv_select_input) TextView tv_select_input;
    @BindView(R.id.llyt_results) LinearLayout llyt_results;
    @BindView(R.id.tv_score_res_1) TextView tv_score_res_1;
    @BindView(R.id.lav_loading_res_1) LottieAnimationView lav_loading_res_1;
    @BindView(R.id.tv_score_res_2) TextView tv_score_res_2;
    @BindView(R.id.lav_loading_res_2) LottieAnimationView lav_loading_res_2;
    @BindView(R.id.lv_score_res_3) ListView lv_score_res_3;
    @BindView(R.id.lav_loading_res_3) LottieAnimationView lav_loading_res_3;
    @BindView(R.id.tv_reset) TextView tv_reset;
    @BindView(R.id.tv_request_permissions) TextView tv_request_permissions;

    @Inject HomePresenter presenter;
    @Inject Intent intent;
    @Inject FileHelper fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        iv_select_input.setOnClickListener(viewClickListener);
        tv_reset.setOnClickListener(viewClickListener);
        tv_request_permissions.setOnClickListener(viewClickListener);
        toggleOutput(false);
        presenter.requestPhonePermissions();
    }

    private View.OnClickListener viewClickListener = view -> {
        switch (view.getId()){
            case R.id.iv_select_input:
                presenter.onBtnClickSelectInput();
                break;
            case R.id.tv_reset:
                toggleOutput(false);
                break;
            case R.id.tv_request_permissions:
                presenter.requestPhonePermissions();
                break;
        }
    };

    @Override
    public void displayMessage(String title, String message) {
        DialogHelper.showCustomAlertDialog(this, title, message, null, null, false, ResourcesHelper.getString(R.string.text_ok), null);
    }

    @Override
    public void transitionTo(@Nullable Class<?> toClass, @Nullable Integer requestCode) {
        if(toClass != null) {
            intent.setClass(this, toClass);
            startActivity(intent);
        }else if(requestCode != null) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/plain");
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void toggleOutput(boolean show) {
        if(show){
            llyt_results.setVisibility(View.VISIBLE);
            rlyt_select_input.setVisibility(View.GONE);
        }else {
            llyt_results.setVisibility(View.GONE);
            rlyt_select_input.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayFAnimation(boolean show) {
        if(show){
            tv_score_res_1.setVisibility(View.GONE);
            lav_loading_res_1.setVisibility(View.VISIBLE);
            lav_loading_res_1.setAnimation("around_and_back_loader.json");
            lav_loading_res_1.playAnimation();
        }else {
            lav_loading_res_1.setVisibility(View.GONE);
            tv_score_res_1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void display7CFAnimation(boolean show) {
        if(show){
            tv_score_res_2.setVisibility(View.GONE);
            lav_loading_res_2.setVisibility(View.VISIBLE);
            lav_loading_res_2.setAnimation("off_time_leap_frog_loader.json");
            lav_loading_res_2.playAnimation();
        }else {
            lav_loading_res_2.setVisibility(View.GONE);
            tv_score_res_2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayHSAnimation(boolean show) {
        if(show){
            lv_score_res_3.setVisibility(View.GONE);
            lav_loading_res_3.setVisibility(View.VISIBLE);
            lav_loading_res_3.setAnimation("side_to_side_loader.json");
            lav_loading_res_3.playAnimation();
        }else {
            lav_loading_res_3.setVisibility(View.GONE);
            lv_score_res_3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void displayHSW(ArrayList<String> highestScoringWords) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, highestScoringWords);
        lv_score_res_3.setAdapter(adapter);
    }

    @Override
    public void display7CFW(String result) {
        tv_score_res_2.setText(result);
    }

    @Override
    public void displayMFW(String result) {
        tv_score_res_1.setText(result);
    }

    @Override
    public void togglePermissions(boolean permissionsSet) {
        if(!permissionsSet){
            iv_select_input.setVisibility(View.GONE);
            tv_select_input.setVisibility(View.GONE);
            tv_request_permissions.setVisibility(View.VISIBLE);
        }else {
            iv_select_input.setVisibility(View.VISIBLE);
            tv_select_input.setVisibility(View.VISIBLE);
            tv_request_permissions.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.INTENT_REQUEST_CODE_GET_TEXT_FILE) {
            if (resultCode == RESULT_OK) {
                LoggingHelper.d(TAG, "onActivityResult RESULT OK");
                if (data != null) {
                    try {
                        String filePath = fileHelper.getFilePath(data.getData());
                        presenter.processText(filePath);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        LoggingHelper.e(TAG, "getFilePath Error: " + e.getMessage());
                    }
                }
            }
        }
    }
}
