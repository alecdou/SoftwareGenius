using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using UnityEngine.UI;
using UnityEngine.Networking;
using SimpleJSON;


public class OverallReportController : MonoBehaviour
{
    private Transform tableContent;
    private OverallReport overallReport;
    private readonly string baseUrl = StaticVariable.url;

    [Serializable]
    private class OverallReport
    {
        public double overallAccuracy;
        public double sweAccuracy;
        public double saAccuracy;
        public double pmAccuracy;
        public double qaAccuracy;
        public string onlineTime;
    }


    IEnumerator GetOverallReport()
    {
        string overallReportUrl = baseUrl + "player/getOverallReport";
        UnityWebRequest overallReportRequest = UnityWebRequest.Get(overallReportUrl);
        yield return overallReportRequest.SendWebRequest();

        if (overallReportRequest.isNetworkError || overallReportRequest.isHttpError)
        {
            Debug.Log(overallReportRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get overall report successfully");
        }

        JSONNode overallReportData = JSON.Parse(overallReportRequest.downloadHandler.text);

        double overallAccuracy = overallReportData["overall_accuracy"] == "NaN" ? 0.0 : (double)overallReportData["overall_accuracy"];
        double sweAccuracy = overallReportData["overall_SE_accuracy"] == "NaN" ? 0.0 : (double)overallReportData["overall_SE_accuracy"];
        double saAccuracy = overallReportData["overall_SA_accuracy"] == "NaN" ? 0.0 : (double)overallReportData["overall_SA_accuracy"];
        double pmAccuracy = overallReportData["overall_PM_accuracy"] == "NaN" ? 0.0 : (double)overallReportData["overall_PM_accuracy"];
        double qaAccuracy = overallReportData["overall_QA_accuracy"] == "NaN" ? 0.0 : (double)overallReportData["overall_QA_accuracy"];
        string onlineTime = overallReportData["total_game_time"];



        overallReport = new OverallReport
        {
            overallAccuracy = overallAccuracy,
            sweAccuracy = sweAccuracy,
            saAccuracy = saAccuracy,
            pmAccuracy = pmAccuracy,
            qaAccuracy = qaAccuracy,
            onlineTime = onlineTime
        };

        SetTable();
    }


    void Start()
    {
        tableContent = transform.Find("TableContent");


        StartCoroutine(GetOverallReport());

    }

    void SetTable()
    {
        tableContent.Find("overall").GetComponent<Text>().text = ((int)Math.Round(overallReport.overallAccuracy * 100)).ToString() + "%";
        tableContent.Find("swe").GetComponent<Text>().text = ((int)Math.Round(overallReport.sweAccuracy * 100)).ToString() + "%";
        tableContent.Find("sa").GetComponent<Text>().text = ((int)Math.Round(overallReport.saAccuracy * 100)).ToString() + "%";
        tableContent.Find("pm").GetComponent<Text>().text = ((int)Math.Round(overallReport.pmAccuracy * 100)).ToString() + "%";
        tableContent.Find("qa").GetComponent<Text>().text = ((int)Math.Round(overallReport.qaAccuracy * 100)).ToString() + "%";
        tableContent.Find("onlineTime").GetComponent<Text>().text = overallReport.onlineTime;
    }
}
