package edu.skku.cs.final_project;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

public class ViewPostViewModel extends BaseObservable {
    private String postKey;
    private ObservableField<String> goodMsg=new ObservableField<>("");
    private ObservableField<String> badMsg=new ObservableField<>("");
    private boolean goodBtnClick=false;
    private boolean badBtnClick=false;
    private int deltaGood=0;
    private int deltaBad=0;

    @Bindable
    private PostModel postModel=new PostModel();
    public ObservableField<Drawable> postImg=new ObservableField<>();

    public PostModel getPostModel() {
        return postModel;
    }
    public void setPostModel(PostModel postModel) {
        this.postModel = postModel;
    }
    public ObservableField<String> getGoodMsg() {
        return goodMsg;
    }
    public void setGoodMsg(ObservableField<String> goodMsg) {
        this.goodMsg = goodMsg;
    }
    public ObservableField<String> getBadMsg() {
        return badMsg;
    }
    public void setBadMsg(ObservableField<String> badMsg) {
        this.badMsg = badMsg;
    }

    public ViewPostViewModel(String postKey){
        this.postKey=postKey;
        HttpUrl.Builder urlBuilder=HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/get_post").newBuilder();
        urlBuilder.addQueryParameter("key", this.postKey);
        String url=urlBuilder.build().toString();
        OkHttpClient client=new OkHttpClient();
        Request req=new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson=new Gson();
                final String myResponse=response.body().string();
                PostModel responseModel=gson.fromJson(myResponse, PostModel.class);
                setPostModel(responseModel);
                notifyPropertyChanged(BR.postModel);
                postImg.set(new BitmapDrawable(ImageUtil.convert(postModel.getImgSrc())));
                goodMsg.set("Good: "+ postModel.getGood());
                badMsg.set("Bad: "+postModel.getBad());
            }
        });
    }

    // good Button click
    public void onClickGoodButton(){
        if(goodBtnClick) return;
        if(badBtnClick){
            badMsg.set("Bad: "+postModel.getBad());
            badBtnClick=false;
            deltaBad--;
        }
        int good = postModel.getGood();
        good+=1;
        goodMsg.set("Good: "+good);
        goodBtnClick=true;
        deltaGood++;
    }
    // bad Button click
    public void onClickBadButton(){
        if(badBtnClick) return;
        if(goodBtnClick){
            goodMsg.set("Good: "+postModel.getGood());
            goodBtnClick=false;
            deltaGood--;
        }
        int bad=postModel.getBad();
        bad+=1;
        badMsg.set("Bad: "+bad);
        badBtnClick=true;
        deltaBad++;
    }

    // update when the activity is destroyed
    public void updatePost(){
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/update_post").newBuilder();
        urlBuilder.addQueryParameter("key", postKey);
        urlBuilder.addQueryParameter("good", Integer.toString(deltaGood));
        urlBuilder.addQueryParameter("bad", Integer.toString(deltaBad));
        String url=urlBuilder.build().toString();
        Log.v("로그", "updatePost() url: "+url);
        OkHttpClient client=new OkHttpClient();
        Request req=new Request.Builder().url(url).build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.v("로그", "updatePost(): failed");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.v("로그", "updatePost(): success");
            }
        });
    }
}
