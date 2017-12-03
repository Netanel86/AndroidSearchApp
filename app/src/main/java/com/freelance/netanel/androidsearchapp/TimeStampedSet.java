package com.freelance.netanel.androidsearchapp;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * <p>Represent's a String set where each string is assigned with a time stamp in milliseconds.
 * Set is ordered from newest value to oldest (LIFO) depending on the time stamp.
 * Use {@code getNameOnlyList()} method to fetch the values for UI usage.</p>
 * @see SortedSet
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class TimeStampedSet implements SortedSet<String> {
    private static final boolean STAMPED = true;
    private static final String SEPARATOR = ":";

    private TreeSet<String> set;

    public TimeStampedSet(){
        set = new TreeSet<>( new Comparator<String>() {
            @Override
            public int compare(String stringA, String stringB) {
                int result;
                String[] splitA = stringA.split(SEPARATOR);
                String[] splitB = stringB.split(SEPARATOR);
                if(splitA[1].compareTo(splitB[1]) == 0) {
                    result = 0;
                } else {
                    long valA = Long.parseLong(splitA[0]);
                    long valB = Long.parseLong(splitB[0]);
                    result = valA < valB ? 1 : -1;
                }
                return result;
            }
        });
    }

    public TimeStampedSet(Collection<String> items) {
        this();
        set.addAll(items);
    }

    public TreeSet<String> getInnerSet() {
        return set;
    }

    public ArrayList<String> getNameOnlyList() {
        return new ArrayList<>(getList(!STAMPED));
    }

    private boolean addDisticntAndNew(String stamped){
        String source = getIfContained(stamped);
        if (source != null){
            set.remove(source);
        }
        return set.add(stamped);
    }

    private String getIfContained(String sourceStamped) {
        Iterator iterator = set.iterator();
        if(sourceStamped == null) {
            return null;
        } else {
            while(iterator.hasNext()) {
                String targetStamped = (String) iterator.next();
                String targetName = getName(targetStamped);
                String sourceName = getName(sourceStamped);

                if(sourceName.equals(targetName)) {
                    return targetStamped;
                }
            }
        }
        return null;
    }

    private String addTimeStamp(String str) {
        return Long.toString(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))
                + SEPARATOR + str;
    }

    private String getName(String source) {
        return source.split(SEPARATOR)[1];
    }

    private List<String> getList(boolean getStampedList ) {
        List<String> list = new ArrayList<>();
        for (String stamped: set) {
            if (getStampedList) {
                list.add(stamped);
            } else {
                list.add(stamped.split(SEPARATOR)[1]);
            }
        }
        return list;
    }

    @Override
    public boolean add(String s) {
        return addDisticntAndNew(addTimeStamp(s));
    }

    @Override
    public boolean contains(Object o) {
        String sourceStamped = (String)o;
        return getIfContained(sourceStamped) != null;
    }

    ///region Untouched Methods
    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @NonNull
    @Override
    public Iterator<String> iterator() {
        return set.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return set.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return set.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends String> c) {

        for (String s : c) {
            addDisticntAndNew(addTimeStamp(s));
        }
        return set.size() >= c.size();
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return set.retainAll(c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return set.removeAll(c);
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public Comparator<? super String> comparator() {
        return set.comparator();
    }

    @NonNull
    @Override
    public SortedSet<String> subSet(String fromElement, String toElement) {
        return set.subSet(fromElement, toElement);
    }

    @NonNull
    @Override
    public SortedSet<String> headSet(String toElement) {
        return set.headSet(toElement);
    }

    @NonNull
    @Override
    public SortedSet<String> tailSet(String fromElement) {
        return set.tailSet(fromElement);
    }

    @Override
    public String first() {
        return set.first();
    }

    @Override
    public String last() {
        return set.last();
    }
    ///endregion
}
