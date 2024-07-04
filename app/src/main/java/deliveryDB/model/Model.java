package deliveryDB.model;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

import deliveryDB.data.Product;
import deliveryDB.data.ProductPreview;

public interface Model {
    public Optional<Product> find(int productCode);

    public List<ProductPreview> previews();

    public boolean loadedPreviews();

    public List<ProductPreview> loadPreviews();

    // Create a mocked version of the model.
    //
    public static Model mock() {
        return new MockedModel();
    }

    // Create a model that connects to a database using the given connection.
    //
    public static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
