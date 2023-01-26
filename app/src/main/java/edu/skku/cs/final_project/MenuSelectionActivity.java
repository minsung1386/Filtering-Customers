package edu.skku.cs.final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.skku.cs.final_project.databinding.ActivityMenuSelectionBinding;

public class MenuSelectionActivity extends AppCompatActivity implements CallAnotherActivityNavigator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String username = intent.getStringExtra("Username");
        MenuSelectionViewModel menuSelectionViewModel=new MenuSelectionViewModel(this, username);
        ActivityMenuSelectionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_selection);
        binding.setMenuSelectionViewModel(menuSelectionViewModel);
        binding.executePendingBindings();
    }



    @Override
    public void finishActivity() {

    }

    @Override
    public void callNewActivity(String username) {
        Intent intent=new Intent(getApplicationContext(), NewPostActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    @Override
    public void callAnotherActivity(String username) {
        Intent intent=new Intent(getApplicationContext(), ListUpPostActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    @Override
    public void showToast(String msg) {

    }

}