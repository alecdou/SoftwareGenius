using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using System;
using UnityEngine.Networking;
using SimpleJSON;

public class ProfileManager : MonoBehaviour
{
    private Transform student;
    private Transform bars;
    private Transform barValues;
    private Transform tableContent;
    private readonly string baseUrl = StaticVariable.url;
    private Profile profile;

    [Serializable]
    private class Profile
    {
        public string studentId;
        public string username;
        public string realName;
        public List<int> expPoints;
        public List<int> damagePoints;
        public List<int> noQuestions;
        public List<int> level;
        public List<double> accuracy;
    }

    private void ConstructProfile(JSONNode playerData, JSONNode worldData, JSONNode reportData)
    {
        string studentId = playerData["userId"].ToString();
        string username = playerData["username"];
        string realName = playerData["realName"];

        // overall, SE, SA, PM, QA

        // construct level
        List<int> level = new List<int>();
        level.Add(worldData["SE"] == null ? 0 : (int)worldData["SE"]["level"]);
        level.Add(worldData["SA"] == null ? 0 : (int)worldData["SA"]["level"]);
        level.Add(worldData["PM"] == null ? 0 : (int)worldData["PM"]["level"]);
        level.Add(worldData["QA"] == null ? 0 : (int)worldData["QA"]["level"]);
        level.Insert(0, level[0] + level[1] + level[2] + level[3]);


        // construct exp points
        List<int> expPoints = new List<int>();
        expPoints.Add(playerData["overallExp"]);
        expPoints.Add(worldData["SE"] == null ? 0 : (int)worldData["SE"]["exp"]);
        expPoints.Add(worldData["SA"] == null ? 0 : (int)worldData["SA"]["exp"]);
        expPoints.Add(worldData["PM"] == null ? 0 : (int)worldData["PM"]["exp"]);
        expPoints.Add(worldData["QA"] == null ? 0 : (int)worldData["QA"]["exp"]);

        // construct damagePoints;
        List<int> damagePoints = new List<int>();
        damagePoints.Add(worldData["SE"] == null ? 0 : (((int)worldData["SE"]["level"]) / 2) + 20);
        damagePoints.Add(worldData["SA"] == null ? 0 : (((int)worldData["SA"]["level"]) / 2) + 20);
        damagePoints.Add(worldData["PM"] == null ? 0 : (((int)worldData["PM"]["level"]) / 2) + 20);
        damagePoints.Add(worldData["QA"] == null ? 0 : (((int)worldData["QA"]["level"]) / 2) + 20);
        damagePoints.Insert(0, (int)Math.Round((damagePoints[0] + damagePoints[1] + damagePoints[2] + damagePoints[3]) / 4.0));

        // construct noQuestions answered
        List<int> noQuestions = new List<int>();
        noQuestions.Add(worldData["SE"] == null ? 0 : (int)worldData["SE"]["totalQuesNo"]);
        noQuestions.Add(worldData["SA"] == null ? 0 : (int)worldData["SA"]["totalQuesNo"]);
        noQuestions.Add(worldData["PM"] == null ? 0 : (int)worldData["PM"]["totalQuesNo"]);
        noQuestions.Add(worldData["QA"] == null ? 0 : (int)worldData["QA"]["totalQuesNo"]);
        noQuestions.Insert(0, noQuestions[0] + noQuestions[1] + noQuestions[2] + noQuestions[3]);

        // construct accuracy
        List<double> accuracy = new List<double>();
        accuracy.Add(worldData["SE"] == null || worldData["SE"]["totalQuesNo"] == 0 ? 0.0 : (double)worldData["SE"]["correctQuesNo"] / worldData["SE"]["totalQuesNo"]);
        accuracy.Add(worldData["SA"] == null || worldData["SA"]["totalQuesNo"] == 0 ? 0.0 : (double)worldData["SA"]["correctQuesNo"] / worldData["SA"]["totalQuesNo"]);
        accuracy.Add(worldData["PM"] == null || worldData["PM"]["totalQuesNo"] == 0 ? 0.0 : (double)worldData["PM"]["correctQuesNo"] / worldData["PM"]["totalQuesNo"]);
        accuracy.Add(worldData["QA"] == null || worldData["QA"]["totalQuesNo"] == 0 ? 0.0 : (double)worldData["QA"]["correctQuesNo"] / worldData["QA"]["totalQuesNo"]);

        if (reportData["overall_accuracy"] == "NaN")
        {
            accuracy.Insert(0, 0.0);
        }
        else
        {
            accuracy.Insert(0, (double)reportData["overall_accuracy"]);
        }

        profile = new Profile
        {
            studentId = studentId,
            username = username,
            realName = realName,
            expPoints = expPoints,
            damagePoints = damagePoints,
            noQuestions = noQuestions,
            level = level,
            accuracy = accuracy
        };

        SetStudent();
        SetTable("Rect0");
        SetBarValues();
        SetBarHeights();
        SetValuePositions();

    }

