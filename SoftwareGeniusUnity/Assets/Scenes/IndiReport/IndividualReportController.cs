using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using System;
using SimpleJSON;
using UnityEngine.Networking;
public class IndividualReportController : MonoBehaviour
{
    private Transform tableContent;
    private Transform student;
    private Transform bars;
    private Transform barValues;

    private Report report;

    [Serializable]
    private class Report
    {
        public string studentName; // report
        public string studentId; //report
        public string email; // report
        public string ranking; // report
        public List<double> accuracy;
        public string onlineTime; // report

    }


    IEnumerator GetReportById(int studentId)
    {

        string reportUrl = StaticVariable.url + "player/getReport/" + studentId.ToString();


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


        report = new Report
        {
            studentName = reportData["real_name"],
            studentId = reportData["userId"],
            email = reportData["email"],
            ranking = reportData["ranking"],
            onlineTime = reportData["duration"],
            accuracy = GetAccuracy(reportData)
        };


        SetStudent();
        SetTable();
        SetBarValues();
        SetBarHeights();
        SetValuePositions();
        SetHeader("Rect0");

    }

    private List<double> GetAccuracy(JSONNode reportData)
    {
        List<double> accuracy = new List<double>();

        accuracy.Add(reportData["overall_accuracy"] == "NaN" || reportData["overall_accuracy"] == null ? 0.0 : (double)reportData["overall_accuracy"]);
        accuracy.Add(reportData["SE_accuracy"] == "NaN" || reportData["SE_accuracy"] == null ? 0.0 : (double)reportData["SE_accuracy"]);
        accuracy.Add(reportData["SA_accuracy"] == "NaN" || reportData["SA_accuracy"] == null ? 0.0 : (double)reportData["SA_accuracy"]);
        accuracy.Add(reportData["PM_accuracy"] == "NaN" || reportData["PM_accuracy"] == null ? 0.0 : (double)reportData["PM_accuracy"]);
        accuracy.Add(reportData["QA_accuracy"] == "NaN" || reportData["QA_accuracy"] == null ? 0.0 : (double)reportData["QA_accuracy"]);

        return accuracy;

    }


    private void Start()
    {
        tableContent = transform.Find("TableContent");
        student = transform.Find("Student");
        bars = transform.Find("BarChart").Find("Bars");
        barValues = transform.Find("BarChart").Find("BarValues");

        if (StaticVariable.isFromReportList == true)
        {
            StartCoroutine(GetReportById(int.Parse(StaticVariable.reportId)));
        } else
        {
            StartCoroutine(GetReportById(int.Parse(StaticVariable.studentId)));
        }
        //StartCoroutine(GetReportById(1));

    }



    private void SetTable()
    {
        tableContent.Find("StudentId").GetComponent<Text>().text = report.studentId;
        tableContent.Find("Email").GetComponent<Text>().text = report.email;
        tableContent.Find("Ranking").GetComponent<Text>().text = report.ranking;
        tableContent.Find("OnlineTime").GetComponent<Text>().text = report.onlineTime;
    }

    private void SetStudent()
    {
        student.Find("StudentName").GetComponent<Text>().text = report.studentName;
        student.Find("Initials").GetComponent<Text>().text = GetInitials();
        student.Find("Avatar").GetComponent<Image>().color = SetColor();
    }

    private void SetBarValues()
    {
        barValues.Find("Acc0").GetComponent<Text>().text = ((int)System.Math.Round(report.accuracy[0] * 100)).ToString() + "%";
        barValues.Find("Acc1").GetComponent<Text>().text = ((int)System.Math.Round(report.accuracy[1] * 100)).ToString() + "%";
        barValues.Find("Acc2").GetComponent<Text>().text = ((int)System.Math.Round(report.accuracy[2] * 100)).ToString() + "%";
        barValues.Find("Acc3").GetComponent<Text>().text = ((int)System.Math.Round(report.accuracy[3] * 100)).ToString() + "%";
        barValues.Find("Acc4").GetComponent<Text>().text = ((int)System.Math.Round(report.accuracy[4] * 100)).ToString() + "%";
    }

    private void SetBarHeights()
    {
        bars.Find("Rect0").GetComponent<RectTransform>().sizeDelta = new Vector2((int)System.Math.Round(report.accuracy[0] * 180), bars.Find("Rect0").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect1").GetComponent<RectTransform>().sizeDelta = new Vector2((int)System.Math.Round(report.accuracy[1] * 180), bars.Find("Rect1").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect2").GetComponent<RectTransform>().sizeDelta = new Vector2((int)System.Math.Round(report.accuracy[2] * 180), bars.Find("Rect2").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect3").GetComponent<RectTransform>().sizeDelta = new Vector2((int)System.Math.Round(report.accuracy[3] * 180), bars.Find("Rect3").GetComponent<RectTransform>().rect.width);
        bars.Find("Rect4").GetComponent<RectTransform>().sizeDelta = new Vector2((int)System.Math.Round(report.accuracy[4] * 180), bars.Find("Rect4").GetComponent<RectTransform>().rect.width);
    }

    private void SetValuePositions()
    {
        barValues.Find("Acc0").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect0").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect0").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Acc1").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect1").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect1").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Acc2").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect2").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect2").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Acc3").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect3").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect3").GetComponent<RectTransform>().rect.width - 85);
        barValues.Find("Acc4").GetComponent<RectTransform>().anchoredPosition = new Vector2(bars.Find("Rect4").GetComponent<RectTransform>().anchoredPosition.x, bars.Find("Rect4").GetComponent<RectTransform>().rect.width - 85);
    }

    private void SetHeader(string choice)
    {
        if (choice == "Rect0" || choice == "all")
        {
            transform.Find("BarChart").Find("Header").GetComponent<Text>().text = "Overall Accuracy";
        }
        else if (choice == "Rect1" || choice == "char0")
        {
            transform.Find("BarChart").Find("Header").GetComponent<Text>().text = "Software Engineering World Accuracy";
        }
        else if (choice == "Rect2" || choice == "char1")
        {
            transform.Find("BarChart").Find("Header").GetComponent<Text>().text = "Software Architecture World Accuracy";
        }
        else if (choice == "Rect3" || choice == "char2")
        {
            transform.Find("BarChart").Find("Header").GetComponent<Text>().text = "Product Management World Accuracy";
        }
        else if (choice == "Rect4" || choice == "char3")
        {
            transform.Find("BarChart").Find("Header").GetComponent<Text>().text = "Quality Assurance World Accuracy";
        }
    }

    public void OnClick()
    {
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.name);
        SetHeader(EventSystem.current.currentSelectedGameObject.transform.name);
    }


    private string GetInitials()
    {
        string name = report.studentName;
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

        return colors[int.Parse(report.studentId) % 5];

    }
}
