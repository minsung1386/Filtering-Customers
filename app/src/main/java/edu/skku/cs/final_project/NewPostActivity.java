package edu.skku.cs.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.skku.cs.final_project.databinding.ActivityNewPostBinding;

public class NewPostActivity extends AppCompatActivity implements CallAnotherActivityNavigator {
    NewPostViewModel newPostViewModel;
    final int PICK_IMAGE=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String username=intent.getStringExtra("Username");
        newPostViewModel=new NewPostViewModel(this, username);
        ActivityNewPostBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_new_post);
        binding.setNewPostViewModel(newPostViewModel);
        binding.executePendingBindings();
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void callNewActivity(String username) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void callAnotherActivity(String username) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        newPostViewModel.encodeSelectedImage(getApplicationContext(), requestCode, resultCode, data);
    }
}