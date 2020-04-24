using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class LandBtn : MonoBehaviour
{
    //public DataManagerMap dataManager;

    private Button m_button;
    [SerializeField]
    private Image myIcon;

    private TextMeshProUGUI myText;
    private int landNo;
    private int landId;
    private int landStatus;//according to its sprite
    private string difficulty;
    private int currentLevel;
    public void SetIcon(Sprite mySprite)
    {
        myIcon.sprite = mySprite;

    }
    public void SetText(string ownername)
    {
        myText = GetComponentInChildren<TextMeshProUGUI>();
        myText.text = ownername;

    }
    public void SetNo(int number)
    {
        landNo = number;
        //Debug.Log("Land creation: " + landNo);
    }
    public void SetClicked()
    {
        if (landStatus == 3)
        {
            return;
        }
        if (landStatus == 0 || landStatus == 2)
            DifficultyShow.ChangeDifficultyText(difficulty);
        else
            DifficultyShow.ResetDifficultyText();
        LevelManagement.PastLevel = currentLevel;
        LandCtrl.standingOn = landId;
        CharacCtrl.ChangePosition(landNo);
        Debug.Log("You clicked on: " + landNo);
    }
    public void SetStatus(int status)
    {
        landStatus = status;
    }
    public void SetDifficulty(string diff)
    {
        difficulty = diff;
    }
    public void SetLandId(int id)
    {
        landId = id;
    }
    public void SetCurrentLevel(int difficulty)
    {
        currentLevel = difficulty;
    }
    void Start()
    {
        m_button = GetComponent<Button>();
        m_button.onClick.AddListener(() => SetClicked());
    }
}