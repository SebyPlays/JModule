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
import java.util.HashMap;
import java.util.jar.JarFile;

public class ModuleLoader {

    private final HashMap<String, Integer> modulePID = new HashMap<>();
    private int nextPID = 0;

    //creates "module" directory
    public ModuleLoader() throws IOException {
        this.createDependentDirectories();
    }

    //load all modules in "modules" directory
    public void loadModules() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        for (String moduleName : new File(System.getProperty("user.dir") + "/modules").list()) {
            loadModule(moduleName);
        }
        return;
    }

    //load a specific module
    public void loadModule(String moduleName) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException, InvalidModuleDescriptionException, InstantiationException {
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " is being  initialized..", true, true);

        File file = new File(System.getProperty("user.dir") + "/modules/" + moduleName);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
        Class jClass = Class.forName(this.getModuleYML(moduleName).getModuleMain(), true, urlClassLoader);
        Method method = jClass.getDeclaredMethod("onLoad");
        Object objectClassInstance = jClass.newInstance();
        method.invoke(objectClassInstance);
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " successfully initialized.", true, true);
        enableModule(file, moduleName, this.getModuleYML(moduleName));
        return;
    }

    public void enableModule(File file, String moduleName, ModuleDescriptor moduleDescriptor) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
        Class jClass = Class.forName(moduleDescriptor.getModuleMain(), true, urlClassLoader);
        jClass.getClassLoader().loadClass(moduleDescriptor.getModuleMain());
        Method method = jClass.getDeclaredMethod("onEnable");
        Object objectClassInstance = jClass.newInstance();
        method.invoke(objectClassInstance);
        this.modulePID.put(moduleName.toUpperCase(), nextPID++);
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + "::" + this.modulePID.get(moduleName.toUpperCase()) + " successfully enabled.", true, true);
    }

    public void disableModules() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        for (String moduleName : new File(System.getProperty("user.dir") + "/modules").list()) {
            disableModule(moduleName);
        }
    }

    public void disableModule(String moduleName) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Killing process: " + moduleName.toUpperCase() + "::" + this.modulePID.get(moduleName.toUpperCase()) + "..", true, true);
        this.modulePID.remove(moduleName.toUpperCase());
        File file = new File(System.getProperty("user.dir") + "/modules/" + moduleName);
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader());
        Class jClass = Class.forName(this.getModuleYML(moduleName).getModuleMain(), true, urlClassLoader);
        Method method = jClass.getDeclaredMethod("onDisable");
        Object objectClassInstance = jClass.newInstance();
        method.invoke(objectClassInstance);
        LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Disabled module: " + moduleName.toUpperCase() + " successfully.", true, true);
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

    private ModuleDescriptor getModuleYML(String moduleName) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/modules/" + moduleName);
        JarFile jarFile = new JarFile(file);

        ModuleDescriptor moduleDescriptor = new ModuleDescriptor(jarFile.getInputStream(jarFile.getJarEntry("module.yml")));
        if (moduleDescriptor == null)
            throw new InvalidModuleDescriptionException();
        moduleDescriptor.getModuleDescription();
        return moduleDescriptor;
    }
}