    IEnumerator GetProfileByStudentId(string studentId)
    {
        string playerUrl = baseUrl + "player/getUser/" + studentId;
        string worldUrl = baseUrl + "world/getCharsByUserId/" + studentId;
        string reportUrl = baseUrl + "player/getReport/" + studentId;

        // requesting player data
        UnityWebRequest playerRequest = UnityWebRequest.Get(playerUrl);
        yield return playerRequest.SendWebRequest();

        if (playerRequest.isNetworkError || playerRequest.isHttpError)
        {
            Debug.Log(playerRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get player data successfully");
        }

        JSONNode playerData = JSON.Parse(playerRequest.downloadHandler.text);
        Debug.Log(playerData);


        // requesting report data
        UnityWebRequest reportRequest = UnityWebRequest.Get(reportUrl);
        yield return reportRequest.SendWebRequest();

        if (reportRequest.isNetworkError || reportRequest.isHttpError)
        {
            Debug.Log(reportRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get report data successfully");
        }
        JSONNode reportData = JSON.Parse(reportRequest.downloadHandler.text);
        Debug.Log(reportData);


        // requesting world data
        UnityWebRequest worldRequest = UnityWebRequest.Get(worldUrl);
        yield return worldRequest.SendWebRequest();

        if (worldRequest.isNetworkError || worldRequest.isHttpError)
        {
            Debug.Log(worldRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get world data successfully");
        }

        JSONNode worldData = JSON.Parse(worldRequest.downloadHandler.text);
        Debug.Log(worldData);


        ConstructProfile(playerData, worldData, reportData);

    }


    public void Start()
    {
        student = transform.Find("Student");
        bars = transform.Find("BarChart").Find("Bars");
        barValues = transform.Find("BarChart").Find("BarValues");
        tableContent = transform.Find("BarChart").Find("TableContent");

        if (StaticVariable.isFromLeaderboard == true)
        {
            StartCoroutine(GetProfileByStudentId(StaticVariable.leaderboardId));
        } else
        {
            StartCoroutine(GetProfileByStudentId(StaticVariable.studentId));

        }
        // StartCoroutine(GetProfileByStudentId("1"));


    }

    private void SetStudent()
    {
        Debug.Log(profile.realName);
        //student.Find("StudentName").GetComponent<Text>().text = profile.realName;
        student.Find("Username").GetComponent<Text>().text = profile.username;
        student.Find("Initials").GetComponent<Text>().text = GetInitials();
        student.Find("Avatar").GetComponent<Image>().color = SetColor();
        student.Find("StudentName").GetComponent<Text>().text = profile.realName;

    }

    private void SetTable(string choice)
    {
        // tableContent.Find("Accuracy").GetComponent<Text>().text = ((int)System.Math.Round(profile.accuracy * 100)).ToString() + "%";
        if (choice == "Rect0" || choice == "all")
        {
            tableContent.Find("Role").GetComponent<Text>().text = "Overall";
            tableContent.Find("Level").GetComponent<Text>().text = profile.level[0].ToString();
            tableContent.Find("Exp").GetComponent<Text>().text = profile.expPoints[0].ToString();
            tableContent.Find("Damage").GetComponent<Text>().text = profile.damagePoints[0].ToString();
            tableContent.Find("No. Questions").GetComponent<Text>().text = profile.noQuestions[0].ToString();
            tableContent.Find("Accuracy").GetComponent<Text>().text = ((int)System.Math.Round(profile.accuracy[0] * 100)).ToString() + "%";
        }
        else if (choice == "Rect1" || choice == "char0")
        {
            tableContent.Find("Role").GetComponent<Text>().text = "Software Engineering";
            tableContent.Find("Level").GetComponent<Text>().text = profile.level[1].ToString();
            tableContent.Find("Exp").GetComponent<Text>().text = profile.expPoints[1].ToString();
            tableContent.Find("Damage").GetComponent<Text>().text = profile.damagePoints[1].ToString();
            tableContent.Find("No. Questions").GetComponent<Text>().text = profile.noQuestions[1].ToString();
            tableContent.Find("Accuracy").GetComponent<Text>().text = ((int)System.Math.Round(profile.accuracy[1] * 100)).ToString() + "%";

        }
        else if (choice == "Rect2" || choice == "char1")
        {
            tableContent.Find("Role").GetComponent<Text>().text = "Software Architecture";
            tableContent.Find("Level").GetComponent<Text>().text = profile.level[2].ToString();
            tableContent.Find("Exp").GetComponent<Text>().text = profile.expPoints[2].ToString();
            tableContent.Find("Damage").GetComponent<Text>().text = profile.damagePoints[2].ToString();
            tableContent.Find("No. Questions").GetComponent<Text>().text = profile.noQuestions[2].ToString();
            tableContent.Find("Accuracy").GetComponent<Text>().text = ((int)System.Math.Round(profile.accuracy[2] * 100)).ToString() + "%";

        }
        else if (choice == "Rect3" || choice == "char2")
        {
            tableContent.Find("Role").GetComponent<Text>().text = "Product Management";
            tableContent.Find("Level").GetComponent<Text>().text = profile.level[3].ToString();
            tableContent.Find("Exp").GetComponent<Text>().text = profile.expPoints[3].ToString();
            tableContent.Find("Damage").GetComponent<Text>().text = profile.damagePoints[3].ToString();
            tableContent.Find("No. Questions").GetComponent<Text>().text = profile.noQuestions[3].ToString();
            tableContent.Find("Accuracy").GetComponent<Text>().text = ((int)System.Math.Round(profile.accuracy[3] * 100)).ToString() + "%";

        }
        else if (choice == "Rect4" || choice == "char3")
        {
            tableContent.Find("Role").GetComponent<Text>().text = "Quality Assurance";
            tableContent.Find("Level").GetComponent<Text>().text = profile.level[4].ToString();
            tableContent.Find("Exp").GetComponent<Text>().text = profile.expPoints[4].ToString();
            tableContent.Find("Damage").GetComponent<Text>().text = profile.damagePoints[4].ToString();
            tableContent.Find("No. Questions").GetComponent<Text>().text = profile.noQuestions[4].ToString();
            tableContent.Find("Accuracy").GetComponent<Text>().text = ((int)System.Math.Round(profile.accuracy[4] * 100)).ToString() + "%";

        }
    }

    private void SetBarValues()
    {
        barValues.Find("Exp0").GetComponent<Text>().text = profile.expPoints[0].ToString();
        barValues.Find("Exp1").GetComponent<Text>().text = profile.expPoints[1].ToString();
        barValues.Find("Exp2").GetComponent<Text>().text = profile.expPoints[2].ToString();
        barValues.Find("Exp3").GetComponent<Text>().text = profile.expPoints[3].ToString();
        barValues.Find("Exp4").GetComponent<Text>().text = profile.expPoints[4].ToString();
    }

    private void SetBarHeights()
    {
        // height of the first bar will always be 200
        bars.Find("Rect0").GetComponent<RectTransform>().sizeDelta = new Vector2(200, bars.Find("Rect0").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect1").GetComponent<RectTransform>().sizeDelta = new Vector2((float)profile.expPoints[1] / (float)profile.expPoints[0] * 200, bars.Find("Rect1").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect2").GetComponent<RectTransform>().sizeDelta = new Vector2((float)profile.expPoints[2] / (float)profile.expPoints[0] * 200, bars.Find("Rect2").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect3").GetComponent<RectTransform>().sizeDelta = new Vector2((float)profile.expPoints[3] / (float)profile.expPoints[0] * 200, bars.Find("Rect3").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect4").GetComponent<RectTransform>().sizeDelta = new Vector2((float)profile.expPoints[4] / (float)profile.expPoints[0] * 200, bars.Find("Rect4").GetComponent<RectTransform>().rect.width);

    }

    private void SetValuePositions()
    {
        barValues.Find("Exp0").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect0").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect0").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Exp1").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect1").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect1").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Exp2").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect2").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect2").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Exp3").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect3").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect3").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Exp4").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect4").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect4").GetComponent<RectTransform>().rect.width - 85);

    }

    public void OnClick()
    {
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.name);
        SetTable(EventSystem.current.currentSelectedGameObject.transform.name);
    }

    public void OnclickCharacters()
    {
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.name);
        SetTable(EventSystem.current.currentSelectedGameObject.transform.name);
    }


    private string GetInitials()
    {
        string name = profile.realName;
        string[] words = name.Split(' ');
        string res = "";

        for (int i = 0; i < words.Length; i++)
        {
            res += words[i][0];
        }
        return res.ToUpper();
    }

    private Color32 SetColor()
    {
        List<Color32> colors = new List<Color32>();
        Color32 color;

        color = new Color32(130, 190, 225, 200);
        colors.Add(color);
        color = new Color32(176, 191, 26, 200);
        colors.Add(color);
        color = new Color32(255, 191, 0, 200);
        colors.Add(color);
        color = new Color32(147, 112, 219, 200);
        colors.Add(color);
        color = new Color32(229, 43, 80, 200);
        colors.Add(color);


        return colors[int.Parse(profile.studentId) % 5];

    }

}
