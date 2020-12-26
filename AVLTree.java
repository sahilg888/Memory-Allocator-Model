import java.util.*;

// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    
    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree last=InsertBST(address, size, key);
        AVLTree current=last;
        while(current!=null && current.parent!=null){
            current.height=Math.max(height(current.left),height(current.right))+1;
            if(!current.isbalanced() && !current.issentinel()){
                current=restructure(current);
                break;
            }
            else{
                current=current.parent;
            }
        }
        return last;
    }

    private AVLTree InsertBST(int address,int size,int key){
        AVLTree node= new AVLTree(address, size, key);
        AVLTree root=this.getRoot();
        if(root==null){
            AVLTree current=getSentinel();
            current.right=node;
            node.parent=current;
            return node;
        }
        else{
            AVLTree current=root;
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
        // if(!sanity()){
        //     System.out.println("delete sanity false");
        //     return false;
        // }
        if(e==null){
            return false;
        }
        AVLTree current=DeleteBST(e);
        if(current==null){return false;}
        while(current!=null && current.parent!=null){
            current.height=Math.max(height(current.left),height(current.right))+1;
            if(!current.isbalanced() && !current.issentinel()){
                current=restructure(current);
                current=current.parent;
            }
            else{
                current=current.parent;
            }
        }
        if(current.issentinel()){
            return true;
        }
        else{return false;}
    }
    
    private AVLTree DeleteBST(Dictionary e){
        if(e==null){
            return null;
        }
        AVLTree node=searchnode(this.getRoot(),e.address, e.size, e.key);
        if(node==null){return null;}

        else{
            // No child 
            if(node.left==null && node.right==null){
                // System.out.println("no childcase");
                return node.Delete_nochild();
            }

            // Two child
            else if(node.right!=null && node.left!=null){
                AVLTree successor=node.getNext();
                //System.out.println(successor.key);
                int tempkey=successor.key;
                int tempaddress=successor.address;
                int tempsize=successor.size;
                AVLTree result=null;
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
        return null;
    }

    private AVLTree Delete_nochild(){
        AVLTree node=this;
        if(node.isleftchild()){
            node.parent.left=null;
            AVLTree nodeparent=node.parent;
            node.parent=null;
            return nodeparent;
        }
        else if(node.isrightchild()){
            node.parent.right=null;
            AVLTree nodeparent=node.parent;
            node.parent=null;
            return nodeparent;
        }
        else{return null;}
    }

    private AVLTree Delete_onechild(){
        AVLTree node=this;
        if(node.left!=null && node.right==null){
            if(node.isleftchild()){
                node.parent.left=node.left;
                node.left.parent=node.parent;
                AVLTree nodeparent=node.parent;
                node.parent=null;
                node.left=null;
                return nodeparent;
            }
            else if(node.isrightchild()){
                node.parent.right=node.left;
                node.left.parent=node.parent;
                AVLTree nodeparent=node.parent;
                node.parent=null;
                node.left=null;
                return nodeparent;
            }
            else{return null;}
        }
        else if(node.right!=null && node.left==null){
            if(node.isleftchild()){
                node.parent.left=node.right;
                node.right.parent=node.parent;
                AVLTree nodeparent=node.parent;
                node.parent=null;
                node.right=null;
                return nodeparent;
            }
            else if(node.isrightchild()){
                node.parent.right=node.right;
                node.right.parent=node.parent;
                AVLTree nodeparent=node.parent;
                node.parent=null;
                node.right=null;
                return nodeparent;
            }
        }
        return null;
    }

    private AVLTree searchnode(AVLTree node,int address,int size,int key){
        if (node==null){
            return null;
        }
        if(node.key==key ){
            if(node.address==address && node.size==size){
                return node;
            }
            else if(node.address<address){
                return searchnode(node.right,address,size,key);
            }
            else{
                return searchnode(node.left,address,size,key);
            }
        }
        else if(node.key<key){
            return searchnode(node.right, address, size, key);
        }
        else{
            return searchnode(node.left, address, size, key);
        }
    }
    
    //HELPERS for restructing
    private boolean isbalanced(){
        return (Math.abs(height(this.left)-height(this.right))<=1);   
    }

    private int balancefactor(){
        return height(this.left)-height(this.right);
    }

    private AVLTree restructure(AVLTree node){
        if(node==null){return null;}
        int b=node.balancefactor();
        AVLTree z=node;
        if(b>1){
            if(height(z.left.left)>=height(z.left.right)){
                // System.out.println("case1");
                z=rightrotate(z);
                return z;
            }
            else{
                // System.out.println("case2");
                z.left=leftrotate(z.left);
                z=rightrotate(z);
                return z;
            }
        }
        else if(b<-1){
            if(height(z.right.right)>=height(z.right.left)){
                // System.out.println("case3");
                z=leftrotate(z);
                return z;
            }
            else{
                // System.out.println("case4");
                z.right=rightrotate(z.right);
                z=leftrotate(z);
                return z;
            }
        }
        else{
            return z;
        }
    }

    private AVLTree rightrotate(AVLTree node){
        if (node==null || node.left==null){return null;}
        AVLTree x=node;
        AVLTree y=node.left;
        x.left=y.right;
        if(x.left!=null){
            x.left.parent=x;
        }
        y.right=x;
        y.parent=x.parent;
        if(node.isleftchild()){
            node.parent.left=y;
        }
        if(node.isrightchild()){
            node.parent.right=y;
        }
        x.parent=y;
        x.height=Math.max(height(x.left),height(x.right))+1;
        y.height=Math.max(height(y.left),height(y.right))+1;
        return y;
    }

    private AVLTree leftrotate(AVLTree node){
        if (node==null || node.right==null){return null;}
        AVLTree x=node;
        AVLTree y=node.right;
        x.right=y.left;
        if(x.right!=null){
            x.right.parent=x;
        }
        y.left=x;
        y.parent=x.parent;
        if(node.isleftchild()){
            node.parent.left=y;
        }
        if(node.isrightchild()){
            node.parent.right=y;
        }
        x.parent=y;
        x.height=Math.max(height(x.left),height(x.right))+1;
        y.height=Math.max(height(y.left),height(y.right))+1;
        return y;
    }

    private int height(AVLTree node){
        if(node==null){
            return -1;
        }
        else{return node.height;}
    }
    
    //FIND
    public AVLTree Find(int k, boolean exact)
    { 
        AVLTree current=this.getRoot();
        AVLTree best=null;
        if(exact){
            if(current==null){return null;}
            else{
                while(current!=null){
                    if(current.key==k){
                        best=current;
                        current=current.left;
                    }
                    else if(current.key<k){
                        current=current.right;
                    }
                    else{
                        current=current.left;
                    }
                }
            }
        }
        else{
            if(current==null){return null;}
            else{
                while(current!=null){
                    if(current.key>=k){
                        best=current;
                        current=current.left;
                    }
                    else if(current.key<k){
                        current=current.right;
                    }
                }
            }
            // return null;
        }
        return best;
    }


    //GETFIRST
    public AVLTree getFirst()
    { 
        AVLTree root=this.getRoot();
        if(root==null){
            return null;
        }
        else{
            return root.getMinnode();
        }
    }


    //GETNEXT
    public AVLTree getNext()
    {
        if(this.issentinel()){
            return null;
        }
        if (this.right!=null){
            return this.right.getMinnode();
        }
        else{
            AVLTree y=this.parent;
            AVLTree x=this;
            while(y!=null && y.left!=x){
                x=y;
                y=y.parent;
            }
            return y;
        }
    }


    //SANITY
    public boolean sanity()
    { 
        AVLTree node=this;
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
                return node.right.checkAVL();
            }
        }
        else{
            return false;
        }
    }

    private boolean checkAVL(){

        AVLTree node=this;
        if(!node.isbalanced()){return false;}
        else{
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
                    return node.right.checkAVL();
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
                    return node.left.checkAVL();
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
                    return ( node.left.checkAVL() && node.right.checkAVL());
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
    private AVLTree getRoot(){
        AVLTree current=this;
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

    private AVLTree getSentinel(){
        AVLTree current=this;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.issentinel()){return current;}
        else{return null;}
    }

    private AVLTree getMinnode(){
        if (this.left==null){
            return this;
        }
        else{
            AVLTree current=this;
            while(current.left!=null){
                current=current.left;
            }
            return current;
        }
    }

    private AVLTree getMaxnode(){
        if(this.right==null){
            return this;
        }
        else{
            AVLTree current=this;
            while(current!=null){
                current=current.right;
            }
            return current;
        }
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

    private boolean issentinel(){
        if(this.address==-1 && this.size==-1 && this.key==-1 && this.left==null && this.parent==null){
            return true;
        }
        else{return false;}
    }
}
