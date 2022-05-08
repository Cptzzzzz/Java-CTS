import java.util.ArrayList;

public class GatimaanTrain extends Train{

    public String getNumber() {
        return super.getNumber();
    }

    public void setNumber(String number) {
        super.setNumber(number);
    }

    public String getLineNumber() {
        return super.getLineNumber();
    }

    public void setLineNumber(String lineNumber) {
        super.setLineNumber(lineNumber);
    }

    public double[] getTicketPrice() {
        return super.getTicketPrice();
    }

    public void setTicketPrice(double price1,double price2,double price3) {
        double [] ticketPrice=new double[]{price1,price2,price3};
        super.setTicketPrice(ticketPrice);
    }

    public void setTicketPrice(double [] ticketPrice) {
        super.setTicketPrice(ticketPrice);
    }

    public int[] getTicketNumber() {
        return super.getTicketNumber();
    }

    public void setTicketNumber(int number1,int number2,int number3) {
        int [] ticketNumber=new int[]{number1,number2,number3};
        super.setTicketNumber(ticketNumber);
    }

    public void setTicketNumber(int [] ticketNumber){
        super.setTicketNumber(ticketNumber);
    }



    public String checkTicket(String start,String end,String seatId,int distance){

        return String.format("[%s: %s->%s] seat:%s remain:%d distance:%d price:%.2f"
                ,this.getNumber()
                ,start
                ,end
                ,seatId
                ,getTicketNumber()[getTicketIdBySeatId(seatId)]
                ,distance
                ,distance*getTicketPrice()[getTicketIdBySeatId(seatId)]
        );
    }

    public String listTrain(int number){
        return String.format("[%d] %s: %s [%s]%.2f:%d [%s]%.2f:%d [%s]%.2f:%d"
            ,number,this.getNumber(),this.getLineNumber()
            ,"SC",this.getTicketPrice()[0],this.getTicketNumber()[0]
            ,"HC",this.getTicketPrice()[1],this.getTicketNumber()[1]
            ,"SB",this.getTicketPrice()[2],this.getTicketNumber()[2]);
    }

    public int getTicketIdBySeatId(String seatId){
        if(seatId.equals("SC"))return 0;
        if(seatId.equals("HC"))return 1;
        if(seatId.equals("SB"))return 2;
        return -1;
    }
    /**
     * 返回this与gatimaanTrain的字典序比较结果
     * this>gatimaanTrain则返回true，
     * 否则返回false
     */
    public boolean greater(GatimaanTrain gatimaanTrain){
        return this.getNumber().compareTo(gatimaanTrain.getNumber()) > 0;
    }
    public void buyTickets(int seatId,int ticketNum){
        this.getTicketNumber()[seatId]-=ticketNum;
    }
    public void cancelTickets(int seatId,int ticketNum){
        this.getTicketNumber()[seatId]+=ticketNum;
    }
    static ArrayList<GatimaanTrain> gatimaanTrainArray = new ArrayList<>();

    public static void commandAddTrain(String [] commandList){
        if(commandList.length!=9){
            System.out.println("Arguments illegal");
            return;
        }
        if(checkNumber(commandList[1])){
            System.out.println("Train serial illegal");
            return;
        }
        GatimaanTrain gatimaanTrain=new GatimaanTrain();
        if(!checkTrain(commandList[1])){
            System.out.println("Train serial duplicate");
            return;
        }
        gatimaanTrain.setNumber(commandList[1]);
        if(Line.checkLine(commandList[2])){
            System.out.println("Line illegal");
            return;
        }
        Line line=Line.getLineByNumber(commandList[2]);
        if(line.isFull()){
            System.out.println("Line illegal");
            return;
        }
        gatimaanTrain.setLineNumber(commandList[2]);
        double [] price=new double[3];
        int [] number=new int[3];
        for(int i=0;i<3;i++){
            try{
                price[i]=Double.parseDouble(commandList[2*i+3]);
                if(price[i]<=0){
                    System.out.println("Price illegal");
                    return;
                }
            }catch (Exception e){
                System.out.println("Price illegal");
                return;
            }
            try{
                number[i]=Integer.parseInt(commandList[2*i+4]);
                if(number[i]<0){
                    System.out.println("Ticket num illegal");
                    return;
                }
            }catch (Exception e){
                System.out.println("Ticket num illegal");
                return;
            }
        }
        gatimaanTrain.setTicketPrice(price);
        gatimaanTrain.setTicketNumber(number);
        addGatimaanTrain(gatimaanTrain);
        System.out.println("Add Train Success");
    }

    public static void commandDelTrain(String [] commandList) {
        if(commandList.length!=2){
            System.out.println("Arguments illegal");
            return;
        }
        if(checkNumber(commandList[1])){
            System.out.println("Train serial illegal");
            return;
        }
        if(checkTrain(commandList[1])){
            System.out.println("Train does not exist");
            return;
        }
        delGatimaanTrain(commandList[1]);
        System.out.println("Del Train Success");
    }

