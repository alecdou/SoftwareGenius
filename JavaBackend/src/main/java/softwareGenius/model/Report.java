package softwareGenius.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


// TODO: changed according character and user
public class Report {

    /** The type of the report: admin or player. */
    private String type; // ??

    /** The id of the player who the report describes. */
    private Integer userId;

    private String userEmail; // why do we need email?

    List<LocalDateTime> loginRecords;

    /** The average online time of a player */
    Duration avgGameTime;

    // TODO: changed according to character model
    private Float accuracy;


    private Integer overallExp;

    List<Character> characterList;

    public Report() {
    }


}
