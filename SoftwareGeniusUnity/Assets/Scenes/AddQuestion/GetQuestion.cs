using System.Collections;
using System.Text;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using SimpleJSON;
using UnityEngine.Networking;

public class GetQuestion : MonoBehaviour
{
    public string question;
    public TMP_InputField inputFieldQuestion;
    public string type;
    public GameObject typeDropdown;
    public int level;
    public GameObject answerDropdown;
    public int answer;
    public GameObject levelDropdown;
    public string optionA;
    public TMP_InputField inputFieldOptionA;
    public string optionB;
    public TMP_InputField inputFieldOptionB;
    public string optionC;
    public TMP_InputField inputFieldOptionC;
    public string optionD;
    public TMP_InputField inputFieldOptionD;
    private readonly string backendurl = StaticVariable.url + "question/addQuestion";
    [SerializeField] public AlertWindow alert;
    [SerializeField] public PromptWindow successAddQuestion;

    public void getQuestion()
    {
        question = inputFieldQuestion.GetComponent<TMP_InputField>().text;
        type = typeDropdown.GetComponent<TextMeshProUGUI>().text;
        if (type.Equals("SoftwareEngineering")) {
            type = "SW";
        } else if (type.Equals("SoftwareArchitecture"))
        {
            type = "SA";
        } else if (type.Equals("ProjectManager"))
        {
            type = "PM";
        }
        else
        {
            type = "QA";
        }

        string levelstr = levelDropdown.GetComponent<TextMeshProUGUI>().text;
        if (levelstr.Equals("Easy"))
        {
            level = 1;
        } else if (levelstr.Equals("Medium")) {
            level = 2;
        } else {
            level = 3;
        }
        string answerstr = answerDropdown.GetComponent<TextMeshProUGUI>().text;
        if (answerstr.Equals("A"))
        {
            answer = 1;
        }
        else if (type.Equals("B"))
        {
            answer = 2;
        }
        else if (type.Equals("C"))
        {
            answer = 3;
        }
        else
        {
            answer = 4;
        }
        optionA = inputFieldOptionA.GetComponent<TMP_InputField>().text;
        optionB = inputFieldOptionB.GetComponent<TMP_InputField>().text;
        optionC = inputFieldOptionC.GetComponent<TMP_InputField>().text;
        optionD = inputFieldOptionD.GetComponent<TMP_InputField>().text;

        if (question.Trim().Length == 0)
        {
            alert.Show("Invalid input for question");
            return;
        } 
        if (optionA.Trim().Length == 0) {
            alert.Show("Invalid input for optionA");
            return;
        } 
        if (optionB.Trim().Length == 0) {
            alert.Show("Invalid input for optionB");
            return;
        } 
        if (optionC.Trim().Length == 0) {
            alert.Show("Invalid input for optionC");
            return;
        } 
        if (optionD.Trim().Length == 0) {
            alert.Show("Invalid input for optionD");
            return;
        }

        StartCoroutine(postQuestion());

    }

    IEnumerator postQuestion()
    {

        Question savedQuestion = new Question
        {
            problem = question,
            category = type,
            difficultyLevel = level,
            answer = answer,
            choice1 = optionA,
            choice2 = optionB,
            choice3 = optionC,
            choice4 = optionD
        };

        string json_Qn = JsonUtility.ToJson(savedQuestion);
        UnityWebRequest PostRequest = UnityWebRequest.Post(backendurl, json_Qn);
        PostRequest.uploadHandler.contentType = "application/json";
        PostRequest.uploadHandler = new UploadHandlerRaw(System.Text.Encoding.UTF8.GetBytes(json_Qn));
        PostRequest.SetRequestHeader("Accept", "application/json");
        PostRequest.SetRequestHeader("Content-Type", "application/json");

        yield return PostRequest.SendWebRequest();

        if (PostRequest.isNetworkError || PostRequest.isHttpError)
        {
            Debug.LogError(PostRequest.error);
            yield break;
        }
        string PromptMessage;
        if (savedQuestion.problem.Length > 20) { 
            PromptMessage = "Question added.\n" + "Problem: " + savedQuestion.problem.Substring(0, 20) + "..."; 
        }
        else
        {
            PromptMessage = "Question added.\n" + "Problem: " + savedQuestion.problem + "...";
        }
        successAddQuestion.Show(PromptMessage);
        //Clear input
        clearInput();

    }

    private void clearInput()
    {
        inputFieldQuestion.GetComponent<TMP_InputField>().text = "";
        inputFieldOptionA.GetComponent<TMP_InputField>().text = "";
        inputFieldOptionB.GetComponent<TMP_InputField>().text = "";
        inputFieldOptionC.GetComponent<TMP_InputField>().text = "";
        inputFieldOptionD.GetComponent<TMP_InputField>().text = "";
    }

    [System.Serializable]
    private class Question
    {
        public string category;
        public string problem;
        public string choice1;
        public string choice2;
        public string choice3;
        public string choice4;
        public int answer;
        public int difficultyLevel;
        public int userAnswered = 0;
        public int userCorrect = 0;
    }
}
