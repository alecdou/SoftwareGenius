using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using System.Text.RegularExpressions;
using System;

public class UI : MonoBehaviour
{
    public InputField userName;

    public InputField loginEmail;

    public InputField email;

    public InputField realName;

    public InputField password;

    public InputField loginPassword;

    public InputField reEnterPW;

    public Button signupBtn;

    public Button loginBtn;

    public DataManager dataManager;

    [SerializeField] public AlertWindow alert;
    private void Start()
    {
        try
        {
            this.userName.characterLimit = 15;
            this.realName.characterLimit = 25;
            this.password.characterLimit = 15;
            this.reEnterPW.characterLimit = 15;
            this.loginEmail.characterLimit = 15;
            this.loginPassword.characterLimit = 15;
        }
        catch(Exception e)
        {
            Debug.Log(e);
        }
    }

    public void changeUserName(string userName)
    {
        dataManager.data.userName = userName;
    }

    public void changeLoginEmail(string loginEmail)
    {
        dataManager.lgdata.email = loginEmail;
    }

    public void changeEmail(string email)
    {
        dataManager.data.email = email;
    }

    public void changeRealName(string realName)
    {
        dataManager.data.realName = realName;
    }

    public void changePassword(string pw)
    {
        dataManager.data.password = pw;
    }

    public void changeLoginPassword(string loginPw)
    {
        dataManager.lgdata.password = loginPw;
    }

    public const string MatchEmailPattern = @"^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$";

    public static bool validateEmail(string email)
    {
        if (email != null)
            return Regex.IsMatch(email, MatchEmailPattern);
        else
            return false;
    }

    public void ClickSignUp()
    {
        bool emailformat = validateEmail(dataManager.data.email);
        if (dataManager.data.userName == "" || dataManager.data.email == "" || emailformat == false || dataManager.data.realName == ""
            || dataManager.data.password == "" || dataManager.data.password != reEnterPW.text)
        {
            alert.Show("Invalid input");
        }
        else
        {
            StartCoroutine(dataManager.signUp());
            userName.text = "";
            email.text = "";
            realName.text = "";
            password.text = "";
            reEnterPW.text = "";
        }
    }

    public void ClickLogin()
    {
        bool emailformat = validateEmail(dataManager.lgdata.email);
        if (dataManager.lgdata.email == "" || dataManager.lgdata.password == "" || emailformat == false)
        {
            alert.Show("Invalid input");
        }
        else
        {
            StartCoroutine(dataManager.login());
            loginEmail.text = "";
            loginPassword.text = "";
        }
    }
}
