package FintessM;

public class Income {
    private String name;
    private String income;

    Income(String name, int income){
        if(name.equals("discount")){
            this.name="contis kedvezmény";
        }else {
            this.name = name;
        }
        this.income=Integer.toString(income);
    }

    public String getIncome() {
        return income;
    }

    public String getName() {
        return name;
    }
}
