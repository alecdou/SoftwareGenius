using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;
using UnityEngine.Networking;
using System;
using SimpleJSON;
public class ReportList : MonoBehaviour
{
    private Transform buttonScrollList;
    private Transform buttonListViewPort;
    private Transform buttonListContent;
    private Transform reportEntryTemplate;
    private List<Transform> reportEntryTransformList;
    private int choice;
    private List<ReportEntry> reports = new List<ReportEntry>();
    bool updated = false;
    private readonly string baseUrl = StaticVariable.url;

    static public string studentId;
    private int noPlayers;

    public void HandleInputData(int val)
    {
        choice = val;
        updated = true;
        Debug.Log(choice);
    }

    IEnumerator GetReportEntry(string studentId)
    {
        string reportUrl = baseUrl + "player/getReport/" + studentId;

        // requesting player data
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

        ReportEntry report = ConstructReportEntry(reportData);

        reports.Add(report);
        Debug.Log("report length" + reports.Count);

        if (reports.Count == noPlayers)
        {
            Display(choice);

        }

    }

    IEnumerator GetNoPlayers()
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

        int noReports = (int)overallReportData["student_num"];
        noPlayers = (int)overallReportData["student_num"];

        Debug.Log("numofplayers !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + noPlayers.ToString());
        Debug.Log("call from getNoPlayers");

    }

    IEnumerator GetAccountType()
    {
        string accountTypeUrl = baseUrl + "player/getAll";
        UnityWebRequest accountTypeRequest = UnityWebRequest.Get(accountTypeUrl);
        yield return accountTypeRequest.SendWebRequest();

        if (accountTypeRequest.isNetworkError || accountTypeRequest.isHttpError)
        {
            Debug.Log(accountTypeRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get account type successfully");
        }

        JSONNode accountTypeData = JSON.Parse(accountTypeRequest.downloadHandler.text);

        bool isAdmin;

        for (int i = 0; i < accountTypeData.Count; i++)
        {
            isAdmin = accountTypeData[i]["isAdmin"];
            if (!isAdmin)
            {
                Debug.Log(accountTypeData[i]["userId"]);
                StartCoroutine(GetReportEntry(accountTypeData[i]["userId"]));
            }
        }

    }

    private ReportEntry ConstructReportEntry(JSONNode reportData)
    {
        string studentId = reportData["userId"];
        string name = reportData["real_name"];
        double accuracy;
        if (reportData["overall_accuracy"] == "NaN")
        {
            accuracy = 0.0;
        }
        else
        {
            accuracy = (double)reportData["overall_accuracy"];
        }

        ReportEntry reportEntry = new ReportEntry
        {
            studentId = studentId,
            name = name,
            accuracy = accuracy
        };

        Debug.Log("report entry successfully constructed");
        return reportEntry;
    }


    [Serializable]
    private class ReportEntry
    {
        public string studentId;
        public string name;
        public double accuracy;
    }


    public void Update()
    {
        if (updated == true && reports.Count == noPlayers)
        {
            ClearList(reportEntryTransformList);
            Debug.Log("call from update");
            Display(choice);
            updated = false;
        }
    }


    public void ReportClicked()
    {
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.Find("StudentId").GetComponent<Text>().text);
        StaticVariable.reportId = EventSystem.current.currentSelectedGameObject.transform.Find("StudentId").GetComponent<Text>().text;
        SceneManager.LoadScene("IndiReport");
    }

    private void Start()
    {
        StaticVariable.isFromReportList = true;

        buttonScrollList = transform.Find("ButtonScrollList");
        buttonListViewPort = buttonScrollList.Find("ButtonListViewPort");
        buttonListContent = buttonListViewPort.Find("ButtonListContent");
        reportEntryTemplate = buttonListContent.Find("ReportEntryTemplate");
        reportEntryTemplate.gameObject.SetActive(false);

        StartCoroutine(GetNoPlayers());
        StartCoroutine(GetAccountType());

    }


    private void Display(int choice)
    {
        List<ReportEntry> sortedList = Sort(reports, choice);

        reportEntryTransformList = new List<Transform>();
        for (int i = 0; i < sortedList.Count; i++)
        {
            Transform entryTransform = Instantiate(reportEntryTemplate, buttonListContent);
            entryTransform.gameObject.SetActive(true);
            reportEntryTransformList.Add(entryTransform);
            reportEntryTransformList[i].Find("StudentId").GetComponent<Text>().text = sortedList[i].studentId;
            reportEntryTransformList[i].Find("Name").GetComponent<Text>().text = sortedList[i].name;
            reportEntryTransformList[i].Find("Accuracy").GetComponent<Text>().text = (Math.Round(sortedList[i].accuracy, 2) * 100).ToString() + "%";
            reportEntryTransformList[i].Find("bg").gameObject.SetActive(i % 2 == 0);
        }
    }


    private void ClearList(List<Transform> reportEntryTransformList)
    {
        foreach (Transform entryTransform in reportEntryTransformList)
        {
            Destroy(entryTransform.gameObject);
        }
    }


    private List<ReportEntry> Sort(List<ReportEntry> reportEntryList, int criteria)
    {
        if (criteria == 0)
        {
            for (int i = 0; i < reportEntryList.Count; i++)
            {
                for (int j = i + 1; j < reportEntryList.Count; j++)
                {
                    if (int.Parse(reportEntryList[j].studentId) < int.Parse(reportEntryList[i].studentId))
                    {
                        ReportEntry temp = reportEntryList[i];
                        reportEntryList[i] = reportEntryList[j];
                        reportEntryList[j] = temp;
                    }
                }
            }

        }
        else if (criteria == 1)
        {
            for (int i = 0; i < reportEntryList.Count; i++)
            {
                for (int j = i + 1; j < reportEntryList.Count; j++)
                {
                    if (reportEntryList[j].accuracy < reportEntryList[i].accuracy)
                    {
                        ReportEntry temp = reportEntryList[i];
                        reportEntryList[i] = reportEntryList[j];
                        reportEntryList[j] = temp;
                    }
                }
            }

        }
        else if (criteria == 2)
        {
            for (int i = 0; i < reportEntryList.Count; i++)
            {
                for (int j = i + 1; j < reportEntryList.Count; j++)
                {
                    if (reportEntryList[j].accuracy > reportEntryList[i].accuracy)
                    {
                        ReportEntry temp = reportEntryList[i];
                        reportEntryList[i] = reportEntryList[j];
                        reportEntryList[j] = temp;
                    }
                }
            }

        }
        else if (criteria == 3)
        {
            for (int i = 0; i < reportEntryList.Count; i++)
            {
                for (int j = i + 1; j < reportEntryList.Count; j++)
                {
                    if (string.Compare(reportEntryList[j].name, reportEntryList[i].name) < 0)
                    {
                        ReportEntry temp = reportEntryList[i];
                        reportEntryList[i] = reportEntryList[j];
                        reportEntryList[j] = temp;
                    }
                }
            }

        }
        return reportEntryList;
    }
}