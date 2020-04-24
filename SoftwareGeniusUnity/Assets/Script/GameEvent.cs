using System;
using System.Collections.Generic;
using UnityEngine;


[CreateAssetMenu(fileName = "GameEvent", menuName = "Quiz/new GameEvent")]

public class GameEvent : ScriptableObject
{
    public delegate void UpdateQuestionUICallback(QuestionLoad question);
    public UpdateQuestionUICallback UpdateQuestionUI;

    public delegate void UpdateQuestionAnswerCallback(AnswerData pickedAnswer);
    public UpdateQuestionAnswerCallback UpdateQuestionAnswer;

    public delegate void DisplayResolutionScreenCallback(UIManager.ResolutionScreenType type, int score);
    public DisplayResolutionScreenCallback DisplayResolutionScreen;

    public delegate void ScoreUpdatedCallback();
    public ScoreUpdatedCallback ScoreUpdated;


    public enum LevelType { Easy, Medium, Hard };

    [HideInInspector]
    public int CurrentFinalScore = 0; //当前EXP 

    [HideInInspector]

    public int Play_HP = 100; //血量

    [HideInInspector]
    public int npc_HP = 100; //血量
    public LevelType levelType;

    //新增
    public string stuName = StaticVariable.nameStudent;//player Identifier

    //post
    public int WorldType = 0;//默认值
    public int level = 0;//默认值
    public int landID = LandCtrl.standingOn;
    public string mode = "battle";
    public string playerId = StaticVariable.studentId;
    public int npcId;
    public string status = "pending";

    public int combatID;
    //由playerID和npcID获得
    public int Player_DP = 50;
    public int NPC_DP = 10;


    //save
    public int characterId;
    public int numOfQnsAnswered = 0;
    public List<int> idOfAnsweredQns;
    public List<int> idOfCorrectlyAnsweredQns;

    public int EXP = 0;

}