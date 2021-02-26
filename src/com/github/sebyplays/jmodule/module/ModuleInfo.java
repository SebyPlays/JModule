package com.github.sebyplays.jmodule.module;

import lombok.Getter;

public class ModuleInfo {

    @Getter public String moduleName;
    @Getter public String moduleVersion;
    @Getter public String moduleAuthor;
    @Getter public String moduleMainPath;
    @Getter public String moduleDescription;
    @Getter public String modulePriority;

    public ModuleInfo(String moduleName){
        this.moduleName = moduleName;
        return;
    }

    public ModuleInfo(){
        return;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }
}
