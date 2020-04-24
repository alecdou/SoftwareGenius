using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class LoginUI : UI
{
    // UI References
    [SerializeField]
    private Text Txt_name = null;

    [SerializeField]
    private Image FbImage = null;

    [SerializeField]
    private GameObject LogoutButton = null;

    [SerializeField]
    private GameObject LoginButton = null;

    public void Btn_DoLogin()
    {
        DatabaseManager.Instance.Login(delegate (string id, string name, string email)
        {
            Txt_name.text = name;
            LoginButton.SetActive(false);
            LogoutButton.SetActive(true);

            DatabaseManager.Instance.GetProfilePic(delegate (bool picResult, Texture2D tex)
            {
                if (picResult)
                {
                    FbImage.sprite = Sprite.Create(tex, new Rect(0, 0, 50, 50), new Vector2());
                    FbImage.gameObject.SetActive(true);
                }
            });
        });
    }

    public void Btn_DoLogout()
    {
        DatabaseManager.Instance.Logout(delegate ()
        {
            Txt_name.text = "Guest";
            LoginButton.SetActive(true);
            LogoutButton.SetActive(false);
            FbImage.gameObject.SetActive(false);
        });
    }
}
