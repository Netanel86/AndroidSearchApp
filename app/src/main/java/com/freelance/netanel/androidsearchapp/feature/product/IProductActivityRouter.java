package com.freelance.netanel.androidsearchapp.feature.product;


import com.freelance.netanel.androidsearchapp.model.Product;

/**
 * <p></p>
 *
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 * Created on 06/03/2018
 */

public interface IProductActivityRouter {
    Product getProductFromIntent();

    void closeView();
}
