abstract public class Train {
    private String number;
    private String lineNumber;
    private double [] ticketPrice;
    private int [] ticketNumber;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public double[] getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double[] ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int[] getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int[] ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    abstract public String checkTicket(String start,String end,String seadId,int diatance);
    abstract public String listTrain(int number);
    abstract public int getTicketIdBySeatId(String seatId);

    /**
     * 添加列车命令的处理函数
     */
    public static void commandAddTrain(String [] commandList){
        try{
            if(commandList[1].charAt(0)=='0'){
                NormalTrain.commandAddTrain(commandList);
            }else if(commandList[1].charAt(0)=='G'){
                GatimaanTrain.commandAddTrain(commandList);
            }else if(commandList[1].charAt(0)=='K'){
                KoyaTrain.commandAddTrain(commandList);
            }else{
                System.out.println("Train serial illegal");
            }
        }catch (Exception e){
            System.out.println("Arguments illegal");
        }
    }

    /**
     * 删除列车命令的处理函数
     */
    public static void commandDelTrain(String [] commandList){
        try{
            if(commandList[1].charAt(0)=='0'){
                NormalTrain.commandDelTrain(commandList);
            }else if(commandList[1].charAt(0)=='G'){
                GatimaanTrain.commandDelTrain(commandList);
            }else if(commandList[1].charAt(0)=='K'){
                KoyaTrain.commandDelTrain(commandList);
            }else{
                System.out.println("Train serial illegal");
            }
        }catch (Exception e){
            System.out.println("Arguments illegal");
        }
    }

    /**
     * 查询火车票务信息命令的处理函数
     */
    public static void commandCheckTicket(String [] commandList){
        try{
            if(commandList[1].charAt(0)=='0'){
                NormalTrain.commandCheckTicket(commandList);
            }else if(commandList[1].charAt(0)=='G'){
                GatimaanTrain.commandCheckTicket(commandList);
            }else if(commandList[1].charAt(0)=='K'){
                KoyaTrain.commandCheckTicket(commandList);
            }else{
                System.out.println("Train serial illegal");
            }
        }catch (Exception e){
            System.out.println("Arguments illegal");
        }
    }

    /**
     * 列出火车信息命令的处理函数
     */
    public static void commandListTrain(String [] commandList){
        if(commandList.length>2){
            System.out.println("Arguments illegal");
            return;
        }
        if(commandList.length==1){
            if(countTrain()==0){
                System.out.println("No Trains");
                return;
            }
            listTrainInfo();
        }else{
            if(Line.checkLine(commandList[1])){
                System.out.println("Line does not exist");
                return;
            }
            if(countTrainByLineNumber(commandList[1])==0){
                System.out.println("No Trains");
                return;
            }
            listTrainInfo(commandList[1]);
        }
    }


    public static int listTrainInfo(){
        int tot=1;
        tot=KoyaTrain.listTrainInfo(tot);
        tot=GatimaanTrain.listTrainInfo(tot);
        tot=NormalTrain.listTrainInfo(tot);
        return tot;
    }
    public static int listTrainInfo(String lineNumber){
        int tot=1;
        tot=KoyaTrain.listTrainInfo(lineNumber,tot);
        tot=GatimaanTrain.listTrainInfo(lineNumber,tot);
        tot=NormalTrain.listTrainInfo(lineNumber,tot);
        return tot;
    }

    /**
     * 获得现有火车数量
     */
    public static int countTrain(){
        int tot=0;
        tot+=KoyaTrain.countTrain();
        tot+=GatimaanTrain.countTrain();
        tot+=NormalTrain.countTrain();
        return tot;
    }

    /**
     * 获得在对应线路上的火车数量
     */
    public static int countTrainByLineNumber(String lineNumber){
        int tot=0;
        tot+=KoyaTrain.countTrainByLineNumber(lineNumber);
        tot+=GatimaanTrain.countTrainByLineNumber(lineNumber);
        tot+=NormalTrain.countTrainByLineNumber(lineNumber);
        return tot;
    }

    public static void delTrainByLineNumber(String lineNumber){
        KoyaTrain.delTrainByLineNumber(lineNumber);
        GatimaanTrain.delTrainByLineNumber(lineNumber);
        NormalTrain.delTrainByLineNumber(lineNumber);
    }

    /**
     * 检查列车车次后四位是否符合规范，
     * 符合规范返回true，不符合返回false
     */
    public static boolean checkNumber(String number){
        if(number.length()!=5)return true;
        char c;
        for(int i=1;i<5;i++){
            c=number.charAt(i);
            if(c<'0'||c>'9'){
                return true;
            }
        }
        return false;
    }

    public static int getTrainTypeByNumber(String trainNumber){
        char x=trainNumber.charAt(0);
        switch (x){
            case '0':
                return 1;
            case 'G':
                return 2;
            case 'K':
                return 3;
            default:
                return -1;
        }
    }

    /**
     * 成功买票返回true，否则false
     * @param commandList
     * @return
     */
    public static boolean buyTicket(String [] commandList){
        switch (getTrainTypeByNumber(commandList[1])){
            case 1:
                return NormalTrain.buyTicket(commandList);
            case 2:
                return GatimaanTrain.buyTicket(commandList);
            case 3:
                return KoyaTrain.buyTicket(commandList);
            default:
                System.out.println("Train does not exist");
                return false;
        }
    }

    public static double getPrice(String trainNumber,String start,String end,String seatId){
        switch (getTrainTypeByNumber(trainNumber)){
            case 1:
                return NormalTrain.getPrice(trainNumber,start,end,seatId);
            case 2:
                return GatimaanTrain.getPrice(trainNumber,start,end,seatId);
            case 3:
                return KoyaTrain.getPrice(trainNumber,start,end,seatId);
        }
        return 0;
    }

    public static void cancelTicket(String trainNumber,String seatId,int number){
        switch (getTrainTypeByNumber(trainNumber)){
            case 1:
                NormalTrain.cancelTicket(trainNumber,seatId,number);
                break;
            case 2:
                GatimaanTrain.cancelTicket(trainNumber,seatId,number);
                break;
            case 3:
                KoyaTrain.cancelTicket(trainNumber,seatId,number);
                break;
        }
    }
}
