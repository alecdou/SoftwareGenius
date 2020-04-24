using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class AlertWindow : MonoBehaviour
{
    private Text alertmessage;

    private void Awake()
    {
        alertmessage = transform.Find("AlertMessage").GetComponent<Text>();
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
