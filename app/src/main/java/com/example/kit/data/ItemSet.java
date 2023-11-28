package com.example.kit.data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Data Collection representing a collection of {@link Item}, that can calculate the total value
 * of the items in the set.
 */
public class ItemSet {
    private final ArrayList<Item> items;

    /**
     * Basic constructor, initializes an empty set of Items
     */
    public ItemSet() {
        items = new ArrayList<>();
    }

    /**
     * Gets an {@link Item} at a given position in the ItemSet
     * @param position Position of the item.
     * @return {@link Item} at the given position.
     */
    public Item getItem(int position) {
        return items.get(position);
    }

    /**
     * Add a {@link Item} to the ItemSet via an item and the ID
     * @param item The Item to be added
     */
    public void addItem(Item item){
        items.add(item);
    }

    /**
     * Removes an {@link Item} from the ItemSet
     * @param item {@link Item} to removed
     */
    public void removeItem(Item item) {
        items.remove(item);
    }

    /**
     * Returns the number of {@link Item}s in the ItemSet
     * @return The number of items in the set
     */
    public int getItemCount() {
        return items.size();
    }

    /**
     * Clear the ItemSet
     */
    public void clear() {
        items.clear();
    }

    /**
     * Calculate the total value of the {@link Item}s within this ItemSet.
     * @return Returns a BigDecimal value of the items within the ItemSet.
     */
    public BigDecimal getItemSetValue() {
        BigDecimal totalValue = new BigDecimal(0);
        for (Item item : items) {
            totalValue = totalValue.add(item.valueToBigDecimal());
        }
        return totalValue;
    }

    public ItemSet filterByKeyword(String keyword) {
        ItemSet filteredSet = new ItemSet();
        String trimmedKeyword = keyword.trim();

        filteredSet.items.addAll(
                this.items.stream()
                        .filter(item -> containsIgnoreCase(item.getName(), trimmedKeyword) ||
                                containsIgnoreCase(item.getDescription(), trimmedKeyword))
                        .collect(Collectors.toList())
        );
        return filteredSet;
    }

    private boolean containsIgnoreCase(String source, String toFind) {
        return source != null && toFind != null &&
                source.trim().toLowerCase().contains(toFind.toLowerCase());
    }

    public ItemSet filterByDateRange(String lowerDateString, String upperDateString) {
        ItemSet filteredSet = new ItemSet();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try {
            Date lowerDate = lowerDateString.isEmpty() ? null : dateFormat.parse(lowerDateString);
            Date upperDate = upperDateString.isEmpty() ? null : dateFormat.parse(upperDateString);

            filteredSet.items.addAll(
                    this.items.stream()
                            .filter(item -> {
                                Date acquisitionDate = item.getAcquisitionDate().toDate();
                                return (lowerDate == null || acquisitionDate.equals(lowerDate) || acquisitionDate.after(lowerDate)) &&
                                        (upperDate == null || acquisitionDate.equals(upperDate) || acquisitionDate.before(upperDate));
                            })
                            .collect(Collectors.toList())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return filteredSet;
    }

    public ItemSet filterByPriceRange(String lowerPriceString, String upperPriceString) {
        ItemSet filteredSet = new ItemSet();
        BigDecimal lowerPrice = new BigDecimal(lowerPriceString.isEmpty() ? "0" : lowerPriceString);
        BigDecimal upperPrice = new BigDecimal(upperPriceString.isEmpty() ? String.valueOf(BigDecimal.valueOf(Long.MAX_VALUE)) : upperPriceString);

        filteredSet.items.addAll(
                this.items.stream()
                        .filter(item -> {
                            BigDecimal itemValue = item.valueToBigDecimal();
                            return (itemValue.compareTo(lowerPrice) >= 0) &&
                                    (itemValue.compareTo(upperPrice) <= 0);
                        })
                        .collect(Collectors.toList())
        );

        return filteredSet;
    }


}
