using System;
using System.Collections;
using System.Collections.Generic;
using SimpleJSON;
using TMPro;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.UI;

[Serializable()]
public struct UIManagerParameters
{
    [Header("Answers Options")]
    [SerializeField] float margins;
    public float Margins { get { return margins; } }

    [Header("Resolution Screen Options")]
    [SerializeField] Color correctBGColor;
    public Color CorrectBGColor { get { return correctBGColor; } }
    [SerializeField] Color incorrectBGColor;
    public Color IncorrectBGColor { get { return incorrectBGColor; } }
    [SerializeField] Color finalBGColor;
    public Color FinalBGColor { get { return finalBGColor; } }

}
[Serializable()]
public struct UIElements
{
    [SerializeField] RectTransform answersContentArea;
    public RectTransform AnswersContentArea { get { return answersContentArea; } }

    [SerializeField] TextMeshProUGUI questionInfoTextObject;
    public TextMeshProUGUI QuestionInfoTextObject { get { return questionInfoTextObject; } }

    [SerializeField] TextMeshProUGUI scoreText;
    public TextMeshProUGUI ScoreText { get { return scoreText; } }

    [SerializeField] TextMeshProUGUI npc_HP;
    public TextMeshProUGUI Npc_HP { get { return npc_HP; } }

    [SerializeField] TextMeshProUGUI player_HP;
    public TextMeshProUGUI Player_HP { get { return player_HP; } }

    [Space]

    [SerializeField] Animator resolutionScreenAnimator;
    public Animator ResolutionScreenAnimator { get { return resolutionScreenAnimator; } }

    [SerializeField] Image resolutionBG;
    public Image ResolutionBG { get { return resolutionBG; } }

    [SerializeField] TextMeshProUGUI resolutionStateInfoText;
    public TextMeshProUGUI ResolutionStateInfoText { get { return resolutionStateInfoText; } }

    [SerializeField] TextMeshProUGUI resolutionScoreText;
    public TextMeshProUGUI ResolutionScoreText { get { return resolutionScoreText; } }

    [Space]

    [SerializeField] CanvasGroup mainCanvasGroup;
    public CanvasGroup MainCanvasGroup { get { return mainCanvasGroup; } }

    [SerializeField] RectTransform finishUIElements;
    public RectTransform FinishUIElements { get { return finishUIElements; } }

    [SerializeField] RectTransform winUIElements;
    public RectTransform WinUIElements { get { return winUIElements; } }
    [SerializeField] RectTransform loseUIElements;
    public RectTransform LoseUIElements { get { return loseUIElements; } }
    [SerializeField] RectTransform winBattleUIElements;
    public RectTransform WinBattleUIElements { get { return winBattleUIElements; } }

    [SerializeField] Slider playerHealthBar;
    public Slider PlayerHealthBar { get { return playerHealthBar; } }

    [SerializeField] Slider nPCHealthBar;
    public Slider NPCHealthBar { get { return nPCHealthBar; } }

}
[Serializable()]
public class UIManager : MonoBehaviour
{
    public enum ResolutionScreenType { Correct, Incorrect, Finish }

    [Header("References")]
    [SerializeField] GameEvent events = null;

    [Header("UI Elements (Prefabs)")]
    [SerializeField] AnswerData answerPrefab = null;

    [SerializeField] UIElements uIElements = new UIElements();

    [Space]
    [SerializeField] UIManagerParameters parameters = new UIManagerParameters();

    private List<AnswerData> currentAnswers = new List<AnswerData>();
    private int resStateParaHash = 0;

    private IEnumerator IE_DisplayTimedResolution = null;

    public object PlayHealthBar { get; private set; }

    void OnEnable()
    {
        events.UpdateQuestionUI += UpdateQuestionUI;
        events.DisplayResolutionScreen += DisplayResolution;
        events.ScoreUpdated += UpdateScoreUI;
    }

    void OnDisable()
    {
        events.UpdateQuestionUI -= UpdateQuestionUI;
        events.DisplayResolutionScreen -= DisplayResolution;
        events.ScoreUpdated -= UpdateScoreUI;
    }


    void Start()
    {

        UpdateScoreUI();
        resStateParaHash = Animator.StringToHash("ScreenState");
    }

    void UpdateQuestionUI(QuestionLoad question)
    {
        uIElements.QuestionInfoTextObject.text = question.problem;
        CreateAnswers(question);
    }

