import java.io.*;
import java.util.*;

class ElementAlreadyThereException extends Exception{
	ElementAlreadyThereException(String message){
		super(message);
	}
}


class ElementNotFoundException extends Exception{
	ElementNotFoundException(String message){
		super(message);
	}
}


class DeletingProductNotFoundException extends Exception{
	DeletingProductNotFoundException(String message){
		super(message);
	}
}


class MoreQuantityDemandedException extends Exception{
	MoreQuantityDemandedException(String message){
		super(message);
	}
}

class LessFundsException extends Exception{
	LessFundsException(String message){
		super(message);
	}
}


class Amacon_database{
	String name;
	int price = 0;
	int quantity = 0;
	boolean is_cat = false;
	boolean is_sub = false;
	boolean is_pro = false;
	List<Amacon_database> children = new ArrayList<Amacon_database>();
	Amacon_database parent = null;
	Amacon_database root = null;
	

	void add_children(String path, String element, int price, int quantity, Amacon_database root){
		// using node.parent instead this can cause some error.. see if there are any
		String[] o = path.split(" > ");
		// node.root = root;
		int flag = 0;
		try{
			Amacon_database temp = new Amacon_database();
			Amacon_database temp2 = new Amacon_database();			
			for(Amacon_database node_: root.children){
				if(node_.name.equals(o[0])){
					flag = 1;
					temp = node_;
				}
			}
			if(flag == 0){
				Amacon_database node = new Amacon_database();
				node.parent = root;
				node.name = o[0];
				root.children.add(node);
				temp = node;
			}
			else{
				flag = 0;
				for(Amacon_database node_: temp.children){
					if(node_.name.equals(o[1])){
						flag = 1;
						temp2 = node_;
					}
				}
			}
			if(flag == 0){
				Amacon_database node = new Amacon_database();
				node.parent = temp;
				node.name = o[1];
				temp.children.add(node);
				temp2 = node;
			}
			else{
				flag = 0;
				for(Amacon_database node_: temp2.children){
					if(node_.name.equals(element)){
						throw new ElementAlreadyThereException(path +" > " + element + " already there");
					}
				}
			}
			Amacon_database node = new Amacon_database();
			node.parent = temp2;
			node.name = element;
			node.price = price;
			node.quantity = quantity;
			temp2.children.add(node);
			System.out.println(path+" > "+element + " added");
		}
		catch(Exception e){
			System.err.println(e);
		}
	}
	
	// int get_index_of_object(Amacon_database parent_object){
	//	 for(Amacon_database obj: parent_object.children){
	//	 	 if(obj.name == )
	//	 }
	// }
	
	void delete_node(String path, Amacon_database root){
		try{
			Amacon_database node = search(path, 0, root);
			if(node == null){
				throw new DeletingProductNotFoundException(path + " Not found");
			}
			System.out.println(node.name+" -----");
			Amacon_database parent = node.parent;
			for(Iterator<Amacon_database> iter = parent.children.listIterator(); iter.hasNext();){
				Amacon_database a = iter.next();
				if(a.name.equals(node.name)){
					iter.remove();
				}
			}
		}
		catch(Exception e){
			System.err.println(e);
		}
	}
	
	Amacon_database search(String path, int choice, Amacon_database root){
		String[] s = path.split(" > ");
		int length = s.length;
		int flag1 = 0;
		int flag2 = 0;
		int flag3 = 0;
		try{
			for(Amacon_database node: root.children){
				if(node.name.equals(s[0]) && length == 1){
					flag1 = 1;
					if(choice == 1){System.out.println(node.name  + " Found");}
					return node;
				}
				else if(node.name.equals(s[0])){
					for(Amacon_database node2:node.children){
						if(node2.name.equals(s[1]) && length == 2){
							flag2 = 1;	
							if(choice == 1){
								System.out.println(node2.name +  " Found");
							}
							return node2;
						}
						else if(node2.name.equals(s[1])){
							for(Amacon_database node3: node2.children){
								if(node3.name.equals(s[2]) && length == 3){
									flag3 = 1;
									if(choice == 1){
										System.out.println(path + " " + node3.price + " "+node3.quantity + " Found");
									}	
									return node3;
								}
							}
							if(flag3 == 0 && choice == 1)
								throw new ElementNotFoundException(path + " Not found");
						}	
					}
					if(flag2 == 0  && choice == 1)
						throw new ElementNotFoundException(path + " Not found");
				}
			}
			if(flag1 == 0  && choice == 1){
				throw new ElementNotFoundException(path + " Not found");	
			}
		}catch(Exception e){
			System.err.println(e);
		}
		return null;
	}

	void modify(String path, String name, int price, int quantity, Amacon_database root){
		try{
			Amacon_database node = search(path, 0, root);
			node.name = name;
			node.price = price;
			node.root = root;
			node.is_cat = is_cat;
			node.quantity = quantity;
			node.is_sub = is_sub;
			node.is_pro = is_pro;
		}
		catch(Exception e){
			System.err.println(e);
		}
		
	}

	int sale(String path, int quantity, int funds, Amacon_database root){
		try{
			Amacon_database node = search(path, 0, root);
			if(quantity > node.quantity) throw new MoreQuantityDemandedException(path + " -> " + "Quantity demanded is more than existing\n\tDemanded -> " + quantity + " Present -> "+node.quantity);
			else if(funds < (node.price * quantity)) throw new LessFundsException("You have less funds, you poor creature. Add "+ (node.price*quantity - funds) +" more");
			else{
				node.quantity -= quantity;
				return node.price;
			}
		}
		catch(Exception e){
			System.err.println(e);
			return 0;
		}
	}
}













