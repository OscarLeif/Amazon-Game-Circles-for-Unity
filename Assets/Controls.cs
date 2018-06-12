using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Controls : MonoBehaviour {

	public void onClickInitialize()
    {
        GameCircles.Instance.InitGamecircles();
    }

    public void onClickLogin()
    {
        GameCircles.Instance.ShowSignDialog();
    }

    public void onClickLeaderboards()
    {
        GameCircles.Instance.ShowLeaderboardsOverlay();
    }

    public void onClickAchievements()
    {
        GameCircles.Instance.ShowAchievementsOverlay();
    }
}
