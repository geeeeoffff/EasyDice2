package com.geoffrathbone.woddice.wodroller.numberSource;

import android.util.Log;

import com.geoffrathbone.woddice.wodroller.Constants;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Geoff on 6/15/2016.
 */
public class TrueRandomNumberSource implements RandomNumberSource {

    private final static String TAG = "failed to generate url from API_HOST_NAME String";
    // the dice in this game are all 10 sided
    private static int MIN_NUMBER = 1;
    private static int MAX_NUMBER = 10;
    // indicates to the api that it is okay to generate duplicate numbers
    private static boolean GENERATE_WITH_REPLACEMENT = true;
    // we use a base 10 number system here.
    private static int NUMBER_SYSTEM_BASE = 10;

    private JSONRPC2Request request;
    private JSONRPC2Session jsonrpc2Session;

    public TrueRandomNumberSource() {
        URL serverUrl = null;
        try {
            serverUrl = new URL(Constants.API_HOST_NAME);
        } catch (MalformedURLException ex) {
            Log.wtf(TAG, "all is lost, failed to make url");
        }

        this.jsonrpc2Session = new JSONRPC2Session(serverUrl);
    }

    private JSONRPC2Request generateRequest(int quantityToGenerate) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.API_KEY_PARAMETER, Constants.RANDOM_DOT_ORG_API_KEY);
        parameters.put(Constants.NUMBER_COUNT_PARAMETER, quantityToGenerate);
        parameters.put(Constants.MIN_NUMBER_VALUE_PARAMETER, MIN_NUMBER);
        parameters.put(Constants.MAX_NUMBER_VALUE_PARAMETER, MAX_NUMBER);
        parameters.put(Constants.REPLACEMENT_PARAMETER, GENERATE_WITH_REPLACEMENT);
        parameters.put(Constants.BASE_PARAMETER, NUMBER_SYSTEM_BASE);
        return new JSONRPC2Request(Constants.API_METHOD_GENERATE_INTEGERS, parameters, "1234");
    }

    private JSONRPC2Response submitRequest(JSONRPC2Request request) {
        JSONRPC2Response response = null;

        try {
            response = this.jsonrpc2Session.send(request);
        } catch (JSONRPC2SessionException ex) {
            Log.wtf(TAG, "Failed to send request to random number service");
        }

        return response;
    }

    @Override
    public ArrayList<Integer> generateRandomNumbers(int count) {
        JSONRPC2Request request = generateRequest(count);
        JSONRPC2Response response = submitRequest(request);

        if (response == null) {
            Log.wtf(TAG, "response received from server was null");

            return null;
        }

        if (!response.indicatesSuccess()) {
            Log.wtf(TAG, "failed to generate the true random numbers");
            logErrorFromServer(response.getError());

            return null;
        }

        HashMap<String, Object> resultMap = (HashMap<String, Object>) response.getResult();
        HashMap<String, Object> randomDataMap = (HashMap<String, Object>) resultMap.get(Constants.RESULT_MAP_RANDOM_MAP_KEY);
        ArrayList<Integer> randomNumbers = (ArrayList<Integer>) randomDataMap.get(Constants.RANDOM_MAP_DATA_ARRAY_KEY);

        return randomNumbers;
    }

    private void logErrorFromServer(JSONRPC2Error jsonrpc2Error) {
        if (jsonrpc2Error == null) {
            Log.wtf(TAG, "an Error was encountered, but the JSONRPC2Error obj was null");

            return;
        }

        Log.wtf(TAG, "error cause: " + jsonrpc2Error.getCause());
        Log.wtf(TAG, "error message: " + jsonrpc2Error.getMessage());
        jsonrpc2Error.printStackTrace();
    }
}
