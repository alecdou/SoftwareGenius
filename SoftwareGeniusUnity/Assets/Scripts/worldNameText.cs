using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class worldNameText : MonoBehaviour
{
    [SerializeField] GameEvent events = null;
    //public DataManagerMap dataManager;
    
    private TextMeshProUGUI txt;

    private int worldType;//queried, 0-3

    private List<string> worldNames=new List<string>(){"Software Engineering",
        "Software Architecture", "Project Management", "Quality Assurance" };
    
    // Start is called before the first frame update
    void Start()
    {
        //dataManager.Load();
        worldType = events.WorldType;
        txt = GetComponent<TextMeshProUGUI>();
        if (events.mode == "battle")
        {
            Debug.Log("Worldname" + DataManager.userName);
            txt.text = DataManager.userName + "'s " + worldNames[worldType] + " World";
        }
        else
        {
            txt.text = "Ready to challenge your friend?";
        }
       
    }

}