    public static void commandCheckTicket(String [] commandList){
        if(commandList.length!=5){
            System.out.println("Arguments illegal");
            return;
        }
        if(checkNumber(commandList[1])){
            System.out.println("Train serial illegal");
            return;
        }
        if(checkTrain(commandList[1])){
            System.out.println("Train serial does not exist");
            return;
        }
        GatimaanTrain gatimaanTrain=getGatimaanTrainByNumber(commandList[1]);
        Line line=Line.getLineByNumber(gatimaanTrain.getLineNumber());
        if(line.checkStation(commandList[2])||line.checkStation(commandList[3])){
            System.out.println("Station does not exist");
            return;
        }
        if(!checkSeatId(commandList[4])){
            System.out.println("Seat does not match");
            return;
        }
        System.out.println(gatimaanTrain.checkTicket(
                commandList[2],
                commandList[3],
                commandList[4],
                line.getDistance(commandList[2],commandList[3])
        ));
    }
    /**
     * 检查是否有同名火车，
     * 有返回false，没有返回true
     */
    public static boolean checkTrain(String number){
        for (GatimaanTrain gatimaanTrain : gatimaanTrainArray) {
            if (number.equals(gatimaanTrain.getNumber())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 向gatimaanTrainArray中添加新的GatimaanTrain，
     * 同时保证gatimaanTrainArray中火车按字典序升序
     */
    public static void addGatimaanTrain(GatimaanTrain gatimaanTrain){
        GatimaanTrain train;
        for(int i = 0; i< gatimaanTrainArray.size(); i++){
            train= gatimaanTrainArray.get(i);
            if(train.greater(gatimaanTrain)){
                gatimaanTrainArray.add(i,gatimaanTrain);
                return;
            }
        }
        gatimaanTrainArray.add(gatimaanTrain);
    }

    public static void delGatimaanTrain(String number){
        GatimaanTrain train;
        for(int i = 0; i< gatimaanTrainArray.size(); i++){
            train= gatimaanTrainArray.get(i);
            if(number.equals(train.getNumber())){
                gatimaanTrainArray.remove(i);
                return;
            }
        }
    }


    public static GatimaanTrain getGatimaanTrainByNumber(String number){
        for (GatimaanTrain gatimaanTrain : gatimaanTrainArray) {
            if (number.equals(gatimaanTrain.getNumber())) {
                return gatimaanTrain;
            }
        }
        return null;
    }

    /**
     * 检查席位代号与火车类型是否对应，
     * 如果对应返回true，否则返回false
     */
    public static boolean checkSeatId(String seatId){
        return seatId.equals("SC") || seatId.equals("HC") || seatId.equals("SB");
    }

    public static int listTrainInfo(int tot){
        for (GatimaanTrain gatimaanTrain : gatimaanTrainArray) {
            System.out.println(gatimaanTrain.listTrain(tot++));
        }
        return tot;
    }

    public static int listTrainInfo(String lineNumber,int tot){
        for (GatimaanTrain gatimaanTrain : gatimaanTrainArray) {
            if (lineNumber.equals(gatimaanTrain.getLineNumber()))
                System.out.println(gatimaanTrain.listTrain(tot++));
        }
        return tot;
    }

    public static int countTrain(){
        return gatimaanTrainArray.size();
    }

    public static int countTrainByLineNumber(String lineNumber){
        int tot=0;
        for (GatimaanTrain gatimaanTrain : gatimaanTrainArray) {
            if (lineNumber.equals(gatimaanTrain.getLineNumber()))
                tot++;
        }
        return tot;
    }

    public static void delTrainByLineNumber(String lineNumber){
        for(int i=gatimaanTrainArray.size()-1;i>=0;i--){
            if(lineNumber.equals(gatimaanTrainArray.get(i).getLineNumber())){
                gatimaanTrainArray.remove(i);
            }
        }
    }

    public static boolean buyTicket(String [] commandList){
        if(checkTrain(commandList[1])){
            System.out.println("Train does not exist");
            return false;
        }
        GatimaanTrain gatimaanTrain=getGatimaanTrainByNumber(commandList[1]);
        String lineNumber= gatimaanTrain.getLineNumber();
        Line line=Line.getLineByNumber(lineNumber);
        if (line.checkStation(commandList[2]) || line.checkStation(commandList[3])){
            System.out.println("Station does not exist");
            return false;
        }
        int seatId=gatimaanTrain.getTicketIdBySeatId(commandList[4]);
        if(seatId==-1){
            System.out.println("Seat does not match");
            return false;
        }
        int ticketNum;
        try{
            ticketNum=Integer.valueOf(commandList[5]);
            if(ticketNum<=0){
                throw new Exception("");
            }
        }catch (Exception e){
            System.out.println("Ticket number illegal");
            return false;
        }
        if(ticketNum>gatimaanTrain.getTicketNumber()[seatId]){
            System.out.println("Ticket does not enough");
            return false;
        }
        gatimaanTrain.buyTickets(seatId,ticketNum);
        return true;
    }

    public static double getPrice(String trainNumber,String start,String end,String seatId){
        GatimaanTrain gatimaanTrain=GatimaanTrain.getGatimaanTrainByNumber(trainNumber);
        Line line=Line.getLineByNumber(gatimaanTrain.getLineNumber());
        return line.getDistance(start,end)
                *gatimaanTrain.getTicketPrice()[
                gatimaanTrain.getTicketIdBySeatId(seatId)
                ];
    }

    public static void cancelTicket(String trainNumber,String seatId,int number){
        GatimaanTrain gatimaanTrain=getGatimaanTrainByNumber(trainNumber);
        gatimaanTrain.cancelTickets(gatimaanTrain.getTicketIdBySeatId(seatId),number);
    }
}
