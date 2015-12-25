package in.masukang.twlkr.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import in.masukang.twlkr.R;
import in.masukang.twlkr.utils.Constants;

/**
 * Created by teresa on 12/25/2015.
 */
@EFragment(R.layout.fragment_home_tweet)
public class HomeTweetFragment extends Fragment {
    @ViewById
    ListView mLVmain;

    Set<String> mSfollowing;
    TweetTimelineListAdapter adapter;

    @AfterViews
    void init() {
        mLVmain.setAdapter(adapter);
    }

    public void setmSfollowing(Set<String> following, Context context) {
        this.mSfollowing = following;
        TweetTimelineListAdapter.Builder builder = new TweetTimelineListAdapter.Builder(context);

        for (String user : following) {
            Log.d("user ",user);
            final UserTimeline userTimeline = new UserTimeline.Builder()
                    .screenName(user)
                    .build();
            builder.setTimeline(userTimeline);
        }
        adapter = builder.build();
    }

    public void setAdapter(TweetTimelineListAdapter adapter) {
        this.adapter = adapter;
    }
}
