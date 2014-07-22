package com.kamikikai.timemeister;

import java.util.Calendar;

/**
 * Created by daisuke on 14/02/20.
 */
public interface TimerPreference {
    boolean checkPref(String key);
    void readPref(String key);
    void writePref(Calendar cal,String key,boolean flag);
}