    void DisplayResolution(ResolutionScreenType type, int score)
    {
        UpdateResUI(type, score);
        uIElements.ResolutionScreenAnimator.SetInteger(resStateParaHash, 2);//popup
        uIElements.MainCanvasGroup.blocksRaycasts = false;

        if (type != ResolutionScreenType.Finish)
        {
            if (IE_DisplayTimedResolution != null)
            {
                StopCoroutine(IE_DisplayTimedResolution);
            }
            IE_DisplayTimedResolution = DisplayTimedResolution();
            StartCoroutine(IE_DisplayTimedResolution);
        }
    }

    IEnumerator DisplayTimedResolution()
    {
        yield return new WaitForSeconds(GameUtility.ResolutionDelayTime);
        uIElements.ResolutionScreenAnimator.SetInteger(resStateParaHash, 1);
        uIElements.MainCanvasGroup.blocksRaycasts = true;
    }

    void UpdateResUI(ResolutionScreenType type, int score)
    {
        //var highscore = PlayerPrefs.GetInt(GameUtility.SavePrefKey);

        switch (type)
        {
            case ResolutionScreenType.Correct:
                uIElements.ResolutionBG.color = parameters.CorrectBGColor;
                uIElements.ResolutionStateInfoText.text = "CORRECT!";
                uIElements.ResolutionScoreText.text = "Monster   -" + score + " HP";
                break;
            case ResolutionScreenType.Incorrect:
                uIElements.ResolutionBG.color = parameters.IncorrectBGColor;
                uIElements.ResolutionStateInfoText.text = "WRONG!";
                uIElements.ResolutionScoreText.text = "You   -" + score + " HP";
                break;
            case ResolutionScreenType.Finish:
                uIElements.ResolutionBG.color = parameters.FinalBGColor;
                uIElements.ResolutionStateInfoText.text = "";

                if (events.npc_HP <= 0)
                {
                    if(events.mode == "battle" )
                        uIElements.WinUIElements.gameObject.SetActive(true);
                    else
                        uIElements.WinBattleUIElements.gameObject.SetActive(true);

                    events.status = "succeeded";
                }
                else
                {
                    uIElements.LoseUIElements.gameObject.SetActive(true);
                    events.status = "failed";
                }

                uIElements.FinishUIElements.gameObject.SetActive(true);
                StartCoroutine(SaveDataAfter());

                break;
        }
    }

    private IEnumerator CalculateScore(int exp)
    {
        var EXPadded = 0;
        while (EXPadded < exp)
        {
            EXPadded++;
            uIElements.ResolutionScoreText.text = "EXP Added: " + EXPadded.ToString();

            yield return null;
        }

        while (EXPadded > exp)
        {
            EXPadded--;
            uIElements.ResolutionScoreText.text = "EXP Added: " + EXPadded.ToString();

            yield return null;
        }
        
    }

    IEnumerator SaveDataAfter()
    {

        PostAfterData AfterData = new PostAfterData();
        AfterData.characterId = GameManagement.Chara.charId;
        AfterData.status = events.status;

        List<int> AQ_list = events.idOfAnsweredQns;
        string AQ = "";
        for (int j = 0; j < AQ_list.Count - 1; j++)
        {
            AQ += AQ_list[j].ToString() + ",";
        }
        AQ += AQ_list[AQ_list.Count - 1].ToString();
        AQ = "[" + AQ + "]";

        List<int> CA_list = events.idOfCorrectlyAnsweredQns;
        string CA = "";
        if (CA_list.Count > 0)
        {
            for (int j = 0; j < CA_list.Count - 1; j++)
            {
                CA += CA_list[j].ToString() + ",";
            }
            CA += CA_list[CA_list.Count - 1].ToString();
            CA = "[" + CA + "]";
        }

        AfterData.numOfQnsAnswered = events.numOfQnsAnswered;
        AfterData.idOfAnsweredQns = AQ;
        AfterData.idOfCorrectlyAnsweredQns = CA;
        

        //test
        /*AfterData.characterId = 1;
        AfterData.status = "failed";
        AfterData.numOfQnsAnswered = 5;
        AfterData.idOfAnsweredQns = "[3,4,1,19,20]";
        AfterData.idOfCorrectlyAnsweredQns = "[3,4,1,19]";
        ;

        ";*/

        string json = JsonUtility.ToJson(AfterData);
        Debug.Log(json);

        //string url = "localhost:9090/api/combat/1/end";
        string url = StaticVariable.url + "combat/"+GameManagement.CID.combatID+"/end";


        
        UnityWebRequest userRequest = UnityWebRequest.Post(url, json);
        userRequest.uploadHandler.contentType = "application/json";
        userRequest.uploadHandler = new UploadHandlerRaw(System.Text.Encoding.UTF8.GetBytes(json));
        userRequest.SetRequestHeader("Accept", "application/json");
        userRequest.SetRequestHeader("Content-Type", "application/json");

        //Debug.Log(json);

        yield return userRequest.SendWebRequest();


        if (userRequest.isNetworkError || userRequest.isHttpError)
        {
            Debug.LogError(userRequest.error);
            yield break;
        }
        else
        {
            Debug.Log(userRequest.downloadHandler.text);
            JSONNode StartBattleInfo = JSON.Parse(userRequest.downloadHandler.text);

            string StartBattleString = StartBattleInfo.ToString();
            GetExp e = JsonUtility.FromJson<GetExp>(StartBattleString);
            uIElements.ResolutionScoreText.text = "Totol EXP: " + e.addedExp;
            //StartCoroutine(CalculateScore(e.addedExp));
        }

    }

    

