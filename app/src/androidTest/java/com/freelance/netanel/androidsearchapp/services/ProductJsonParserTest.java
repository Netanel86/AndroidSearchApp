package com.freelance.netanel.androidsearchapp.services;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.netanel.androidsearchapp.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>Tests the {@link JsonParser} class,
 * it uses the {@link Product} class to test the mapping of the parser.
 * The tested scenarios are:</p>
 * <ul>
 * <li> When json input is a JsonArray with an array of products.
 * <li> When json input is a JsonObject with a nested array of products.
 * <li> When json input is a JsonObject containing a product member.
 * <li> When json input is a JsonObject mapped to a single product.
 * <li> When json input is a JsonObject with a nested array and member name is {@code null}.
 * <li> When json input is a JsonObject with a nested array and member name is empty.
 * <li> When json input is a JsonObject and requested member name is not found.
 * <li> When json input is empty.
 * <li> When json input is {@code null}.
 * <li> When requested type is a collection with a member name that matches the json input,
 * and the member is not a collection.</li>
 * <li> When requested type is an object with a member name that matches the json input,
 * and the member is not an object.</li>
 * </ul>
 * @author Netanel Iting
 * @version %I%, %G%
 * @since 1.0
 */
@RunWith(AndroidJUnit4.class)
public class ProductJsonParserTest {
    @Rule
    public final ExpectedException mException = ExpectedException.none();

    private IJsonParser mTarget;

    @Before
    public void setup() {
        mTarget = new JsonParser();
    }

    @Test
    public void parse_WhenJsonIsArrayWithProducts_ShouldReturnListOfProducts() throws Exception {
        final String jsonInput =
                "["
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'},"
                        + "{'id':2,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "]";
        Type listType = createProductListType();

        List<Product> result = mTarget.fromJson(jsonInput, listType);

        assertThat(result, hasSize(2));
        assertThat(result, hasItem(new Product(1, "product name", "product description",
                "http://product.image.url")));
    }

    @Test
    public void parse_WhenJsonIsObjectWithNestedArrayProducts_ShouldReturnListOfProducts()
            throws Exception {
        final String jsonInput =
                "{'products':["
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'},"
                        + "{'id':2,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "]}";
        Type listType = createProductListType();
        Product validProduct = new Product(1, "product name", "product description",
                "http://product.image.url");

        List<Product> result = mTarget.fromJson(jsonInput, listType, "products");

        assertThat(result, hasSize(2));
        assertThat(result, hasItem(validProduct));
    }

    @Test
    public void parse_WhenJsonIsObjectWithMemberProduct_ShouldReturnProduct() throws Exception {
        final String jsonInput =
                "{'product':"
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "}";
        Product validProduct = new Product(1, "product name", "product description",
                "http://product.image.url");

        Product result = mTarget.fromJson(jsonInput, Product.class, "product");

        assertThat(result, is(validProduct));
    }

    @Test
    public void parse_WhenJsonObjectIsProduct_ShouldReturnProduct() throws Exception {
        final String jsonInput =
                "{"
                        + "'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}";
        Product validProduct = new Product(1, "product name", "product description",
                "http://product.image.url");

        Product result = mTarget.fromJson(jsonInput, Product.class);

        assertThat(result, is(validProduct));
    }

    @Test
    public void parse_WhenJsonIsObjectWithNestedArrayAndMemberNameIsNull_ShouldThrowException()
            throws Exception {
        final String jsonInput =
                "{'products':["
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'},"
                        + "{'id':2,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "]}";
        Type listType = createProductListType();
        mException.expect(IllegalArgumentException.class);

        List<Product> result = mTarget.fromJson(jsonInput, listType, null);
    }

    @Test
    public void parse_WhenJsonIsObjectWithNestedArrayAndMemberNameEmpty_ShouldThrowException() throws Exception {
        final String jsonInput =
                "{'products':["
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'},"
                        + "{'id':2,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "]}";
        Type listType = createProductListType();
        mException.expect(IllegalArgumentException.class);

        List<Product> result = mTarget.fromJson(jsonInput, listType);
    }

    @Test
    public void parse_WhenJsonIsObjectAndMemberIsNotFound_ShouldReturnNull() throws Exception {
        final String jsonInput =
                "{'products':["
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'},"
                        + "{'id':2,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "]}";
        Type listType = createProductListType();

        List<Product> listResult = mTarget.fromJson(jsonInput, listType, "foo");
        Product objectResult = mTarget.fromJson(jsonInput,Product.class,"foo");

        assertThat(listResult,nullValue());
        assertThat(objectResult,nullValue());
    }

    @Test
    public void parse_WhenJsonIsEmpty_ShouldReturnNull() throws Exception {
        String jsonInput = "{}";
        Type listType = createProductListType();

        List<Product> resultList = mTarget.fromJson(jsonInput, listType);
        Product resultSingle = mTarget.fromJson(jsonInput, Product.class);

        assertThat(resultList, is(nullValue()));
        assertThat(resultSingle, is(nullValue()));
    }

    @Test
    public void parse_WhenJsonIsNull_ShouldThrowException() throws Exception {
        mException.expect(IllegalArgumentException.class);

        Product result = mTarget.fromJson((String) null, Product.class);
    }

    @Test
    public void parse_WhenJsonWithMemberNameDoNotMatchProductListType_ShouldThrowException() throws Exception {
        final String jsonInput =
                "{'product':"
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'"
                        + "}}";
        Type listType = createProductListType();
        mException.expect(ClassCastException.class);

        List<Product> listResult = mTarget.fromJson(jsonInput, listType, "product");

        assertThat(listResult,nullValue());
    }

    @Test
    public void parse_WhenJsonWithMemberNameDoNotMatchProductType_ShouldThrowException() throws Exception {
        final String jsonInput =
                "{'products':["
                        + "{'id':1,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'},"
                        + "{'id':2,"
                        + "'name':'product name',"
                        + "'description':'product description',"
                        + "'imageUrl':'http://product.image.url'}"
                        + "]}";
        mException.expect(ClassCastException.class);

        Product result = mTarget.fromJson(jsonInput,Product.class,"products");

        assertThat(result,nullValue());
    }


    private Type createProductListType() {
        return new TypeToken<List<Product>>() {
        }.getType();
    }
}
