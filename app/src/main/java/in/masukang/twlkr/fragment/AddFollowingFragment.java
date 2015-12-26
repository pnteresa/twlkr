package in.masukang.twlkr.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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
    LinearLayout mLLrowContainer;

    Set<String> mSfollowing = new HashSet<>();

    @Click(R.id.mBadd)
    void add() {
        LayoutInflater layoutInflater =
                (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.row_item_add_following, null);
        final EditText textOut = (EditText)addView.findViewById(R.id.mETusername);
        Button buttonRemove = (Button)addView.findViewById(R.id.mBremove);
        buttonRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((LinearLayout) addView.getParent()).removeView(addView);
            }
        });
        mLLrowContainer.addView(addView, 0);
    }

    @Click(R.id.mBfollow)
    void saveFollowing() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constants.FOLLOWING_DATA, 0);
        SharedPreferences.Editor ed = sp.edit();

        //parse from mLLrowContainer here

        ed.putStringSet(Constants.FOLLOWING_ID_SET, mSfollowing);
        ed.commit();

        ((MainActivity) getActivity()).showHomeTweet(mSfollowing);
    }
}
