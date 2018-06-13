package plugins.ata;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.amazon.ags.api.AGResponseCallback;
import com.amazon.ags.api.AGResponseHandle;
import com.amazon.ags.api.AmazonGamesCallback;
import com.amazon.ags.api.AmazonGamesClient;
import com.amazon.ags.api.AmazonGamesFeature;
import com.amazon.ags.api.AmazonGamesStatus;
import com.amazon.ags.api.achievements.AchievementsClient;
import com.amazon.ags.api.leaderboards.LeaderboardsClient;
import com.amazon.ags.api.leaderboards.SubmitScoreResponse;
import com.unity3d.player.UnityPlayer;

import java.util.EnumSet;

/**
 * Created by OscarLeif on 5/6/2017.
 */

public class GameCircles extends Application
{
    public static String TAG = "Game Circles Plugin";
    public static String UnityObjName = "AmazonGameCircles";
    private static final GameCircles instance = new GameCircles();

    private Activity activity;
    private boolean IsInitialized = false;
    private boolean gameServicesAvaliable;

    private AmazonGamesClient agsClient;
    private LeaderboardsClient lbClient;

    // Get instance of the GameCirclesObject
    public static GameCircles getInstance()
    {
        GameCircles.instance.activity = UnityPlayer.currentActivity;
        Log.d(TAG, "Amazon Game Circles Plugin instantiated.");
        return GameCircles.instance;
    }

    // Initialize Amazon Game Circles
    public void init()
    {
        Log.d(TAG, "Initializing Amazon Game Circles plugin.");
        AmazonGamesClient.initialize(activity, callback, myGameFeatures);
        IsInitialized = true;
    }

    public boolean isSigned()
    {
        boolean isSigned = false;
        if (gameServicesAvaliable)
        {
            if (agsClient != null)
            {
                if (agsClient.getPlayerClient().isSignedIn())
                {
                    isSigned = true;
                }
            }
        }

        //This should be do it but how can I return this to Unity?
        //I only know using a String in some project that I don't remember using this
        // That is the reason of the UnityObjName
        //TODO complete return value to Unity
        return isSigned;
    }

    public boolean isGameServicesAvaliable()
    {
        return gameServicesAvaliable;
    }

    public void ShowLeaderboardsOverlay()
    {
        Log.d("AmazonGameCircle", "Show Leaderboards overlay");
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

    public void ShowLeaderboardOverlay(String leaderboardId)
    {
        Log.d(TAG, "Show Leaderboard By Id");
        if(gameServicesAvaliable)
        {
            LeaderboardsClient lbClients = agsClient.getLeaderboardsClient();
            if(lbClients!=null)
            {
                lbClients.showLeaderboardOverlay(leaderboardId);
            }
        }
    }

    //TODO Fix this and test I don't remember the use of this
    public void GetLeaderboards(String leaderboardId)
    {
        Log.d("AmazonGameCircle", "Get Leaderboards ");
        if (gameServicesAvaliable)
        {
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            if (lbClient != null)
            {
                lbClient.getLeaderboards(leaderboardId);
            }
        }
    }

    public void SubmitScoreLeaderboard(String leaderboardId, int score)
    {
        Log.d(TAG, "Start submit score");
        if (gameServicesAvaliable)
        {
            LeaderboardsClient lbClient = agsClient.getLeaderboardsClient();
            AGResponseHandle<SubmitScoreResponse> handle = lbClient.submitScore(leaderboardId, score);

            //Optional callback to recieve notification of success or failure
            handle.setCallback(new AGResponseCallback<SubmitScoreResponse>()
            {
                @Override
                public void onComplete(SubmitScoreResponse result)
                {
                    if (result.isError())
                    {
                        // Add optional error
                    } else
                    {
                        // continue game flow.
                        Toast.makeText(activity, "Record updated", Toast.LENGTH_SHORT);
                    }
                }
            });
        } else
        {
            Log.d(TAG, "Developer mode this only work using a Signed App");
        }
    }


    public void ShowAchievementsOverlay()
    {
        if (gameServicesAvaliable)
        {
            AchievementsClient acClient = agsClient.getAchievementsClient();
            if (acClient != null)
            {
                acClient.showAchievementsOverlay();
            }
        }
    }

    public void ShowAchievementOverlay(String achievementId)
    {
        if (gameServicesAvaliable)
        {
            AchievementsClient acClient = agsClient.getAchievementsClient();
            if (acClient != null)
            {
                acClient.showAchievementsOverlay(achievementId);
            }
        }
    }

    public void ShowSignDialog()
    {
        if (gameServicesAvaliable)
        {
            Log.d(TAG, "Show Sign Dialog");
            if(agsClient!=null)
            {
                agsClient.showSignInPage();
            }
            else
            {
                Log.d(TAG,"Game Circles client not ready");
            }
        }
        else
        {
            Toast.makeText(this.activity,"Loading game circle", Toast.LENGTH_SHORT).show();
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