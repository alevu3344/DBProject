package db_lab.controller;

import db_lab.data.DAOException;
import db_lab.data.ProductPreview;
import db_lab.model.Model;
import db_lab.view.RestaurantsPage;

public class RestaurantsController {

    private RestaurantsPage rp;
    private Model model;

    public RestaurantsController(RestaurantsPage rp, Model model){
        this.rp = rp;
        this.model = model;
        this.userRequestedInitialPage();
    }

    public void userRequestedInitialPage() {
        this.loadInitialPage();
    }

    public void userClickedReloadPreviews() {
        this.loadInitialPage();
    }

    public void userClickedPreview(ProductPreview productPreview) {
        try {
            this.rp.loadingProduct();
            var product = this.model.find(productPreview.code);
            if (product.isPresent()) {
                this.rp.productPage(product.get());
            } else {
                this.rp.failedToLoadProduct(productPreview);
            }
        } catch (DAOException e) {
            this.rp.failedToLoadProduct(productPreview);
        }
    }

    public void userClickedBack() {
        if (this.model.loadedPreviews()) {
            this.rp.previewPage(this.model.previews());
        } else {
            this.loadInitialPage();
        }
    }

    void loadInitialPage() {
        try {
            this.rp.loadingPreviews();
            var previews = this.model.loadPreviews();
            this.rp.previewPage(previews);
        } catch (DAOException e) {
            e.printStackTrace();
            this.rp.failedToLoadPreviews();
        }
    }
    
}
