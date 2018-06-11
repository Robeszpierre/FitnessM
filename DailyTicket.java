package FintessM;

public class DailyTicket {
    private String type;
    private int price;
    private int contiDiscount;

    public final String GYM = "kondi";
    public final String SOLARIUM = "szoli (4 perc)";
    public final String SAUNA = "szauna";
    public final String COMBINED = "kondi + szauna";
    public final String SALTROOM = "sószoba";

    DailyTicket(){};

    DailyTicket(String ticketType){
        if (null != ticketType) {
            this.type=ticketType;
            switch (ticketType) {
                case GYM:
                    this.price=1000;
                    this.contiDiscount=500;
                    break;
                case SOLARIUM:
                    this.price=400;
                    break;
                case SAUNA:
                    this.price=900;
                    this.contiDiscount=400;
                    break;
                case COMBINED:
                    this.price=1500;
                    break;
                case SALTROOM:
                    this.price=500;
                    this.contiDiscount=200;
                    break;
            }
        }
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getContiDiscount() {
        return contiDiscount;
    }
}
