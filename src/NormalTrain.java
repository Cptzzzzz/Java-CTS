import java.util.ArrayList;

public class NormalTrain extends Train{

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
                ,"CC",this.getTicketPrice()[0],this.getTicketNumber()[0]
                ,"SB",this.getTicketPrice()[1],this.getTicketNumber()[1]
                ,"GG",this.getTicketPrice()[2],this.getTicketNumber()[2]);
    }

    public int getTicketIdBySeatId(String seatId){
        if(seatId.equals("CC"))return 0;
        if(seatId.equals("SB"))return 1;
        if(seatId.equals("GG"))return 2;
        return -1;
    }
    /**
     * 返回this与normalTrain的字典序比较结果
     * this>normalTrain则返回true，
     * 否则返回false
     */
    public boolean greater(NormalTrain normalTrain){
        return this.getNumber().compareTo(normalTrain.getNumber()) > 0;
    }

    public void buyTickets(int seatId,int ticketNum){
        this.getTicketNumber()[seatId]-=ticketNum;
    }
    public void cancelTickets(int seatId,int ticketNum){
        this.getTicketNumber()[seatId]+=ticketNum;
    }
    static ArrayList<NormalTrain> normalTrainArray = new ArrayList<>();
    public static void commandAddTrain(String [] commandList){
        if(commandList.length!=9){
            System.out.println("Arguments illegal");
            return;
        }
        if(checkNumber(commandList[1])){
            System.out.println("Train serial illegal");
            return;
        }
        NormalTrain normalTrain=new NormalTrain();
        if(!checkTrain(commandList[1])){
            System.out.println("Train serial duplicate");
            return;
        }
        normalTrain.setNumber(commandList[1]);
        if(Line.checkLine(commandList[2])){
            System.out.println("Line illegal");
            return;
        }
        Line line=Line.getLineByNumber(commandList[2]);
        if(line.isFull()){
            System.out.println("Line illegal");
            return;
        }
        normalTrain.setLineNumber(commandList[2]);
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
        normalTrain.setTicketPrice(price);
        normalTrain.setTicketNumber(number);
        addNormalTrain(normalTrain);
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
        delNormalTrain(commandList[1]);
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
        NormalTrain normalTrain=getNormalTrainByNumber(commandList[1]);
        Line line=Line.getLineByNumber(normalTrain.getLineNumber());
        if(line.checkStation(commandList[2])||line.checkStation(commandList[3])){
            System.out.println("Station does not exist");
            return;
        }
        if(!checkSeatId(commandList[4])){
            System.out.println("Seat does not match");
            return;
        }
        System.out.println(normalTrain.checkTicket(
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
        for (NormalTrain normalTrain : normalTrainArray) {
            if (number.equals(normalTrain.getNumber())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 向normalTrainArray中添加新的NormalTrain，
     * 同时保证normalTrainArray中火车按字典序升序
     */
    public static void addNormalTrain(NormalTrain normalTrain){
        NormalTrain train;
        for(int i = 0; i< normalTrainArray.size(); i++){
            train= normalTrainArray.get(i);
            if(train.greater(normalTrain)){
                normalTrainArray.add(i,normalTrain);
                return;
            }
        }
        normalTrainArray.add(normalTrain);
    }

    public static void delNormalTrain(String number){
        NormalTrain train;
        for(int i = 0; i< normalTrainArray.size(); i++){
            train= normalTrainArray.get(i);
            if(number.equals(train.getNumber())){
                normalTrainArray.remove(i);
                return;
            }
        }
    }

    public static NormalTrain getNormalTrainByNumber(String number){
        for (NormalTrain normalTrain : normalTrainArray) {
            if (number.equals(normalTrain.getNumber())) {
                return normalTrain;
            }
        }
        return null;
    }

    /**
     * 检查席位代号与火车类型是否对应，
     * 如果对应返回true，否则返回false
     */
    public static boolean checkSeatId(String seatId){
        return seatId.equals("CC") || seatId.equals("SB") || seatId.equals("GG");
    }

    /**
     *根据序号打印火车信息，
     * tot是火车的序号
     */
    public static int listTrainInfo(int tot){
        for (NormalTrain normalTrain : normalTrainArray) {
            System.out.println(normalTrain.listTrain(tot++));
        }
        return tot;
    }

    public static int listTrainInfo(String lineNumber,int tot){
        for (NormalTrain normalTrain : normalTrainArray) {
            if (lineNumber.equals(normalTrain.getLineNumber()))
                System.out.println(normalTrain.listTrain(tot++));
        }
        return tot;
    }

    public static int countTrain(){
        return normalTrainArray.size();
    }

    public static int countTrainByLineNumber(String lineNumber){
        int tot=0;
        for (NormalTrain normalTrain : normalTrainArray) {
            if (lineNumber.equals(normalTrain.getLineNumber()))
                tot++;
        }
        return tot;
    }

    public static void delTrainByLineNumber(String lineNumber){
        for(int i=normalTrainArray.size()-1;i>=0;i--){
            if(lineNumber.equals(normalTrainArray.get(i).getLineNumber())){
                normalTrainArray.remove(i);
            }
        }
    }

    /**
     * 成功买票返回true，否则false
     * @param commandList
     * @return
     */
    public static boolean buyTicket(String [] commandList){
        if(checkTrain(commandList[1])){
            System.out.println("Train does not exist");
            return false;
        }
        NormalTrain normalTrain=getNormalTrainByNumber(commandList[1]);
        String lineNumber= normalTrain.getLineNumber();
        Line line=Line.getLineByNumber(lineNumber);
        if (line.checkStation(commandList[2]) || line.checkStation(commandList[3])){
            System.out.println("Station does not exist");
            return false;
        }
        int seatId=normalTrain.getTicketIdBySeatId(commandList[4]);
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
        if(ticketNum>normalTrain.getTicketNumber()[seatId]){
            System.out.println("Ticket does not enough");
            return false;
        }
        normalTrain.buyTickets(seatId,ticketNum);
        return true;
    }

    public static double getPrice(String trainNumber,String start,String end,String seatId){
        NormalTrain normalTrain=NormalTrain.getNormalTrainByNumber(trainNumber);
        Line line=Line.getLineByNumber(normalTrain.getLineNumber());
        return line.getDistance(start,end)
                *normalTrain.getTicketPrice()[
                        normalTrain.getTicketIdBySeatId(seatId)
                ];
    }

    public static void cancelTicket(String trainNumber,String seatId,int number){
        NormalTrain normalTrain=getNormalTrainByNumber(trainNumber);
        normalTrain.cancelTickets(normalTrain.getTicketIdBySeatId(seatId),number);
    }
}
