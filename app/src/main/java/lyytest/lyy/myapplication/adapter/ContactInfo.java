package lyytest.lyy.myapplication.adapter;

public class ContactInfo {
    protected String ordernum = "小明";
    protected String sendername = "hulk";
    protected String senderphone = "123123123";
    protected String destination ="广州大学华软软件学院";
    protected static final String NAME_PREFIX = "快递员名字： ";
    protected static final String SURNAME_PREFIX = "目的地： ";
    protected static final String EMAIL_PREFIX = "手机号： ";

    public ContactInfo(String ordernum, String sendername, String senderphone,String destination){
        this.ordernum = ordernum;
        this.sendername = sendername;
        this.senderphone =senderphone;
        this.destination=destination;
    }
}
