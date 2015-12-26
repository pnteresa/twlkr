package in.masukang.twlkr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.masukang.twlkr.utils.Constants;
import in.masukang.twlkr.utils.KeyConstants;
import in.masukang.twlkr.utils.GuestSessionManager;
import io.fabric.sdk.android.Fabric;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
    Context mContext;

    @AfterViews
    void init() {
        mContext = this;

        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                GuestSessionManager.setSession(session);

                SharedPreferences sp1 = mContext.getSharedPreferences(Constants.FOLLOWING_DATA, MODE_WORLD_READABLE);
                if (sp1.getStringSet(Constants.FOLLOWING_ID_SET, null) != null) {
                    Intent intent = new Intent(mContext, MainActivity_.class);
                    startActivity(intent);
                }
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(mContext, "Could not get guest Twitter session", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    @Click(R.id.mBcontinue)
    void gotoAddFollow() {
        Intent intent = new Intent(mContext, MainActivity_.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(KeyConstants.TWITTER_KEY, KeyConstants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_login);
    }


}
