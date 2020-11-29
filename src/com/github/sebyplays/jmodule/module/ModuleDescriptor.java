package com.github.sebyplays.jmodule.module;

import com.esotericsoftware.yamlbeans.YamlException;
import com.github.sebyplays.yamlutilizer.yaml.YamlUtilizer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class ModuleDescriptor extends ModuleInfo {
    private YamlUtilizer yamlUtilizer = new YamlUtilizer();
    private File file;

    public static String moduleName;
    public static String moduleVersion;
    public static String moduleAuthor;
    public static String moduleMainPath;
    public static String moduleDescription;
    private Yaml yaml = new Yaml();
    private InputStream inputStream;
    private DescriptorVariant descriptorVariant;

    public ModuleDescriptor(File fileName) throws FileNotFoundException, YamlException {
        super(fileName.getName());
        this.file = fileName;
        this.yamlUtilizer.setFile(this.file);
        this.yamlUtilizer.load();
        this.descriptorVariant = DescriptorVariant.FILEBASED;
    }

    public ModuleDescriptor(InputStream inputStream) {
        this.inputStream = inputStream;
        this.descriptorVariant = DescriptorVariant.INPUTSTREAMBASED;
    }

    public ModuleDescriptor() {
        super();
    }

    public String getAbsoluteFileName() {
        return file.getName();
    }

    public ModuleInfo getModuleDescription() throws FileNotFoundException {
        if (this.descriptorVariant == DescriptorVariant.FILEBASED) {
            this.moduleAuthor = this.yamlUtilizer.getString("author");
            this.moduleDescription = this.yamlUtilizer.getString("description");
            this.moduleVersion = this.yamlUtilizer.getString("version");
            this.moduleName = this.yamlUtilizer.getString("name");
            this.moduleMainPath = this.yamlUtilizer.getString("main");
            return null;
        }
        if (this.descriptorVariant == DescriptorVariant.INPUTSTREAMBASED) {
            Map<String, Object> keyVal = yaml.load(this.inputStream);
            this.moduleAuthor = (String) keyVal.get("author");
            this.moduleMainPath = (String) keyVal.get("main");
            this.moduleName = (String) keyVal.get("name");
            this.moduleVersion = (String) keyVal.get("version");
            this.moduleVersion = (String) keyVal.get("description");
            return null;
        }
        return null;
    }
}
