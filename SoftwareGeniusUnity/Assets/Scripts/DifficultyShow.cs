using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class DifficultyShow : MonoBehaviour
{
    public static TextMeshProUGUI txt;
    // Start is called before the first frame update
    void Start()
    {
        txt = GetComponent<TextMeshProUGUI>();
        txt.text = "";
        
        
    }
    public static void ChangeDifficultyText(string text)
    {
        txt.text = "Difficulty record: "+text;
    }
    public static void ResetDifficultyText()
    {
        txt.text = "";
    }
}
