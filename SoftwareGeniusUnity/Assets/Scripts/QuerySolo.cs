using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using SimpleJSON;
using System.Security.Cryptography.X509Certificates;
using UnityEngine.Events;

public class QuerySolo : MonoBehaviour
{
    [SerializeField] GameEvent events = null;
    private int worldType = 0;//not synchronized for now
    private string worldCode;
    private int userId = 1;
    public static string landListStr;
    public static Lands landList;

    // Start is called before the first frame update
    void Start()
    {
        //worldType = events.WorldType;
        worldCode = getWorldTypeStr(worldType);
        // url for getLandsByUserIdAndCategory/
        string url1 = "http://localhost:9090/api/world/getLandsByUserIdAndCategory/" + userId.ToString() + "/" + worldCode;
        Debug.Log("url1:" + url1);
        StartCoroutine(GetLandsRequest(url1));


    }

    private string getWorldTypeStr(int num)
    {
        switch (num)
        {
            case 0:
                return "SE";
            case 1:
                return "SA";
            case 2:
                return "QA";
            case 3:
                return "PM";
            default:
                return "";

        }
    }

    IEnumerator GetLandsRequest(string url)
    {
        UnityWebRequest webRequest = UnityWebRequest.Get(url);
        yield return webRequest.SendWebRequest();

        if (webRequest.isNetworkError || webRequest.isHttpError)
        {
            Debug.Log("Error: " + webRequest.error);
            yield break;
        }

        if (webRequest.downloadHandler.text == "")
        {
            Debug.Log("World has not been unlocked");
            yield break;
        }
        JSONNode rawJson = JSON.Parse(webRequest.downloadHandler.text);
        landListStr = "{\"landList\":" + rawJson.ToString() + "}";
        Debug.Log("Land List string:" + landListStr);

        landList = JsonUtility.FromJson<Lands>(landListStr);

    }
    public class Lands
    {
        public List<LandInfo> landList;
    }

    public class LandInfo
    {
        public int landId;//1-24
        public int ind;//0-23
        public int worldId;
        public int ownerId;
        public int ownerDifficulty;
        /*
        public static LandInfo CreateFromJSON(string jsonString)
        {
            return JsonUtility.FromJson<LandInfo>(jsonString);
        }
        */
        // Given JSON input:
        // {"name":"Dr Charles","lives":3,"health":0.8}
        // this example will return a PlayerInfo object with
        // name == "Dr Charles", lives == 3, and health == 0.8f.
    }
}
/*
 * 0:SE 1:SA 2:QA 3:PM
 * localhost:9090/api/world/getLandsByUserIdAndCategory/1/SE
 * 
 */
