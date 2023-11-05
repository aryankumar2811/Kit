package com.example.kit.database;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kit.R;
import com.example.kit.data.Item;
import com.example.kit.databinding.ItemListRowBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private final ItemListRowBinding binding;

    public ItemViewHolder(ItemListRowBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    // TODO: Expand
    public void displayItem(Item item){
        // Fill in item row values
        DateFormat df = DateFormat.getDateTimeInstance();
        binding.itemNameRow.setText(item.getName());
        binding.itemDateRow.setText(df.format(item.getAcquisitionDate().toDate()));
        binding.itemValueRow.setText(item.getValue());

        ArrayList<String> tags = item.getTags();

        // Populate chips with tags
        int num_chips = binding.itemTagGroupRow.getChildCount();
        for(int i = 0; i < num_chips; i++) {
            Chip chip = (Chip) binding.itemTagGroupRow.getChildAt(i);

            // Hide the unused chips
            if(i >= tags.size()) {
                chip.setText("");
                chip.setVisibility(View.GONE);
                continue;
            }

            chip.setText(tags.get(i));
            chip.setVisibility(View.VISIBLE);
        }
    }
}
