package edu.skku.cs.final_project;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewPostViewModel extends BaseObservable {
    private PostModel postModel;
    private String imgSrc="null";
    CallAnotherActivityNavigator navigator;


    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
    public String getImgSrc() {
        return imgSrc;
    }

    @Bindable
    private String imgPath="Image path shows up";
    private ObservableField<String> inputTitle;
    private ObservableField<String> inputContent;
    private ObservableField<String> inputReportedUsername;
    private ObservableField<String> inputReportedPhoneNum;

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
        notifyPropertyChanged(BR.imgPath);
    }
    public String getImgPath() {
        return imgPath;
    }
    public ObservableField<String> getInputTitle() {
        return inputTitle;
    }
    public void setInputTitle(ObservableField<String> inputTitle) {
        this.inputTitle = inputTitle;
    }
    public ObservableField<String> getInputContent() {
        return inputContent;
    }
    public void setInputContent(ObservableField<String> inputContent) { this.inputContent = inputContent; }
    public ObservableField<String> getInputReportedUsername() {
        return inputReportedUsername;
    }
    public void setInputReportedUsername(ObservableField<String> inputReportedUsername) {
        this.inputReportedUsername = inputReportedUsername;
    }
    public ObservableField<String> getInputReportedPhoneNum() {
        return inputReportedPhoneNum;
    }
    public void setInputReportedPhoneNum(ObservableField<String> inputReportedPhoneNum) {
        this.inputReportedPhoneNum = inputReportedPhoneNum;
    }

    // Constructor (View Model)
    public NewPostViewModel(CallAnotherActivityNavigator navigator, String username){
        this.navigator=navigator;
        postModel=new PostModel(username);
        this.inputTitle=new ObservableField<>("");
        this.inputContent=new ObservableField<>("");
        this.inputReportedUsername=new ObservableField<>("");
        this.inputReportedPhoneNum=new ObservableField<>("");
    }

    // Attach image file
    public void onButtonAttachClicked(){
        navigator.callNewActivity("");
    }
    // Encode selected image & store in the model
    public void encodeSelectedImage(Context context, int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK) {
            if (requestCode == 200) {
                Uri selectedImageUri = data.getData();
                File file = new File(selectedImageUri.getPath());
                setImgPath(file.getAbsolutePath());
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
                    String encodedString=ImageUtil.convert(bitmap);
                    setImgSrc(encodedString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            // 사진 고르기 취소
            navigator.showToast("Image Selection Cancelled.");
        }
    }
    // get current date for the post
    public String getDate(){
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MM_dd");
        Date date=new Date();
        return formatter.format(date);
    }
    public String getPostId(){
        String ret;
        ret=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return ret;
    }

    // Post button
    public void onButtonPostClicked() {
        postModel.setPostId(getPostId());
        postModel.setDate(getDate());
        postModel.setTitle(inputTitle.get());
        postModel.setContent(inputContent.get());
        postModel.setImgSrc(imgSrc);
        postModel.setReportedUsername(inputReportedUsername.get());
        postModel.setReportedPhoneNum(inputReportedPhoneNum.get());

        Gson gson = new Gson();
        String json = gson.toJson(postModel, PostModel.class);
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://23mwgusvk2.execute-api.ap-northeast-2.amazonaws.com/dev/add_post").newBuilder();
        String url=urlBuilder.build().toString();
        Log.v("로그", "add post url: "+url);
        Request req= new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), json)).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                Log.v("로그", "add post 실패");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.v("로그", "add post 성공, response: "+response.body().string());
            }
        });

        navigator.finishActivity();
    }
}
