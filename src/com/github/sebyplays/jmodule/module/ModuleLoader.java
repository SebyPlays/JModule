package com.github.sebyplays.jmodule.module;

import com.github.sebyplays.jmodule.module.exceptions.InvalidModuleDescriptionException;
import de.github.sebyplays.logmanager.api.LogManager;
import de.github.sebyplays.logmanager.api.LogType;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class ModuleLoader {

    //creates "module" directory
    public ModuleLoader() throws IOException {
        this.createDependentDirectories();
    }

    //load all modules in "modules" directory
    public boolean loadModules() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        for (String moduleName : new File(System.getProperty("user.dir") + "/modules").list()) {
            loadModule(moduleName);
        }
        return false;
    }

    //load a specific module
    public boolean loadModule(String moduleName) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InvalidModuleDescriptionException, InstantiationException {
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " is being  initialized..", true, true);

        File file = new File(System.getProperty("user.dir") + "/modules/" + moduleName);
        JarFile jarFile = new JarFile(file);

        ModuleDescriptor moduleDescriptor = new ModuleDescriptor(jarFile.getInputStream(jarFile.getJarEntry("module.yml")));
        if (moduleDescriptor == null)
            throw new InvalidModuleDescriptionException();
        moduleDescriptor.getModuleDescription();

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
        Class jClass = Class.forName(moduleDescriptor.getModuleMain(), true, urlClassLoader);
        Method method = jClass.getDeclaredMethod("onLoad");
        Object objectClassInstance = jClass.newInstance();
        method.invoke(objectClassInstance);

        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " successfully initialized.", true, true);
        enableModule(file, moduleName, moduleDescriptor);
        return false;
    }

    public boolean enableModule(File file, String moduleName, ModuleDescriptor moduleDescriptor) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
        Class jClass = Class.forName(moduleDescriptor.getModuleMain(), true, urlClassLoader);
        Method method = jClass.getDeclaredMethod("onEnable");
        Object objectClassInstance = jClass.newInstance();
        method.invoke(objectClassInstance);
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " successfully enabled.", true, true);
        return false;
    }

    public boolean disableModules() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        for (String moduleName : new File(System.getProperty("user.dir") + "/modules").list()) {
            disableModule(moduleName);
        }
        return false;
    }

    public boolean disableModule(String moduleName) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Disabling module: " + moduleName.toUpperCase() + "..", true, true);
        File file = new File(System.getProperty("user.dir") + "/modules/" + moduleName);
        JarFile jarFile = new JarFile(file);

        ModuleDescriptor moduleDescriptor = new ModuleDescriptor(jarFile.getInputStream(jarFile.getJarEntry("module.yml")));
        if (moduleDescriptor == null)
            throw new InvalidModuleDescriptionException();
        moduleDescriptor.getModuleDescription();

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
        Class jClass = Class.forName(moduleDescriptor.getModuleMain(), true, urlClassLoader);
        Method method = jClass.getDeclaredMethod("onDisable");
        Object objectClassInstance = jClass.newInstance();
        method.invoke(objectClassInstance);
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Disabled module: " + moduleName.toUpperCase() + " successfully.", true, true);
        return false;
    }

    public void reloadModules() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Modules are being reloaded..", true, true);
        this.disableModules();
        loadModules();
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Modules reloaded successfully..", true, true);
        return;
    }

    private void createDependentDirectories() throws IOException {
        if (!(new File(System.getProperty("user.dir") + "/modules")).exists()) {
            new File(System.getProperty("user.dir") + "/modules").mkdirs();
            LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Dependent Module directory has been created..", true, true);
            return;
        }
        return;
    }
}
