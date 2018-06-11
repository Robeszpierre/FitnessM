package FintessM;

public class ContiPDF {
    private String type;
    private int leaseNum;
    private String name;
    private int refNum;
    private String sellDate;
    private int price;

    ContiPDF(String t, int l, String n, int r, String s, int p){
        type=t;
        leaseNum=l;
        name=n;
        refNum=r;
        sellDate=s;
        price=p;
    }

    public String getType() {
        return type;
    }

    public int getLeaseNum() {
        return leaseNum;
    }

    public String getName() {
        return name;
    }

    public int getRefNum() {
        return refNum;
    }

    public String getSellDate() {
        return sellDate;
    }

    public int getPrice() {
        return price;
    }
}
