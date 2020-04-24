using SimpleJSON;
using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using TMPro;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.SceneManagement;

public class DataManager : MonoBehaviour
{
    public Data data;
    public loginData lgdata;
    public UI ui;
    public static string userName;

    //public string signupurl = "http://localhost:9090/api/player/addUser";
    //public string loginurl = "http://localhost:9090/api/player/login/";

    [SerializeField] GameEvent events = null;

    //private string signupfile = "signupData.txt";
    //private string loginfile = "loginData.txt";

    public IEnumerator signUp()
    {
        this.data.isAdmin = SceneChanger.isAdmin;
        string json = JsonUtility.ToJson(this.data);
        Debug.Log(json);
        Debug.Log(SceneChanger.isAdmin);
        UnityWebRequest PostRequest = UnityWebRequest.Post(StaticVariable.url + "player/addUser", json);
        PostRequest.uploadHandler.contentType = "application/json";
        PostRequest.uploadHandler = new UploadHandlerRaw(System.Text.Encoding.UTF8.GetBytes(json));
        PostRequest.SetRequestHeader("Accept", "application/json");
        PostRequest.SetRequestHeader("Content-Type", "application/json");

        yield return PostRequest.SendWebRequest();

        if (PostRequest.isNetworkError || PostRequest.isHttpError)
        {
            showSignUpErrorMessage();
            Debug.LogError(PostRequest.error);
            //yield break;
        }
        else
        {
            Debug.Log(json);
            SceneManager.LoadScene("Home");
        }

        //writeToFile(signupfile, json);
    }

    public void showSignUpErrorMessage()
    {
        ui.alert.Show("Invalid input!");
    }

    public void showLoginErrorMessage()
    {
        ui.alert.Show("user not found / password wrong!");
    }

    public IEnumerator login()
    {
        string loginEmail = lgdata.email;
        string loginPw = lgdata.password;
        
       
        UnityWebRequest loginRequest = UnityWebRequest.Get(StaticVariable.url + "player/login/" + loginEmail + "/" + loginPw);
        yield return loginRequest.SendWebRequest();

        if (loginRequest.isNetworkError || loginRequest.isHttpError)
        {
            showLoginErrorMessage();
            //yield break;
        }
        else
        {
            // return the successful login in stu id
            Debug.Log(loginRequest.downloadHandler.text);
            string userId = loginRequest.downloadHandler.text;
            StaticVariable.studentId = loginRequest.downloadHandler.text;


            UnityWebRequest playerRequest = UnityWebRequest.Get(StaticVariable.url + "player/getUser/" + userId);
            yield return playerRequest.SendWebRequest();

            if (playerRequest.isNetworkError || playerRequest.isHttpError)
            {
                Debug.Log(playerRequest.error);
            }
            else
            {
                Debug.Log("Get player data successfully");
            }

            JSONNode playerData = JSON.Parse(playerRequest.downloadHandler.text);
            bool isAdmin = playerData["isAdmin"];
            StaticVariable.isStudent = !isAdmin;
            userName = playerData["username"];
            //StaticVariable.nameStudent = userName;
            Debug.Log(StaticVariable.nameStudent);
            if (isAdmin)
            {
                SceneManager.LoadScene("TeacherMode");
            }
            else
                SceneManager.LoadScene("Mode");
            Debug.Log(playerData);

        }
        //writeToFile(loginfile, json);
    }

    public void writeToFile(string fileName, string json)
    {
        string path = GetFilePath(fileName);
        FileStream fileStream = new FileStream(path, FileMode.Create);

        using (StreamWriter writer = new StreamWriter(fileStream))
        {
            writer.Write(json);
        }
    }

    private string GetFilePath(string fileName)
    {
        return Application.persistentDataPath + "/" + fileName;
    }
}
