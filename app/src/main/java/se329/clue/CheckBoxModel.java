package se329.clue;

/**
 * Created by davisbatten on 4/17/16.
 */
public class CheckBoxModel {
    private String name;
    private boolean selected;

    public CheckBoxModel(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
