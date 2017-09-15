package caoanh.multipanefragment.hero_detail;

import java.util.ArrayList;
import java.util.List;

import caoanh.multipanefragment.expandable_list_hero.HeroObject;

/**
 * Created by anhhpc on 6/6/2017.
 */

public class HeroDetailObject extends HeroObject {
    private String bio;
    private int strengthIni;
    private int agilityIni;
    private int intelligenceIni;
    private double strengthGain;
    private double agilityGain;
    private double intelligenceGain;
    private int speed;
    private int attackMinIni;
    private int attackMaxIni;
    private int defenseIni;
    private double turnRate;
    private int attackRange;
    private double baseAttackTime;
    private List<Ability> arrayList = new ArrayList<Ability>();

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackMinIni() {
        return attackMinIni;
    }

    public void setAttackMinIni(int attackMinIni) {
        this.attackMinIni = attackMinIni;
    }

    public int getAttackMaxIni() {
        return attackMaxIni;
    }

    public void setAttackMaxIni(int attackMaxIni) {
        this.attackMaxIni = attackMaxIni;
    }

    public int getDefenseIni() {
        return defenseIni;
    }

    public void setDefenseIni(int defenseIni) {
        this.defenseIni = defenseIni;
    }

    public int getStrengthIni() {
        return strengthIni;
    }

    public void setStrengthIni(int strengthIni) {
        this.strengthIni = strengthIni;
    }

    public int getAgilityIni() {
        return agilityIni;
    }

    public void setAgilityIni(int agilityIni) {
        this.agilityIni = agilityIni;
    }

    public int getIntelligenceIni() {
        return intelligenceIni;
    }

    public void setIntelligenceIni(int intelligenceIni) {
        this.intelligenceIni = intelligenceIni;
    }

    public double getStrengthGain() {
        return strengthGain;
    }

    public void setStrengthGain(double strengthGain) {
        this.strengthGain = strengthGain;
    }

    public double getAgilityGain() {
        return agilityGain;
    }

    public void setAgilityGain(double agilityGain) {
        this.agilityGain = agilityGain;
    }

    public double getIntelligenceGain() {
        return intelligenceGain;
    }

    public void setIntelligenceGain(double intelligenceGain) {
        this.intelligenceGain = intelligenceGain;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public double getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public double getBaseAttackTime() {
        return baseAttackTime;
    }

    public void setBaseAttackTime(double baseAttackTime) {
        this.baseAttackTime = baseAttackTime;
    }

    public List<Ability> getAbilityList() {
        return arrayList;
    }

    public void setArrayList(List<Ability> arrayList) {
        this.arrayList = arrayList;
    }
}
