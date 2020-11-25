package com.github.sebyplays.jmodule.module.api;

import com.github.sebyplays.jmodule.module.ModuleLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JavaModule {

    public void onValidation(){

    }

    public void onActivation(){

    }

    public void onDeactivation(){


    }
    //test method
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        new ModuleLoader().loadModule();
    }

}
