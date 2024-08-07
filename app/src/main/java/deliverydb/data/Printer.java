package deliverydb.data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for formatting and printing data fields and their values.
 * <p>
 * This class provides functionality to create and format data fields as strings
 * for better readability in debugging or logging.
 * </p>
 */
public final class Printer {

    /**
     * Represents a data field with a name and a value.
     * <p>
     * This class is used to store and format information about data fields.
     * </p>
     */
    public static class Field {

        public final String name;
        public final Object value;

        /**
         * Constructs a new Field with the specified name and value.
         *
         * @param name  the name of the field
         * @param value the value of the field
         */
        public Field(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            if (this.value instanceof String) {
                return this.name + "='" + this.value.toString() + "'";
            } else {
                return this.name + "=" + this.value.toString();
            }
        }
    }

    /**
     * Creates a new {@link Field} instance with the specified name and value.
     *
     * @param name  the name of the field
     * @param value the value of the field
     * @return a new {@link Field} instance
     */
    public static Field field(String name, Object value) {
        return new Field(name, value);
    }

    /**
     * Generates a formatted string representation of a name and a list of fields.
     * <p>
     * The generated string includes the name followed by a list of fields formatted as "name=value".
     * </p>
     *
     * @param name   the name to be included in the string
     * @param fields the list of fields to be formatted
     * @return a string representation of the name and fields
     */
    public static String stringify(String name, List<Field> fields) {
        var builder = new StringBuilder(name);
        var fieldsString = fields.stream()
                .map(Field::toString)
                .collect(Collectors.joining(", "));
        builder.append("[");
        builder.append(fieldsString);
        builder.append("]");
        return builder.toString();
    }
}
