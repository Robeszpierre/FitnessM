package FintessM;

public class DailyTicket {
    private String type;
    private int price;

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
                    break;
                case SOLARIUM:
                    this.price=400;
                    break;
                case SAUNA:
                    this.price=900;
                    break;
                case COMBINED:
                    this.price=1500;
                    break;
                case SALTROOM:
                    this.price=500;
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
}
