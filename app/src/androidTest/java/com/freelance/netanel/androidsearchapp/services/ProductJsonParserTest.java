package com.freelance.netanel.androidsearchapp.services;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.netanel.androidsearchapp.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Tests the {@link JSONParser} class,
 * it uses the {@link Product} class to test the mapping of the parser.
 * The tested scenarios are:
 * <ul>
 * <li> When json input is a {@link com.google.gson.JsonArray} with an array of products.
 * <li> When json input is a {@link com.google.gson.JsonObject} with a nested array of products.
 * <li> When json input is a JsonObject with a nested array but key is <code>null</code>.
 * <li> When json input is a JsonObject containing a product member.
 * <li> When json input is a JsonObject mapped to a single product.
 * <li> When json input is <code>null</code>
 * </ul>
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
@RunWith(AndroidJUnit4.class)
public class ProductJsonParserTest {

    // TODO: 16/11/2017 implement all tests
    private IJSONParser target;

    @Before
    public void setup(){
        target = new JSONParser();
    }

    @Test
    public void parse_WhenJsonIsArrayWithProducts_ShouldReturnListOfProducts() throws Exception {
        final String jsonInput =
                "[{'id': 1, 'name':'product name', 'description':'product description', 'imageUrl':'http://product.image.url'},{'id': 2, 'name':'product name', 'description':'product description', 'imageUrl':'http://product.image.url'}]";
        Type listType = new TypeToken<List<Product>>() {
        }.getType();

        List<Product> result = target.fromJson(jsonInput, listType);

        assertThat(result, hasSize(2));
        assertThat(result, hasItem(new Product(1, "product name", "product description",
                "http://product.image.url")));
    }

    @Test
    public void parse_WhenJsonIsObjectWithNestedArrayProducts_ShouldReturnListOfProducts() throws Exception{
        final String jsonInput =
                "{'products':[{'id': 1, 'name':'product name', 'description':'product description', 'imageUrl':'http://product.image.url'},{'id': 2, 'name':'product name', 'description':'product description', 'imageUrl':'http://product.image.url'}]}";
        Type listType = new TypeToken<List<Product>>() {
        }.getType();

        List<Product> result = target.fromJson(jsonInput, listType, "products");

        assertThat(result, hasSize(2));
        assertThat(result, hasItem(new Product(1, "product name", "product description",
                "http://product.image.url")));
    }

    @Test
    public void parse_WhenJsonIsObjectWithNestedArrayAndKeyIsNull_ShouldThrowException() throws Exception {

    }

    @Test
    public void parse_WhenJsonIsObjectWithMemberProduct_ShouldReturnProduct() throws Exception {

    }

    @Test
    public void parse_WhenJsonObjectIsProduct_ShouldReturnProduct() throws Exception {
        final String jsonInput =
                "{'id': 1, 'name':'product name', 'description':'product description', 'imageUrl':'http://product.image.url'}";
        Product validProduct = new Product(1, "product name", "product description",
                "http://product.image.url");

        Product result = target.fromJson(jsonInput, Product.class);

        assertThat(result, is(validProduct));
    }

    @Test
    public void parse_WhenJsonIsNull_ShouldThrowException() throws Exception {

    }
}
