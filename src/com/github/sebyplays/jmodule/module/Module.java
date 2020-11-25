package com.github.sebyplays.jmodule.module;

public interface Module {

    void onSerialization();
    void onInitialization();
    void onDeserialization();
    ModuleDescription moduleDescriptor();
    Module module();


}
