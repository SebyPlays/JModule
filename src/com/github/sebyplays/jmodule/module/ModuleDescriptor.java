package com.github.sebyplays.jmodule.module;

import com.esotericsoftware.yamlbeans.YamlException;
import com.github.sebyplays.yamlutilizer.yaml.YamlUtilizer;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class ModuleDescriptor extends ModuleInfo {
    private final YamlUtilizer yamlUtilizer = new YamlUtilizer();
    private File file;

    public static String moduleName;
    public static String moduleVersion;
    public static String moduleAuthor;
    public static String moduleMainPath;
    public static String moduleDescription;
    public static String modulePriority;
    private final Yaml yaml = new Yaml();
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

    public void getModuleDescription() throws FileNotFoundException {
        if (this.descriptorVariant == DescriptorVariant.FILEBASED) {
            moduleAuthor = this.yamlUtilizer.getString("author");
            moduleDescription = this.yamlUtilizer.getString("description");
            moduleVersion = this.yamlUtilizer.getString("version");
            moduleName = this.yamlUtilizer.getString("name");
            moduleMainPath = this.yamlUtilizer.getString("main");
            modulePriority = this.yamlUtilizer.getString("priority");
            return;
        }
        if (this.descriptorVariant == DescriptorVariant.INPUTSTREAMBASED) {
            Map<String, Object> keyVal = yaml.load(this.inputStream);
            moduleAuthor = (String) keyVal.get("author");
            moduleMainPath = (String) keyVal.get("main");
            moduleName = (String) keyVal.get("name");
            moduleVersion = (String) keyVal.get("version");
            moduleVersion = (String) keyVal.get("description");
            modulePriority = (String) keyVal.get("priority");
        }
    }
}
