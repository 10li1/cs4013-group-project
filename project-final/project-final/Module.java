public class Module {
    private String moduleCode;
    private String moduleName;

    /**
     * constructor with module info
     * 
     * @param moduleCode  module code
     * @param moduleName  module name 
     */
    public Module(String moduleCode,String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    /**
     * return module code 
     *  
     * @return module code
     */
    public String getModuleCode(){
        return moduleCode;
    }

    /**
     * set module code 
     * 
     * @param moduleCode set module code
     */
    public void setModuleCode(String moduleCode){
        this.moduleCode = moduleCode;
    }

    /**
     * return module name 
     * 
     * @return module name
     */
    public String getModuleName(){
        return moduleName;
    }

    /**
     * set module name  
     * 
     * @param moduleName set up module name
     */
    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }

}