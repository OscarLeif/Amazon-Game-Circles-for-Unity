using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameCircles : MonoBehaviour
{
    private static string AndroidJavaClassName = "hammergames.amazonGC.GameCircles";
    private AndroidJavaObject plugin;
    private bool m_isInit = false;

    private static GameCircles inst = null;

    public static GameCircles Inst
    {
        get
        {
            if (inst == null)
            {
                inst = GameObject.FindObjectOfType<GameCircles>();
                if (inst == null)
                {
                    GameObject singleton = new GameObject(typeof(GameCircles).Name);
                    inst = singleton.AddComponent<GameCircles>();
                    inst.name = typeof(GameCircles).Name;
                }
            }
            return inst;
        }
    }

    private void Awake()
    {
        if (inst == null)
        {
            inst = Inst;
            DontDestroyOnLoad(this.gameObject);
        }
        else
        {
            Destroy(this.gameObject);
        }
    }

    // Use this for initialization
    private void Start()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        AndroidJavaClass jc = new AndroidJavaClass(AndroidJavaClassName);
        plugin = jc.CallStatic<AndroidJavaObject>("getInstance");
#endif
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("init");
#endif
        Debug.Log("Start Called");
    }

    public void ShowLeaderboardsOverlay()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("ShowLeaderboardsOverlay");
#endif
    }

    public void ShowLeaderboardOverlay(string leaderboardId, int score)
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("ShowLeaderboard",leaderboardId, score );
#endif
    }

    public void SubmitScore(string leaderboardId, int score)
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("SubmitScoreLeaderboard", leaderboardId,score);
#endif
    }

    public void ShowAchievementsOverlay()
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("ShowAchievementsOverlay");
#endif
    }

    public void ShowAchievement(string achievementId)
    {
#if UNITY_ANDROID && !UNITY_EDITOR
        plugin.Call("ShowAchievementOverlay", achievementId);
#endif
    }

    public bool IsSignedIn()
    {
        bool signed = false;
        {
#if UNITY_ANDROID && !UNITY_EDITOR
            signed = plugin.Call<bool>("isSigned");
#endif
        }
        return signed;
    }

    public void ShowSignDialog()
    {
        Debug.Log("Touch Is Working");
#if UNITY_ANDROID && !UNITY_EDITOR
            plugin.Call("ShowSignDialog");
#endif
    }
}
