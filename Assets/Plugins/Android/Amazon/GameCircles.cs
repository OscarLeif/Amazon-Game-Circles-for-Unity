using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameCircles : PluginSingleton<GameCircles>
{
    private AndroidJavaObject plugin;

    private bool m_isInit = false;

    private void Awake()
    {
        base.Awake();
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
        //TODO Warning this is in Development
        //Check Android Studio Project;
        return false;
    }




}
