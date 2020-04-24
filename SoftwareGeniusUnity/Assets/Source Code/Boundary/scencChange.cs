using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.Networking;

public class scencChange : MonoBehaviour
{

    [SerializeField] GameEvent events = null;

    IEnumerator sendLockWorldInfo(string cateogy)
    {
        string WorldLockUrl = StaticVariable.url + "world/unlock/" + StaticVariable.studentId + "/" + cateogy;


        UnityWebRequest GetRequest = UnityWebRequest.Get(WorldLockUrl);
        yield return GetRequest.SendWebRequest();


        if (GetRequest.isNetworkError || GetRequest.isHttpError)
        {
            Debug.LogError(GetRequest.error);
            yield break;
        }
    }

    public void onClickSA()
    {
        if (StaticVariable.isSAunlock || StaticVariable.checkIfCanUnlock)
        {
            if (StaticVariable.checkIfCanUnlock & !StaticVariable.isSAunlock)
            {
                StartCoroutine(sendLockWorldInfo("SA"));
            }
            events.WorldType = 1;
            events.mode = "battle";
            LandCtrl.standingOn = 0;
            SceneManager.LoadScene("MapSolo");
        }
    }
    public void onClickPM()
    {
        if (StaticVariable.isPMunlock || StaticVariable.checkIfCanUnlock)
        {
            if (StaticVariable.checkIfCanUnlock & !StaticVariable.isPMunlock)
            {
                StartCoroutine(sendLockWorldInfo("PM"));
            }

            events.WorldType = 2;
            events.mode = "battle";
            LandCtrl.standingOn = 0;
            SceneManager.LoadScene("MapSolo");
        }
    }
    public void onClickQA()
    {
        Debug.Log(StaticVariable.checkIfCanUnlock);
        if (StaticVariable.isQAunlock || StaticVariable.checkIfCanUnlock)
        {
            if (StaticVariable.checkIfCanUnlock & !StaticVariable.isQAunlock)
            {
                StartCoroutine(sendLockWorldInfo("QA"));
            }

            events.WorldType = 3;
            events.mode = "battle";
            LandCtrl.standingOn = 0;
            SceneManager.LoadScene("MapSolo");
        }
    }
    public void onClickSE()
    {
        Debug.Log(StaticVariable.checkIfCanUnlock);
        if (StaticVariable.isSEunlock || StaticVariable.checkIfCanUnlock)
        {
            if (StaticVariable.checkIfCanUnlock & !StaticVariable.isSEunlock)
            {
                Debug.Log("SE unlock");
                StartCoroutine(sendLockWorldInfo("SE"));
            }

            events.WorldType = 0;
            events.mode = "battle";
            LandCtrl.standingOn = 0;
            SceneManager.LoadScene("MapSolo");
        }
    }

}