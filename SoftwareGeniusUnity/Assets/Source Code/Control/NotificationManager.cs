using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public enum NotificationType
{
    Load,
    Notice
}

public class Notification
{
    public SimpleCallback Callback { get; private set; }
    public NotificationType Type { get; private set; }
    public string Text { get; private set; }
    public bool ShowHideButton { get; private set; }

    public Notification(NotificationType t, string tex, bool s, SimpleCallback cb)
    {
        Type = t;
        Text = tex;
        ShowHideButton = s;
        Callback = cb;
    }
}

public class NotificationManager : MonoBehaviour // Singleton class
{
    // Main variables
    private List<Notification> NotificationList = new List<Notification>();

    // Helper variables
    public static NotificationManager Instance { get; private set; }
    private bool LoadingRunning = false;
    private int NotificationId = 0;
    private Notification CurrentNotification = null;

    // Other references
    [SerializeField]
    private float TextDelay = 0.1f;

    [SerializeField]
    private Text Txt_content = null;

    [SerializeField]
    private GameObject NotificationObjects = null;

    [SerializeField]
    private Button HideButton = null;

    public void ShowNotification(NotificationType type, string content = null, bool showHideButton = false, SimpleCallback callback = null)
    {
        NotificationList.Add(new Notification(type, content, showHideButton, callback));
        if (!LoadingRunning)
            ProcessNextNotification();
    }

    public void HideNotification(float delay = 0)
    {
        if (delay > 0)
            StartCoroutine(Sequence_HideNotification(delay));
        else
        {
            StopAllCoroutines();

            if (LoadingRunning)
            {
                LoadingRunning = false;
            }

            if (HideButton.gameObject.activeSelf)
                HideButton.gameObject.SetActive(false);
            NotificationObjects.SetActive(false);

            CurrentNotification.Callback?.Invoke();

            ProcessNextNotification();
        }
    }

    private IEnumerator Sequence_HideNotification(float delay)
    {
        yield return new WaitForSeconds(delay);

        if (LoadingRunning)
        {
            LoadingRunning = false;
        }

        if (HideButton.gameObject.activeSelf)
            HideButton.gameObject.SetActive(false);
        NotificationObjects.SetActive(false);

        CurrentNotification.Callback?.Invoke();

        ProcessNextNotification();
    }

    private IEnumerator Sequence_Loading(string loadingText = "Loading")
    {
        LoadingRunning = true;
        int i = 0;
        string[] texts = new string[4];
        texts[0] = loadingText + ".";
        texts[1] = loadingText + "..";
        texts[2] = loadingText + "...";
        texts[3] = loadingText + "....";

        while (LoadingRunning)
        {

            Txt_content.text = texts[i];
            i++;
            if (i >= texts.Length)
                i = 0;

            yield return new WaitForSeconds(TextDelay);
        }
    }

    private void ProcessNextNotification()
    {
        if (NotificationId < NotificationList.Count)
        {
            CurrentNotification = NotificationList[NotificationId];

            switch (CurrentNotification.Type)
            {
                case NotificationType.Load:
                    if (CurrentNotification.Text != null)
                    {
                        StartCoroutine(Sequence_Loading(CurrentNotification.Text));
                    }
                    else
                    {
                        StartCoroutine(Sequence_Loading());
                    }
                    break;
                case NotificationType.Notice:
                    Txt_content.text = CurrentNotification.Text;
                    if (CurrentNotification.ShowHideButton)
                        HideButton.gameObject.SetActive(true);
                    break;
            }
            NotificationId++;
            NotificationObjects.SetActive(true);
        }
    }

    private void Awake()
    {
        if (Instance == null)
            Instance = this;
        else
            Destroy(this);
    }
}
