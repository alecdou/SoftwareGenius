using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CharControl : MonoBehaviour
{
    [SerializeField]
    private Sprite[] charSprite;

    [SerializeField] GameEvent events = null;

    private Image m_Char;
    // Start is called before the first frame update
    void Start()
    {
        m_Char = GetComponent<Image>();
        m_Char.sprite = charSprite[events.WorldType];

    }

}
