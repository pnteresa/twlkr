package in.masukang.twlkr.fragment;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashSet;
import java.util.Set;

import in.masukang.twlkr.MainActivity;
import in.masukang.twlkr.R;
import in.masukang.twlkr.utils.Constants;

/**
 * Created by teresa on 12/25/2015.
 */
@EFragment(R.layout.fragment_add_following)
public class AddFollowingFragment extends Fragment {
    @ViewById
    EditText mETusername;

    Set<String> mSfollowing = new HashSet<>();

    @Click(R.id.mBfollow)
    void saveFollowing() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constants.FOLLOWING_DATA, 0);
        SharedPreferences.Editor ed = sp.edit();
        Log.d("et null",(mETusername==null)+"" );
        Log.d("et txt null",(mETusername.getText()==null)+"" );
        mSfollowing.add(mETusername.getText().toString());
        ed.putStringSet(Constants.FOLLOWING_ID_SET, mSfollowing);
        ed.commit();

        ((MainActivity) getActivity()).showHomeTweet(mSfollowing);
    }
}
