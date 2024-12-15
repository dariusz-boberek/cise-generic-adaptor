package eu.cise.adaptor.core.servicehandler.port.out.api.enums;

/**
 * Base Interface for Sort By related Enums to implement
 */
public interface SortByEnum extends ValueEnum<String> {

    public String value();

    public String getOrderByExpressionAsc();

    public String getOrderByExpressionDesc();

}
