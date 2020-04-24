using SimpleJSON;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.UI;

public class UserInfoCtrl : MonoBehaviour
{
    private List<UserInfoUI> UserListUI;

    [SerializeField]//set for "UserInfo"
    private GameObject buttonTemplate;
    [SerializeField]//set for "Content"
    private GridLayoutGroup gridGroup;

    [SerializeField]//set for "Avatar"'s sprites
    private Sprite[] avatars;

    private string userListStr;
    private Users userList;

    void Start()
    {
        StartCoroutine(GetAllUserRequest());

        
    }
    IEnumerator GetAllUserRequest()
    {
        string worldtype = LandCtrl.worldCode;
        string url = StaticVariable.url + "world/getUsersByCategory/" + worldtype;
        UnityWebRequest webRequest = UnityWebRequest.Get(url);
        yield return webRequest.SendWebRequest();

        if (webRequest.isNetworkError || webRequest.isHttpError)
        {
            Debug.Log("Error: " + webRequest.error);
            yield break;
        }

        if (webRequest.downloadHandler.text == "")
        {
            Debug.Log("Users does not exist");
            yield break;
        }

        JSONNode rawJson = JSON.Parse(webRequest.downloadHandler.text);
        userListStr = "{\"listOfUsers\":" + rawJson.ToString() + "}";
        Debug.Log("UserList:"+rawJson.ToString());
        userList = JsonUtility.FromJson<Users>(userListStr);

        UserListUI = new List<UserInfoUI>();
        foreach (UserInfo userInfo in userList.listOfUsers)
        {
            if (userInfo.userId == LandCtrl.userId || userInfo.isAdmin)
            {
                continue;
            }
            UserInfoUI newUser = new UserInfoUI();
            newUser.iconSprite = avatars[Random.Range(0, avatars.Length)];
            newUser.userName = userInfo.username;
            newUser.userId = userInfo.userId;

            UserListUI.Add(newUser);
        }

        GenList();
    }
    void GenList()
    {
        if (UserListUI.Count < 3)
        {
            gridGroup.constraintCount = UserListUI.Count;
        }
        else
        {
            gridGroup.constraintCount = 3;
        }
        foreach (UserInfoUI newUser in UserListUI)
        {
            GameObject newButton = Instantiate(buttonTemplate) as GameObject;
            newButton.SetActive(true);

            newButton.GetComponent<UserInfoBtn>().SetAvatar(newUser.iconSprite);
            newButton.GetComponent<UserInfoBtn>().SetName(newUser.userName);
            newButton.GetComponent<UserInfoBtn>().SetId(newUser.userId);
            newButton.transform.SetParent(buttonTemplate.transform.parent, false);
        }
    }
    public class UserInfoUI
    {
        public Sprite iconSprite;
        public string userName;
        public int userId;
    }
    public class Users
    {
        public List<UserInfo> listOfUsers;
    }

    [System.Serializable]
    public class UserInfo
    {
        public int userId;
        public string username;
        public string realName;
        public string userAvatar;
        public string password;
        public string email;
        public string accountType;
        public bool isAdmin;
        public int overallExp;
        /*
        public static UserInfo CreateFromJSON(string jsonString)
        {
            return JsonUtility.FromJson<UserInfo>(jsonString);
        }*/
    }
}
