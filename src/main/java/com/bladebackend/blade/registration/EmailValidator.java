package com.bladebackend.blade.registration;

import org.springframework.stereotype.Service;
import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String>{

    @Override
    public boolean test(String s) {
        //TODO VALIDATE EMAIL
        return true;
    }
}