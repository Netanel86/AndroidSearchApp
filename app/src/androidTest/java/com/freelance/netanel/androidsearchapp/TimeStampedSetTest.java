package com.freelance.netanel.androidsearchapp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Netanel on 03/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TimeStampedSetTest {
    @Test
    public void add_WhenMultipleInputWithIdenticalName_ShouldReturnDistinct() throws Exception {

        TimeStampedSet set = new TimeStampedSet();
        set.add("shirt");
        set.add("pants");
        set.add("hello");
        set.add("xbox");
        set.add("hello");
        set.add("xbox");
        set.add("hello");
        set.add("zbox");
        set.add("shirt");
        set.add("mens pants");
        assertThat(set,hasSize(6));
    }
}
