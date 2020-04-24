using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class AnswerData : MonoBehaviour
{
    [Header("UI Element")]
    [SerializeField] TextMeshProUGUI infoTextObject;
    [SerializeField] Image toggle;

    [Header("Textures")]
    [SerializeField] Sprite uncheckedToggle;
    [SerializeField] Sprite checkedToggle;

    [Header("References")]
    [SerializeField] GameEvent events;

    private RectTransform _rect;
    public RectTransform Rect
    {
        get
        {
            if(_rect == null)
            {
                _rect = GetComponent<RectTransform>() ?? gameObject.AddComponent<RectTransform>();
            }
            return _rect;
        }
    }

    private int _answerIndex = -1;
    public int AnswerIndex { get { return _answerIndex; } }

    private bool Checked = false;

    public void UpdateData(string info, int index)
    {
        infoTextObject.text = info;
        _answerIndex = index;
    }

    public void Reset()
    {
        Checked = false;
        UpdateUI();   
    }
    public void SwitchState()
    {
        Checked = !Checked;
        UpdateUI();

        //update the current picked answer
        events.UpdateQuestionAnswer?.Invoke(this);
    }
    void UpdateUI()
    {
        toggle.sprite = (Checked) ? checkedToggle:uncheckedToggle;
    }
}

