package plugins.ata;

import android.app.Fragment;
import android.os.Bundle;
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

public class GameCircles extends Fragment
{

    // Constants.
    public static final String TAG = "GameCircles_plugin";

    // Singleton instance.
    public static GameCircles instance;

    // Unity Context
    private String gameObjectName;

    //region Amazon Game circles variables
    private AmazonGamesClient agsClient;

    private AmazonGamesCallback callback = new AmazonGamesCallback()
    {
        @Override
        public void onServiceReady(AmazonGamesClient amazonGamesClient) {
            agsClient = amazonGamesClient;
        }

        @Override
        public void onServiceNotReady(AmazonGamesStatus amazonGamesStatus) {
            Toast.makeText(UnityPlayer.currentActivity, "Failed to Initialize", Toast.LENGTH_SHORT);
        }
    };

    //list of features your game uses (in this example, achievements and leaderboards)
    private EnumSet<AmazonGamesFeature> myGameFeatures = EnumSet.of(AmazonGamesFeature.Leaderboards);
    //endregion

    public static void start(String gameObjectName)
    {
        // Instantiate and add to Unity Player Activity.
        instance = new GameCircles();
        instance.gameObjectName = gameObjectName;
        UnityPlayer.currentActivity.getFragmentManager().beginTransaction().add(instance, GameCircles.TAG).commit();
    }

    //region Android Activity-Fragment Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AmazonGamesClient.initialize(UnityPlayer.currentActivity, callback, myGameFeatures);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(this.agsClient!=null)
        {
            agsClient.release();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        agsClient.shutdown();
    }

    //endregion


    //region Amazon Game Circles Functions

    public void ShowLeaderboardsOverlay()
    {
        Log.d("AmazonGameCircle", "Show Leaderboards overlay");
        if (this.agsClient!=null)
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
        if(this.agsClient!=null)
        {
            LeaderboardsClient lbClients = agsClient.getLeaderboardsClient();
            if(lbClients!=null)
            {
                lbClients.showLeaderboardOverlay(leaderboardId);
            }
        }
    }

    public void SubmitScoreLeaderboard(String leaderboardId, int score)
    {
        Log.d(TAG, "Start submit score");
        if (this.agsClient != null)
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
                        Toast.makeText(UnityPlayer.currentActivity, "Record updated", Toast.LENGTH_SHORT);
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
        if (this.agsClient!=null)
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
        if (this.agsClient!=null)
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
        if (this.agsClient!=null)
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
    }

    public boolean isSigned()
    {
        boolean isSigned = false;

        if (agsClient != null)
        {
                if (agsClient.getPlayerClient().isSignedIn())
                {
                    isSigned = true;
                }
        }
        //This should be do it but how can I return this to Unity?
        //I only know using a String in some project that I don't remember using this
        // That is the reason of the UnityObjName
        //TODO complete return value to Unity
        return isSigned;
    }

    //endregion

}
