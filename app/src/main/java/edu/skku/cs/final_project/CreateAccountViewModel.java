package edu.skku.cs.final_project;

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

public class CreateAccountViewModel extends BaseObservable {
    private UserModel userModel;
    private CallAnotherActivityNavigator navigator;

    @Bindable
    private ObservableField<String> inputUsername;
    private ObservableField<String> inputPassword;
    public CreateAccountViewModel(CallAnotherActivityNavigator navigator){
        userModel=new UserModel("", "");
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

    public void onButtonSignUpClicked(){
        userModel.setUsername(inputUsername.get());
        userModel.setPassword(inputPassword.get());
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/create").newBuilder();
        urlBuilder.addQueryParameter("username", userModel.getUsername());
        urlBuilder.addQueryParameter("password", userModel.getPassword());
        String url = urlBuilder.build().toString();
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
                ResponseModel resp=gson.fromJson(myResponse, ResponseModel.class);
                //create success
                if(resp.getUsername_success().equalsIgnoreCase("true")){
//                    navigator.showToast("Signed Up Successfully");
                    navigator.finishActivity();
                }
                else{
                    //실패
//                    navigator.showToast("username already exist");
                }
            }
        });
    }
}

