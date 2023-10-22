package com.duofan.fly.validate.plugins;

import jakarta.validation.Valid;
import lombok.experimental.Delegate;

import java.util.ArrayList;
import java.util.List;

public class ValidationList<E> implements List<E> {

    @Delegate
    @Valid
    public List<E> list = new ArrayList<>();

    @Override
    public String toString() {
        return list.toString();
    }
}  
 