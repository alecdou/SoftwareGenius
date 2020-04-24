using UnityEngine;

public abstract class LoginAPI // Stategy class
{
    public abstract void Initialise(MonoBehaviour dbmono);
    public abstract void Authenticate(GetStringCallback callback = null);
    public abstract void GetName(GetStringCallback callback = null);
    public abstract void GetEmail(GetStringCallback callback = null);
    public abstract void GetProfilePic(GetProfilePicCallback callback = null);
}
