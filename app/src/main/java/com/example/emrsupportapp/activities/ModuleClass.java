package com.example.emrsupportapp.activities;

public class ModuleClass {
    String moduleStatus;

    public ModuleClass(String moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public String getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    @Override
    public String toString() {
        return "ModuleClass{" +
                "moduleStatus='" + moduleStatus + '\'' +
                '}';
    }
}
