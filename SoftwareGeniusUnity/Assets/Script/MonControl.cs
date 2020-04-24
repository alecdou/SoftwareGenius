
using UnityEngine;
using UnityEngine.UI;

public class MonControl : MonoBehaviour
{
    [SerializeField]
    private Sprite[] monSprite;

    [SerializeField] GameEvent events = null;

    private Image m_Char;
    // Start is called before the first frame update
    void Start()
    {
        m_Char = GetComponent<Image>();
        m_Char.sprite = monSprite[events.level-1];

    }

}
