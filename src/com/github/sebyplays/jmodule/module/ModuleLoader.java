package com.github.sebyplays.jmodule.module;

import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ModuleLoader extends ModuleInvoker{

    public HashMap<UUID, Module> modules = new HashMap<>();
    public HashMap<UUID, Thread> threads = new HashMap<>();
    private boolean multiThreading;

    public ModuleLoader() throws IOException {
        createDependentDirectories();
    }

    public ModuleLoader(boolean multiThreading) throws IOException {
        createDependentDirectories();
        this.multiThreading = multiThreading;
    }

    @SneakyThrows
    public void loadModules() {
        for (String moduleName : new File(System.getProperty("user.dir") + "/modules").list()) {
            if(multiThreading){
                Module module = new Module(new File(moduleName));
                this.threads.put(module.getModuleUUID(), new Thread(){
                    @SneakyThrows
                    @Override
                    public void run() {
                        loadModule(module);
                    }
                });
                return;
            }
            loadModule(new Module(new File(moduleName)));
        }
        return;
    }

    @SneakyThrows
    public void loadModule(Module moduleName){
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.getModuleInfo().getModuleName().toUpperCase() + " is being  initialized..", false, false, true, true);
        invokeMethod(moduleName, "onLoad");
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.getModuleInfo().getModuleName().toUpperCase() + " successfully initialized.", false, false, true, true);
        enableModule(moduleName);
        return;
    }

    @SneakyThrows
    public void enableModule(Module moduleName){
        invokeMethod(moduleName, "onEnable");
        this.modules.put(moduleName.getModuleUUID(), moduleName);
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.getModuleInfo().getModuleName().toUpperCase() + "::" + this.modules.get(moduleName) + " successfully enabled.", false, false, true, true);
    }

    public void disableModules() {
        for (Module moduleName : this.modules.values()) {
            disableModule(moduleName);
        }
    }

    @SneakyThrows
    public void disableModule(Module module){
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Killing process: " + module.getModuleInfo().getModuleName().toUpperCase() + "::" + this.modules.get(module) + "..", false, false, true, true);
        invokeMethod(module, "onDisable");
        this.modules.remove(module);
        if(multiThreading){
            this.threads.get(module.getModuleUUID()).stop();
            this.threads.remove(module.getModuleUUID());
        }
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Disabled module: " + module.getModuleInfo().getModuleName().toUpperCase() + " successfully.", false, false, true, true);
    }

    @SneakyThrows
    public void reloadModules(){
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Modules are being reloaded..", false, false, true, true);
        this.disableModules();
        loadModules();
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Modules reloaded successfully..", false, false, true, true);
        return;
    }
}