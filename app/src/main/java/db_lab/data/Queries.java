package db_lab.data;

public final class Queries {

    public static final String TAGS_FOR_PRODUCT =
        """
        SELECT t.tag_name
        FROM TAGGED t
        WHERE t.product_code = ?
        

        """;

    public static final String LIST_PRODUCTS =
        """
        SELECT p.code, p.name
        FROM PRODUCT p
        """;

    public static final String PRODUCT_COMPOSITION =
        """
        SELECT m.code, m.description, pc.percent
        FROM MATERIAL m
        JOIN COMPOSITION pc ON m.code = pc.material_code
        WHERE pc.product_code = ?

        """;

    public static final String FIND_PRODUCT =
        """
        SELECT p.code, p.name, p.description
        FROM PRODUCT p
        WHERE p.code = ?
        """;
}
