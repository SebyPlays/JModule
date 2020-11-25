package com.github.sebyplays.jmodule.module;

public class ModuleInfo implements ModuleDescription {

    private String moduleName = null;

    public ModuleInfo(String moduleName){
        this.moduleName = moduleName;
        return;
    }

    public ModuleInfo(){
        return;
    }

    @Override
    public String getModuleName() {
        return new ModuleDescriptor().moduleName;
    }

    @Override
    public String getModuleAuthor() {
        return null;
    }

    @Override
    public String getModuleVersion() {
        return null;
    }

    @Override
    public String getModuleMain() {
        return null;
    }
}
