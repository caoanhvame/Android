package caoanh.multipanefragment.list_history;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anhhpc on 10/4/2017.
 */

public class Player implements Serializable{
    private String accountId;
    private int playerSlot;
    private int heroId;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int backpack0;
    private int backpack1;
    private int backpack2;
    private int kills;
    private int deaths;
    private int assists;
    private int leaver_status;
    private int lastHits;
    private int denies;
    private int gpm;
    private int xpm;
    private int level;
    private int heroDamage;
    private int towerDamage;
    private int heroHealing;
    private int gold;
    private int goldSpent;
    private ArrayList<AbilityUpgrade> abilityUpgradeUpgrades = new ArrayList<>();

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getPlayerSlot() {
        return playerSlot;
    }

    public void setPlayerSlot(int playerSlot) {
        this.playerSlot = playerSlot;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getItem0() {
        return item0;
    }

    public void setItem0(int item0) {
        this.item0 = item0;
    }

    public int getItem1() {
        return item1;
    }

    public void setItem1(int item1) {
        this.item1 = item1;
    }

    public int getItem2() {
        return item2;
    }

    public void setItem2(int item2) {
        this.item2 = item2;
    }

    public int getItem3() {
        return item3;
    }

    public void setItem3(int item3) {
        this.item3 = item3;
    }

    public int getItem4() {
        return item4;
    }

    public void setItem4(int item4) {
        this.item4 = item4;
    }

    public int getItem5() {
        return item5;
    }

    public void setItem5(int item5) {
        this.item5 = item5;
    }

    public int getBackpack0() {
        return backpack0;
    }

    public void setBackpack0(int backpack0) {
        this.backpack0 = backpack0;
    }

    public int getBackpack1() {
        return backpack1;
    }

    public void setBackpack1(int backpack1) {
        this.backpack1 = backpack1;
    }

    public int getBackpack2() {
        return backpack2;
    }

    public void setBackpack2(int backpack2) {
        this.backpack2 = backpack2;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getLeaver_status() {
        return leaver_status;
    }

    public void setLeaver_status(int leaver_status) {
        this.leaver_status = leaver_status;
    }

    public int getLastHits() {
        return lastHits;
    }

    public void setLastHits(int lastHits) {
        this.lastHits = lastHits;
    }

    public int getDenies() {
        return denies;
    }

    public void setDenies(int denies) {
        this.denies = denies;
    }

    public int getGpm() {
        return gpm;
    }

    public void setGpm(int gpm) {
        this.gpm = gpm;
    }

    public int getXpm() {
        return xpm;
    }

    public void setXpm(int xpm) {
        this.xpm = xpm;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHeroDamage() {
        return heroDamage;
    }

    public void setHeroDamage(int heroDamage) {
        this.heroDamage = heroDamage;
    }

    public int getTowerDamage() {
        return towerDamage;
    }

    public void setTowerDamage(int towerDamage) {
        this.towerDamage = towerDamage;
    }

    public int getHeroHealing() {
        return heroHealing;
    }

    public void setHeroHealing(int heroHealing) {
        this.heroHealing = heroHealing;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(int goldSpent) {
        this.goldSpent = goldSpent;
    }

    public ArrayList<AbilityUpgrade> getAbilityUpgradeUpgrades() {
        return abilityUpgradeUpgrades;
    }

    public void addAbilityUpgrades (AbilityUpgrade abilityUpgrade){
        abilityUpgradeUpgrades.add(abilityUpgrade);
    }

    public void setAbilityUpgradeUpgrades(ArrayList<AbilityUpgrade> abilityUpgradeUpgrades) {
        this.abilityUpgradeUpgrades = abilityUpgradeUpgrades;
    }
}
