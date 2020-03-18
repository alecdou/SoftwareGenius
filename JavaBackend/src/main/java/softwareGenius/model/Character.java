package softwareGenius.model;

public class Character {
    private final Integer charId;
    private final Integer userId;
    private final String charName;
    private Integer exp;
    private Integer level;
    private Integer attackPt;
    private Integer defencePt;
    /** number of correctly answered question **/
    private Integer correctQuesNo;
    /** number of answered question **/
    private Integer totalQuesNo;


    public Character(Integer charId, Integer userId, String charName, Integer exp, Integer level, Integer attackPt,
                     Integer defencePt, Integer correctQuesNo, Integer totalQuesNo) {
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

    public Integer getCharId() {
        return charId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCharName() {
        return charName;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getAttackPt() {
        return attackPt;
    }

    public void setAttackPt(int attackPt) {
        this.attackPt = attackPt;
    }

    public Integer getDefencePt() {
        return defencePt;
    }

    public void setDefencePt(int defencePt) {
        this.defencePt = defencePt;
    }

    public Integer getCorrectQuesNo() {
        return correctQuesNo;
    }

    public void setCorrectQuesNo(int correctQuesNo) {
        this.correctQuesNo = correctQuesNo;
    }

    public Integer getTotalQuesNo() {
        return totalQuesNo;
    }

    public void setTotalQuesNo(int totalQuesNo) {
        this.totalQuesNo = totalQuesNo;
    }
}
