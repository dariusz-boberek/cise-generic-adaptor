package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 *
 *  Enum to use as value catalog for Sort By Contact Person properties API requests
 *
 */
public enum SortByContactPerson implements SortByEnum {
    SURNAME("surname", "cp.surname asc, cp.name asc", "cp.surname desc, cp.name asc"),
    NAME("name", "cp.name asc, cp.surname asc", "cp.name desc, cp.surname desc"),
    AUTHORITY("authority", "a.name asc, a.country asc, cp.surname asc, cp.name asc", "a.name desc, a.country desc, cp.surname asc, cp.name asc");

    private final String value;
    private final String orderByExpressionAsc;
    private final String orderByExpressionDesc;

    SortByContactPerson(String value, String orderByExpressionAsc, String orderByExpressionDesc) {
        this.value = value;
        this.orderByExpressionAsc = orderByExpressionAsc;
        this.orderByExpressionDesc = orderByExpressionDesc;
    }

    @Override
    public String value() {
        return value;
    }

    public static SortByContactPerson fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (SortByContactPerson sortByContactPerson : SortByContactPerson.values()) {
            if (sortByContactPerson.value.equals(value)) {
                return sortByContactPerson;
            }
        }
        throw new IllegalArgumentException("No enum value of type " + SortByContactPerson.class.getName() + " with value: " + value);
    }

    @Override
    public String getOrderByExpressionAsc() {
        return orderByExpressionAsc;
    }

    @Override
    public String getOrderByExpressionDesc() {
        return orderByExpressionDesc;
    }
}
