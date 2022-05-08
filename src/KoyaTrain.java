import java.util.ArrayList;

public class KoyaTrain extends Train{

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

    public void setTicketPrice(double price1,double price2) {
        double [] ticketPrice=new double[]{price1,price2};
        super.setTicketPrice(ticketPrice);
    }

    public void setTicketPrice(double [] ticketPrice) {
        super.setTicketPrice(ticketPrice);
    }

    public int[] getTicketNumber() {
        return super.getTicketNumber();
    }

    public void setTicketNumber(int number1,int number2) {
        int [] ticketNumber=new int[]{number1,number2};
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
        return String.format("[%d] %s: %s [%s]%.2f:%d [%s]%.2f:%d"
                ,number,this.getNumber(),this.getLineNumber()
                ,"1A",this.getTicketPrice()[0],this.getTicketNumber()[0]
                ,"2A",this.getTicketPrice()[1],this.getTicketNumber()[1]);
    }
    public int getTicketIdBySeatId(String seatId){
        if(seatId.equals("1A"))return 0;
        if(seatId.equals("2A"))return 1;
        return -1;
    }
    /**
     * 返回this与koyaTrain的字典序比较结果
     * this>koyaTrain则返回true，
     * 否则返回false
     */
    public boolean greater(KoyaTrain koyaTrain){
        return this.getNumber().compareTo(koyaTrain.getNumber()) > 0;
    }
    public void buyTickets(int seatId,int ticketNum){
        this.getTicketNumber()[seatId]-=ticketNum;
    }
    public void cancelTickets(int seatId,int ticketNum){
        this.getTicketNumber()[seatId]+=ticketNum;
    }
    static ArrayList<KoyaTrain> koyaTrainArray = new ArrayList<>();

    public static void commandAddTrain(String [] commandList){
        if(commandList.length!=7){
            System.out.println("Arguments illegal");
            return;
        }
        if(checkNumber(commandList[1])){
            System.out.println("Train serial illegal");
            return;
        }
        KoyaTrain koyaTrain=new KoyaTrain();
        if(!checkTrain(commandList[1])){
            System.out.println("Train serial duplicate");
            return;
        }
        koyaTrain.setNumber(commandList[1]);
        if(Line.checkLine(commandList[2])){
            System.out.println("Line illegal");
            return;
        }
        Line line=Line.getLineByNumber(commandList[2]);
        if(line.isFull()){
            System.out.println("Line illegal");
            return;
        }
        koyaTrain.setLineNumber(commandList[2]);
        double [] price=new double[2];
        int [] number=new int[2];
        for(int i=0;i<2;i++){
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
        koyaTrain.setTicketPrice(price);
        koyaTrain.setTicketNumber(number);
        addKoyaTrain(koyaTrain);
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
        delKoyaTrain(commandList[1]);
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
        KoyaTrain koyaTrain=getKoyaTrainByNumber(commandList[1]);
        Line line=Line.getLineByNumber(koyaTrain.getLineNumber());
        if(line.checkStation(commandList[2])||line.checkStation(commandList[3])){
            System.out.println("Station does not exist");
            return;
        }
        if(!checkSeatId(commandList[4])){
            System.out.println("Seat does not match");
            return;
        }
        System.out.println(koyaTrain.checkTicket(
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
        for (KoyaTrain koyaTrain : koyaTrainArray) {
            if (number.equals(koyaTrain.getNumber())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 向koyaTrainArray中添加新的KoyaTrain，
     * 同时保证koyaTrainArray中火车按字典序升序
     */
    public static void addKoyaTrain(KoyaTrain koyaTrain){
        KoyaTrain train;
        for(int i = 0; i< koyaTrainArray.size(); i++){
            train= koyaTrainArray.get(i);
            if(train.greater(koyaTrain)){
                koyaTrainArray.add(i,koyaTrain);
                return;
            }
        }
        koyaTrainArray.add(koyaTrain);
    }

    public static void delKoyaTrain(String number){
        KoyaTrain train;
        for(int i = 0; i< koyaTrainArray.size(); i++){
            train= koyaTrainArray.get(i);
            if(number.equals(train.getNumber())){
                koyaTrainArray.remove(i);
                return;
            }
        }
    }

    public static KoyaTrain getKoyaTrainByNumber(String number){
        for (KoyaTrain koyaTrain : koyaTrainArray) {
            if (number.equals(koyaTrain.getNumber())) {
                return koyaTrain;
            }
        }
        return null;
    }

    /**
     * 检查席位代号与火车类型是否对应，
     * 如果对应返回true，否则返回false
     */
    public static boolean checkSeatId(String seatId){
        return seatId.equals("1A") || seatId.equals("2A");
    }

    public static int listTrainInfo(int tot){
        for (KoyaTrain koyaTrain : koyaTrainArray) {
            System.out.println(koyaTrain.listTrain(tot++));
        }
        return tot;
    }

    public static int listTrainInfo(String lineNumber,int tot){
        for (KoyaTrain koyaTrain : koyaTrainArray) {
            if (lineNumber.equals(koyaTrain.getLineNumber()))
                System.out.println(koyaTrain.listTrain(tot++));
        }
        return tot;
    }

    public static int countTrain(){
        return koyaTrainArray.size();
    }

    public static int countTrainByLineNumber(String lineNumber){
        int tot=0;
        for (KoyaTrain koyaTrain : koyaTrainArray) {
            if (lineNumber.equals(koyaTrain.getLineNumber()))
                tot++;
        }
        return tot;
    }

    public static void delTrainByLineNumber(String lineNumber){
        for(int i=koyaTrainArray.size()-1;i>=0;i--){
            if(lineNumber.equals(koyaTrainArray.get(i).getLineNumber())){
                koyaTrainArray.remove(i);
            }
        }
    }

    /**
     * 成功买票返回true，否则false
     * @param commandList
     * @return
     */
    public static boolean buyTicket(String [] commandList,boolean negative){
        if(checkTrain(commandList[1])){
            System.out.println("Train does not exist");
            return false;
        }
        KoyaTrain koyaTrain=getKoyaTrainByNumber(commandList[1]);
        String lineNumber= koyaTrain.getLineNumber();
        Line line=Line.getLineByNumber(lineNumber);
        if (line.checkStation(commandList[2]) || line.checkStation(commandList[3])){
            System.out.println("Station does not exist");
            return false;
        }
        int seatId=koyaTrain.getTicketIdBySeatId(commandList[4]);
        if(seatId==-1){
            System.out.println("Seat does not match");
            return false;
        }
        if(negative!=true){
            System.out.println("Cert illegal");
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
        if(ticketNum>koyaTrain.getTicketNumber()[seatId]){
            System.out.println("Ticket does not enough");
            return false;
        }
        koyaTrain.buyTickets(seatId,ticketNum);
        return true;
    }

    public static double getPrice(String trainNumber,String start,String end,String seatId){
        KoyaTrain koyaTrain=KoyaTrain.getKoyaTrainByNumber(trainNumber);
        Line line=Line.getLineByNumber(koyaTrain.getLineNumber());
        return line.getDistance(start,end)
                *koyaTrain.getTicketPrice()[
                koyaTrain.getTicketIdBySeatId(seatId)
                ];
    }

    public static void cancelTicket(String trainNumber,String seatId,int number){
        KoyaTrain koyaTrain=getKoyaTrainByNumber(trainNumber);
        koyaTrain.cancelTickets(koyaTrain.getTicketIdBySeatId(seatId),number);
    }
}
