package com.freelance.netanel.androidsearchapp.infra;

/**
 * <p>Supplies a base interface for implementing an MVP presenter</p>
 * @param <V> the type of view attached to the presenter.
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 07/02/2018
 */
public interface IMvpPresenter<V> {

    /**
     * Binds the view to the presenter.
     * @param view the view to bind.
     */
    void bindView(V view);

    /**
     * Gets the bounded view
     * @return the view bounded to this presenter
     */
    V getView();
}
