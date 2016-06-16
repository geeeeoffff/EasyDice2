package com.geoffrathbone.woddice.wodroller;

/**
 * Created by Geoff on 6/14/2016.
 */
public class Constants {
    public final static String API_KEY_PARAMETER = "apiKey";
    public final static String NUMBER_COUNT_PARAMETER = "n";
    public final static String MIN_NUMBER_VALUE_PARAMETER = "min";
    public final static String MAX_NUMBER_VALUE_PARAMETER = "max";
    // if true,  the resulting numbers may contain duplicate values
    public final static String REPLACEMENT_PARAMETER = "replacement";
    public final static String BASE_PARAMETER = "base";
    public final static String API_METHOD_GENERATE_INTEGERS = "generateIntegers";
    public final static String API_HOST_NAME = "https://api.random.org/json-rpc/1/invoke";
    // todo consider moving this key
    public final static String RANDOM_DOT_ORG_API_KEY = "2eca5636-3c02-477d-a457-cb5967426d80";

    // response object constants
    public final static String RESPONSE_ERROR = "error";
    public final static String RESULT_MAP_RANDOM_MAP_KEY = "random";
    public final static String RANDOM_MAP_DATA_ARRAY_KEY = "data";
}
