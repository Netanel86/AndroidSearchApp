package com.freelance.netanel.androidsearchapp;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import android.support.test.runner.AndroidJUnit4;

import com.freelance.netanel.androidsearchapp.domain.history_repo.TimeStampList;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Netanel on 03/12/2017.
 */

@RunWith(AndroidJUnit4.class)
public class TimeStampedSetTest {
    @Test
    public void add_WhenMultipleInputWithIdenticalName_ShouldReturnDistinct() throws Exception {
        TimeStampList<String> target = new TimeStampList<>();
        target.add("pants");
        target.add("shirt");
        target.add("hello");
        target.add("pants");


        assertThat(target.getObjectTList(),hasSize(3));
    }
}
