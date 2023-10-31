package com.example.kit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.kit.data.ItemSet;
import com.example.kit.databinding.ItemListBinding;


public class ItemListFragment extends Fragment {

    private ItemListBinding itemListBinding;
    private ItemSet itemSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        itemListBinding = ItemListBinding.inflate(inflater, container, false);
        View view = itemListBinding.getRoot();
        return view;
    }

    public void initAddItemButton() {
//        itemListBinding.addItemButton.setOnClickListener(onClick -> {

//        });
    }
}
