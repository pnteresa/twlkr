package in.masukang.twlkr;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.tweetui.CollectionTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.HashSet;
import java.util.Set;

import in.masukang.twlkr.fragment.AddFollowingFragment;
import in.masukang.twlkr.fragment.AddFollowingFragment_;
import in.masukang.twlkr.fragment.HomeTweetFragment;
import in.masukang.twlkr.fragment.HomeTweetFragment_;
import in.masukang.twlkr.utils.Constants;

/**
 * Created by teresa on 12/25/2015.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity{
    @ViewById
    AdView mAVad;
    @ViewById
    FrameLayout mFLframe;

    @AfterViews
    void init() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAVad.loadAd(adRequest);

        SharedPreferences sp1 = this.getSharedPreferences(Constants.FOLLOWING_DATA, MODE_WORLD_READABLE);

        Set<String> followingSet = sp1.getStringSet(Constants.FOLLOWING_ID_SET, null);
        if (followingSet == null) {
            Log.d("sp1 ", "null");
            AddFollowingFragment fragment = new AddFollowingFragment_();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.mFLframe,fragment);
            ft.commit();
        }
        else {
            Log.d("sp1 ", "not null");
            showHomeTweet(followingSet);
        }
    }

    public void showHomeTweet(Set<String> followingSet) {
        HomeTweetFragment fragment = new HomeTweetFragment_();

        TweetTimelineListAdapter.Builder builder = new TweetTimelineListAdapter.Builder(this);
        for (String user : followingSet) {
            Log.d("user ",user);
            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(user)
                    .build();
            builder.setTimeline(userTimeline);
        }
        TweetTimelineListAdapter adapter = builder.build();
        fragment.setAdapter(adapter);

        fragment.setmSfollowing(followingSet, this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.mFLframe,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }
}
