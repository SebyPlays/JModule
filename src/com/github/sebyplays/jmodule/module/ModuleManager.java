package com.github.sebyplays.jmodule.module;


public interface ModuleManager {

    void serializeModule();
    void preserializeModule();
    void postserializeModule();

    void deserializeModule();
    void predeserializeModule();
    void postdeserializeModule();


}
