public class Index {
    //Variables
    int MAX_KEY_WORD_COUNT;
    KeyWord keyWordArrayLinearProbe[];
    KeyWord keyWordArrayQuadraticProbe[];
    KeyWord keyWordArrayDoubleHashing[];
    KeyWord keyWordArrayChaining[];
    int currentCount = 0;

    //Constructor
    public Index(int MAX_KEY_WORD_COUNT){
        this.keyWordArrayLinearProbe = new KeyWord[MAX_KEY_WORD_COUNT];
        this.keyWordArrayQuadraticProbe = new KeyWord[MAX_KEY_WORD_COUNT];
        this.keyWordArrayDoubleHashing = new KeyWord[MAX_KEY_WORD_COUNT];
        this.keyWordArrayChaining = new KeyWord[MAX_KEY_WORD_COUNT];
        this.MAX_KEY_WORD_COUNT = MAX_KEY_WORD_COUNT;
    }

    //Java Bean Functions
    public int getMAX_KEY_WORD_COUNT() { return MAX_KEY_WORD_COUNT; }
    public void setMAX_KEY_WORD_COUNT(int MAX_KEY_WORD_COUNT) { this.MAX_KEY_WORD_COUNT = MAX_KEY_WORD_COUNT; }
    public int getCurrentCount() { return currentCount; }
    public void setCurrentCount(int currentCount) { this.currentCount = currentCount; }
    public KeyWord[] getKeyWordArrayLinearProbe() { return keyWordArrayLinearProbe; }
    public KeyWord[] getKeyWordArrayQuadraticProbe() { return keyWordArrayQuadraticProbe; }
    public KeyWord[] getKeyWordArrayDoubleHashing() { return keyWordArrayDoubleHashing; }
    public KeyWord[] getKeyWordArrayChaining() { return keyWordArrayChaining; }
    public void setKeyWordArrayLinearProbe(KeyWord[] keyWordArrayLinearProbe) { this.keyWordArrayLinearProbe = keyWordArrayLinearProbe; }

    //Function to populate the index
    public void populateIndex(bst bst) {
       recursivePopulate(bst.root);
    }
    //Helper function to populate index
    public void recursivePopulate(bst.Node t){
        if(t!=null){
            recursivePopulate(t.l);
            //Create separate Records and KeyWords
            Record recordLinear = new Record(t.record.id, t.record.author, t.record.title, t.record.next);
            KeyWord tempKWLinear = new KeyWord(t.keyword, recordLinear);
            Record recordQuadratic = new Record(t.record.id, t.record.author, t.record.title, t.record.next);
            KeyWord tempKWQuadratic = new KeyWord(t.keyword, recordQuadratic);
            Record recordDoubleHashing = new Record(t.record.id, t.record.author, t.record.title, t.record.next);
            KeyWord tempKWDoubleHashing = new KeyWord(t.keyword, recordDoubleHashing);
            Record recordChaining = new Record(t.record.id, t.record.author, t.record.title, t.record.next);
            KeyWord tempKWChaining = new KeyWord(t.keyword, recordChaining);
            //Insert into various indexes
            insertToHashLinearProbe(sumString(tempKWLinear.keyword) % MAX_KEY_WORD_COUNT, tempKWLinear);
            insertToHashQuadraticProbing(sumString(tempKWQuadratic.keyword) % MAX_KEY_WORD_COUNT, tempKWQuadratic, 0);
            insertToHashDoubleHashing(sumString(tempKWDoubleHashing.keyword) % MAX_KEY_WORD_COUNT, tempKWDoubleHashing, 0);
            insertToHashChaining(sumString(tempKWChaining.keyword) % MAX_KEY_WORD_COUNT, tempKWChaining);
            //Update index count
            currentCount++;
            recursivePopulate(t.r);
        }
    }
    //Function to insert to hash via quadratic probing
    public void insertToHashQuadraticProbing(int hashPosition, KeyWord kw, int attempts){
        if(keyWordArrayQuadraticProbe[hashPosition] == null){ keyWordArrayQuadraticProbe[hashPosition] = kw;}
        else{
            kw.record.author = "*" + kw.record.author;
            attempts++;
            hashPosition =  (hashPosition + (int) Math.pow(attempts,2)) % MAX_KEY_WORD_COUNT;
            insertToHashQuadraticProbing(hashPosition, kw, attempts);
        }
    }
    //Function to insert to hash via double hashing
    public void insertToHashDoubleHashing(int hashPosition, KeyWord kw, int attempts){
        if(keyWordArrayDoubleHashing[hashPosition] == null){ keyWordArrayDoubleHashing[hashPosition] = kw;}
        else{
            kw.record.author = "$" + kw.record.author;
            attempts++;
            hashPosition = (hashPosition + attempts * (2 - (hashPosition % 2))) % MAX_KEY_WORD_COUNT;
            insertToHashDoubleHashing(hashPosition, kw, attempts);
        }
    }
    //Function to insert to hash via chaining
    public void insertToHashChaining(int hashPosition, KeyWord kw){
        if(keyWordArrayChaining[hashPosition] == null){ keyWordArrayChaining[hashPosition] = kw;}
        else{
            KeyWord temp = keyWordArrayChaining[hashPosition];
            while(temp.nextKeyword != null){
                temp = temp.nextKeyword;
            }
            temp.nextKeyword = kw;
        }
    }
    //Function to insert to hash via linear probing
    public void insertToHashLinearProbe(int hashPosition, KeyWord kw){
        if(keyWordArrayLinearProbe[hashPosition] == null){ keyWordArrayLinearProbe[hashPosition] = kw;}
        else {
            kw.record.author = "^" + kw.record.author;
            hashPosition = (hashPosition + 1) % MAX_KEY_WORD_COUNT;
            insertToHashLinearProbe(hashPosition, kw);
        }
    }
    //Function to sum the string
    public int sumString(String s){
        int sum = 0 ;
        for(int i = 0; i< s.length(); i++){
            sum += s.charAt(i);
        }
        return sum;
    }
    //Function to print open addressing
    public void printIndex(KeyWord[] keyWordArray){
        for(int i = 0; i < keyWordArray.length; i++){
            if(keyWordArray[i] != null) {
                System.out.print("\tIndex " + i + " ");
                System.out.print(keyWordArray[i].keyword + " - ");
                Record tempRecord = keyWordArray[i].record;
                while (tempRecord != null) {
                    System.out.print(tempRecord.author + ", ");
                    System.out.print(tempRecord.title + " ---> ");
                    tempRecord = tempRecord.next;
                }
                System.out.println();
            }
        }
    }

