
// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if(blockSize<=0){
            return -1;
        }
        else{
            Dictionary block=freeBlk.Find(blockSize,false);
            if (block==null){return -1;}
            else{
                if(block.size>blockSize){
                    freeBlk.Delete(block);
                    freeBlk.Insert(block.address+blockSize,block.size-blockSize, block.size-blockSize);
                    allocBlk.Insert(block.address, blockSize, block.address); 
                    return block.address;
                }
                else{
                    freeBlk.Delete(block);
                    allocBlk.Insert(block.address, block.size, block.address);
                    return block.address; 
                }
            }
        }  
    } 
    
    public int Free(int startAddr) {
        if(startAddr<0){
            return -1;
        }
        else{
            Dictionary block= allocBlk.Find(startAddr,true);
            if(block!=null){
                freeBlk.Insert(block.address, block.size, block.size);
                block.Delete(block);
                return 0;
            }
            return -1;
        }
        
    }
}