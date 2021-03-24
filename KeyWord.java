public class KeyWord {
    String keyword;
    Record record;
    KeyWord nextKeyword = null;
    public KeyWord() {
    }
    public KeyWord(String keyword, Record record){
        this.keyword = keyword;
        this.record = record;
    }

    public String getKeyword() {
        return keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public Record getRecord() {
        return record;
    }
    public void setRecord(Record record) {
        this.record = record;
    }
    public KeyWord getNextKeyword(){return nextKeyword; }
    public void setNextKeyword(KeyWord nextKeyword) { this.nextKeyword = nextKeyword; }
}
