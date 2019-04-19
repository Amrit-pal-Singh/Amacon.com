import java.io.*;
import java.util.*;


class Amacon{
	public static void main(String args[]){
		Amacon_database root = new Amacon_database();
		root.name = "root";
		root.add_children("electronics > smartphone", "Oneplus", 12, 10, root);
		root.add_children("electronics > smart", "plus", 112, 10, root);
		root.add_children("electr > smart", "negative", 1121, 10, root);
		root.add_children("electronics > smart", "minus", 11200, 10, root);
		root.add_children("cars > desi", "Audi", 110000, 10, root);
		root.add_children("electronics > smart", "minus", 11200, 10, root);

		
		root.search("cars > desi > Audi", 1,  root);		
		root.delete_node("electronics > smartphone > Oneplu", root);
		root.search("electronics > smartphone > Oneplu", 1,  root);		
		
		Amacon_customer c_root = new Amacon_customer();
		c_root.add_funds(10);
		c_root.add_product("electronics > smartphone > Oneplus", root, 2);
		c_root.check_out(root);
	}
}
