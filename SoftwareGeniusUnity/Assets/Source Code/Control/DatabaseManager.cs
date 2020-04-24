using System.Collections;
using UnityEngine;
using UnityEngine.Networking;
using Facebook.MiniJSON;
using System.Collections.Generic;

// Callbacks
public delegate void QuizResultCallback(float result);
public delegate void SimpleCallback();
public delegate void LoginCallback(string id, string name, string email);
public delegate void GetStringCallback(bool result, string data);
public delegate void GetProfilePicCallback(bool result, Texture2D tex);
public delegate void FetchDicCallback(Dictionary<string, object> dic);

public class DatabaseManager : MonoBehaviour
{
    // Main variables
    private LoginAPI loginAPI = null;

    // Helper variables
    public string UserId { get; private set; }
    public string UserName { get; private set; }
    public string UserEmail { get; private set; }
    public bool UserLoggedIn { get; private set; }
    public static DatabaseManager Instance { get; private set; }

    public void Login(LoginCallback callback = null)
    {
        NotificationManager.Instance.ShowNotification(NotificationType.Load, "Authenticating");

        loginAPI.Authenticate(delegate (bool result, string id)
        {
            if (result)
            {
                NotificationManager.Instance.HideNotification();
                NotificationManager.Instance.ShowNotification(NotificationType.Load, "Logging in");
                // API authentication success, get user's name, email, then login to db

                loginAPI.GetName(delegate (bool nameResult, string name)
                {
                    if (nameResult)
                    {
                        loginAPI.GetEmail(delegate (bool emailResult, string email)
                        {
                            if (emailResult)
                            {
                                NotificationManager.Instance.HideNotification();
                                //database login to load

                                /*DBLogin(id, name, email, delegate (Dictionary<string, object> accountResult)
                                {
                                    if (accountResult.TryGetValue("accountinfo", out object accountinfo))
                                    {
                                        string accountinfostr = Json.Serialize(accountinfo);
                                        Dictionary<string, object> accountinfodic = Json.Deserialize(accountinfostr) as Dictionary<string, object>;
                                        *//* foreach (KeyValuePair<string, object> entry in accountinfodic)
                                         {
                                             Debug.Log(entry.Key + ", " + entry.Value); // data from db
                                         }*//*

                                        UserId = id;
                                        UserName = name;
                                        UserEmail = email;
                                        UserLoggedIn = true;
                                        callback?.Invoke(UserId, UserName, UserEmail);
                                    }
                                    else
                                    {
                                        NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Error: accountinfo not found.", true);
                                    }
                                });*/
                            }
                            else
                            {
                                NotificationManager.Instance.HideNotification();
                                NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Failed to get email.", true);
                            }
                        });
                    }
                    else
                    {
                        NotificationManager.Instance.HideNotification();
                        NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Failed to get name.", true);
                    }
                });
            }
            else
            {
                NotificationManager.Instance.HideNotification();
                NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Authentication failed.", true);
            }
        });
    }

    public void Logout(SimpleCallback callback = null)
    {
        UserId = null;
        UserName = null;
        UserEmail = null;
        UserLoggedIn = false;
        callback?.Invoke();
    }

    public void GetProfilePic(GetProfilePicCallback callback = null)
    {
        loginAPI.GetProfilePic(delegate (bool result, Texture2D tex)
        {
            callback?.Invoke(result, tex);
        });
    }


    private IEnumerator Request(string uri, FetchDicCallback callback, bool showServerMessage = true)
    {
        using (UnityWebRequest webRequest = UnityWebRequest.Get(uri))
        {
            NotificationManager.Instance.ShowNotification(NotificationType.Load, "Fetching data");
            yield return webRequest.SendWebRequest();
            if (webRequest.isNetworkError)
            {
                NotificationManager.Instance.HideNotification();
                //NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Network error: " + URL, true);
            }
            else if (webRequest.isHttpError)
            {
                NotificationManager.Instance.HideNotification();
                //NotificationManager.Instance.ShowNotification(NotificationType.Notice, "HTTP error: " + URL, true);
            }
            else if (webRequest.error != null)
            {
                NotificationManager.Instance.HideNotification();
                //NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Error <" + webRequest.error + ">: " + URL, true);
            }
            else
            {
                NotificationManager.Instance.HideNotification();

                Dictionary<string, object> dic = Json.Deserialize(webRequest.downloadHandler.text) as Dictionary<string, object>;
                if (dic == null)
                {
                    if (showServerMessage)
                    {
                        NotificationManager.Instance.ShowNotification(NotificationType.Notice, "" + webRequest.downloadHandler.text, true);
                    }
                    else
                    {
                        callback?.Invoke(dic); // return null result to continue other processes
                    }
                }
                else
                {
                    if (dic.TryGetValue("jsonResult", out object jsonResult))
                    {
                        if (jsonResult.ToString() == "success")
                        {
                            callback?.Invoke(dic);
                        }
                        else
                        {
                            NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Request Failed:\nJson Error.", true);
                        }
                    }
                    else
                    {
                        NotificationManager.Instance.ShowNotification(NotificationType.Notice, "Request Failed:\nCorrupted data.", true);
                    }
                }
            }
        }
    }

    // called by javascript on WebGL build
    private void SetUrl(string url)
    {
        //URL = url;
    }

    private void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            loginAPI = new FacebookLoginAPI();
            loginAPI.Initialise(this);
            UserId = null;
            UserName = null;
            UserEmail = null;
            UserLoggedIn = false;
        }
        else
            Destroy(this);
    }

}
