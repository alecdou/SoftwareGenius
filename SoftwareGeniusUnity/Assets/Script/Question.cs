/*using System.Collections.Generic;
using UnityEngine;


[System.Serializable]
public struct Answer
{
    [SerializeField] private string _info;
    [SerializeField] private bool _isCorrect;

    public string Info { get { return _info; } }
    public bool IsCorrect { get { return _isCorrect; } }
}

[CreateAssetMenu(fileName = "New Question", menuName = "Quiz/new Question")]

public class Question : ScriptableObject
{
    //public enum AnswerType { Multi, Single }

    [SerializeField] private int level = 0;
    public int Level { get { return level; } }

    //question + answer
    [SerializeField] private string _info = string.Empty;
    public string Info { get { return _info; } }

    [SerializeField] Answer[] _anwsers = null;
    public Answer[] Answers { get { return _anwsers; } }

 
    [SerializeField] private bool _useTimer = false;
    public bool UseTimer { get { return _useTimer; } }

    [SerializeField] private int _timer = 0;
    public int Timer { get { return _timer; } }

    
    [SerializeField] private int _addScore = 10;
    public int AddScore { get { return _addScore; } }

    public List<int> GetCorrectAnswers()
    {
        List<int> CorrectAnswers = new List<int>();

        for (int i = 0; i < Answers.Length; i++)
        {
            if (Answers[i].IsCorrect)
            {
                CorrectAnswers.Add(i);
            }
        }

        return CorrectAnswers;
    }
}


/*
public struct Answer
{
    private string _info;
    private bool _isCorrect;

    public string Info { get { return _info; } }
    public bool IsCorrect { get { return _isCorrect; } }
}
public class Question
{
    private int level = 0;
    public int Level { get { return level; } }


    private string _info = string.Empty;
    public string Info { get { return _info; } }

    Answer[] _anwsers = null;
    public Answer[] Answers { get { return _anwsers; } }


    private bool _useTimer = false;
    public bool UseTimer { get { return _useTimer; } }

    private int _timer = 0;
    public int Timer { get { return _timer; } }

    private int _addScore;
    public int AddScore { get { return _addScore; } }

    public List<int> GetCorrectAnswers()
    {
        List<int> CorrectAnswers = new List<int>();

        for (int i = 0; i < Answers.Length; i++)
        {
            if (Answers[i].IsCorrect)
            {
                CorrectAnswers.Add(i);
            }
        }

        return CorrectAnswers;
    }

}
public struct Character
{
    int Player_DP;

}
public struct NPC
{
    int NPC_DP;

}
public class LoadBeforeData
{
    public List<Question> Questions;
    public Character character;
    public NPC npc;



}
*/