    void CreateAnswers(QuestionLoad question)
    {
        EraseAnswers();

        float offset = 0 - parameters.Margins;

        AnswerData newAnswer1 = (AnswerData)Instantiate(answerPrefab, uIElements.AnswersContentArea);
        newAnswer1.UpdateData(question.choice1, 0);

        newAnswer1.Rect.anchoredPosition = new Vector2(0, offset);

        offset -= (newAnswer1.Rect.sizeDelta.y + parameters.Margins);
        uIElements.AnswersContentArea.sizeDelta = new Vector2(uIElements.AnswersContentArea.sizeDelta.x, offset * -1);

        currentAnswers.Add(newAnswer1);

        AnswerData newAnswer2 = (AnswerData)Instantiate(answerPrefab, uIElements.AnswersContentArea);
        newAnswer2.UpdateData(question.choice2, 1);

        newAnswer2.Rect.anchoredPosition = new Vector2(0, offset);

        offset -= (newAnswer2.Rect.sizeDelta.y + parameters.Margins);
        uIElements.AnswersContentArea.sizeDelta = new Vector2(uIElements.AnswersContentArea.sizeDelta.x, offset * -1);

        currentAnswers.Add(newAnswer2);

        AnswerData newAnswer3 = (AnswerData)Instantiate(answerPrefab, uIElements.AnswersContentArea);
        newAnswer3.UpdateData(question.choice3, 2);

        newAnswer3.Rect.anchoredPosition = new Vector2(0, offset);

        offset -= (newAnswer3.Rect.sizeDelta.y + parameters.Margins);
        uIElements.AnswersContentArea.sizeDelta = new Vector2(uIElements.AnswersContentArea.sizeDelta.x, offset * -1);

        currentAnswers.Add(newAnswer3);

        AnswerData newAnswer4 = (AnswerData)Instantiate(answerPrefab, uIElements.AnswersContentArea);
        newAnswer4.UpdateData(question.choice4, 3);

        newAnswer4.Rect.anchoredPosition = new Vector2(0, offset);

        offset -= (newAnswer4.Rect.sizeDelta.y + parameters.Margins);
        uIElements.AnswersContentArea.sizeDelta = new Vector2(uIElements.AnswersContentArea.sizeDelta.x, offset * -1);

        currentAnswers.Add(newAnswer4);

    }

    void EraseAnswers()
    {
        foreach (var answer in currentAnswers)
        {
            Destroy(answer.gameObject);
        }
        currentAnswers.Clear();
    }

    void UpdateScoreUI()
    {
        uIElements.Player_HP.text = "HP: " + events.Play_HP;
        uIElements.Npc_HP.text = "HP: " + events.npc_HP;
        uIElements.PlayerHealthBar.value = events.Play_HP;
        uIElements.NPCHealthBar.value = events.npc_HP;
    }
    public class PostAfterData
    {
        public int characterId;
        public string status;
        public int numOfQnsAnswered;
        public string idOfAnsweredQns;
        public string idOfCorrectlyAnsweredQns;
    }

    public class GetExp
    {
        public int addedExp;
    }

}