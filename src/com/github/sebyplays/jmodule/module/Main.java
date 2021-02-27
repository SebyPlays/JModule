package com.github.sebyplays.jmodule.module;

import lombok.SneakyThrows;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {
        new ModuleLoader(true).loadModules();
    }

}
