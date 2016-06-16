package com.geoffrathbone.woddice.wodroller.numberSource;

import java.util.ArrayList;

/**
 * Created by Geoff on 6/14/2016.
 */
public interface RandomNumberSource {
    ArrayList<Integer> generateRandomNumbers(int count);
}
