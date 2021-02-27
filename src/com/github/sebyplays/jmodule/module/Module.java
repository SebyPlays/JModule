package com.github.sebyplays.jmodule.module;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Module {

    @Getter private File path;
    @Getter public ModuleDescriptor moduleDescriptor;
    @Getter private ModuleInfo moduleInfo;
    @Getter private UUID moduleUUID = UUID.randomUUID();
    @Getter private ModuleLoader moduleLoader = new ModuleLoader();
    public Module(File file) throws IOException {
        System.out.println(file.getPath());
        this.path = file;
        this.moduleDescriptor = new ModuleInvoker().getModuleYML(this);
        this.moduleInfo = new ModuleInfo(this);
    }



}
