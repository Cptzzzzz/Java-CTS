import java.util.ArrayList;

public class Line {
    private String number;
    private int capacity;
    private ArrayList<Station> stationArray= new ArrayList<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String toString(){
        String res=String.format("%s %d/%d"
                ,this.getNumber()
                ,this.getTrainNumber()
                ,this.getCapacity());
        for (Station station : this.stationArray) {
            res += String.format(" %s:%d", station.getName(), station.getDistance());
        }
        return res;
    }

    public void addStation(Station newStation){
        Station station;
        for(int i=0;i<this.stationArray.size();i++){
            station=this.stationArray.get(i);
            if(station.greater(newStation)){
                this.stationArray.add(i,newStation);
                return;
            }
        }
        this.stationArray.add(newStation);
    }

    public void delStation(String stationName){
        Station station;
        for(int i=0;i<this.stationArray.size();i++){
            station=this.stationArray.get(i);
            if(station.getName().equals(stationName)){
                this.stationArray.remove(i);
                return;
            }
        }
    }

    public boolean greater(Line line){
        return this.getNumber().compareTo(line.getNumber()) > 0;
    }

    /**
     * 返回当前线路上的火车数量
     */
    public int getTrainNumber(){
        return Train.countTrainByLineNumber(this.getNumber());
    }

    /**
     * 返回当前线路是否满载，
     * true代表满载，false代表还可容纳火车
     */
    public boolean isFull(){
        return this.getTrainNumber() >= this.capacity;
    }
    /**
     * 检查这个线路是否有同名站点，
     * 有则返回false，没有返回true
     */
    public boolean checkStation(String stationName){
        for (Station station : this.stationArray) {
            if (stationName.equals(station.getName())) {
                return false;
            }
        }
        return true;
    }

    public int getDistance(String start,String end){
        int startDistance=0,endDistance=0;
        for (Station station : this.stationArray) {
            if (start.equals(station.getName())) {
                startDistance = station.getDistance();
            }
            if (end.equals(station.getName())) {
                endDistance = station.getDistance();
            }
        }
        return startDistance>endDistance?(startDistance-endDistance):(endDistance-startDistance);
    }

    public static ArrayList<Line> lineArray= new ArrayList<>();

    /**
     * 处理addLine命令
     * @param commandList
     */
    public static void commandAddLine(String [] commandList){
        if(commandList.length<=3||commandList.length%2==0){
            System.out.println("Arguments illegal");
            return;
        }
        Line newLine=new Line();
        Station newStation;
        try{
            newLine.setNumber(commandList[1]);
            newLine.setCapacity(Integer.valueOf(commandList[2]));
            int totStation=(commandList.length-3)/2;
            for(int k=1;k<=totStation;k++){
                newStation=new Station(commandList[2*k+1],commandList[2*k+2]);
                if(!newLine.checkStation(newStation.getName())){
                    System.out.println("Station duplicate");
                    return;
                }
                newLine.addStation(newStation);
            }
            if(!checkLine(newLine.getNumber())){
                System.out.println("Line already exists");
                return;
            }
            if(newLine.getCapacity()<=0){
                System.out.println("Capacity illegal");
                return;
            }
            addLine(newLine);
            System.out.println("Add Line success");
        }catch (Exception e){
            System.out.println("Arguments illegal");
        }
    }

    /**
     * 处理delLine命令
     */
    public static void commandDelLine(String [] commandList){
        if(commandList.length!=2){
            System.out.println("Arguments illegal");
            return;
        }
        if(checkLine(commandList[1])){
            System.out.println("Line does not exist");
            return;
        }
        delLine(commandList[1]);
        System.out.println("Del Line success");
        return;
    }

