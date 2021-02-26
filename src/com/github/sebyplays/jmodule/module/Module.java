package com.github.sebyplays.jmodule.module;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Module {

    @Getter private File path;
    @Getter private ModuleInfo moduleInfo;
    @Getter public ModuleDescriptor moduleDescriptor;
    @Getter private UUID moduleUUID = UUID.randomUUID();
    @Getter private ModuleLoader moduleLoader = new ModuleLoader();
    public Module(File file) throws IOException {
        this.path = file;
    }



}
