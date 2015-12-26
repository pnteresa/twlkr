package in.masukang.twlkr.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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

    Set<String> mFollowingSet;
    ListAdapter mAdapter;

    @AfterViews
    void init() {
        mLVmain.setAdapter(mAdapter);
    }


    public void setAdapter(ListAdapter adapter) {
        this.mAdapter = adapter;
    }

    public Set<String> getmSfollowing() {
        return mFollowingSet;
    }

    public void setmSfollowing(Set<String> mSfollowing) {
        this.mFollowingSet = mSfollowing;
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }
}
