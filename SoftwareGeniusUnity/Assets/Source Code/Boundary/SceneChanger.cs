using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneChanger : MonoBehaviour
{
    public static bool isAdmin;
    //[SerializeField] private string level;
    public void ButtonMoveScene(string level)
    {
        SceneManager.LoadScene(level);
    }

    public void toSignUpAdmin()
    {
        isAdmin = true;
        SceneManager.LoadScene("SignUpAdmin");
        //StaticVariable.isStudent = false;
    }

    public void toSignUpStudent()
    {
        isAdmin = false;
        SceneManager.LoadScene("SignUpStudent");
        //StaticVariable.isStudent = true;
    }

    public void toProfilePage()
    {
        SceneManager.LoadScene("Profile");
    }
    public void toAddQuestionScene()
    {
        SceneManager.LoadScene("AddQuestion");
    }

    public void toMainScene()
    {
        if (StaticVariable.isStudent)
        {
            SceneManager.LoadScene("Mode");
        }
        else
        {
            SceneManager.LoadScene("TeacherMode");
        }
        StaticVariable.scene = "Mode";
    }

    public void toLeaderBoard()
    {
        SceneManager.LoadScene("LeaderBoard");
    }

    public void profilePageTransferScene()
    {
        if (StaticVariable.isFromLeaderboard == true)
        {
            SceneManager.LoadScene("LeaderBoard");
            StaticVariable.isFromLeaderboard = false;

        }
        else
        {
            SceneManager.LoadScene("Mode");
        }
    }

    public void reportPageTransferScene()
    {
        if (StaticVariable.isFromReportList == false)
        {
            SceneManager.LoadScene("Mode");
        }


        else {
            StaticVariable.isFromReportList = false;
            SceneManager.LoadScene("AllReport");
        }
    }

    public void toIndiReport()
    {
        StaticVariable.isFromReportList = false;
        SceneManager.LoadScene("IndiReport");
    }

    public void toAllReport()
    {
        SceneManager.LoadScene("AllReport");
        StaticVariable.scene = "AllReport";
    }

    public void toQuestionTank()
    {
        SceneManager.LoadScene("Questions");
    }
    public void SignUpOptions()
    {
        SceneManager.LoadScene("SignUpOptions");
    }

    public void SignUpStudent()
    {
        SceneManager.LoadScene("SignUpStudent");
    }

    public void SignUpAdmin()
    {
        SceneManager.LoadScene("SignUpAdmin");
    }

    public void Login()
    {
        SceneManager.LoadScene("Login");
    }
    public void toGame()
    {
        SceneManager.LoadScene("1level");
    }
    public void toOverallReport()
    {
        SceneManager.LoadScene("OverallReport");
    }
}
