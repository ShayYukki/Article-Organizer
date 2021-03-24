//Shayling Zhao
//NetID: SXZ190015

public class bst {

    Node root;

    public class Node{

        // These attributes of the Node class will not be sufficient for those attempting the AVL extra credit.
        // You are free to add extra attributes as you see fit, but do not remove attributes given as it will mess with help code.
        String keyword;
        Record record;
        int size;
        Node l;
        Node r;

        private Node(String k){
            // Instantialize a new Node with keyword k.
            keyword = k;

        }

        private void update(Record r){
            //Adds the Record r to linked list of records. Do not have to check if the record is already in the list.
            //Add the Record r to the front of your linked list.
            if (this.record == null) {
                this.record = r;
            }
            else {
               r.next = this.record;
               this.record = r;
            }
        }
    }

    public bst(){
        this.root = null;
    }

    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id, fd.author, fd.title, null);
        //TODO Write a recursive insertion that adds recordToAdd to the list of records for the node associated
        //with keyword. If there is no node, this code should add the node.
        if (root == null) {
            root = new Node(keyword);
            root.update(recordToAdd);
        }
        else {
            helperInsert(keyword, recordToAdd, root);
        }
    }
    private Node helperInsert(String keyword, Record recordToAdd, Node node1) {
        if (node1.keyword.equals(keyword)) { //if node already equals keyword
            node1.update(recordToAdd);
            return node1;
        }
        else if (keyword.compareTo(node1.keyword) > 0){
           if(node1.r != null){
               return helperInsert(keyword, recordToAdd, node1.r);
           }
           else {
               node1.r = new Node(keyword);
               node1.r.update(recordToAdd);
               return node1.r;
           }
        }

        else if(keyword.compareTo(node1.keyword) < 0){
            if(node1.l != null){
                return helperInsert(keyword,recordToAdd, node1.l);
            }
            else{
                node1.l = new Node(keyword);
                node1.l.update(recordToAdd);
                return node1.l;
            }
        }
        return null;
    }

    public boolean contains(String keyword){
        //Recursive function which returns true if a particular keyword exists in the bst
        if (this.root == null) {
            return false;
        }
        else {
            Node temp = helperContains(root, keyword); //Calling helper recursive method
            if(temp == null){
                return false;
            }
            else{
                return true;
            }
        }
    }
    private Node helperContains(Node node1, String keyword) {
        if (node1.keyword.equals(keyword)) { //if node already equals keyword
            return node1;
        }
        if (node1.l != null) { //if the left side of binary tree exists
            if (keyword.compareTo(node1.keyword) < 0) { //traverse down left side of binary tree
                return helperContains(node1.l, keyword); //returning node if found
            }
        }
        if (node1.r != null) { //if the right side of binary tree exists
            if (keyword.compareTo(node1.keyword) > 0) { //traverse down the right side of binary tree
                return helperContains(node1.r, keyword); //returning node if found
            }
        }
        return null;
    }

    public Record get_records(String keyword){
        //TODO Returns the first record for a particular keyword. This record will link to other records
        //If the keyword is not in the bst, it should return null.
        if(root == null){
            return null;
        }
        else{
            return helperContains(root, keyword).record;
        }
    }

    public void delete(String keyword){
        //TODO Write a recursive function which removes the Node with keyword from the binary search tree.
        //You may not use lazy deletion and if the keyword is not in the bst, the function should do nothing.
        root = helperDelete(root, keyword);
    }
    public Node helperDelete(Node node1, String keyword){
        Node parent = null;
        Node current = node1;

        while(current != null && current.keyword.compareTo(keyword)!= 0 ){
            parent = current;
            if(keyword.compareTo(current.keyword)< 0){
                current = parent.l;
            }
            else{
                current = parent.r;
            }
        }
        if(current == null){ //unable to find keyword
            return node1;
        }
        if(current.l == null && current.r == null){ // leaf, no children
            if(current != node1){ //if it's not the intial node
                if(parent.l.equals(current)){
                    parent.l = null;
                }
                else{
                    parent.r = null;
                }
            }
            else{ // if it's just the initial node
                node1 = null;
            }
        }
        else if(current.l != null && current.r != null ){ //has both children
            Node replacement = minimumKey(current.r); // the replacement node
            helperDelete(replacement,keyword); //delete the replacement node recursively
            current = replacement;  //replace current with the replacement.
        }
        else{   //has 1 child
            if(current.l != null){
                if(parent.l.equals(current)){
                    parent.l = current.l;
                }
                else{
                    parent.r = current.l;
                }
            }
            else if(current.r != null){
                if(parent.l.equals(current)){
                    parent.l = current.r;
                }
                else{
                    parent.r = current.r;
                }
            }
            else{
                node1 = null;
            }
        }

        return node1;
    }

    public Node minimumKey(Node node1){
        while(node1.l != null){
            node1 = node1.l;
        }
        return node1;
    }
    public void print(){
        print(root);
    }

    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        }
    }




    public static void main(String[] args) {


    }
}