    /**
     * 处理addStation命令
     * @param commandList
     */
    public static void commandAddStation(String [] commandList){
        Station station;
        Line line;
        try{
            if(commandList.length!=4){
                System.out.println("Arguments illegal");
                return;
            }
            if(checkLine(commandList[1])){
                System.out.println("Line does not exist");
                return;
            }
            line=getLineByNumber(commandList[1]);
            station=new Station(commandList[2],commandList[3]);
            if(!line.checkStation(station.getName())){
                System.out.println("Station duplicate");
                return;
            }
            if(station.getDistance()<=0){
                System.out.println("Arguments illegal");
                return;
            }
            line.addStation(station);
            System.out.println("Add Station success");
            return;
        }catch (Exception e){
            System.out.println("Arguments illegal");
            return;
        }
    }

    /**
     * 处理delStation命令
     * @param commandList
     */
    public static void commandDelStation(String [] commandList){
        Line line;
        try{
            if(commandList.length!=3){
                System.out.println("Arguments illegal");
                return;
            }
            if(checkLine(commandList[1])){
                System.out.println("Line does not exist");
                return;
            }
            line=getLineByNumber(commandList[1]);
            if(line.checkStation(commandList[2])){
                System.out.println("Station does not exist");
                return;
            }
            line.delStation(commandList[2]);
            System.out.println("Delete Station success");
            return;
        }catch (Exception e){
            System.out.println("Arguments illegal");
            return;
        }
    }

    /**
     * 处理lineInfo命令
     * @param commandList
     */
    public static void commandLineInfo(String [] commandList){
        Line line;
        try{
            if(commandList.length!=2){
                System.out.println("Arguments illegal");
                return;
            }
            if(checkLine(commandList[1])){
                System.out.println("Line does not exist");
                return;
            }
            line=getLineByNumber(commandList[1]);
            System.out.println(line.toString());
        }catch (Exception e){
            System.out.println("Arguments illegal");
            return;
        }
    }

    /**
     * 处理listLine命令
     * @param commandList
     */
    public static void commandListLine(String [] commandList){
        Line line;
        try{
            if(commandList.length!=1){
                System.out.println("Arguments illegal");
                return;
            }
            if(lineArray.size()>0){
                for(int i=0;i<lineArray.size();i++){
                    line=lineArray.get(i);
                    System.out.println(String.format("[%d] ",i+1)+line.toString());
                }
            }else{
                System.out.println("No Lines");
            }
        }catch (Exception e){
            System.out.println("Arguments illegal");
            return;
        }
    }

    /**
     * 向lineArray中添加Line，同时保证lineArray有序
     * @param newLine
     */
    public static void addLine(Line newLine){
        Line line;
        for(int i=0;i<lineArray.size();i++){
            line=lineArray.get(i);
            if(line.greater(newLine)){
                lineArray.add(i,newLine);
                return;
            }
        }
        lineArray.add(newLine);
        return;
    }

    /**
     * 在lineArray中删除lineNumber对应的line，
     * 以及删除在此路线上的列车
     * @param lineNumber
     */
    public static void delLine(String lineNumber){
        Line line;
        for(int i=0;i<lineArray.size();i++){
            line=lineArray.get(i);
            if(lineNumber.equals(line.getNumber())){
                lineArray.remove(i);
                Train.delTrainByLineNumber(lineNumber);
                return;
            }
        }
    }

    /**
     * 检查是否有同名线路，
     * 如果有则返回false，否则返回true
     * @param lineNumber
     * @return
     */
    public static boolean checkLine(String lineNumber){
        Line line;
        for(int i=0;i<lineArray.size();i++){
            line=lineArray.get(i);
            if(lineNumber.equals(line.getNumber())){
                return false;
            }
        }
        return true;
    }

    /**
     * 返回对应线路编号的线路实例
     * @param lineNumber
     * @return
     */
    public static Line getLineByNumber(String lineNumber){
        Line line=null;
        for(int i=0;i<lineArray.size();i++){
            line=lineArray.get(i);
            if(lineNumber.equals(line.getNumber())){
                return line;
            }
        }
        return line;
    }


}
