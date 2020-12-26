// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }
    
    
    // INSERT
    
    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree node= new BSTree(address, size, key);
        BSTree root=this.getRoot();
        if(root==null){
            BSTree current=getSentinel();
            current.right=node;
            node.parent=current;
            return node;
        }
        else{
            BSTree current=root;
            while(current!=null){
                if(current.key<key){
                    if(current.right==null){
                        current.right=node;
                        node.parent=current;
                        return node;
                    }
                    else{current=current.right;}
                }
                else if(current.key>key){
                    if(current.left==null){
                        current.left=node;
                        node.parent=current;
                        return node;
                    }
                    else{current=current.left;}
                }
                else{
                    if(current.address<address){
                        if(current.right==null){
                            current.right=node;
                            node.parent=current;
                            return node;
                        }
                        else{current=current.right;}
                    }
                    else{
                        if(current.left==null){
                            current.left=node;
                            node.parent=current;
                            return node;
                        }
                        else{current=current.left;}
                    }
                }
            }
            return null;
        }
    }

    //DELETE
    
    public boolean Delete(Dictionary e)
    {   
        if(e==null){
            return false;
        }
        BSTree node=this.searchnode(e.address, e.size, e.key);
        if(node==null){return false;}

        else{
            // System.out.println(node.key);
            // No child 
            if(node.left==null && node.right==null){
                // System.out.println("no childcase");
                return node.Delete_nochild();
            }

            // Two child
            else if(node.right!=null && node.left!=null){
                BSTree successor=node.getNext();
                //System.out.println(successor.key);
                int tempkey=successor.key;
                int tempaddress=successor.address;
                int tempsize=successor.size;
                boolean result=false;
                if(successor.left==null && successor.right==null){
                    result= successor.Delete_nochild();
                }
                if(successor.left!=null || successor.right!=null){
                    result= successor.Delete_onechild();
                }
                node.key=tempkey;
                node.address=tempaddress;
                node.size=tempsize;
                // System.out.println("Two childcase");
                return result;
            }

            //One Child
            else if(node.left!=null || node.right!=null){
                // System.out.println("one childcase");
                return node.Delete_onechild();
            }
            
        }
        return false;
    }

    private boolean Delete_nochild(){
        BSTree node=this;
        if(node.isleftchild()){
            node.parent.left=null;
            node.parent=null;
            return true;
        }
        else if(node.isrightchild()){
            node.parent.right=null;
            node.parent=null;
            return true;
        }
        else{return false;}
    }

    private boolean Delete_onechild(){
        BSTree node=this;
        if(node.left!=null && node.right==null){
            if(node.isleftchild()){
                node.parent.left=node.left;
                node.left.parent=node.parent;
                node.parent=null;
                node.left=null;
                return true;
            }
            else if(node.isrightchild()){
                node.parent.right=node.left;
                node.left.parent=node.parent;
                node.parent=null;
                node.left=null;
                return true;
            }
            else{return false;}
        }
        else if(node.right!=null && node.left==null){
            if(node.isleftchild()){
                node.parent.left=node.right;
                node.right.parent=node.parent;
                node.parent=null;
                node.right=null;
                return true;
            }
            else if(node.isrightchild()){
                node.parent.right=node.right;
                node.right.parent=node.parent;
                node.parent=null;
                node.right=null;
                return true;
            }
        }
        return false;
    }

    private boolean isleftchild(){
        if (!this.issentinel() && this.parent.left==this){
            return true;
        }
        else{return false;}
    }

    private boolean isrightchild(){
        if (!this.issentinel() && this.parent.right==this){
            return true;
        }
        else{return false;}
    }
    
    private BSTree searchnode(int address,int size,int key){
        BSTree current=this.getFirst();
        while(current!=null){
            if(current.key==key && current.size==size && current.address==address){
                return current;
            }
            else{
                current=current.getNext();
            }
        }
        return null;
    }


    //FIND

    public BSTree Find(int key, boolean exact)
    { 
        if(exact){
            BSTree current=this.getFirst();
            if(current==null){return null;}
            else{
                while(current!=null){
                    if(current.key==key){
                        return current;
                    }
                    else{
                        current=current.getNext();
                    }
                }
                return null;
            }
            // return null;
        }
        else{
            BSTree current=this.getFirst();
            if(current==null){return null;}
            else{
                while(current!=null){
                    if(current.key>=key){
                        return current;
                    }
                    else{
                        current=current.getNext();
                    }
                }
                return null;
            }
            // return null;
        }
    }


    //GETFIRST

    public BSTree getFirst()
    { 
        BSTree root=this.getRoot();
        if(root==null){
            return null;
        }
        else{
            return root.getMinnode();
        }
    }

    //GETNEXT

    public BSTree getNext()   //Equivalent to successor.
    { 
        if(this.issentinel()){
            return null;
        }
        if (this.right!=null){
            return this.right.getMinnode();
        }
        else{
            BSTree y=this.parent;
            BSTree x=this;
            while(y!=null && y.left!=x){
                x=y;
                y=y.parent;
            }
            return y;
        }
    }


    //HELPER FUNCTIONS
    private BSTree getPrev(){             //equivalent to predecessor
        if(this.issentinel()){
            return null;
        }
        if (this.left!=null){
            return this.left.getMaxnode();
        }
        else{
            if(this.issentinel()){
                return null;
            }
            BSTree y=this.parent;
            BSTree x=this;
            while(y!=null && y.right!=x){
                x=y;
                y=y.parent;
            }
            return y;
        }
    }

    private BSTree getRoot(){
        BSTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.issentinel()){
            return current.right;
        }
        else{
            return null;
        }
    }

    private BSTree getSentinel(){
        BSTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.issentinel()){return current;}
        else{return null;}
    }

    private BSTree getMinnode(){
        if (this.left==null){
            return this;
        }
        else{
            BSTree current=this;
            while(current.left!=null){
                current=current.left;
            }
            return current;
        }
    } 

    private BSTree getMaxnode(){
        if(this.right==null){
            return this;
        }
        else{
            BSTree current=this;
            while(current!=null){
                current=current.right;
            }
            return current;
        }
    }

    //SANITY

    public boolean sanity()
    { 
        BSTree node=this;
        while(node.parent!=null){
            if(node.checkparentpointer()){
                node=node.parent;
            }
            else{return false;}
        }
        if(node.issentinel()){
            if(node.right==null){
                return true;
            }
            else{
                return node.right.checkBST();
            }
        }
        else{
            return false;
        }
    }
    private boolean checkBST(){

        BSTree node=this;
        if (node.left==null && node.right!=null) {
            if(node.checkrightpointer()){
                if(node.parent!=null){
                    if(!node.checkparentpointer()){return false;}
                }
                if(node.right.key<node.key) {
                    return false;
                }
                else if (node.key==node.right.key && node.address>node.right.address){
                    return false;
                }
                return node.right.checkBST();
            }
            else{return false;}
        }
        else if(node.left!=null && node.right==null ) {
            if(node.checkleftpointer()){
                if(node.parent!=null){
                    if(!node.checkparentpointer()){return false;}
                }
                if(node.left.key>node.key) {
                    return false;
                }
                else if(node.key==node.left.key && node.address<node.left.address){
                    return false;
                }
                return node.left.checkBST();
            }
            else{return false;}  
        }
        else if(node.left!=null && node.right!=null){
            if(node.checkleftpointer() && node.checkrightpointer()){
                if(node.parent!=null){
                    if(!node.checkparentpointer()){return false;}
                }
                if(node.key<node.left.key || node.key>node.right.key){
                    return false;
                }
                if( (node.key==node.left.key && node.left.address>node.address) || (node.key==node.right.key && node.right.address < node.address)  ) {
                    return false;
                }
                return ( node.left.checkBST() && node.right.checkBST());
            }
            else{return false;}
        }
        else {
            if(node.parent!=null){
                if(!node.checkparentpointer()){return false;}
            }
            return true;
        }
    }
    private boolean checkrightpointer(){
        if(this.right!=null){
            return this.right.parent==this;
        }
        return false;
    }
    private boolean checkleftpointer(){
        if(this.left!=null){
            return this.left.parent==this;
        }
        return false;
    }
    private boolean checkparentpointer(){
        if(this.parent!=null){
            if(this.isleftchild()){
                return this.parent.left==this;
            }
            else{
                return this.parent.right==this;
            }
        }
        return false;
    }

    private boolean issentinel(){
        if(this.address==-1 && this.size==-1 && this.key==-1 && this.left==null && this.parent==null){
            return true;
        }
        else{return false;}
    }
}