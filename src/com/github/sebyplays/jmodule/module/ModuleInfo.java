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
        return ModuleDescriptor.moduleAuthor;
    }

    @Override
    public String getModuleVersion() {
        return ModuleDescriptor.moduleVersion;
    }

    @Override
    public String getModuleMain() {
        return ModuleDescriptor.moduleMainPath;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
}