    //Function to print chaining
    public void printIndexChaining(KeyWord[] keyWordArrayChaining){
        for(int i = 0; i < keyWordArrayChaining.length; i++){
            if(keyWordArrayChaining[i] != null) {
                System.out.print("\tIndex " + i + " ");
                System.out.print(keyWordArrayChaining[i].keyword + " - ");
                Record tempRecord = keyWordArrayChaining[i].record;
                while (tempRecord != null) {
                    System.out.print(tempRecord.author + ", ");
                    System.out.print(tempRecord.title + " ---> ");
                    tempRecord = tempRecord.next;
                }
                //Check to see is there is chaining
                KeyWord temp = keyWordArrayChaining[i].nextKeyword;
                while(temp != null){
                    System.out.print(" *****> ");
                    System.out.print("\tIndex " + i + " ");
                    System.out.print(temp.keyword + " - ");
                    Record innertempRecord = temp.record;
                    while (innertempRecord != null) {
                        System.out.print(innertempRecord.author + ", ");
                        System.out.print(innertempRecord.title + " ---> ");
                        innertempRecord = innertempRecord.next;
                    }
                    temp = temp.nextKeyword;
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        test T = new test("datafile.txt");
        Index i = new Index(500);
        i.populateIndex(T.a);
        System.out.println("LINEAR PROBE ");
        i.printIndex(i.getKeyWordArrayLinearProbe());
        System.out.println("LINEAR PROBE " + "\n\n");
        System.out.println("QUADRATIC PROBE ");
        i.printIndex(i.getKeyWordArrayQuadraticProbe());
        System.out.println("QUADRATIC PROBE " + "\n\n");
        System.out.println("DOUBLE HASHING PROBE ");
        i.printIndex(i.getKeyWordArrayDoubleHashing());
        System.out.println("DOUBLE HASHING PROBE " + "\n\n");
        System.out.println("CHAINING");
        i.printIndexChaining(i.getKeyWordArrayChaining());
        System.out.println("CHAINING " + "\n\n");
    }
}
