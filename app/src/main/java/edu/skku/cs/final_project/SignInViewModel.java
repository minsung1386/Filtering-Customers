package edu.skku.cs.final_project;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SignInViewModel extends BaseObservable {
    private UserModel userModel;
    private CallAnotherActivityNavigator navigator;

    public void callMenuSelectionActivity(String username){
        navigator.callNewActivity(username);
    }
    public void callCreateAccountActivity(){
        navigator.callAnotherActivity("");
    }
    @Bindable
    private String msgString=null;
    public String getMsgString(){return msgString;}
    public void setMsgString(String msgString){
        this.msgString=msgString;
        notifyPropertyChanged(BR.msgString);
    }

    private ObservableField<String> inputUsername;
    private ObservableField<String> inputPassword;
    public SignInViewModel(CallAnotherActivityNavigator navigator){
        userModel=new UserModel("initu", "initp");
        this.inputUsername=new ObservableField<>("");
        this.inputPassword=new ObservableField<>("");
        this.navigator=navigator;
    }
    public ObservableField<String> getInputUsername() {
        return inputUsername;
    }
    public void setInputUsername(ObservableField<String> inputUsername) {
        this.inputUsername = inputUsername;
    }
    public ObservableField<String> getInputPassword() {
        return inputPassword;
    }
    public void setInputPassword(ObservableField<String> inputPassword) {
        this.inputPassword = inputPassword;
    }

    public void onButtonSignInClicked(){
        userModel.setUsername(inputUsername.get());
        userModel.setPassword(inputPassword.get());
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/signin").newBuilder();
        urlBuilder.addQueryParameter("username", userModel.getUsername());
        urlBuilder.addQueryParameter("password", userModel.getPassword());
        String url = urlBuilder.build().toString();
        Log.v("로그", "url: "+url);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson=new Gson();
                final String myResponse = response.body().string();
                ResponseModel resp = gson.fromJson(myResponse, ResponseModel.class);
                if(resp.getUsername_success().equalsIgnoreCase("true")&&resp.getPassword_success().equalsIgnoreCase("true")){
                    Log.v("로그", "로그인 성공");
                    callMenuSelectionActivity(userModel.getUsername());
                }
                else if(resp.getUsername_success().equalsIgnoreCase("true")){
                    Log.v("로그", "비밀번호 오류");
                    setMsgString("Wrong password, please try again");
                }
                else{
                    Log.v("로그", "아이디 존재하지 않음");
                    setMsgString("Wrong username, please try again");
                }
            }
        });
    }
    public void onButtonCreateAccountClicked() {
        callCreateAccountActivity();
    }
}