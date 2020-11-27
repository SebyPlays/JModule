package com.github.sebyplays.jmodule.module.exceptions;

public class InvalidModuleDescriptionException extends RuntimeException{

    public InvalidModuleDescriptionException() throws InvalidModuleDescriptionException{
        super("Occupied or inexistent module.yml!");
    }


}
