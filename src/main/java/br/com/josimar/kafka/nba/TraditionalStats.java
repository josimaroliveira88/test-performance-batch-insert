package br.com.josimar.kafka.nba;

public class TraditionalStats {
    private int gameId;
    private String date;
    private String type;
    private int playerId;
    private String player;
    private String team;
    private String home;
    private String away;
    private int min;
    private int pts;
    private int fgm;
    private int fga;
    private double fgPct;
    private int threePm;
    private int threePa;
    private double threePct;
    private int ftm;
    private int fta;
    private double ftPct;
    private int oreb;
    private int dreb;
    private int reb;
    private int ast;
    private int stl;
    private int blk;
    private int tov;
    private int pf;
    private int plusMinus;
    private boolean win;
    private String season;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getFgm() {
        return fgm;
    }

    public void setFgm(int fgm) {
        this.fgm = fgm;
    }

    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    public double getFgPct() {
        return fgPct;
    }

    public void setFgPct(double fgPct) {
        this.fgPct = fgPct;
    }

    public int getThreePm() {
        return threePm;
    }

    public void setThreePm(int threePm) {
        this.threePm = threePm;
    }

    public int getThreePa() {
        return threePa;
    }

    public void setThreePa(int threePa) {
        this.threePa = threePa;
    }

    public double getThreePct() {
        return threePct;
    }

    public void setThreePct(double threePct) {
        this.threePct = threePct;
    }

    public int getFtm() {
        return ftm;
    }

    public void setFtm(int ftm) {
        this.ftm = ftm;
    }

    public int getFta() {
        return fta;
    }

    public void setFta(int fta) {
        this.fta = fta;
    }

    public double getFtPct() {
        return ftPct;
    }

    public void setFtPct(double ftPct) {
        this.ftPct = ftPct;
    }

    public int getOreb() {
        return oreb;
    }

    public void setOreb(int oreb) {
        this.oreb = oreb;
    }

    public int getDreb() {
        return dreb;
    }

    public void setDreb(int dreb) {
        this.dreb = dreb;
    }

    public int getReb() {
        return reb;
    }

    public void setReb(int reb) {
        this.reb = reb;
    }

    public int getAst() {
        return ast;
    }

    public void setAst(int ast) {
        this.ast = ast;
    }

    public int getStl() {
        return stl;
    }

    public void setStl(int stl) {
        this.stl = stl;
    }

    public int getBlk() {
        return blk;
    }

    public void setBlk(int blk) {
        this.blk = blk;
    }

    public int getTov() {
        return tov;
    }

    public void setTov(int tov) {
        this.tov = tov;
    }

    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    public int getPlusMinus() {
        return plusMinus;
    }

    public void setPlusMinus(int plusMinus) {
        this.plusMinus = plusMinus;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Override
    public String toString() {
        return "TraditionalStats [gameId=" + gameId + ", date=" + date + ", type=" + type + ", playerId=" + playerId
                + ", player=" + player + ", team=" + team + ", home=" + home + ", away=" + away + "]";
    }

}
