package com.geoffrathbone.woddice.wodroller;

import android.util.Log;

import com.geoffrathbone.woddice.wodroller.numberSource.TrueRandomNumberSource;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RandomApiUnitTest {
    private final static String TAG = "AppTest";

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void basicRandomApiTest() throws Exception {
        // Constants for the test
        int numberOfIntegersToGenerate = 100;
        int minimumGeneratedValue = 1;
        int maximumGeneratedValue = 10;
        boolean generateNumbersWithReplacement = true;
        int integerBase = 10;

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put(Constants.API_KEY_PARAMETER, Constants.RANDOM_DOT_ORG_API_KEY);
        parameters.put(Constants.NUMBER_COUNT_PARAMETER, numberOfIntegersToGenerate);
        parameters.put(Constants.MIN_NUMBER_VALUE_PARAMETER, minimumGeneratedValue);
        parameters.put(Constants.MAX_NUMBER_VALUE_PARAMETER, maximumGeneratedValue);
        parameters.put(Constants.REPLACEMENT_PARAMETER, generateNumbersWithReplacement);
        parameters.put(Constants.BASE_PARAMETER, integerBase);

        JSONRPC2Request request = new JSONRPC2Request(Constants.API_METHOD_GENERATE_INTEGERS, parameters, "1234");
        URL serverUrl = null;
        try {
            serverUrl = new URL(Constants.API_HOST_NAME);
        } catch (MalformedURLException ex) {
            Log.wtf(TAG, "all is lost, failed to make url");
            assertFalse("failed to make URL", true);
        }

        JSONRPC2Session session = new JSONRPC2Session(serverUrl);

        JSONRPC2Response response = null;

        try {
            response = session.send(request);
        } catch (JSONRPC2SessionException ex) {
            assertFalse("failed to send request", true);
        }

        assertNotNull(response);
        HashMap<String, Object> result = (HashMap<String, Object>) response.getResult();
        assertNull(result.get(Constants.RESPONSE_ERROR));
        assertTrue(response.indicatesSuccess());
    }

    @Test
    public void testTrueRandomNumberSource() throws Exception{
        TrueRandomNumberSource source = new TrueRandomNumberSource();
        int testQuantity = 50;
        ArrayList<Integer> testResult = source.generateRandomNumbers(testQuantity);

        assertNotNull(testResult);
        assertEquals(testQuantity, testResult.size());
    }
}