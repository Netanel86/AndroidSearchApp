package com.freelance.netanel.androidsearchapp.domain.history_repo;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>Represent's collection wrapper class for wrapping objects in a
 * collection with an assigned time stamp. the rules enforced by the collection are:</p>
 * <ul>
 * <li>sorted in a descending order, ordering depends on an assigned time stamp for
 * each item where newest is first.</li>
 * <li>holds distinct items, where a new item will overwrite its existing copy,
 * distinctive comparison ignores time stamp value.</li>
 * </ul>
 * @param <T> the type of object to be paired with a time stamp. class type must implement
 * {@code Comparable} interface.
 * @see Comparable
 * @see ICollectionWrapper
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public class TimeStampList<T extends Comparable<T>> implements ICollectionWrapper<T> {
    private List<TimeStampItem> originalCollection;

    /***
     * Get's the wrapped collection.
     * Used for storing the data in memory.
     */
    @Override
    public List<? extends IWrappedItem> getWrappedList() {
        return originalCollection;
    }

    /***
     * Get's the wrapped collection.
     * Used for fetching values for UI usage.
     * @return a sorted list of type {@code <T>} items.
     */
    @Override
    public List<T> getObjectTList() {
        List<T> list = new ArrayList<>();
        sortCollection();

        for (TimeStampItem item: originalCollection) {
                list.add(item.objectT);
        }
        return list;
    }

    /***
     * Created a new instance of {@code TimeStampList} class.
     */
    public TimeStampList() {
        originalCollection = new ArrayList<>();
    }

    /**
     * Adds an object to the beginning of the collection.
     * If the object already exists, the old occurrence will be removed, and the new will be added.
     * @param objectT the type {@code <T>} item to add.
     * @return true if the item was successfully added, false otherwise.
     */
    @Override
    public boolean add(T objectT) {
        TimeStampItem newItem = new TimeStampItem(objectT);
        TimeStampItem identicalItem = getIfContained(newItem);
        if (identicalItem != null) {
            originalCollection.remove(identicalItem);
        }
        return originalCollection.add(newItem);
    }

    /**
     * Clears the collection.
     */
    @Override
    public void clear() {
        originalCollection.clear();
    }

    /**
     * Adds a collection of {@code <T>} type objects to the collection.
     * If an object already exists,  the old occurrence will be removed, and the new will be added.
     * @param collection a collection of type {@code <T>} items to be added.
     * @return true if the collection was successfully added, false otherwise.
     */
    @Override
    public boolean addAllObjectTItems(Collection<T> collection) {
        for (T item: collection) {
            this.add(item);
        }
        return originalCollection.size() >= collection.size();
    }

    /**
     * Adds a collection of post time stamped items to the underline collection.
     * @param collection a collection of type {@code TimeStampItem} items to be added.
     * @return true if the collection was successfully added, false otherwise.
     */
    @Override
    public boolean addAllWrappedItems(Collection<? extends IWrappedItem> collection) {
        originalCollection.addAll((Collection<TimeStampItem>)collection);
        return originalCollection.size() >= collection.size();
    }

    private TimeStampItem getIfContained(TimeStampItem newItem) {
        TimeStampItem target = null;
        for (TimeStampItem item: originalCollection) {
            if(item.compareTo(newItem) == 0) {
                target =  item;
                break;
            }
        }
        return target;
    }

    private void sortCollection() {
        Set<TimeStampItem> distinctSet = new HashSet<>();
        distinctSet.addAll(originalCollection);
        originalCollection.clear();
        originalCollection.addAll(distinctSet);
        Collections.sort(originalCollection);
        Collections.reverse(originalCollection);
    }

    /**
     * <p>Represent's an object paired with a time stamp of the time of creation.
     * Handling of this class is private, it has a private constructor and can not be initiated
     * outside of the {@code TimeStampList} scope.</p>
     */
    public class TimeStampItem implements IWrappedItem, Comparable<TimeStampItem> {
        private T objectT;
        private long timeStamp;

        private TimeStampItem(T objectT) {
            this.objectT = objectT;
            timeStamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        }

        @Override
        public int compareTo(@NonNull TimeStampItem that) {
            int result;
            if (this.objectT.compareTo(that.objectT) == 0) {
                result = 0;
            } else {
                result = this.timeStamp < that.timeStamp ? -1 : 1;
            }
            return result;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TimeStampItem that = (TimeStampItem) o;

            return objectT != null ? objectT.equals(that.objectT) : that.objectT == null;
        }

        @Override
        public int hashCode() {
            int result = objectT != null ? objectT.hashCode() : 0;
            return result;
        }
    }
}
