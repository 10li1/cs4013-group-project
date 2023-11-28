public class Module {
    private String moduleCode;
    private String moduleName;

    /**
     * constructor with module info
     * 
     * @param moduleCode
     * @param moduleName
     */
    public Module(String moduleCode,String moduleName) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
    }

    /**
     * return module code 
     *  
     * @return
     */
    public String getModuleCode(){
        return moduleCode;
    }

    /**
     * set module code 
     * 
     * @param moduleCode
     */
    public void setModuleCode(String moduleCode){
        this.moduleCode = moduleCode;
    }

    /**
     * return module name 
     * 
     * @return
     */
    public String getModuleName(){
        return moduleName;
    }

    /**
     * set module name  
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName){
        this.moduleName = moduleName;
    }

}