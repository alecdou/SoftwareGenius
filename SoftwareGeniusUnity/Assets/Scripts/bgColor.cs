using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class bgColor : MonoBehaviour
{
    [SerializeField] GameEvent events = null;
    //public DataManagerMap dataManager;
    private int worldType;
    
    private Image image;
    // Start is called before the first frame update
    void Start()
    {
        
        worldType = events.WorldType;
        image = GetComponent<Image>();
        switch (worldType){
            case 0://dark blue
                image.color = new Color32(28, 23, 59, 255);
                break;
            case 1://dark green
                image.color = new Color32(9, 64, 19, 255);
                break;
            case 2://brown
                image.color = new Color32(99, 70, 23, 255);
                break;
            case 3://dark red
                image.color = new Color32(110, 13, 13, 200);
                break;
        }
        
    }

   
}
