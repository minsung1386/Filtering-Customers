package edu.skku.cs.final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.skku.cs.final_project.databinding.ActivityViewPostBinding;

public class ViewPostActivity extends AppCompatActivity implements CallAnotherActivityNavigator{
    public ViewPostViewModel viewPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String key=intent.getStringExtra("Key");
        viewPostViewModel=new ViewPostViewModel(key);

        ActivityViewPostBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_view_post);
        binding.setViewPostViewModel(viewPostViewModel);

    }
    @Override
    protected void onDestroy() {
        viewPostViewModel.updatePost();
        super.onDestroy();
    }


    @Override
    public void callNewActivity(String username) {

    }

    @Override
    public void callAnotherActivity(String username) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void finishActivity() {

    }
}