package caoanh.multipanefragment.expandable_list_item;

import java.util.ArrayList;

/**
 * Created by Cao Anh on 30/10/2016.
 */

public class ItemObject {
    private String itemKey;
    private String ID;
    private String image;
    private String name;
    private String cost;
    private String manaCost;
    private String coolDown;
    private String mainDescription;
    private String alternateDescription;
    private String extraAttribute;
    private ArrayList<String> createdFrom;
    private ArrayList<String> createdTo;
    private String lore;
    private boolean isExpand;

    public ItemObject(){

    }

    public ItemObject(String itemKey, String ID, String image, String name, String cost, String manaCost, String coolDown, String mainDescription, String alternateDescription, String extraAttribute, ArrayList<String> createdFrom, ArrayList<String> createdTo, String lore) {
        this.itemKey = itemKey;
        this.ID = ID;
        this.image = image;
        this.name = name;
        this.cost = cost;
        this.manaCost = manaCost;
        this.coolDown = coolDown;
        this.mainDescription = mainDescription;
        this.alternateDescription = alternateDescription;
        this.extraAttribute = extraAttribute;
        this.createdFrom = createdFrom;
        this.createdTo = createdTo;
        this.lore = lore;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	public String getCoolDown() {
		return coolDown;
	}

	public void setCoolDown(String coolDown) {
		this.coolDown = coolDown;
	}

	public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public String getAlternateDescription() {
        return alternateDescription;
    }

    public void setAlternateDescription(String alternateDescription) {
        this.alternateDescription = alternateDescription;
    }

    public String getExtraAttribute() {
        return extraAttribute;
    }

    public void setExtraAttribute(String extraAttribute) {
        this.extraAttribute = extraAttribute;
    }

    public ArrayList<String> getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(ArrayList<String> createdFrom) {
        this.createdFrom = createdFrom;
    }

    public ArrayList<String> getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(ArrayList<String> createdTo) {
        this.createdTo = createdTo;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }
}
