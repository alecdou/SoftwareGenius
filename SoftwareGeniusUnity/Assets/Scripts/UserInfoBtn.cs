using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UserInfoBtn : MonoBehaviour
{
    [SerializeField]
    private Image myAvatar;
    [SerializeField]
    private Text myUserName;
    private Button m_button;

    private int userId;
    public void SetAvatar(Sprite mySprite)
    {
        myAvatar.sprite = mySprite;

    }
    public void SetName(string username)
    {
        myUserName.text = username;

    }
    public void SetId(int id)
    {
        userId = id;
    }
    public void SetClicked()
    {
        LandCtrl.opponentId = userId;
        Debug.Log("You clicked on: " + myUserName);
    }
    void Start()
    {
        m_button = GetComponent<Button>();
        m_button.onClick.AddListener(() => SetClicked());
    }
}
