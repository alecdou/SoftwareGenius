using UnityEngine;

public class StaticVariable : MonoBehaviour
{
    //keeping track of previous scene
    //mainly used for profile
    //as page can be accessed through both mode & leaderboard
    static public string scene = "Mode";
    static public string nameStudent = "Angel";
    static public bool isStudent = true;
    static public string questionId;
    static public string studentId = "9";
    static public string reportId = "1";
    static public string leaderboardId;
    static public bool isFromReportList;
    static public bool isFromLeaderboard;

    static public bool isSEunlock = false;
    static public bool isSAunlock = false;
    static public bool isPMunlock = false;
    static public bool isQAunlock = false;


    static public bool checkIfCanUnlock = false;

    static public string url = "https://1a3a674f.ngrok.io/api/";
    // static public string url = "http://localhost:9090/api/";

    //defalut Mode Scene
}
