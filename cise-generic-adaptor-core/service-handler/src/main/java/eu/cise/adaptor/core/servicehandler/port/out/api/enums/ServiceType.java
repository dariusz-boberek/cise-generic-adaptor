package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Enum to use as value catalog for Service Type API requests
 */
public enum ServiceType implements ValueEnum<String> {
    ACTION_SERVICE("ActionService"),
    AGENT_SERVICE("AgentService"),
    AIRCRAFT_SERVICE("AircraftService"),
    ANOMALY_SERVICE("AnomalyService"),
    CARGO_DOCUMENT_SERVICE("CargoDocumentService"),
    CARGO_SERVICE("CargoService"),
    CERTIFICATE_DOCUMENT_SERVICE("CertificateDocumentService"),
    CRISIS_INCIDENT_SERVICE("CrisisIncidentService"),
    DOCUMENT_SERVICE("DocumentService"),
    EVENT_DOCUMENT_SERVICE("EventDocumentService"),
    INCIDENT_SERVICE("IncidentService"),
    IRREGULAR_MIGRATION_INCIDENT_SERVICE("IrregularMigrationIncidentService"),
    LAND_VEHICLE_SERVICE("LandVehicleService"),
    LAW_INFRINGEMENT_INCIDENT_SERVICE("LawInfringementIncidentService"),
    LOCATION_SERVICE("LocationService"),
    LOCATION_DOCUMENT_SERVICE("LocationDocumentService"),
    MARITIME_SAFETY_INCIDENT_SERVICE("MaritimeSafetyIncidentService"),
    METEO_OCEANOGRAPHIC_CONDITION_SERVICE("MeteoOceanographicConditionService"),
    MOVEMENT_SERVICE("MovementService"),
    OPERATIONAL_ASSET_SERVICE("OperationalAssetService"),
    ORGANIZATION_SERVICE("OrganizationService"),
    ORGANIZATION_DOCUMENT_SERVICE("OrganizationDocumentService"),
    PERSON_SERVICE("PersonService"),
    PERSON_DOCUMENT_SERVICE("PersonDocumentService"),
    RISK_DOCUMENT_SERVICE("RiskDocumentService"),
    RISK_SERVICE("RiskService"),
    VESSEL_DOCUMENT_SERVICE("VesselDocumentService"),
    VESSEL_SERVICE("VesselService");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static ServiceType fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ServiceType serviceType : ServiceType.values()) {
            if (serviceType.value.equals(value)) {
                return serviceType;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + ServiceType.class.getName() + " with value: " + value);
    }

}
