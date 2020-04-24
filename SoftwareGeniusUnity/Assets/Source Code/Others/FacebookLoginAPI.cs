using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Facebook.Unity;
using System;

public class FacebookLoginAPI : LoginAPI
{
    private MonoBehaviour monoScript = null;
    public override void Initialise(MonoBehaviour dbmono)
    {
        InitialiseFBLoop();
        monoScript = dbmono;
    }

    public override void Authenticate(GetStringCallback callback)
    {
        if (FB.IsInitialized)
        {
            var perms = new List<string>() { "public_profile", "email" };
            FB.LogInWithReadPermissions(perms, delegate (ILoginResult result)
            {
                if (FB.IsLoggedIn)
                {
                    callback?.Invoke(true, AccessToken.CurrentAccessToken.UserId); // return string id
                }
                else
                {
                    callback?.Invoke(false, null);
                }
            });
        }
        else
        {
            callback?.Invoke(false, null);
        }
    }

    public override void GetName(GetStringCallback callback = null)
    {
        FB.API("me?fields=name", Facebook.Unity.HttpMethod.GET, delegate (IGraphResult nameResult)
        {
            if (String.IsNullOrEmpty(nameResult.Error) && !nameResult.Cancelled)
            {
                string name = nameResult.ResultDictionary["name"] as string;
                callback?.Invoke(true, name); // return string name
            }
            else
            {
                callback?.Invoke(false, null);
            }
        });
    }

    public override void GetEmail(GetStringCallback callback = null)
    {
        FB.API("me?fields=email", Facebook.Unity.HttpMethod.GET, delegate (IGraphResult nameResult)
        {
            if (String.IsNullOrEmpty(nameResult.Error) && !nameResult.Cancelled)
            {
                string email = nameResult.ResultDictionary["email"] as string;
                callback?.Invoke(true, email); // return string email
            }
            else
            {
                callback?.Invoke(false, null);
            }
        });
    }

    public override void GetProfilePic(GetProfilePicCallback callback = null)
    {
        FB.API("/me/picture?redirect=false", HttpMethod.GET, delegate (IGraphResult picResult)
        {
            if (String.IsNullOrEmpty(picResult.Error) && !picResult.Cancelled)
            {
                IDictionary data = picResult.ResultDictionary["data"] as IDictionary;
                string photoURL = data["url"] as String;

                monoScript.StartCoroutine(fetchProfilePic(photoURL, callback));
            }
            else
            {
                callback?.Invoke(false, null);
            }
        });
    }

    private IEnumerator fetchProfilePic(string url, GetProfilePicCallback callback = null)
    {
        WWW www = new WWW(url);
        yield return www; //wait until it has downloaded
        if (www.error == null)
            callback?.Invoke(true, www.texture); // return Texture2D
        else
            callback?.Invoke(false, null);
    }

    private void InitialiseFBLoop()
    {
        if (!FB.IsInitialized)
        {
            // Initialize the Facebook SDK
            FB.Init(InitCallback, OnHideUnity);
        }
        else
        {
            // Already initialized, signal an app activation App Event
            FB.ActivateApp();
        }
    }

    private void InitCallback()
    {
        if (FB.IsInitialized)
        {
            FB.ActivateApp();
        }
        else
        {
            InitialiseFBLoop();
        }
    }

    private void OnHideUnity(bool isGameShown)
    {
        if (!isGameShown)
        {
            // Pause the game - we will need to hide
            Time.timeScale = 0;
        }
        else
        {
            // Resume the game - we're getting focus again
            Time.timeScale = 1;
        }
    }
}
