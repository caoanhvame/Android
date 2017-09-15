package caoanh.multipanefragment.hero_detail;

/**
 * Created by anhhpc on 6/27/2017.
 */

public class Ability {
    private int id;
    private String dname;
    private String affects;
    private String desc;
    private String notes;
    private String dmg;
    private String attributes;
    private String cmb;
    private String lore;
    private String image;
    private boolean isMainSkill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getAffects() {
        return affects;
    }

    public void setAffects(String affects) {
        this.affects = affects;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDmg() {
        return dmg;
    }

    public void setDmg(String dmg) {
        this.dmg = dmg;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getCmb() {
        return cmb;
    }

    public void setCmb(String cmb) {
        this.cmb = cmb;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isMainSkill() {
        return isMainSkill;
    }

    public void setMainSkill(boolean mainSkill) {
        isMainSkill = mainSkill;
    }
}
