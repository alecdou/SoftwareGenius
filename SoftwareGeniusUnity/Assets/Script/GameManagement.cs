using System.Collections.Generic;
using UnityEngine;
using System.Linq;
using System.Collections;
using TMPro;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using SimpleJSON;
using UnityEngine.Networking;

public class GameManagement : MonoBehaviour
{

    public JSONNode newJson1;
    public JSONNode newJson2;
    public JSONNode newJson3;
    public Qqustion QList;
    public static Combat CID;
    public static Character Chara;

    public int QuestionIndex = 0;
    [SerializeField] GameEvent events = null;
    [SerializeField] Animator timerAnimtor = null;
    [SerializeField] TextMeshProUGUI timerText = null;
    [SerializeField] Color timerHalfWayOutColor = Color.yellow;
    [SerializeField] Color timerAlmostOutColor = Color.red;
    private Color timerDefaultColor = Color.white;


    private List<AnswerData> PickedAnswers = new List<AnswerData>();
    private List<int> FinishedQuestions = new List<int>();
    private int currentQuestion = -1;

    private int timerStateParaHash = 0;

    private IEnumerator IE_WaitTillNextRound = null;
    private IEnumerator IE_StartTimer = null;

    private bool IsFinished
    {
        get
        {
            if (events.npc_HP <= 0 || events.Play_HP <= 0)
                return true;
            else
                return false;

        }
    }

    void OnEnable()
    {
        events.UpdateQuestionAnswer += UpdateAnswers;
    }

    void OnDisable()
    {
        events.UpdateQuestionAnswer -= UpdateAnswers;
    }

    void Awake()
    {
        events.EXP = 0;
        
        events.Play_HP = 100;
        events.npc_HP = 100;
        events.status = "pending";
        events.numOfQnsAnswered = 0;
        events.idOfAnsweredQns = new List<int>();
        events.idOfCorrectlyAnsweredQns = new List<int>();
    }

    void Start()
    {
        StartCoroutine(PostBefore());
    }

    public void UpdateAnswers(AnswerData newAnswer)
    {
        //if (Questions[currentQuestion].GetAnswerType == Question.AnswerType.Single)
        {
            foreach (var answer in PickedAnswers)
            {
                if (answer != newAnswer)
                {
                    answer.Reset();
                }
            }
            PickedAnswers.Clear();
            PickedAnswers.Add(newAnswer);
        }

    }

    void Display()
    {
        //EraseAnswers
        PickedAnswers = new List<AnswerData>();

        var question = GetRandomQuestion();

        if (events.UpdateQuestionUI != null)
        {
            events.UpdateQuestionUI(question);
        }
        else { Debug.LogWarning("Something wrong while trying to display new Question UI Data. GameEvents.UpdateQuestionUI is null. Issue occured in GameManager.Display() method."); }



        UpdateTimer(true);

    }

    public void Accept()
    {
        UpdateTimer(false);
        bool isCorrect = CheckAnswers();
        FinishedQuestions.Add(QList.ListOfQuestions[currentQuestion].id);
        events.numOfQnsAnswered++;
        events.idOfAnsweredQns.Add(QList.ListOfQuestions[currentQuestion].id);
        if (isCorrect)
        {
            events.idOfCorrectlyAnsweredQns.Add(QList.ListOfQuestions[currentQuestion].id);
        }
        UpdateScore((isCorrect) ? true : false);

        var type = (IsFinished) ? UIManager.ResolutionScreenType.Finish : (isCorrect) ? UIManager.ResolutionScreenType.Correct : UIManager.ResolutionScreenType.Incorrect;

        if (events.DisplayResolutionScreen != null)
        {
            int score = 0;
            if (isCorrect)
            {
                score = 20 + events.level/2;
            }
            else
            {
                score = 10+events.level*5;
            }
            events.DisplayResolutionScreen(type, score);
        }


        if (type != UIManager.ResolutionScreenType.Finish)
        {
            if (IE_WaitTillNextRound != null)
            {
                StopCoroutine(IE_WaitTillNextRound);
            }
            IE_WaitTillNextRound = WaitTillNextRound();
            StartCoroutine(IE_WaitTillNextRound);
        }
    }

    void UpdateTimer(bool state)
    {
        switch (state)
        {
            case true:
                IE_StartTimer = StartTimer();
                StartCoroutine(IE_StartTimer);

                timerAnimtor.SetInteger(timerStateParaHash, 2); //pop up
                break;
            case false:
                if (IE_StartTimer != null)
                {
                    StopCoroutine(IE_StartTimer);
                }

                timerAnimtor.SetInteger(timerStateParaHash, 1);
                break;
        }
    }

    IEnumerator StartTimer()
    {
        var totalTime = 25;
        var timeLeft = totalTime;

        timerText.color = timerDefaultColor;
        while (timeLeft >= 0)
        {
            timerText.text = timeLeft.ToString();



            if (timeLeft < totalTime / 2 && timeLeft > totalTime / 4)
            {
                timerText.color = timerHalfWayOutColor;
            }
            if (timeLeft < totalTime / 4)
            {
                timerText.color = timerAlmostOutColor;
            }


            yield return new WaitForSeconds(1.0f);
            timeLeft--;
        }
        Accept();
    }

