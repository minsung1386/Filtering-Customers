package edu.skku.cs.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;

import edu.skku.cs.final_project.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity implements CallAnotherActivityNavigator{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SignInViewModel signInViewModel= new SignInViewModel(this);
        ActivitySignInBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        binding.setSignInViewModel(signInViewModel);
        binding.executePendingBindings();


    }

    @Override
    public void finishActivity(){
        this.finish();
    }

    @Override
    public void callNewActivity(String username) {
        Intent intent =new Intent(getApplicationContext(), MenuSelectionActivity.class);
        intent.putExtra("Username", username);
        startActivity(intent);
    }

    @Override
    public void callAnotherActivity(String username) {
        startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
    }

    @Override
    public void showToast(String msg) { }

}