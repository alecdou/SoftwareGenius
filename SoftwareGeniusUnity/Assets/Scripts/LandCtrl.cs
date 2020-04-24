using SimpleJSON;
using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.UI;


public class LandCtrl : MonoBehaviour
{
    public static int standingOn = 0;//position of charac!!
    public static int worldId;
    public static int opponentId = 0;//initialize as 0
    public static string worldCode;
    [SerializeField] GameEvent events = null;//set for "Game{}"

    private List<Land> Map;//list of UI objects

    [SerializeField]//set for "Land"
    private GameObject buttonTemplate;
    [SerializeField]//set for "MapButtons"
    private GridLayoutGroup gridGroup;
    [SerializeField]//set for "Image"
    private Sprite[] iconSprites;

    private int worldType = 0;//as is in gameevents

    public static int userId;//player's id as is in gameevents
    private int worldOwnerId;//to judge solo or duel mode
    private string landListStr;
    private Lands landList;


    // Start is called before the first frame update
    void Start()
    {
        worldType = events.WorldType;
        Debug.Log("worldtype:" + getWorldTypeStr(worldType));
        userId = Int32.Parse(StaticVariable.studentId);
        worldCode = getWorldTypeStr(worldType);

        if (events.mode == "battle")//solo mode
        {
            worldOwnerId = userId;
        }
        else
        {
            worldOwnerId = opponentId;
        }
        string url1 = StaticVariable.url + "world/getLandsByUserIdAndCategory/" +
                worldOwnerId.ToString() + "/" + worldCode;
        Debug.Log("url1:" + url1);
        StartCoroutine(GetLandsRequest(url1));

    }
    void GenMap()
    {

        foreach (Land newLand in Map)
        {
            GameObject newButton = Instantiate(buttonTemplate) as GameObject;
            newButton.SetActive(true);

            newButton.GetComponent<LandBtn>().SetIcon(newLand.iconSprite);
            newButton.GetComponent<LandBtn>().SetText(newLand.owner);
            newButton.GetComponent<LandBtn>().SetNo(newLand.landNo);
            newButton.GetComponent<LandBtn>().SetStatus(newLand.status);
            newButton.GetComponent<LandBtn>().SetDifficulty(newLand.difficulty);
            newButton.GetComponent<LandBtn>().SetLandId(newLand.landId);
            newButton.GetComponent<LandBtn>().SetCurrentLevel(newLand.level);

            newButton.transform.SetParent(buttonTemplate.transform.parent, false);
        }
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
                return "PM";
            case 3:
                return "QA";
            default:
                return "";

        }
    }

    private string getDifficultyStr(int num)
    {
        switch (num)
        {
            case 1:
                return "easy";
            case 2:
                return "medium";
            case 3:
                return "hard";
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
        landListStr = "{\"listOfLands\":" + rawJson.ToString() + "}";
        Debug.Log("Land List:" + rawJson.ToString());

        landList = JsonUtility.FromJson<Lands>(landListStr);

        Map = new List<Land>();
        Debug.Log("count:" + landList.listOfLands.Count);
        standingOn = landList.listOfLands[0].landId;
        //if events.mode=="battle"/"duel"

        foreach (LandInfo landInfo in landList.listOfLands)
        {
            Land newLand = new Land();
            newLand.landNo = landInfo.ind;
            newLand.landId = landInfo.landId;
            newLand.level = landInfo.ownerDifficultyLevel;
            worldId = landInfo.worldId;

            if (landInfo.ownerId == userId)
            {
                newLand.iconSprite = iconSprites[0];//landOwnerOccupy
                newLand.status = 0;
                //newLand.owner = landInfo.ownerName;
                newLand.difficulty = getDifficultyStr(landInfo.ownerDifficultyLevel);
            }
            else if (landInfo.ownerId == 0 && events.mode == "battle")
            {
                newLand.iconSprite = iconSprites[1];//land Own World Isolated
                newLand.status = 1;
                //newLand.difficulty = "";
                //newLand.owner = landInfo.landId.ToString();
            }
            else if (landInfo.ownerId != 0 && landInfo.ownerId != userId)
            {
                newLand.iconSprite = iconSprites[2];//land Others Occupy
                newLand.status = 2;
                newLand.owner = "Occupied by " + landInfo.ownerName;
                newLand.difficulty = getDifficultyStr(landInfo.ownerDifficultyLevel);
            }
            else
            {
                newLand.iconSprite = iconSprites[3];//land Others World Isolated
                newLand.status = 3;
                //newLand.difficulty = "";
            }
            Map.Add(newLand);
        }
        GenMap();
    }

    public class Lands
    {
        public List<LandInfo> listOfLands;
    }

    [System.Serializable]
    public class LandInfo
    {
        public int landId;//1-24
        public int ind;//0-23
        public int worldId;
        public int ownerId;
        public string ownerName;
        public int ownerDifficultyLevel;

        // Given JSON input:
        // {"name":"Dr Charles","lives":3,"health":0.8}
        // this example will return a PlayerInfo object with
        // name == "Dr Charles", lives == 3, and health == 0.8f.
    }

    public class Land
    {
        public Sprite iconSprite;
        public string owner;
        public int landNo;
        public int landId;
        public int status;
        public string difficulty;
        public int level;
    }
    [System.Serializable]
    public class UserInfo
    {
        public string username;
        private string userAvatar;
        private string password;
        private string email;
        private string accountType;
        private bool isAdmin;
        private int overallExp;
        private int id;

        public static UserInfo CreateFromJSON(string jsonString)
        {
            return JsonUtility.FromJson<UserInfo>(jsonString);
        }
    }
}
