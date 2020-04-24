using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using SimpleJSON;
using UnityEngine.Networking;


public class QuestionDetail : MonoBehaviour
{

    private Transform questionHeader;
    private Transform description;

    private Transform optionA;
    private Transform optionB;
    private Transform optionC;
    private Transform optionD;
    private Transform tick;
    private Transform accuracy;

    private Question question;

    [System.Serializable]
    private class Question
    {
        public string questionId;
        public string category;
        public string questionDescription;
        public int correctAnswer;
        public string optionA;
        public string optionB;
        public string optionC;
        public string optionD;
        public double correctRate;
        public string level;
    }



    IEnumerator GetQuestionById(int qnId)
    {
        string questionUrl = StaticVariable.url + "question/" + qnId.ToString();
        UnityWebRequest questionRequest = UnityWebRequest.Get(questionUrl);
        yield return questionRequest.SendWebRequest();

        if (questionRequest.isNetworkError || questionRequest.isHttpError)
        {
            Debug.Log(questionRequest.error);
            yield break;
        }
        else
        {
            Debug.Log("Get question successfully");
        }

        JSONNode questionData = JSON.Parse(questionRequest.downloadHandler.text);

        string level = "";
        if (questionData["difficultyLevel"] == 1) level = "Easy";
        if (questionData["difficultyLevel"] == 2) level = "Medium";
        if (questionData["difficultyLevel"] == 3) level = "Hard";


        question = new Question
        {
            questionId = questionData["id"].ToString(),
            category = questionData["category"],
            questionDescription = questionData["problem"],
            correctAnswer = questionData["answer"],
            optionA = questionData["choice1"],
            optionB = questionData["choice2"],
            optionC = questionData["choice3"],
            optionD = questionData["choice4"],
            correctRate = questionData["userAnswered"] == 0 ? 0.0 : (double)questionData["userCorrect"] / questionData["userAnswered"],
            level = level

        };

        SetText();
        SetTickPosition();

    }

    public void Start()
    {
        questionHeader = transform.Find("LeftSide").Find("QuestionHeader");
        description = transform.Find("LeftSide").Find("Description");
        optionA = transform.Find("RightSide").Find("optionA");
        optionB = transform.Find("RightSide").Find("optionB");
        optionC = transform.Find("RightSide").Find("optionC");
        optionD = transform.Find("RightSide").Find("optionD");
        tick = transform.Find("RightSide").Find("tick");
        accuracy = transform.Find("RightSide").Find("correctRate");

        StartCoroutine(GetQuestionById(int.Parse(StaticVariable.questionId)));
        // StartCoroutine(GetQuestionById(1));

    }

    private void SetText()
    {

        questionHeader.Find("id").GetComponent<Text>().text = question.questionId;
        questionHeader.Find("category").GetComponent<Text>().text = question.category;
        questionHeader.Find("level").GetComponent<Text>().text = question.level;
        description.GetComponent<Text>().text = question.questionDescription;

        optionA.GetComponent<Text>().text = question.optionA;
        optionB.GetComponent<Text>().text = question.optionB;
        optionC.GetComponent<Text>().text = question.optionC;
        optionD.GetComponent<Text>().text = question.optionD;

        Debug.Log(question.correctRate * 100);
        accuracy.GetComponent<Text>().text = "Correct Rate: " + ((int)System.Math.Round(question.correctRate * 100)).ToString() + "%";
    }

    private void SetTickPosition()
    {
        RectTransform optionRectTransform = new RectTransform();

        if (question.correctAnswer == 1) optionRectTransform = transform.Find("Static").Find("A").GetComponent<RectTransform>();
        else if (question.correctAnswer == 2) optionRectTransform = transform.Find("Static").Find("B").GetComponent<RectTransform>();
        else if (question.correctAnswer == 3) optionRectTransform = transform.Find("Static").Find("C").GetComponent<RectTransform>();
        else if (question.correctAnswer == 4) optionRectTransform = transform.Find("Static").Find("D").GetComponent<RectTransform>();

        tick.GetComponent<RectTransform>().anchoredPosition = new Vector2(optionRectTransform.anchoredPosition.x + 35, optionRectTransform.anchoredPosition.y + 10);
    }
}
