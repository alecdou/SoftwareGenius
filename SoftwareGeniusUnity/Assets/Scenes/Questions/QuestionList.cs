using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;
using System;
using UnityEngine.Networking;
using SimpleJSON;

public class QuestionList : MonoBehaviour
{

    private Transform buttonScrollList;
    private Transform buttonListViewPort;
    private Transform buttonListContent;
    private Transform questionEntryTemplate;
    private List<Transform> questionEntryTransformList;
    private int choice;
    private List<QuestionEntry> questions = new List<QuestionEntry>();
    bool updated = false;

    static public string questionId;

    public void HandelInputData(int val)
    {
        choice = val;
        updated = true;
        Debug.Log(choice);
    }

    private class Entries
    {
        public List<QuestionEntry> questionEntryList;
    }

    [Serializable]
    private class QuestionEntry
    {
        public string questionId;
        public string title;
        public double accuracy;
    }


    IEnumerator GetAllQuestions()
    {
        string questionUrl = StaticVariable.url + "question/AllQuestions";
        UnityWebRequest questionRequest = UnityWebRequest.Get(questionUrl);
        yield return questionRequest.SendWebRequest();

        if (questionRequest.isNetworkError || questionRequest.isHttpError)
        {
            Debug.Log(questionRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get all questions successfully");
        }

        JSONNode questionData = JSON.Parse(questionRequest.downloadHandler.text);

        for (int i = 0; i < questionData.Count; i++)
        {
            QuestionEntry questionEntry = new QuestionEntry
            {
                questionId = questionData[i]["id"].ToString(),
                title = questionData[i]["problem"],
                accuracy = questionData[i]["userAnswered"] == 0 ? 0.0 : (double)questionData[i]["userCorrect"] / questionData[i]["userAnswered"]
            };

            Debug.Log("Added");
            questions.Add(questionEntry);
        }

        Display(choice);
    }


    void Update()
    {
        if (updated == true)
        {
            ClearList(questionEntryTransformList);
            Display(choice);
            updated = false;
        }
    }

    public void QuestionClicked()
    {
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.Find("QuestionId").GetComponent<Text>().text);
        StaticVariable.questionId = EventSystem.current.currentSelectedGameObject.transform.Find("QuestionId").GetComponent<Text>().text;
        SceneManager.LoadScene("QuestionTeacher");
    }

    private void Start()
    {
        buttonScrollList = transform.Find("ButtonScrollList");
        buttonListViewPort = buttonScrollList.Find("ButtonListViewPort");
        buttonListContent = buttonListViewPort.Find("ButtonListContent");
        questionEntryTemplate = buttonListContent.Find("QuestionEntryTemplate");
        questionEntryTemplate.gameObject.SetActive(false);

        StartCoroutine(GetAllQuestions());
        Debug.Log("Start Called");
    }

    private void Display(int choice)
    {
        Debug.Log("Display called");
        List<QuestionEntry> sortedList = Sort(questions, choice);

        questionEntryTransformList = new List<Transform>();
        for (int i = 0; i < sortedList.Count; i++)
        {
            Transform entryTransform = Instantiate(questionEntryTemplate, buttonListContent);
            entryTransform.gameObject.SetActive(true);
            questionEntryTransformList.Add(entryTransform);
            questionEntryTransformList[i].Find("QuestionId").GetComponent<Text>().text = sortedList[i].questionId;
            questionEntryTransformList[i].Find("Title").GetComponent<Text>().text = sortedList[i].title;
            questionEntryTransformList[i].Find("Accuracy").GetComponent<Text>().text = (Math.Round(sortedList[i].accuracy, 2) * 100).ToString() + "%";
            questionEntryTransformList[i].Find("bg").gameObject.SetActive(i % 2 == 0);
        }
    }

    private void ClearList(List<Transform> questionEntryTransformList)
    {
        foreach (Transform entryTransform in questionEntryTransformList)
        {
            Destroy(entryTransform.gameObject);
        }
    }

    private List<QuestionEntry> Sort(List<QuestionEntry> questionEntryList, int criteria)
    {
        // sort by question ID
        if (criteria == 0)
        {
            for (int i = 0; i < questionEntryList.Count; i++)
            {
                for (int j = i + 1; j < questionEntryList.Count; j++)
                {
                    if (int.Parse(questionEntryList[j].questionId) < int.Parse(questionEntryList[i].questionId))
                    {
                        QuestionEntry temp = questionEntryList[i];
                        questionEntryList[i] = questionEntryList[j];
                        questionEntryList[j] = temp;
                    }
                }
            }
        }
        // sort by accuracy low to high
        else if (criteria == 1)
        {
            for (int i = 0; i < questionEntryList.Count; i++)
            {
                for (int j = i + 1; j < questionEntryList.Count; j++)
                {
                    if (questionEntryList[j].accuracy < questionEntryList[i].accuracy)
                    {
                        QuestionEntry temp = questionEntryList[i];
                        questionEntryList[i] = questionEntryList[j];
                        questionEntryList[j] = temp;
                    }
                }
            }
        }
        // sort by accuracy high to low
        else if (criteria == 2)
        {
            for (int i = 0; i < questionEntryList.Count; i++)
            {
                for (int j = i + 1; j < questionEntryList.Count; j++)
                {
                    if (questionEntryList[j].accuracy > questionEntryList[i].accuracy)
                    {
                        QuestionEntry temp = questionEntryList[i];
                        questionEntryList[i] = questionEntryList[j];
                        questionEntryList[j] = temp;
                    }
                }
            }
        }
        // sort by title
        else if (criteria == 3)
        {
            for (int i = 0; i < questionEntryList.Count; i++)
            {
                for (int j = i + 1; j < questionEntryList.Count; j++)
                {
                    if (string.Compare(questionEntryList[j].title, questionEntryList[i].title) < 0)
                    {
                        QuestionEntry temp = questionEntryList[i];
                        questionEntryList[i] = questionEntryList[j];
                        questionEntryList[j] = temp;
                    }
                }
            }

        }
        return questionEntryList;
    }

}
