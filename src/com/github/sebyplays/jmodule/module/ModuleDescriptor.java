package com.github.sebyplays.jmodule.module;

import com.esotericsoftware.yamlbeans.YamlException;
import com.github.sebyplays.yamlutilizer.yaml.YamlUtilizer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;

public class ModuleDescriptor extends ModuleInfo{
    private YamlUtilizer yamlUtilizer = new YamlUtilizer();
    private File file;

    public String moduleName;
    public String moduleVersion;
    public String moduleAuthor;
    public String moduleMainPath;
    public String moduleDescription;

    public ModuleDescriptor(File fileName) throws FileNotFoundException, YamlException {
        super(fileName.getName());
        this.file = fileName;
        yamlUtilizer.setFile(this.file);
        yamlUtilizer.load();
    }
    public ModuleDescriptor() {
        super();
    }

    public String getAbsoluteFileName() {
        return file.getName();
    }

    public ModuleInfo getModuleDescription() throws FileNotFoundException {
        this.moduleAuthor = this.yamlUtilizer.getString("author");
        this.moduleDescription = this.yamlUtilizer.getString("description");
        this.moduleVersion = this.yamlUtilizer.getString("version");
        this.moduleName = this.yamlUtilizer.getString("name");
        this.moduleMainPath = this.yamlUtilizer.getString("main");
        return null;
    }
}
