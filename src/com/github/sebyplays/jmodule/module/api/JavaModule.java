package com.github.sebyplays.jmodule.module.api;

import com.github.sebyplays.jmodule.module.ModuleBase;
import com.github.sebyplays.jmodule.module.ModuleLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class JavaModule extends ModuleBase {

    public JavaModule() throws IOException {
    }

    public void onValidation(){

    }

    public void onActivation(){
        System.out.println("ur pe is small");
    }

    public void onDeactivation(){


    }
    //test method
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        new ModuleLoader().loadModule();
    }

}
