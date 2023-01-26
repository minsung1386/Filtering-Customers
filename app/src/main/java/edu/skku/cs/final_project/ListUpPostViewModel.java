package edu.skku.cs.final_project;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListUpPostViewModel extends BaseObservable {
    private String username;
    private ObservableArrayList<ListItemViewModel> listItemViewModels;

    CallAnotherActivityNavigator navigator;

    public ListUpPostViewModel(CallAnotherActivityNavigator navigator, String username){
        this.navigator=navigator;
        this.username=username;
        this.searchUsername=new ObservableField<>("");
        this.searchPhoneNum=new ObservableField<>("");
        listItemViewModels=new ObservableArrayList<>();
    }

    public ObservableArrayList<ListItemViewModel> getListItemViewModels(){
        return this.listItemViewModels;
    }

    public void myPost(){
        listItemViewModels.clear();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/my_post").newBuilder();
        urlBuilder.addQueryParameter("author", username);
        String url=urlBuilder.build().toString();
        Request req=new Request.Builder().url(url).build();
        OkHttpClient client=new OkHttpClient();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse = response.body().string();
                try {
                    JSONArray jsonResponse=new JSONArray(myResponse);
                    for(int i=0;i<jsonResponse.length();i++){
                        JSONObject jsonObject=jsonResponse.getJSONObject(i);
                        ListItemViewModel item=new ListItemViewModel(jsonObject.get("Date").toString(), jsonObject.get("Title").toString(),jsonObject.get("Views").toString(), jsonObject.get("Key").toString());
                        listItemViewModels.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    // Search Button
    public void searchPost(){
        listItemViewModels.clear();
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/search_post").newBuilder();
        urlBuilder.addQueryParameter("username", searchUsername.get());
        urlBuilder.addQueryParameter("phoneNum", searchPhoneNum.get());
        String url=urlBuilder.build().toString();
        Log.v("로그", "search post url: "+url);
        Request req=new Request.Builder().url(url).build();
        OkHttpClient client=new OkHttpClient();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String myResponse=response.body().string();
                try {
                    JSONArray jsonResponse=new JSONArray(myResponse);
                    for(int i=0;i<jsonResponse.length();i++){
                        JSONObject jsonObject=jsonResponse.getJSONObject(i);
                        ListItemViewModel item=new ListItemViewModel(jsonObject.get("Date").toString(), jsonObject.get("Title").toString(),jsonObject.get("Views").toString(), jsonObject.get("Key").toString());
                        listItemViewModels.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Bindable
    private ObservableField<String> searchUsername;
    private ObservableField<String> searchPhoneNum;

    public ObservableField<String> getSearchUsername() {
        return searchUsername;
    }
    public void setSearchUsername(ObservableField<String> searchUsername) {
        this.searchUsername = searchUsername;
    }
    public ObservableField<String> getSearchPhoneNum() {
        return searchPhoneNum;
    }
    public void setSearchPhoneNum(ObservableField<String> searchPhoneNum) {
        this.searchPhoneNum = searchPhoneNum;
    }

}
