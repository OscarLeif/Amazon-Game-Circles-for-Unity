package hammergames.amazon;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.amazon.ags.api.achievements.AchievementsClient;
import com.amazon.ags.api.achievements.UpdateProgressResponse;
import com.amazon.ags.api.leaderboards.LeaderboardsClient;
import com.amazon.ags.api.leaderboards.SubmitScoreResponse;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.util.EnumSet;

/**
 * Created by OscarLeif on 5/6/2017.
 */

public class GameCircles extends Application
{
    public static String tag = "Game Circles Plugin";
    public static String UnityObjName = "AmazonGameCircles";
    private static final GameCircles  instance = new GameCircles ();

    private Activity activity;
    private boolean IsInitialized = false;
    private boolean gameServicesAvaliable;

    private AmazonGamesClient agsClient;
    private LeaderboardsClient lbClient;

    // Get instance of the GameCirclesObject
    public static GameCircles getInstance()
    {
        GameCircles .instance.activity = UnityPlayer.currentActivity;
        Log.d ( tag , "Amazon Ads Plugin instantiated.");
        return GameCircles .instance;
    }

    // Initialize Amazon Game Circles
    public void init ( String appKey, boolean testMode )
    {
        Log.d ( tag , "Initializing Amazon Ads plugin.");
        AmazonGamesClient.initialize(activity, callback, myGameFeatures);
        IsInitialized = true;
    }

    public void ShowLeaderboardsOverlay()
    {
        Log.d("AmazonGameCircle", "Show Leaderboards");
        if (gameServicesAvaliable)
        {
            LeaderboardsClient lbClients = agsClient.getLeaderboardsClient();
            if (lbClients != null)
            {
                lbClients.showLeaderboardsOverlay();
            }
        } else
        {
            Log.i("Amazon GameCircle:", "If you're on debug this is normal to appear Game Circles doesn't work in debug mode");
            Log.e("Amazon GameCircle:", "Please check if the app is signed or check your manifest settings");
        }
    }


    AmazonGamesCallback callback = new AmazonGamesCallback()
    {
        @Override
        public void onServiceReady(AmazonGamesClient arg0)
        {
            agsClient = arg0;
            gameServicesAvaliable = true;
        }

        @Override
        public void onServiceNotReady(AmazonGamesStatus arg0)
        {
            gameServicesAvaliable = false;
        }
    };
    EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(AmazonGamesFeature.Achievements, AmazonGamesFeature.Leaderboards);



}
