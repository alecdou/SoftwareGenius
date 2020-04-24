using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class ToStudentDetails : MonoBehaviour
{
    public void GotoStudentDetails()
    {
        Debug.Log(EventSystem.current.currentSelectedGameObject.transform.Find("name").GetComponent<Text>().text);
        StaticVariable.nameStudent = EventSystem.current.currentSelectedGameObject.transform.Find("name").GetComponent<Text>().text;
        SceneManager.LoadScene("IndiReport");
    }
}
