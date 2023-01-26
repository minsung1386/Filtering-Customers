package edu.skku.cs.final_project;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;

import edu.skku.cs.final_project.databinding.ListItemBinding;

public class ListViewAdapter extends BaseAdapter {
    private ObservableArrayList<ListItemViewModel> listItemViewModels = new ObservableArrayList<>();

    public void addAll(ObservableArrayList<ListItemViewModel> listItemViewModels){
        this.listItemViewModels=listItemViewModels;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() { return listItemViewModels.size(); }
    @Override
    public Object getItem(int i) { return listItemViewModels.get(i); }
    @Override
    public long getItemId(int i) { return i; }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItemBinding binding;
        if(view==null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = DataBindingUtil.inflate(inflater, R.layout.list_item, viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding);
        }
        else{
            binding=(ListItemBinding) view.getTag();
        }

        binding.buttonView.setOnClickListener(view1 -> {
            Intent intent=new Intent(view1.getContext(), ViewPostActivity.class);
            intent.putExtra("Key", listItemViewModels.get(i).getPostKey());
            view1.getContext().startActivity(intent);
        });

        binding.setListItemViewModel(listItemViewModels.get(i));

        return view;
    }
}
