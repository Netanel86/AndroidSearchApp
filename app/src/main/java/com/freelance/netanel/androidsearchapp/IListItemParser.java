package com.freelance.netanel.androidsearchapp;

import java.util.List;

public interface IListItemParser<T>
{
    List parse(T object);
}

