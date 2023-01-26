package edu.skku.cs.final_project;

import androidx.databinding.BaseObservable;

public class MenuSelectionViewModel extends BaseObservable {
    private String username;
    private CallAnotherActivityNavigator navigator;

    public MenuSelectionViewModel(CallAnotherActivityNavigator navigator, String username){
        this.navigator=navigator;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void clickNewPost(){
        navigator.callNewActivity(getUsername());
    }
    public void clickViewPosts(){
        navigator.callAnotherActivity(getUsername());
    }
}
