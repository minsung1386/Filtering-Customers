package edu.skku.cs.final_project;

import androidx.databinding.ObservableField;

public class ListItemViewModel {
    public final ObservableField<String> Date=new ObservableField<>();
    public final ObservableField<String> title=new ObservableField<>();
    public final ObservableField<String> views=new ObservableField<>();
    private String postKey;

    public ListItemViewModel(String date, String title, String views, String postKey){
        this.Date.set(date);
        this.title.set(title);
        this.views.set(views);
        this.postKey=postKey;
    }

    public String getPostKey() {
        return postKey;
    }

}
