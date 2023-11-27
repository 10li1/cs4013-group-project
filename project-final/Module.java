public class Module {
    private String moduleCode;
    private String moduleName;

    public Module(String moduleCode,String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    // Getter and setter methods
    public String getModuleCode(){
        return moduleCode;
    }

    public void setModuleCode(String moduleCode){
        this.moduleCode = moduleCode;
    }

    public String getModuleName(){
        return moduleName;
    }

    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }

}