package com.freelance.netanel.androidsearchapp.model;

import java.net.URL;

/**
 * Created by Netanel on 24/09/2017.
 */

public class Product {
    public int id;
    public String name;
    public String imageUrl;
    public String description;

    public Product()
    {

    }
    public Product(int id,String name,String description,String imageUrl)
    {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
