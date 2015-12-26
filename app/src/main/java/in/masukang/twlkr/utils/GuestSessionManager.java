package in.masukang.twlkr.utils;

import com.twitter.sdk.android.core.AppSession;

/**
 * Created by teresa on 12/26/2015.
 */
public class GuestSessionManager {
    public static AppSession session;

    public static AppSession getSession() {
        return session;
    }

    public static void setSession(AppSession session) {
        GuestSessionManager.session = session;
    }

}
