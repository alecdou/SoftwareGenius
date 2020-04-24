using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class Data
{
    public string userName = "";

    public string realName = "";

    public string password = "";

    public string email = "";

    public string accountType = "FB";

    public bool isAdmin = SceneChanger.isAdmin;

    public int overallExp = 0;
}
