package com.github.sebyplays.jmodule.module;

import com.github.sebyplays.jmodule.module.api.JavaModule;
import com.github.sebyplays.yamlutilizer.yaml.YamlUtilizer;
import de.github.sebyplays.consoleprinterapi.api.ConsolePrinter;
import de.github.sebyplays.logmanager.api.LogManager;
import de.github.sebyplays.logmanager.api.LogType;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModuleLoader {


    //TODO Change contents of ModuleInfo.txt to the following
    /*
    to acquire main class:
    main: de.test.classes.Mainclass ;;

    To acquire version of module:
    version: 1... ;;

    To acquire commands registered:
    commands:
        command 1 ;;
        ...



     */
    public ModuleLoader() throws IOException {
        this.createDependentDirectories();
    }
    public boolean loadModule(String moduleName) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        FileReader fileReader = null;
            LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " is being  initialized..", true, false);
        System.out.print("Indexing file.");
        URL url = new File(System.getProperty("user.dir") + "/modules").toURI().toURL();
        URL[] urls = new URL[]{url};
                System.out.print("Indexing file..");
                YamlUtilizer yamlUtilizer = new YamlUtilizer( System.getProperty("user.dir") + "/modules/" + moduleName + "/module.yml");
                System.out.print("Indexing file...");

            ClassLoader classLoader = new URLClassLoader(urls);

            Class jClass = classLoader.loadClass (yamlUtilizer.getString("main").replaceAll(".", "/") + "class");

            Method method = jClass.getMethod("onActivation");
            method.invoke(null, null);
            LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " successfully initialized.", true, false);
        return false;
    }

    public boolean loadModule() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            FileReader fileReader = null;
            for(String moduleName : new File(System.getProperty("user.dir") + "/modules/").list()){
                LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " is being  initialized..", true, false);
                URL url = new File(System.getProperty("user.dir") + "/modules/").toURI().toURL();
                URL[] urls = new URL[]{url};
                ZipInputStream zipInputStream = new ZipInputStream(url.openStream());
                while(true) {
                    System.out.print("Indexing file.");
                    ZipEntry zipEntry = zipInputStream.getNextEntry();
                    String name = zipEntry.getName();
                    System.out.print("Indexing file..");
                    if (name.startsWith("modleinfo")) {
                        fileReader = new FileReader(System.getProperty("user.dir") + "/modules/" + "/" + moduleName + "/moduleinfo.txt");
                        System.out.println(zipEntry.toString() + fileReader.toString());
                        break;
                    }
                    System.out.print("Indexing file...");

                }

                ClassLoader classLoader = new URLClassLoader(urls);

                Class jClass = classLoader.loadClass(moduleName + "." + fileReader.read());

                Method method = jClass.getMethod("onActivation");
                method.invoke(null, null);
                LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Module " + moduleName.toUpperCase() + " successfully initialized.", true, false);
            }
            return false;
        }
        public boolean disableModule(String moduleName) throws IOException {
            //TODO Aquire infos of moduleInfos.txt in specific jar-. Module

            LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Disabling module: " + moduleName.toUpperCase() + "..", true, true);
            new JavaModule().onDeactivation();
            LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Disabled module: " + moduleName.toUpperCase() + " successfully.", true, true);
            return false;

        }

        public void reloadModules() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.disableModule("all");
        this.loadModule();
        return;
        }
        private void createDependentDirectories() throws IOException {
            if(!(new File(System.getProperty("user.dir") + "/modules")).exists()){
                new File(System.getProperty("user.dir") + "/modules").mkdirs();
                LogManager.getLogManager("JModule").log(LogType.INFORMATION, "Dependent Module directory has been created..", true, true);
                return;
            }
            return;
        }
}
