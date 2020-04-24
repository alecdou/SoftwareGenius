using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneChangerAddQuestion : MonoBehaviour
{
    public void toAddQuestionScene() {
        SceneManager.LoadScene("AddQuestion");
    }

    public void toMainScene() {
        SceneManager.LoadScene("Mode");
    }
}
