package edu.skku.cs.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;

import edu.skku.cs.final_project.databinding.ActivityListUpPostBinding;

public class ListUpPostActivity extends AppCompatActivity implements CallAnotherActivityNavigator {
    ListUpPostViewModel listUpPostViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String username = intent.getStringExtra("Username");
        listUpPostViewModel=new ListUpPostViewModel(this, username);

        ActivityListUpPostBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_list_up_post);
        binding.setListUpPostViewModel(listUpPostViewModel);

    }

    @BindingAdapter("app:items")
    public static void addPost(ListView listView, ObservableArrayList<ListItemViewModel> listItemViewModels){
        ListViewAdapter adapter;

        if(listView.getAdapter()==null){
            adapter=new ListViewAdapter();
            listView.setAdapter(adapter);
        }
        else{
            adapter=(ListViewAdapter) listView.getAdapter();
        }
        adapter.addAll(listItemViewModels);
    }



    @Override
    public void callNewActivity(String username) {

    }

    @Override
    public void callAnotherActivity(String username) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void finishActivity() {

    }
}