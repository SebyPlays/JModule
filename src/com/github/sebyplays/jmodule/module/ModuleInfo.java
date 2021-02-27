package com.github.sebyplays.jmodule.module;

import lombok.Getter;

public class ModuleInfo {

    @Getter public String moduleName;
    @Getter public String moduleVersion;
    @Getter public String moduleAuthor;
    @Getter public String moduleMainPath;
    @Getter public String moduleDescription;
    @Getter public String modulePriority;
    @Getter public Module module;

    public ModuleInfo(Module module){
        this.module = module;
        this.moduleName = module.getModuleDescriptor().moduleName;
        this.moduleVersion = module.getModuleDescriptor().moduleVersion;
        this.moduleAuthor = module.getModuleDescriptor().moduleAuthor;
        this.moduleMainPath = module.getModuleDescriptor().moduleMainPath;
        this.moduleDescription = module.getModuleDescriptor().moduleDescription;
        this.modulePriority = module.getModuleDescriptor().modulePriority;
        return;
    }

    public ModuleInfo(){
        return;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
}
