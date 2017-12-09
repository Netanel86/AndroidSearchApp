package com.freelance.netanel.androidsearchapp.domain.feature.history;

import java.util.Collection;
import java.util.List;

/**
 * <p>Supplies an interface for wrapping collections with new logic</p>
 * @param <TObject> the type of object to be handled by the collection, the object should implement
 * {@code Comparable} interface when class manages a sorted collection.
 * @see Comparable
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public interface ICollectionWrapper<TObject> {

    /***
     * Get's the wrapped collection.
     * @return the wrapped collection of {@code IWrappedItem} items.
     */
    List<? extends IWrappedItem> getWrappedList();

    /***
     * Get's the {@code <TObject>} list.
     * @return the list of type {@code <TObject>} items.
     */
    List<TObject> getObjectTList();

    /**
     * Adds a collection of pre-wrapped items to the collection.
     * @param collection a collection of object's extending {@code IWrappedItem} to be added.
     * @return true if the collection was successfully added, false otherwise.
     */
    boolean addAllWrappedItems(Collection<? extends IWrappedItem> collection);

    /**
     * Adds a collection of {@code <TObject>} type objects to the collection.
     * @param collection a collection of type {@code <TObject>} items to be added.
     * @return true if the collection was successfully added, false otherwise.
     */
    boolean addAllObjectTItems(Collection<TObject> collection);

    /**
     * Adds an object to the collection.
     * @param objectT the type {@code <TObject>} item to add.
     * @return true if the item was successfully added, false otherwise.
     */
    boolean add(TObject objectT);

    /**
     * Clears the collection.
     */
    void clear();

    /**
     * Supplies an interface for implementing a custom wrapper item class.
     * if the collection is sorted this class should also implement {@code Comparable} interface.
     */
    interface IWrappedItem {}
}
