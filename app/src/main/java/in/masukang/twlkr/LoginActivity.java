package in.masukang.twlkr;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.masukang.twlkr.fragment.HomeTweetFragment;
import in.masukang.twlkr.fragment.HomeTweetFragment_;
import in.masukang.twlkr.utils.Constants;
import in.masukang.twlkr.utils.KeyConstants;
import io.fabric.sdk.android.Fabric;
import android.support.v4.app.FragmentTransaction;

import java.util.HashSet;
import java.util.Set;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    TwitterLoginButton mTLBlogin;

    @ViewById
    TextView mTVstatus;

    Context mContext;

    @AfterViews
    void init() {
        mContext = this;

        SharedPreferences sp1 = this.getSharedPreferences(Constants.FOLLOWING_DATA, MODE_WORLD_READABLE);
        if (sp1.getStringSet(Constants.FOLLOWING_ID_SET, null) != null) {
            Intent intent = new Intent(mContext,MainActivity_.class);
            intent.putExtra(Constants.IS_FROM_LOGIN,true);
            startActivity(intent);
        }

        mTLBlogin.setCallback(new LoginHandler());
        mTVstatus.setText("ready!");
    }

    @Click(R.id.mBcontinue)
    void gotoAddFollow() {
        Intent intent = new Intent(mContext,MainActivity_.class);
        intent.putExtra(Constants.IS_FROM_LOGIN,true);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(KeyConstants.TWITTER_KEY, KeyConstants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mTLBlogin.onActivityResult(requestCode, resultCode, data);
    }

    private class LoginHandler extends Callback<TwitterSession> {
        @Override
        public void success(Result<TwitterSession> twitterSessionResult) {
//            SessionRecorder.recordSessionActive("Login: twitter account active", twitterSessionResult.data);

            String output = "Status: " +
                    "Your login was successful, " +
                    twitterSessionResult.data.getUserName();

            mTVstatus.setText(output);
            Toast.makeText(mContext,output,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext,MainActivity_.class);
            intent.putExtra(Constants.IS_FROM_LOGIN,true);
            startActivity(intent);
        }

        @Override
        public void failure(TwitterException e) {
            mTVstatus.setText("failed");
            Toast.makeText(mContext,"failed",Toast.LENGTH_SHORT).show();
        }
    }

}
