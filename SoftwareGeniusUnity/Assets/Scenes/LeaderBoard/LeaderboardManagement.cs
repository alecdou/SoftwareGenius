using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;
using SimpleJSON;
using UnityEngine.Networking;

public class LeaderboardManagement : MonoBehaviour
{
    private Transform rankContainer;
    private Transform rankTemplate;
    private Transform rankView;
    private List<Transform> rankEntryTransformList;
    private Text leaderboardTitle;
    private string world = "ALL";
    private readonly string backendurl = StaticVariable.url + "world/getLeaderBoard/";

    public void toSELeaderboard()
    {
        this.world = "SE";
        clearRank(rankEntryTransformList);
        leaderboardTitle.text = "Software Engineer Leaderboard";
        this.Awake();
    }

    public void toSALeaderboard()
    {
        this.world = "SA";
        clearRank(rankEntryTransformList);
        leaderboardTitle.text = "Software Architecture Leaderboard";
        this.Awake();
    }

    public void toPMLeaderboard()
    {
        this.world = "PM";
        clearRank(rankEntryTransformList);
        leaderboardTitle.text = "Product Manager Leaderboard";
        this.Awake();
    }

    public void toQALeaderboard()
    {
        this.world = "QA";
        clearRank(rankEntryTransformList);
        leaderboardTitle.text = "Quality Analysis Leaderboard";
        this.Awake();
    }

    public void toALLLeaderboard()
    {
        this.world = "ALL";
        clearRank(rankEntryTransformList);
        leaderboardTitle.text = "Leaderboard";
        this.Awake();
    }

    IEnumerator getLeaderboard(string category)
    {
        string LeaderboardUrl = backendurl;
        if (category.Equals("ALL"))
        {
            LeaderboardUrl += "general/0/10";
        }
        else
        {
            LeaderboardUrl += "category/" + category + "/0/10";
        }

        UnityWebRequest LeaderBoardRequest = UnityWebRequest.Get(LeaderboardUrl);

        yield return LeaderBoardRequest.SendWebRequest();

        if (LeaderBoardRequest.isNetworkError || LeaderBoardRequest.isHttpError)
        {
            Debug.LogError(LeaderBoardRequest.error);
            yield break;
        }

        JSONNode LeaderBoardInfo = JSON.Parse(LeaderBoardRequest.downloadHandler.text);

        string LeaderBoardjson = "{\"rankEntryList\":" + LeaderBoardInfo.ToString() + "}";

        Ranks ranks = JsonUtility.FromJson<Ranks> (LeaderBoardjson);

        int displayNumber = ranks.rankEntryList.Count;
        if (displayNumber > 10)
        {
            displayNumber = 10;
        }

        this.rankEntryTransformList = new List<Transform>();
        foreach (RankEntry rankEntry in ranks.rankEntryList)
        {
            CreateRankEntryTransform(rankEntry, rankContainer, this.rankEntryTransformList);
        }

    }


    private void Awake()
    {
        rankView = transform.Find("LeaderboardView").Find("LeaderboardViewPort");
        rankContainer = rankView.Find("LeaderboardContainer");
        rankTemplate = rankContainer.Find("LeaderboardEntryTemplate");
        leaderboardTitle = transform.Find("Title").GetComponent<Text>();
        rankTemplate.gameObject.SetActive(false);

        StartCoroutine(getLeaderboard(world));

    }

    public void UserClicked()
    {
        StaticVariable.isFromLeaderboard = true;
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.Find("nameText").GetComponent<Text>().text);
        StaticVariable.leaderboardId = EventSystem.current.currentSelectedGameObject.transform.Find("userId").GetComponent<Text>().text;
        Debug.Log("Leaderboard" + StaticVariable.leaderboardId);
        SceneManager.LoadScene("Profile");
        StaticVariable.scene = "LeaderBoard";
    }

    private void clearRank(List<Transform> transformList)
    {
        foreach (Transform rankTransform in transformList)
        {
            Destroy(rankTransform.gameObject);
        }

    }

    private void CreateRankEntryTransform(RankEntry rankEntry, Transform container, List<Transform> transformList)
    {
        float templateHeight = 75f;
        Transform rankTransform = Instantiate(rankTemplate, container);
        rankTransform.transform.SetParent(rankTemplate.transform.parent);
        RectTransform rankRectTransform = rankTransform.GetComponent<RectTransform>();
        rankRectTransform.anchoredPosition = new Vector2(0, -templateHeight * transformList.Count);
        rankTransform.gameObject.SetActive(true);

        int rank = transformList.Count + 1;
        string rankString;
        switch (rank)
        {
            default:
                rankString = rank + "TH"; break;
            case 1: rankString = "1ST"; break;
            case 2: rankString = "2ND"; break;
            case 3: rankString = "3RD"; break;

        }

        rankTransform.Find("rankText").GetComponent<Text>().text = rankString;

        int points = rankEntry.charScore;
        rankTransform.Find("pointText").GetComponent<Text>().text = points.ToString();

        string name = rankEntry.userName;
        rankTransform.Find("nameText").GetComponent<Text>().text = name;

        int userId = rankEntry.userId;
        rankTransform.Find("userId").GetComponent<Text>().text = userId.ToString();
        rankTransform.Find("userId").gameObject.SetActive(false);

        rankTransform.Find("bgForEntry").gameObject.SetActive(true);
        transformList.Add(rankTransform);
    }

    private class Ranks
    {
        public List<RankEntry> rankEntryList;
    }

    [System.Serializable]
    private class RankEntry
    {
        public int userId;
        public string userName;
        public string userAvatar;
        public int charScore;
    }

}