    IEnumerator WaitTillNextRound()
    {
        yield return new WaitForSeconds(GameUtility.ResolutionDelayTime);
        Display();
    }

    bool CheckAnswers()
    {
        if (!CompareAnswers())
        {
            return false;
        }
        return true;
    }

    bool CompareAnswers()
    {
        if (PickedAnswers.Count > 0)
        {
            List<int> c = new List<int>();
            int ans = QList.ListOfQuestions[currentQuestion].answer - 1;
            c.Add(ans);

            List<int> p = PickedAnswers.Select(x => x.AnswerIndex).ToList();

            var f = c.Except(p).ToList();
            var s = p.Except(c).ToList();

            return !f.Any() && !s.Any();
        }

        return false;
    }

    QuestionLoad GetRandomQuestion()
    {

        var randomIndex = GetRandomQuestionIndex();
        if (randomIndex > QList.ListOfQuestions.Count)
        {
            Debug.Log("not enough Questions");
            return null;
        }
        else
        {
            currentQuestion = randomIndex;
            Debug.Log("Ans" + currentQuestion + ": " + QList.ListOfQuestions[currentQuestion].answer);
            return QList.ListOfQuestions[currentQuestion];
        }

    }

    int GetRandomQuestionIndex()
    {
        QuestionIndex++;
        return QuestionIndex;
    }

    private void UpdateScore(bool add)
    {

        if (add)
        {
            events.npc_HP -= 20 + events.level/2;
        }
        else
        {
            events.Play_HP -= 10+events.level*5;

        }
        if (events.ScoreUpdated != null)
        {
            events.ScoreUpdated();
        }
    }

    public void ReStart()
    {
        SceneManager.LoadScene("1level");
        //SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }

    public void BackToMap()
    {
        LandCtrl.standingOn = 0;
        SceneManager.LoadScene("mapSolo");

    }

    IEnumerator PostBefore()
    {
        PostBeforeData BeforeData = new PostBeforeData();
        BeforeData.worldId = LandCtrl.worldId;
        BeforeData.landId = LandCtrl.standingOn;
        BeforeData.difficultyLevel = events.level;
        BeforeData.mode = events.mode;
        BeforeData.playerId = int.Parse(StaticVariable.studentId);
        /*BeforeData.worldId = 1;
        BeforeData.landID = 1;
        BeforeData.difficultyLevel = 1;
        BeforeData.mode = "battle";
        BeforeData.playerID = 1;*/

        string json = JsonUtility.ToJson(BeforeData);
        string url = StaticVariable.url + "combat/start";

        UnityWebRequest userRequest = UnityWebRequest.Post(url, json);
        userRequest.uploadHandler.contentType = "application/json";
        userRequest.uploadHandler = new UploadHandlerRaw(System.Text.Encoding.UTF8.GetBytes(json));
        userRequest.SetRequestHeader("Accept", "application/json");
        userRequest.SetRequestHeader("Content-Type", "application/json");

        Debug.Log(json);

        yield return userRequest.SendWebRequest();

        if (userRequest.isNetworkError || userRequest.isHttpError)
        {
            Debug.LogError(userRequest.error);
            yield break;
        }
        else
        {


            //Debug.Log(userRequest.downloadHandler.text);
            JSONNode StartBattleInfo = JSON.Parse(userRequest.downloadHandler.text);



            if (StartBattleInfo.Tag == JSONNodeType.Object)
            {
                foreach (KeyValuePair<string, JSONNode> kvp in (JSONObject)StartBattleInfo)
                {
                    if (kvp.Key == "questions")
                        newJson1 = kvp.Value;
                    if (kvp.Key == "combatId")
                        newJson2 = kvp.Value;
                    if (kvp.Key == "character")
                        newJson3 = kvp.Value;

                }
            }


            string StartBattleString = "{\"ListOfQuestions\": " + newJson1.ToString() + "}";
            QList = JsonUtility.FromJson<Qqustion>(StartBattleString);

            string characterString = newJson3.ToString();
            Chara = JsonUtility.FromJson<Character>(characterString);
            events.characterId = Chara.charId;
            Debug.Log("charExp: " + Chara.exp);
            Debug.Log("charLevel: " + Chara.level);


            string combatString = "{\"combatID\": " + newJson2.ToString() + "}";
            CID = JsonUtility.FromJson<Combat>(combatString);
            events.combatID = CID.combatID;
            Debug.Log("combatID: " + CID.combatID);



            timerDefaultColor = timerText.color;
            timerStateParaHash = Animator.StringToHash("TimerState");

            var seed = UnityEngine.Random.Range(int.MinValue, int.MaxValue);
            UnityEngine.Random.InitState(seed);

            Display();
        }

    }

    public class PostBeforeData
    {
        public int worldId;
        public int landId;
        public int difficultyLevel;
        public string mode;
        public int playerId;

    }


}

