package deliveryDB.controller;

import deliveryDB.model.DelModel;
import deliveryDB.utilities.Pair;
import deliveryDB.view.ResMenu;

import java.util.Map;
import javax.swing.JFrame;

import deliveryDB.data.Item;
import deliveryDB.data.Restaurant;
import java.util.LinkedHashMap;

import java.util.Date;



public class ResMenuCtrl implements Controller {
    
    private final DelModel model;
    private ResMenu view;
    private final ResController prevctrl;
    private Restaurant restaurant;
    private final JFrame mainFrame;


    public ResMenuCtrl(ResController prevctrl, JFrame mainFrame, DelModel model, Restaurant restaurant) {    
        this.model = model;
        this.mainFrame = mainFrame;
        this.prevctrl = prevctrl;
        this.restaurant = restaurant;
        this.view = new ResMenu(this.mainFrame, this);
    
    }   

    

    public void handleBack(){
        this.prevctrl.showRestaurants();
    }

    public float getBalance(){
        return this.model.getBalance();
    }

    public void handleSendOrder(Map<Item, Integer> order){
        
        if(this.model.sendOrder(order, this.restaurant.getRestaurantID())){
            this.view.updateBalance(this.model.getBalance());
            this.view.showOrderConfirmation();
            return;
        }
        this.view.showOrderError();
        return;
    }

    public String getRestaurantName(){
        return this.restaurant.getName();
    }

    // get the opening and closing time of the restaurant
    public Pair<Date, Date> getRestaurantTime(){
        return this.restaurant.getOpeningHours();
    }

    public Map<Item, Integer> getItemMap() {
        var menu = this.model.getMenuFor(this.restaurant.getRestaurantID());
        Map<Item, Integer> itemQuantityMap = new LinkedHashMap<>();
        menu.forEach(e -> itemQuantityMap.put(e, 0));
        itemQuantityMap.entrySet().forEach(e -> System.out.println(e.getKey().getName() + " " + e.getKey().getType()));
        return itemQuantityMap;
    }

    public void handleReviews(){
        new ReviewController(this, this.mainFrame, this.model, this.restaurant.getRestaurantID());
    }

    @Override
    public void show() {
        this.view = new ResMenu(this.mainFrame, this);
    }

 
}
