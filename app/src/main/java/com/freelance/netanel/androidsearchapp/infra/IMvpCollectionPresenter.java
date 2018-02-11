package com.freelance.netanel.androidsearchapp.infra;

import java.util.List;

/**
 * <p>Supplies an interface for implementing an MVP presenter which handles a collection of data
 * presenters items, where each item has a corresponding presenter.</p>
 * <p>Remarks: each of the item's presenters should implement IMvpPresenter</p>
 * <p>Created on 07/02/2018</p>
 * @param <V> The type of view which displays the collection (eg. RecyclerView.Adapter), preferably
 *           an interface.
 * @param <M> The type of model representing the data handled by the presenter.
 * @param <P> The type of presenter assigned to each of the collection view items, preferably
 *           an interface.
 * @see IMvpPresenter
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
public interface IMvpCollectionPresenter<V, M, P extends IMvpPresenter> extends IMvpPresenter<V> {

    /**
     * Gets the number of items in the collection.
     * @return The number of items in the collection.
     */
    int getItemCount();

    /**
     * Gets a indicator if the collection is empty.
     * @return True if the collection is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Gets the item model on the given position in the collection.
     * @param position The position of the item.
     * @return The item model instance on the given position.
     */
    M getItem(int position);

    /**
     * Gets the collection of item models handled by the presenter.
     * @return A collection of item models.
     */
    List<M> getItems();

    /**
     * Gets the presenter of the view item on the given position in the collection.
     * @param position The position of the item.
     * @return The presenter of the requested item.
     */
    P getItemPresenter(int position);

    /**
     * Gets an integer representing the type of view to create for an item on a given position
     * in the collection.
     * @param position The position of the item in the collection
     * @return An integer representing the type of view to create for a specified item.
     */
    int getItemViewType(int position);
}
