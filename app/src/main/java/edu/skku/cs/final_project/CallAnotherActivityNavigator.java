package edu.skku.cs.final_project;

public interface CallAnotherActivityNavigator {
    void callNewActivity(String username);
    void callAnotherActivity(String username);
    void showToast(String msg);
    void finishActivity();
}
