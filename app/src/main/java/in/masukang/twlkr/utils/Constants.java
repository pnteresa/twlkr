package in.masukang.twlkr.utils;

/**
 * Created by teresa on 12/25/2015.
 */
public class Constants {
    public static final String TWITTER_KEY = "th0fY9dcNQ5bTLFzSxfOwu1OR";
    public static final String TWITTER_SECRET= "QfUl9ARKpHtQhpsKUExXOIyKnpeCs9waG5KKO1rWbTAuwRPXCX";

    public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
    public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";

    final public static String  CALLBACK_SCHEME = "x-latify-oauth-twitter";
    final public static String  CALLBACK_URL = CALLBACK_SCHEME + "://callback";
}
