using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CharacCtrl : MonoBehaviour
{
    [SerializeField] GameEvent events = null;
    //public DataManagerMap dataManager;
    private int worldType;
    private int explored;

    [SerializeField]//set for "CharacImage"
    private Sprite[] characSprites;

    private Image m_charac;

    //to initialize position
    public static RectTransform m_RectTrans;
    private float m_XAxis, m_YAxis;
    private int x_index, y_index;
    // Start is called before the first frame update
    void Start()
    {
        //dataManager.Load();
        worldType = events.WorldType;
        explored = 0;//dataManager.data.StandingOn;
        //calculate index
        y_index = explored / 6;
        x_index = explored % 6;
        
        m_XAxis = 80 * x_index;
        m_YAxis = - 80 * y_index;
        //initialize sprite
        m_charac = GetComponent<Image>();
        m_charac.sprite = characSprites[worldType];
        //initialize position
        m_RectTrans = GetComponent<RectTransform>();
        m_RectTrans.anchoredPosition = new Vector2(m_XAxis, m_YAxis);
    }

    public static void ChangePosition(int landNo)
    {
        //calculate index
        int y_index = landNo / 6;
        int x_index = landNo % 6;

        float m_XAxis = 80 * x_index;
        float m_YAxis = -80 * y_index;
        
        //change position
        //m_RectTrans = GetComponent<RectTransform>();
        m_RectTrans.anchoredPosition = new Vector2(m_XAxis, m_YAxis);
    }
}
