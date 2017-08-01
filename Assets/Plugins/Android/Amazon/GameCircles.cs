using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameCircles : MonoBehaviour
{
    private AndroidJavaObject plugin;

    private bool m_isInit = false;

    private void Awake()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        AndroidJavaClass jc = new AndroidJavaClass("hammergames.amazon.GameCircles");
        plugin = jc.CallStatic<AndroidJavaObject>("getInstance");
#endif
    }

    // Use this for initialization
    private void Start()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("init");
#endif
    }

    public void ShowLeaderboardsOverlay()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("ShowLeaderboardsOverlay");
#endif
    }

}
