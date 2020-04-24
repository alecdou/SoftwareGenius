using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LoadSceneMap : MonoBehaviour
{
    [SerializeField] GameEvent events = null;
    public void sceneLoader(int SceneIndex)
    {
        if (SceneIndex == 15)
        {
            events.mode = "battle";
        }
        SceneManager.LoadScene(SceneIndex);
    }
    public void EnterCombatSolo()
    {
        events.landID = LandCtrl.standingOn;
        SceneManager.LoadScene(13);
    }
    public void EnterCombatDuel()
    {
        events.landID = LandCtrl.standingOn;
        SceneManager.LoadScene(13);
    }
    public void EnterBattleScene()
    {
        events.mode = "duel";
        LandCtrl.standingOn = 0;//intialize character position
        SceneManager.LoadScene(16);
    }
}
