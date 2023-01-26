package edu.skku.cs.final_project;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.skku.cs.final_project.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity implements CallAnotherActivityNavigator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateAccountViewModel createAccountViewModel = new CreateAccountViewModel(this);
        ActivityCreateAccountBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_create_account);
        binding.setCreateAccountViewModel(createAccountViewModel);
        binding.executePendingBindings();
    }


    @Override
    public void finishActivity() {
        this.finish();
    }


    @Override
    public void callNewActivity(String username) {

    }

    @Override
    public void callAnotherActivity(String username) {

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}