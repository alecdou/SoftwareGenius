using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ButtonControl : MonoBehaviour
{
    [SerializeField] Sprite[] ButtonSprites;
    [SerializeField] Image Button_image;
    [SerializeField] Button Button_Button;
    // Start is called before the first frame update
    public void Update()
    {
        Button_image = GetComponent<Image>();
        Button_Button = GetComponent<Button>();
        if (Button_Button.enabled)
            Button_image.sprite = ButtonSprites[0];
        else
            Button_image.sprite = ButtonSprites[1];
    }
   
}
