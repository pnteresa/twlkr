package in.masukang.twlkr;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import in.masukang.twlkr.utils.Constants;
import in.masukang.twlkr.utils.GuestSessionManager;
import in.masukang.twlkr.utils.KeyConstants;
import io.fabric.sdk.android.Fabric;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
    @ViewById
    ProgressBar mPBloading;
    @ViewById
    Button mBcontinue;

    Context mContext;

    @AfterViews
    void init() {
        mContext = this;

        TwitterCore.getInstance().logInGuest(new Callback<AppSession>() {
            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;
                GuestSessionManager.setSession(session);

                SharedPreferences sp = mContext.getSharedPreferences(Constants.FOLLOWING_DATA, MODE_WORLD_READABLE);
                mPBloading.setVisibility(View.GONE);
                if (sp.getStringSet(Constants.FOLLOWING_ID_SET, null) != null) {
                    Intent intent = new Intent(mContext, MainActivity_.class);
                    startActivity(intent);
                } else {
                    mBcontinue.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(TwitterException e) {
                mPBloading.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setMessage("Couldn't connect to Twitter")
                        .setCancelable(false).setTitle("Error")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
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

        //make your own KeyConstants.java consisting TWITTER_KEY and TWITTER_SECRET variable
        TwitterAuthConfig authConfig = new TwitterAuthConfig(KeyConstants.TWITTER_KEY, KeyConstants.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_login);
    }


}
