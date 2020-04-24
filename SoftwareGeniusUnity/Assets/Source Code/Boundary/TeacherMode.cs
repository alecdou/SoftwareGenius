using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TeacherMode : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        StaticVariable.scene = "TeacherMode";
        StaticVariable.isStudent = false;
    }
}
