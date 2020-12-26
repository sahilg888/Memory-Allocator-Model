// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    @Override
    public int Allocate(int blockSize) {
        if(blockSize<=0){
            return -1;
        }
        else{
            Dictionary block=freeBlk.Find(blockSize,false);
            if (block==null){return -1;}
            else{
                if(block.size>blockSize){
                    int result=block.address;
                    freeBlk.Insert(block.address+blockSize,block.size-blockSize, block.size-blockSize);
                    allocBlk.Insert(block.address, blockSize, block.address); 
                    freeBlk.Delete(block);
                    return result;
                }
                else{
                    int result=block.address;
                    allocBlk.Insert(block.address, blockSize, block.address);
                    freeBlk.Delete(block);
                    return result; 
                }
            }
        }  
    } 
    @Override
    public int Free(int startAddr) {
        if(startAddr<0){
            return -1;
        }
        else{
            Dictionary block= allocBlk.Find(startAddr,true);
            
            if(block!=null){
                // System.out.println(block.address);
                freeBlk.Insert(block.address, block.size, block.size);
                allocBlk.Delete(block);
                return 0;
            }
            return -1;
        }
        
    }

    public void Defragment() {
        if(this.type==1){
            Dictionary merger=new A1List();
            Dictionary current=freeBlk.getFirst();
            while(current!=null){
                merger.Insert(current.address, current.size, current.address);
                current=current.getNext();
            }
            Dictionary node=merger.getFirst();
            if(node==null){return;}
            Dictionary nextnode=node.getNext();
            
            while(node!=null && nextnode!=null){
                if(node.address+node.size==nextnode.address){
                    int[] nodevals={node.address,node.size,node.key};
                    int[] nextnodevals={nextnode.address,nextnode.size,nextnode.key};
                    merger.Delete(nextnode);
                    merger.Delete(node);
                    node=merger.Insert(nodevals[0], nodevals[1]+nextnodevals[1], nodevals[0]);
                    nextnode=node.getNext();
                }
                else{
                    node=nextnode;
                    nextnode=nextnode.getNext();
                }
            }
            this.freeBlk=new A1List();
            Dictionary n=merger.getFirst();
            while(n!=null){
                freeBlk.Insert(n.address, n.size, n.size);
                n=n.getNext();
            }
            return;
        }
        if(this.type==2){
            Dictionary merger=new BSTree();
            Dictionary current=freeBlk.getFirst();
            while(current!=null){
                merger.Insert(current.address, current.size, current.address);
                current=current.getNext();
            }
            Dictionary node=merger.getFirst();
            if(node==null){return;}
            Dictionary nextnode=node.getNext();
            
            while(node!=null && nextnode!=null){
                if(node.address+node.size==nextnode.address){
                    int[] nodevals={node.address,node.size,node.key};
                    int[] nextnodevals={nextnode.address,nextnode.size,nextnode.key};
                    merger.Delete(nextnode);
                    merger.Delete(node);
                    node=merger.Insert(nodevals[0], nodevals[1]+nextnodevals[1], nodevals[0]);
                    nextnode=node.getNext();
                }
                else{
                    node=nextnode;
                    nextnode=nextnode.getNext();
                }
            }
            this.freeBlk=new BSTree();
            Dictionary n=merger.getFirst();
            while(n!=null){
                freeBlk.Insert(n.address, n.size, n.size);
                n=n.getNext();
            }
            return;
        }
        if(this.type==3){
            System.out.println("start");
            Dictionary merger=new AVLTree();
            Dictionary current=freeBlk.getFirst();
            while(current!=null){
                merger.Insert(current.address, current.size, current.address);
                current=current.getNext();
            }
            Dictionary node=merger.getFirst();
            if(node==null){return;}
            Dictionary nextnode=node.getNext();
            
            while(node!=null && nextnode!=null){
                if(node.address+node.size==nextnode.address){
                    int[] nodevals={node.address,node.size,node.key};
                    int[] nextnodevals={nextnode.address,nextnode.size,nextnode.key};
                    merger.Delete(nextnode);
                    merger.Delete(node);
                    node=merger.Insert(nodevals[0], nodevals[1]+nextnodevals[1], nodevals[0]);
                    nextnode=node.getNext();
                }
                else{
                    node=nextnode;
                    nextnode=nextnode.getNext();
                }
            }
            this.freeBlk=new AVLTree();
            Dictionary n=merger.getFirst();
            while(n!=null){
                freeBlk.Insert(n.address, n.size, n.size);
                n=n.getNext();
            }
            return;
        }
    }
}