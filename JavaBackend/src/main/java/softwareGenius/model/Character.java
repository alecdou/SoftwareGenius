package softwareGenius.model;

public class Character {
    private final int charId;
    private final int userId;
    private final String charName;
    private int exp;
    private int level;
    private int attackPt;
    private int defencePt;
    private int correctQuesNo; // number of correctly answered question
    private int totalQuesNo; // number of answered question


    public Character(int charId, int userId, String charName, int exp, int level, int attackPt, int defencePt,
                     int correctQuesNo, int totalQuesNo) {
        this.charId = charId;
        this.userId = userId;
        this.charName = charName;
        this.exp = exp;
        this.level = level;
        this.attackPt = attackPt;
        this.defencePt = defencePt;
        this.correctQuesNo = correctQuesNo;
        this.totalQuesNo = totalQuesNo;
    }

    public int getCharId() {
        return charId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCharName() {
        return charName;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getAttackPt() {
        return attackPt;
    }

    public void setAttackPt(int attackPt) {
        this.attackPt = attackPt;
    }

    public int getDefencePt() {
        return defencePt;
    }

    public void setDefencePt(int defencePt) {
        this.defencePt = defencePt;
    }

    public int getCorrectQuesNo() {
        return correctQuesNo;
    }

    public void setCorrectQuesNo(int correctQuesNo) {
        this.correctQuesNo = correctQuesNo;
    }

    public int getTotalQuesNo() {
        return totalQuesNo;
    }

    public void setTotalQuesNo(int totalQuesNo) {
        this.totalQuesNo = totalQuesNo;
    }
}
