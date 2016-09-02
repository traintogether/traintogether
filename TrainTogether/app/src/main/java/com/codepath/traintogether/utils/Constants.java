package com.codepath.traintogether.utils;

/**
 * Created by ameyapandilwar on 8/17/16
 */
public class Constants {

    public static final String INSTANCE_ID_TOKEN_RETRIEVED = "iid_token_retrieved";
    public static final String FRIENDLY_MSG_LENGTH = "friendly_msg_length";

    // ChatActivity
    public static final int ONE_HOUR = 3600;
    public static final String MESSAGES_CHILD = "messages";
    public static final String GROUPS_CHILD = "groups";
    public static final String USER_RUNS_CHILD = "runs";
    public static final String GROUP_STAT_CHILD = "group_statistics";

    public static final int REQUEST_INVITE = 1;


    //Stats request ids
    public static final int REQUEST_TYPE_SPEED = 1000;
    public static final int REQUEST_TYPE_LOCATION = 1001;
    public static final int REQUEST_AGGREGATE_DISTANCE_DELTA = 1002;

    //permission requests
    public static final int REQUEST_LOCATION = 2000;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";

    public static final String API_ENDPOINT_URL = "http://api.amp.active.com/v2/search/";

    public static final String DEFAULT_FONT_ASSET_PATH = "fonts/VesperLibre-Regular.ttf";

    public static final String REQUESTS_CHILD = "requests";
    public static final String USERS_CHILD = "users";

    //error codes
    public static final int RESULT_NO_USER = 3000;
}
