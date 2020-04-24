using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PromptWindow : MonoBehaviour
{
    private Text alertmessage;

    private void Awake()
    {
        alertmessage = transform.Find("PromptMessage").GetComponent<Text>();
        Hide();
    }
    public void Show(string alertMessage)
    {
        gameObject.SetActive(true);
        alertmessage.text = alertMessage;

    }

    public void Hide()
    {
        gameObject.SetActive(false);
    }
}
