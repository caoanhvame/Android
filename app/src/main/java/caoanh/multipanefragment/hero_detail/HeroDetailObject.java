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
    private List<Ability> abilities = new ArrayList<>();
    private List<Skill> talents = new ArrayList<>();

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    int getAttackMinIni() {
        return attackMinIni;
    }

    void setAttackMinIni(int attackMinIni) {
        this.attackMinIni = attackMinIni;
    }

    int getAttackMaxIni() {
        return attackMaxIni;
    }

    void setAttackMaxIni(int attackMaxIni) {
        this.attackMaxIni = attackMaxIni;
    }

    int getDefenseIni() {
        return defenseIni;
    }

    void setDefenseIni(int defenseIni) {
        this.defenseIni = defenseIni;
    }

    int getStrengthIni() {
        return strengthIni;
    }

    void setStrengthIni(int strengthIni) {
        this.strengthIni = strengthIni;
    }

    int getAgilityIni() {
        return agilityIni;
    }

    void setAgilityIni(int agilityIni) {
        this.agilityIni = agilityIni;
    }

    int getIntelligenceIni() {
        return intelligenceIni;
    }

    void setIntelligenceIni(int intelligenceIni) {
        this.intelligenceIni = intelligenceIni;
    }

    double getStrengthGain() {
        return strengthGain;
    }

    void setStrengthGain(double strengthGain) {
        this.strengthGain = strengthGain;
    }

    double getAgilityGain() {
        return agilityGain;
    }

    void setAgilityGain(double agilityGain) {
        this.agilityGain = agilityGain;
    }

    double getIntelligenceGain() {
        return intelligenceGain;
    }

    void setIntelligenceGain(double intelligenceGain) {
        this.intelligenceGain = intelligenceGain;
    }

    String getBio() {
        return bio;
    }

    void setBio(String bio) {
        this.bio = bio;
    }

    double getTurnRate() {
        return turnRate;
    }

    void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

    int getAttackRange() {
        return attackRange;
    }

    void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    double getBaseAttackTime() {
        return baseAttackTime;
    }

    void setBaseAttackTime(double baseAttackTime) {
        this.baseAttackTime = baseAttackTime;
    }

    List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(Ability ability) {
        abilities.add(ability);
    }

    List<Skill> getTalents() {
        return talents;
    }

    public void setTalents(List<Skill> talents) {
        this.talents = talents;
    }

    public void addTalents(Skill talent) {
        talents.add(talent);
    }
}
