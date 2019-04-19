import java.io.*;
import java.util.*;

class AddingToCartProductNotFoundException extends Exception{
	AddingToCartProductNotFoundException(String message){
		super(message);
	}
}


class Amacon_customer{
    String name;
    HashMap<String, Integer> cart = new HashMap<String, Integer>();
    int funds;


    void add_funds(int add){
        this.funds += add;
    }

    void add_product(String path, Amacon_database root2, int quantity){
        try{
            Amacon_database pro = root2.search(path, 0, root2);
            if(pro == null)
                throw new AddingToCartProductNotFoundException(path + " Not found");
            this.cart.put(path, quantity);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    void check_out(Amacon_database root2){
        for(Map.Entry<String, Integer> iter :this.cart.entrySet()){
            int out = root2.sale(iter.getKey(), iter.getValue(), this.funds, root2); 
            this.funds -= out * iter.getValue();
        }
    } 
}