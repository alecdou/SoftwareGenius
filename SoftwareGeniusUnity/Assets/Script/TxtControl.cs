using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class TxtControl : MonoBehaviour
{

    [SerializeField]
    private TextMeshProUGUI txt;

    [SerializeField] GameEvent events = null;

    // Start is called before the first frame update
    void Start()
    {
        GameEvent.LevelType level = events.levelType;
        txt = GetComponent<TextMeshProUGUI>();
        txt.text = "[ " + level.ToString()+" ]";
    }
}
