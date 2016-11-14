package com.lissa.runner;

import com.lissa.configs.Configurer;
import com.lissa.utils.exceptions.EmptyConnectionException;
import com.lissa.utils.exceptions.InvalidDbTypeException;
import lombok.extern.java.Log;

@Log
public class Starter {

    public static void start() throws InvalidDbTypeException, EmptyConnectionException {
        Configurer.configure();
    }

}
