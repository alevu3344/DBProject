package deliverydb.data;

/**
 * Custom runtime exception to wrap exceptions thrown by DAO objects.
 * <p>
 * This exception is used to handle database access errors without exposing
 * {@link SQLException} directly in other parts of the application.
 * </p>
 */
public final class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DAOException with the specified detail message.
     *
     * @param message the detail message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAOException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new DAOException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
