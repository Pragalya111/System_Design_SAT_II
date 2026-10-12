package com.rideshare.hld.model;

import java.util.List;
import java.util.Map;

/**
 * Model describing the logical architecture, component boundaries,
 * interaction flows, and future production scaling roadmap.
 */
public class ArchitectureInfo {

    private String systemName;
    private String architecturePattern;
    private String description;
    private List<ServiceComponent> logicalServices;
    private List<String> dispatchWorkflowSteps;
    private Map<String, String> futureScalabilityImprovements;

    public ArchitectureInfo() {
    }

    public ArchitectureInfo(String systemName, String architecturePattern, String description,
                            List<ServiceComponent> logicalServices,
                            List<String> dispatchWorkflowSteps,
                            Map<String, String> futureScalabilityImprovements) {
        this.systemName = systemName;
        this.architecturePattern = architecturePattern;
        this.description = description;
        this.logicalServices = logicalServices;
        this.dispatchWorkflowSteps = dispatchWorkflowSteps;
        this.futureScalabilityImprovements = futureScalabilityImprovements;
    }

    public static class ServiceComponent {
        private String serviceName;
        private String role;
        private String currentImplementation;
        private String productionScalePath;

        public ServiceComponent() {
        }

        public ServiceComponent(String serviceName, String role, String currentImplementation, String productionScalePath) {
            this.serviceName = serviceName;
            this.role = role;
            this.currentImplementation = currentImplementation;
            this.productionScalePath = productionScalePath;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCurrentImplementation() {
            return currentImplementation;
        }

        public void setCurrentImplementation(String currentImplementation) {
            this.currentImplementation = currentImplementation;
        }

        public String getProductionScalePath() {
            return productionScalePath;
        }

        public void setProductionScalePath(String productionScalePath) {
            this.productionScalePath = productionScalePath;
        }
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getArchitecturePattern() {
        return architecturePattern;
    }

    public void setArchitecturePattern(String architecturePattern) {
        this.architecturePattern = architecturePattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ServiceComponent> getLogicalServices() {
        return logicalServices;
    }

    public void setLogicalServices(List<ServiceComponent> logicalServices) {
        this.logicalServices = logicalServices;
    }

    public List<String> getDispatchWorkflowSteps() {
        return dispatchWorkflowSteps;
    }

    public void setDispatchWorkflowSteps(List<String> dispatchWorkflowSteps) {
        this.dispatchWorkflowSteps = dispatchWorkflowSteps;
    }

    public Map<String, String> getFutureScalabilityImprovements() {
        return futureScalabilityImprovements;
    }

    public void setFutureScalabilityImprovements(Map<String, String> futureScalabilityImprovements) {
        this.futureScalabilityImprovements = futureScalabilityImprovements;
    }
}
