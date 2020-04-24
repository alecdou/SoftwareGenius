using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;
using System;



public class LevelManagement : MonoBehaviour
{


    [SerializeField] GameEvent events = null;
    [SerializeField] Button EasyButton = null;
    [SerializeField] Button MediumButton = null;
    [SerializeField] Button HardButton = null;

    public static int PastLevel;
    private void Start()
    {
        if (PastLevel == 1)//Easy
        {
            EasyButton.enabled = false;
        }
        else if (PastLevel == 2) //Medium
        {
            EasyButton.enabled = false;
            MediumButton.enabled = false;
        }
        else if (PastLevel == 3)//Hard
        {
            EasyButton.enabled = false;
            MediumButton.enabled = false;
            HardButton.enabled = false;
        }

    }

    [System.Obsolete]
    public void onClickEasy()
    {
        events.level = 1;
        events.levelType = GameEvent.LevelType.Easy;
        SceneManager.LoadScene("2Question");
    }
    public void onClickMedium()
    {
        events.level = 2;
        events.levelType = GameEvent.LevelType.Medium;
        SceneManager.LoadScene("2Question");
    }
    public void onClickHard()
    {
        events.level = 3;
        events.levelType = GameEvent.LevelType.Hard;
        SceneManager.LoadScene("2Question");
    }
    public void onClickBack()
    {
        LandCtrl.standingOn = 0;
        if (events.mode == "battle")
            SceneManager.LoadScene("MapSolo");
        else
            SceneManager.LoadScene("MapBattle");
    }



}
