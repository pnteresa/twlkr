package in.masukang.twlkr;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import in.masukang.twlkr.fragment.AddFollowingFragment;
import in.masukang.twlkr.fragment.AddFollowingFragment_;
import in.masukang.twlkr.fragment.HomeTweetFragment;
import in.masukang.twlkr.fragment.HomeTweetFragment_;
import in.masukang.twlkr.utils.Constants;
import in.masukang.twlkr.utils.GuestSessionManager;
import in.masukang.twlkr.utils.Utils;

/**
 * Created by teresa on 12/25/2015.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @ViewById
    AdView mAVad;
    @ViewById
    FrameLayout mFLframe;
    @ViewById
    ProgressBar mPBloading;

    int i;
    List<Tweet> tweets = new ArrayList<>();
    Set<String> followingSet = new HashSet<>();

    @AfterViews
    void init() {
        mPBloading.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAVad.loadAd(adRequest);

        SharedPreferences sp = this.getSharedPreferences(Constants.FOLLOWING_DATA, MODE_WORLD_READABLE);

        Set<String> followingSet = sp.getStringSet(Constants.FOLLOWING_ID_SET, null);
        if (followingSet == null) {
            Log.d("MainActivity", "line:62 null");
            AddFollowingFragment fragment = new AddFollowingFragment_();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.mFLframe, fragment);
            ft.commit();
        } else {
            Log.d("MainActivity", "line:68 not null");
            showHomeTweet(followingSet);
        }
        mPBloading.setVisibility(View.GONE);
    }

    /**
     * Fetch all username in {@code followingSet} 's tweets to {@link #tweets}
      * @param followingSet set of username
     */
    public void showHomeTweet(Set<String> followingSet) {
        mPBloading.setVisibility(View.VISIBLE);
        final int len = followingSet.size();
        i = 0;
        AppSession session = GuestSessionManager.session;

        for (String user : followingSet) {
            TwitterCore.getInstance().getApiClient(session).getStatusesService()
                    .userTimeline(null, user, Constants.MAX_TWEET_FETCH_PER_USER, null, null, null, null, null, null,
                            new Callback<List<Tweet>>() {
                                @Override
                                public void success(Result<List<Tweet>> result) {
                                    for (Tweet t : result.data) {
                                        tweets.add(t);
                                    }
                                    checkFinish(++i, len);
                                }

                                @Override
                                public void failure(TwitterException exception) {
                                    android.util.Log.d("MainActivity", "line 92: exception " + exception);
                                }
                            });
        }
    }

    /**
     * check if all tweets are fetched
     * @param i curent tweets size
     * @param size expected size
     */
    void checkFinish(int i, int size) {
        if (i == size) {
            if (tweets.size() > Constants.MAX_TWEET_FETCH_PER_USER)
                tweets.subList(0,Constants.MAX_TWEET_FETCH_PER_USER);
            Collections.sort(tweets, new TweetByTimeComparator());
            showTweet();
        }
    }

    /**
     * set adapter from tweets and show HomeTweetFragment
     */
    void showTweet() {
        HomeTweetFragment fragment = new HomeTweetFragment_();
        TweetViewAdapter adapter = new TweetViewAdapter(this, tweets);
        fragment.setAdapter(adapter);
        fragment.setmSfollowing(followingSet);
        mPBloading.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mFLframe, fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * compare tweets by date
     */
    class TweetByTimeComparator implements Comparator<Tweet> {
        @Override
        public int compare(Tweet t1, Tweet t2) {
            Date d1 = Utils.convertTsToDate(t1.createdAt);
            Date d2 = Utils.convertTsToDate(t2.createdAt);
            return d2.compareTo(d1);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
