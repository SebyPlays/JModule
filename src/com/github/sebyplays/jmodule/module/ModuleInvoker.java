package com.github.sebyplays.jmodule.module;

import com.github.sebyplays.jmodule.module.exceptions.InvalidModuleDescriptionException;
import com.github.sebyplays.logmanager.api.LogManager;
import com.github.sebyplays.logmanager.api.LogType;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class ModuleInvoker {

    public ModuleInvoker(){

    }

    public void createDependentDirectories() throws IOException {
        if (!(new File(System.getProperty("user.dir") + "/modules")).exists()) {
            new File(System.getProperty("user.dir") + "/modules").mkdirs();
            LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Dependent Module directory has been created..", false, false, true, true);
            return;
        }
        return;
    }

    //gets the module descriptor
    @SneakyThrows
    public ModuleDescriptor getModuleYML(Module module){
        JarFile jarFile = new JarFile(module.getPath().getPath());

        module.moduleDescriptor = new ModuleDescriptor(jarFile.getInputStream(jarFile.getJarEntry("module.yml")));
        if (module.getModuleDescriptor() == null)
            throw new InvalidModuleDescriptionException();
        module.getModuleDescriptor().getModuleDescription();
        return module.getModuleDescriptor();
    }

    @SneakyThrows
    public void universalMethodInvoke(File file, String classpath, String methodName){
        Class jClass = Class.forName(classpath, true, new URLClassLoader(new URL[]{file.toURI().toURL()}, this.getClass().getClassLoader()));
        Method method = jClass.getDeclaredMethod(methodName);
        method.invoke(jClass.newInstance());
    }

    @SneakyThrows
    public void invokeMethod(Module module, String methodName){
        Class jClass = Class.forName(module.getModuleInfo().getModuleMainPath(), true, new URLClassLoader(new URL[]{module.getPath().toURI().toURL()}, this.getClass().getClassLoader()));
        Method method = jClass.getDeclaredMethod(methodName);
        method.invoke(jClass.newInstance());
    }


}
