public class Record {

    int id;
    String title;
    String author;
    Record next;

    Record(int i, String a, String t, Record r){
        this.id = i;
        this.title = t;
        this.author = a;
        this.next = r;
    }

    public void print(){
        System.out.println(this.title);
        System.out.println(this.author);
//        Record temp = this.next;
//
//        while(temp != null){
//            System.out.println(temp.title);
//            System.out.println(temp.author);
//            temp = temp.next;
//        }
    }

}
