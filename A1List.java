
// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    //INSERT

    public A1List Insert(int address, int size, int key)
    {
        A1List newnode= new A1List(address,size,key);
        if( !this.istrailer()){
            newnode.next=this.next;
            newnode.prev=this;
            this.next.prev=newnode;
            this.next=newnode;
            return newnode;
        }
        else{
            return null;
        }
    }

    //DELETE

    public boolean Delete(Dictionary d) 
    {
        // if(!sanity()){return false;}
        if(d==null){
            return false;
        }
        //For traversing forward
        A1List fwd= this;
        //For traversing backward
        A1List bkwd= this;
        while(fwd!=null && !fwd.istrailer()){
            if(fwd.key==d.key){
                if(fwd.address==d.address && fwd.size==d.size){
                    
                    //If the node found is header or trailer then return false
                    if(fwd.isheader() || fwd.istrailer()){
                        return false;
                    }
                    else{
                        fwd.next.prev=fwd.prev;
                        fwd.prev.next=fwd.next;
                        fwd.next=null;
                        fwd.prev=null;
                        return true;
                    } 
                }
                else{
                    fwd=fwd.next;
                }
            }
            else{fwd=fwd.next;}
        }
        while(bkwd!=null && !bkwd.istrailer()){
            if(bkwd.key==d.key){
                if(bkwd.address==d.address && bkwd.size==d.size){

                    //If the node found is header or trailer then return false
                    if(bkwd.isheader() || bkwd.istrailer()){
                        return false;
                    }
                    else{
                        bkwd.next.prev=bkwd.prev;
                        bkwd.prev.next=bkwd.next;
                        bkwd.next=null;
                        bkwd.prev=null;
                        return true;
                    }
                }
                else{
                    bkwd=bkwd.prev;
                }
            }
            else{bkwd=bkwd.prev;}
        }
        return false;
    } 

    //FIND

    public A1List Find(int k, boolean exact)
    { 
        if (exact){
            A1List current=this.getFirst();
            if(current!=null){
                while(current!=null && !current.istrailer()){
                    if(current.key==k){
                        return current;
                    }
                    else{
                        current=current.next;
                    }
                }
                return null;
            }
            else{return null;}
        }
        else{
            A1List current=this.getFirst();
            if(current!=null){
                while(current!=null && !current.istrailer()){
                    if(current.key>=k){
                        return current;
                    }
                    else{
                        current=current.next;
                    }
                }
                return null;
            }
            else{return null;}
        }
        
    }

    //GET FIRST

    public A1List getFirst()
    {
        A1List header=this;
        while(header.prev!=null){
            header=header.prev;
        }
        if(header.next.istrailer()){
            return null;
        }
        else{
            return header.next;
        }
    }
    
    public A1List getNext() 
    {
        if (!this.next.istrailer()){return this.next;}
        else{return null;}
    }
    
    //SANITY CHECK

    public boolean sanity()
    {
        if(!this.isSentinel()){
            if(this.checknextpointer() && this.checkprevpointer()){
                return (this.checkbackward() && this.checkforward());
            }
            else{return false;}
        }
        else if(this.istrailer()){
            if(this.checkprevpointer()){
                return this.checkbackward();
            }
            else{return false;}
        }
        else if(this.isheader()){
            if(this.checknextpointer()){
                return this.checkforward();
            }
            else{return false;}
        }
        else{return false;}
    }
    private boolean checkbackward(){
        A1List bkwd=this.prev;
        while(bkwd!=null){
            if(bkwd.isheader()){
                return bkwd.checknextpointer();
            }
            else if(bkwd==this){
                return false;
            }
            else{
                boolean resume=(bkwd.checknextpointer() && bkwd.checkprevpointer());
                if(resume){bkwd=bkwd.prev;}
                else{return resume;}
            }
        }
        return true;
    }
    private boolean checkforward(){
        A1List fwd=this.next;
        while(fwd!=null){
            if(fwd.istrailer()){
                return fwd.checkprevpointer();
            }
            else if(fwd==this){
                return false;
            }
            else{
                boolean resume=(fwd.checknextpointer() && fwd.checkprevpointer());
                if(resume){fwd=fwd.next;}
                else{return resume;}
            }
        }
        return true;
    }

    private boolean checknextpointer(){
        if (this.next!=null){
            boolean res=(this.next.prev==this)? true:false;
            return res;
        }
        return false;
    }
    private boolean checkprevpointer(){
        if(this.prev!=null){
            boolean res=(this.prev.next==this)? true:false;
            return res;
        }
        return false;
    }

    //HELPER FUNCTIONS

    private boolean isSentinel(){
        if(this.key==-1 && this.address==-1 && this.size==-1){
            return true;
        }
        else{return false;}
    }

    private boolean isheader(){
        if(this.prev==null && this.isSentinel()){
            return true;
        }
        else{return false;}
    }
    private boolean istrailer(){
        if(this.next==null && this.isSentinel()){
            return true;
        }
        else{return false;}
    }
    private void printlist(){
        A1List start=this.getFirst();
        System.out.print("Header-->");
        while(!start.istrailer()){
            System.out.print("("+start.address+","+ start.size+ ","+start.key+")-->");
            start=start.next;
        }
        System.out.println("Trailer");
    }
    private void printdict(){
        A1List start=this.getFirst();
        System.out.print("Header-->");
        while(!start.istrailer()){
            System.out.print("["+start.address+"->"+(start.address+start.size) +"]-->");
            start=start.next;
        }
        System.out.println("Trailer");
    }
}


