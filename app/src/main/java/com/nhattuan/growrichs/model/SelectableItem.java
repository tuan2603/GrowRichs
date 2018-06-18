package com.nhattuan.growrichs.model;

public class SelectableItem extends Song{
    private boolean isSelected = false;

    public SelectableItem(Song song, boolean isSelected) {
        super(song.getmID(), song.getmName(), song.getmTitle());
        this.isSelected = isSelected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}