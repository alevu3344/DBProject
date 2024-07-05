package deliveryDB.controller;

import deliveryDB.data.DAOException;
import deliveryDB.data.ProductPreview;
import deliveryDB.model.TessilandModel;
import deliveryDB.view.PreviewPage;

public class PreviewsController {

    private PreviewPage resPage;
    private TessilandModel model;

    public PreviewsController(PreviewPage resPage, TessilandModel model){
        this.resPage = resPage;
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
            this.resPage.loadingProduct();
            var product = this.model.find(productPreview.code);
            if (product.isPresent()) {
                this.resPage.productPage(product.get());
            } else {
                this.resPage.failedToLoadProduct(productPreview);
            }
        } catch (DAOException e) {
            this.resPage.failedToLoadProduct(productPreview);
        }
    }

    public void userClickedBack() {
        if (this.model.loadedPreviews()) {
            this.resPage.previewPage(this.model.previews());
        } else {
            this.loadInitialPage();
        }
    }

    private void loadInitialPage() {
        try {
            this.resPage.loadingPreviews();
            var previews = this.model.loadPreviews();
            this.resPage.previewPage(previews);
        } catch (DAOException e) {
            e.printStackTrace();
            this.resPage.failedToLoadPreviews();
        }
    }
    
}
