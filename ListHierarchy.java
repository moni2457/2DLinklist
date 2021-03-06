public class ListHierarchy {
	
    // Linked List Node Object
    class Node {
        Node prev; 							// set link with previous node
        Node next;							// set link with next node
        String value;
        int level = 0;
        Node child;							// set link with the second hierarchy
        public Node(String val, int level) {
        	this.level = level;
            this.value = val;
        }
    }
    
    // public and private Fields
    public Node head;					//head of first level
    public Node tail;					//tail of the first level
    public Node topHead;				//head of second level hierarchy
    public Node topTail;				//tail of second level hierarchy
    private int size = 0;				//size of second level linkList
    public int sizeInTop = 0;			//size of first level linkList
  
    Coin coin;
    int level = 0;						// to get the level generated by coin flio
    
    public ListHierarchy(Coin flip) {
    	if(flip == null) {				//checking for null in flip
    		System.out.println("Please do not enter null value.");
    	} else {
    		this.coin = flip;
    	}
    	
    }
    
    /*Add method invoked due to 
     *  add a node in the linkList
       */
    boolean add(String key) {
    	String value = key.trim();
    	boolean flag = false;			
    	// checking for null value and for alphabets in the string
    	if(  value != null && value.length() > 0   && value.matches("^[a-zA-Z]*$") ) {
    		while(this.coin.flip() != 0) {					// calling the flip method until zero
    			level++;
    		}
    		flag = this.insertNewNode(value, level);			//invoked a method for adding
    	} else {
    		System.out.println("Enter a valid string");
    		flag = false;
    	}
    		
    	return flag;
    }
    
    
    /*method invoked 
     * to find a string
     * from the linkList
    */
    boolean find(String key) {
    	String string  = key.trim();  						//to remove extra white spaces
    	// checking string is null and matches the regex of alphabets
    	if( string != null &&  string.length() > 0 && string.matches("^[a-zA-Z]*$")) {
    		if(size > 0) {								// checking whether list is empty
    			if(sizeInTop > 0) {						//checking in topmost hierarchy
            		Node currentNode = this.topHead;
            		 while(currentNode != null) {		//iterating the linkList
            	            if(currentNode.value.equalsIgnoreCase(key)) {
            	            	return true;
            	            }
            	            currentNode = currentNode.next;
            	        }
            	} 
            	if (size > 0){						//checking in lower hierarchy
            			Node currentNode = this.head;
               		 while(currentNode != null) {			//iterating the linkList
               	            if(currentNode.value.equalsIgnoreCase(key)) {
               	            	return true;
               	            }
               	         currentNode = currentNode.next;
               	        }
            	}
            	return false;
        	} else {
        		System.out.println("List is empty");
        		return false;
        	}
    	} else {
			System.out.println("Enter a valid string");
			return false;
		}
    	
    }
    
    /* linking the newNode
     * added with the other
     * nodes.Setting previous and
     * next node for the new node for both levels 
     * */
    private void settingLinksToNode(Node nodeToInsertAfter, Node newNode, int level ) {
        Node tempNode;
        Node headNode;
        Node tailNode;
        int sizeInc = 0;
        //head and tail for both levels
        if (level == 0) {				
        	headNode = this.head;
        	tailNode = this.tail;
        	sizeInc =this.size;
        } else {
        	headNode = this.topHead;
        	tailNode = this.topTail;
        	sizeInc = this.sizeInTop;
        }
        
        if (nodeToInsertAfter == null) {  // adding newNode to list having only head node.
            headNode.prev = newNode;
            tempNode  = headNode;
            headNode = newNode;
            headNode.next = tempNode;
            if (this.size <= 1) {       //setting the tail for list having one node
            	tailNode = tempNode;
            	tailNode.next = null;
            }
        } else if (nodeToInsertAfter.next == null) {
            newNode.prev = nodeToInsertAfter;		
            nodeToInsertAfter.next = newNode;
            tailNode = newNode;
        } else {
            Node prevNode, nextNode;		//adding the new node in the middle of list
            prevNode = nodeToInsertAfter;
            nextNode = nodeToInsertAfter.next;		//linking the next and prev nodes
            prevNode.next = newNode;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            nextNode.prev = newNode;
        }
        sizeInc++;
        
        if (level == 0) {
        	 this.head = headNode ;
        	 this.tail = tailNode;
        	this.size = sizeInc;
        } else {
        	this.topHead = headNode;
        	this.topTail = tailNode;
        	this.sizeInTop = sizeInc;
        }
  }
   
    
    /*For sorting the list and adding 
     * the new node to the exact sorted 
     * location*/
    private Node findNewNodeLocation(Node newNode, int level) {
    	Node curNode;
    	if(level == 0) {
    		 curNode = this.head;
    	} else {
    		 curNode = this.topHead;
    	}
    	//iterating the linklist for sorting and getting the exact position of the new node
    	//for  both lower and top level
        if (newNode.value.compareToIgnoreCase(curNode.value)  < 0) {
            return null;
        }
        while(curNode.next != null) {
            if (newNode.value == curNode.value) {
                return curNode;
            }
            else if (newNode.value.compareToIgnoreCase(curNode.value) > 0 && newNode.value.compareToIgnoreCase( curNode.next.value) < 0) {
                return curNode;
            }
            curNode = curNode.next;
        }
        return curNode;
    }
    
  /* Creating a new node 
   * and adding value to it
   * */ 
    public boolean insertNewNode(String value,int level) {
        Node newNode = new Node(value, level);
        Node newNodeLocation;
        	  if (this.head == null) {			//adding the node to the first level in head node as list is empty
                  this.head = newNode;			//setting the head and tail
                  this.head.next = this.tail;
                  this.size++;
              } else {
            	  newNodeLocation = findNewNodeLocation(newNode, level); //find the exact location of next node 
                  if (newNodeLocation == null || newNodeLocation.value != newNode.value) {
                	  settingLinksToNode(newNodeLocation, newNode, level);
                  }
              }
        if(newNode.level > 0) {			//adding node to the next level
        	  Node currentNode = this.head;
              while(currentNode != null) {
                  if(currentNode.value == newNode.value) {		// creating a new level hierarchy
                	  currentNode.child = newNode;				
                	  currentNode.child.value = newNode.value;
                	  sizeInTop++;
                	  if(sizeInTop == 1) {
                		 this.topHead =  newNode;			//setting the firstnode of toplevel
                		 this.topHead.next = this.topTail;
                      } else {
                    	  newNodeLocation = findNewNodeLocation(newNode, level);		//finding the location to add the new node
                          if (newNodeLocation == null || newNodeLocation.value != newNode.value) {
                        	  settingLinksToNode(newNodeLocation, newNode, level);
                          }
                      }              	  
                  }
                  currentNode = currentNode.next;   
              }  
        } 
        return true;
    }
 
}

