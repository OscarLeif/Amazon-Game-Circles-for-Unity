using JetBrains.Annotations;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameCircles : MonoBehaviour
{
    #region Fields

    [CanBeNull]
    private static GameCircles _instance = null;

    [SerializeField]
    private bool _persistent = true;

    [NotNull]
    private static readonly object Lock = new object();

    public static bool Quitting { get; private set; }

    #endregion          

    #region Properties
    [NotNull]
    public static GameCircles Instance
    {
        get
        {
            if (Quitting)
            {
                return null;
            }
            lock (Lock)
            {
                if (_instance != null)
                {
                    return _instance;
                }
                var instances = FindObjectsOfType<GameCircles>();
                var count = instances.Length;
                if (count > 0)
                {
                    if (count == 1)
                    {
                        return _instance = instances[0];
                    }
                    for (var i = 1; i < instances.Length; i++)
                    {
                        Destroy(instances[i]);
                    }
                    return _instance = instances[0];
                }
                return _instance = new GameObject(typeof(GameCircles).Name).AddComponent<GameCircles>();
            }
        }
    }
    #endregion

    #region GameCircles Fields

    public bool PluginEnable = false;

    //Package name of the javaObject
    private static string AndroidJavaClassName = "hammergames.amazonGC.GameCircles";

    private AndroidJavaObject javaObject;

    private bool m_isInit = false;

    #endregion

    #region Monobehaviour Methods

    private void Awake()
    {
        if (_persistent)
        {
            DontDestroyOnLoad(gameObject);
        }
    }

    // Use this for initialization
    private void Start()
    {
        if (PluginEnable)
        {
#if UNITY_ANDROID && !UNITY_EDITOR
        AndroidJavaClass jc = new AndroidJavaClass(AndroidJavaClassName);
        javaObject = jc.CallStatic<AndroidJavaObject>("getInstance");
#endif
#if UNITY_ANDROID && !UNITY_EDITOR
        javaObject.Call("init");
        this.m_isInit = true;
#endif
            Debug.Log("Start Called");
        }
    }

    private void OnApplicationQuit()
    {
        Quitting = true;
    }

    #endregion

    #region Game Circles Methods

    public void ShowLeaderboardsOverlay()
    {
        if (PluginEnable && this.m_isInit)
        {
#if UNITY_ANDROID && !UNITY_EDITOR
        javaObject.Call("ShowLeaderboardsOverlay");
#endif
        }
    }

    public void ShowLeaderboardOverlay(string leaderboardId)
    {
        if (PluginEnable && this.m_isInit)
        {
#if UNITY_ANDROID && !UNITY_EDITOR
        javaObject.Call("ShowLeaderboardOverlay",leaderboardId);
#endif
        }
    }

    public void SubmitScore(string leaderboardId, int score)
    {
        if (PluginEnable && this.m_isInit)
        {
#if UNITY_ANDROID && !UNITY_EDITOR
        javaObject.Call("SubmitScoreLeaderboard", leaderboardId,score);
#endif
        }
    }

    public void ShowAchievementsOverlay()
    {
        if (PluginEnable && this.m_isInit)
        {
#if UNITY_ANDROID && !UNITY_EDITOR
        javaObject.Call("ShowAchievementsOverlay");
#endif
        }
    }

    public void ShowAchievement(string achievementId)
    {
        if (PluginEnable && this.m_isInit)
        {
#if UNITY_ANDROID && !UNITY_EDITOR
        javaObject.Call("ShowAchievementOverlay", achievementId);
#endif
        }
    }

    public bool IsSignedIn()
    {
        bool signed = false;
        if (PluginEnable && this.m_isInit)
        {
            {
#if UNITY_ANDROID && !UNITY_EDITOR
            signed = javaObject.Call<bool>("isSigned");
#endif
            }
        }
        return signed;
    }

    public void ShowSignDialog()
    {
        if (PluginEnable && this.m_isInit)
        {
            Debug.Log("Touch Is Working");
#if UNITY_ANDROID && !UNITY_EDITOR
            javaObject.Call("ShowSignDialog");
#endif
        }
    }

    #endregion
}
