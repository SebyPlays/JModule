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
        return new ModuleDescriptor().moduleAuthor;
    }

    @Override
    public String getModuleVersion() {
        return new ModuleDescriptor().moduleVersion;
    }

    @Override
    public String getModuleMain() {
        return new ModuleDescriptor().moduleMainPath;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
}
