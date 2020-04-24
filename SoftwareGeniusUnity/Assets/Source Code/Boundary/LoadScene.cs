using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class LoadScene : MonoBehaviour
{

    [SerializeField] GameEvent events = null;

    public void onClickEnter()
    {
        //events.WorldType = 0;
        SceneManager.LoadScene("1level");
    }

}
