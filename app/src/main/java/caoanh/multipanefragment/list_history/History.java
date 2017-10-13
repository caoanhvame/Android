package caoanh.multipanefragment.list_history;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by anhhpc on 9/21/2017.
 */

public class History implements Serializable{
    private String matchId;
    private int playerHeroId;
    private String startTime;
    private int duration;
    private boolean radiantWin;
    private ArrayList<Player> players = new ArrayList<>();
    private int gameMode;
    private int radiantScore;
    private int direScore;

    public int getRadiantScore() {
        return radiantScore;
    }

    public void setRadiantScore(int radiantScore) {
        this.radiantScore = radiantScore;
    }

    public int getDireScore() {
        return direScore;
    }

    public void setDireScore(int direScore) {
        this.direScore = direScore;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayers(Player player) {
        players.add(player);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getPlayerHeroId() {
        return playerHeroId;
    }

    public void setPlayerHeroId(int playerHeroId) {
        this.playerHeroId = playerHeroId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isRadiantWin() {
        return radiantWin;
    }

    public void setRadiantWin(boolean radiantWin) {
        this.radiantWin = radiantWin;
    }
}
