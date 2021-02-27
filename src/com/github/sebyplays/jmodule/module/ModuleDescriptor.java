package com.github.sebyplays.jmodule.module;

import com.esotericsoftware.yamlbeans.YamlException;
import com.github.sebyplays.yamlutilizer.yaml.YamlUtilizer;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class ModuleDescriptor extends ModuleInfo {
    private final YamlUtilizer yamlUtilizer = new YamlUtilizer();
    private File file;


    private final Yaml yaml = new Yaml();
    private InputStream inputStream;
    private DescriptorVariant descriptorVariant;

    public ModuleDescriptor(File fileName) throws FileNotFoundException, YamlException {
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

    @SneakyThrows
    public String getModuleDescription(){
        if (this.descriptorVariant == DescriptorVariant.FILEBASED) {
            moduleAuthor = this.yamlUtilizer.getString("author");
            moduleDescription = this.yamlUtilizer.getString("description");
            moduleVersion = this.yamlUtilizer.getString("version");
            moduleName = this.yamlUtilizer.getString("name");
            moduleMainPath = this.yamlUtilizer.getString("main");
            modulePriority = this.yamlUtilizer.getString("priority");
            return super.moduleDescription;
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
        return super.moduleDescription;
    }
}

enum DescriptorVariant {

    FILEBASED(1),
    INPUTSTREAMBASED(0);

    DescriptorVariant(int i) {
    }
}
