package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 *
 *  Enum to use as value catalog for Area of Interest API requests
 *
 */
public enum AreaOfInterestDataLocation implements ValueEnum<String> {
    ATLANTIC("Atlantic"),
    BALTIC_SEA("BalticSea"),
    NORTH_SEA("NorthSea"),
    MEDITERRANEAN("Mediterranean"),
    BLACK_SEA("BlackSea"),
    OUTERMOST_REGIONS("OutermostRegions"),
    ARCTIC_OCEAN("ArcticOcean"),
    NON_SPECIFIED("NonSpecified");

    private final String value;

    AreaOfInterestDataLocation(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return value;
    }

    public static AreaOfInterestDataLocation fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (AreaOfInterestDataLocation areaOfInterestDataLocation : AreaOfInterestDataLocation.values()) {
            if (areaOfInterestDataLocation.value.equals(value)) {
                return areaOfInterestDataLocation;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + AreaOfInterestDataLocation.class.getName() + " with value: " + value);
    }

}
