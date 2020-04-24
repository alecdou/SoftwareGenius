using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;

public class WorldLockManager : MonoBehaviour
{
    private readonly string backendurl = StaticVariable.url + "world/unlock/checkUnlockedLands/";
    private Transform PMlockcontrol;
    private Transform QAlockcontrol;
    private Transform SAlockcontrol;
    private Transform SElockcontrol;
    private Button PM_Btn;
    private Button QA_Btn;
    private Button SA_Btn;
    private Button SE_Btn;
    [SerializeField] public AlertWindowForMode promptWindow;

    private

    IEnumerator getLockWorldInfo()
    {
        string WorldLockUrl = backendurl + StaticVariable.studentId;
        string lockedWorld = "";


        UnityWebRequest GetRequest = UnityWebRequest.Get(WorldLockUrl);
        yield return GetRequest.SendWebRequest();


        if (GetRequest.isNetworkError || GetRequest.isHttpError)
        {
            Debug.LogError(GetRequest.error);
            yield break;
        }

        JSONNode WorldLockInfo = JSON.Parse(GetRequest.downloadHandler.text);

        //get unlock world a mask
        int SEworldId = WorldLockInfo["SE"];
        if (SEworldId >= 0)
        {
            SElockcontrol.gameObject.SetActive(false);
            StaticVariable.isSEunlock = true;
        }
        else
        {
            StaticVariable.isSEunlock = false;
            lockedWorld += "Software Engineer\n";
        }
        int QAworldId = WorldLockInfo["QA"];
        if (QAworldId >= 0)
        {
            QAlockcontrol.gameObject.SetActive(false);
            StaticVariable.isQAunlock = true;
        }
        else
        {
            StaticVariable.isQAunlock = false;
            lockedWorld += "Quality Assurance\n";
        }
        int PMworldId = WorldLockInfo["PM"];
        if (PMworldId >= 0)
        {
            PMlockcontrol.gameObject.SetActive(false);
            StaticVariable.isPMunlock = true;
        }
        else
        {
            StaticVariable.isPMunlock = false;
            lockedWorld += "Project Management\n";
        }
        int SAworldId = WorldLockInfo["SA"];
        if (SAworldId >= 0)
        {
            SAlockcontrol.gameObject.SetActive(false);
            StaticVariable.isSAunlock = true;
        }
        else
        {
            StaticVariable.isSAunlock = false;
            lockedWorld += "Software Architecture";
        }

        bool checkIfCanUnlock = true;
        if (!(StaticVariable.isSEunlock & StaticVariable.isQAunlock & StaticVariable.isSAunlock & StaticVariable.isPMunlock))
        {
            //all the other world > 6
            if (StaticVariable.isPMunlock & PMworldId <= 5)
            {
                checkIfCanUnlock = false;
                Debug.Log("PM");
            }

            if (StaticVariable.isSEunlock & SEworldId <= 5)
            {
                checkIfCanUnlock = false;
                Debug.Log("SE");
            }

            if (StaticVariable.isSAunlock & SAworldId <= 5)
            {
                checkIfCanUnlock = false;
                Debug.Log("SA");
            }

            if (StaticVariable.isQAunlock & QAworldId <= 5)
            {
                Debug.Log("canlock" + QAworldId);
                checkIfCanUnlock = false;
            }
        }
        else
        {
            checkIfCanUnlock = false;
        }

        StaticVariable.checkIfCanUnlock = checkIfCanUnlock;
        Debug.Log(checkIfCanUnlock);
        if (checkIfCanUnlock)
        {
            promptWindow.Show("You can select a world to unlock:\n" + lockedWorld);
            SElockcontrol.gameObject.SetActive(false);
            SAlockcontrol.gameObject.SetActive(false);
            PMlockcontrol.gameObject.SetActive(false);
            QAlockcontrol.gameObject.SetActive(false);
        }


    }



    // Start is called before the first frame update
    void Start()
    {
        PMlockcontrol = transform.Find("PMLock");
        QAlockcontrol = transform.Find("QALock");
        SAlockcontrol = transform.Find("SALock");
        SElockcontrol = transform.Find("SELock");

        PM_Btn = transform.Find("PM_Btn").GetComponent<Button>();
        QA_Btn = transform.Find("QA_Btn").GetComponent<Button>();
        SA_Btn = transform.Find("SA_Btn").GetComponent<Button>();
        SE_Btn = transform.Find("SE_Btn").GetComponent<Button>();

        StartCoroutine(getLockWorldInfo());

    }

}

   
