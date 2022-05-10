import java.util.ArrayList;


public class User {
    private String name;
    private char sex;
    private int region;
    private int race;
    private int identity;




    private ArrayList<String> trainNumber = new ArrayList<>();
    private ArrayList<String> startStation = new ArrayList<>();
    private ArrayList<String> endStation = new ArrayList<>();
    private ArrayList<String> seatId = new ArrayList<>();
    private ArrayList<Integer> ticketNumber = new ArrayList<>();
    private ArrayList<Character> orderStatus=new ArrayList<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getAadhaar() {
        return String.format("%04d%04d%04d",
                this.getRegion(),
                this.getRace(),
                this.getIdentity()
        );
    }

    public void setAadhaar(String Aadhaar) {
        int number = 0;
        char c;
        for (int i = 0; i < 12; i++) {
            c = Aadhaar.charAt(i);
            number = number * 10 + c - '0';
            if ((i + 1) % 4 == 0) {
                if (i == 3) this.setRegion(number);
                else if (i == 7) this.setRace(number);
                else this.setIdentity(number);
                number = 0;
            }
        }
    }

    /**
     * 打印用户的格式化信息，格式如下:
     * @return Name:name
     * Sex:sex
     * Aadhaar:aadhaar
     */
    public String toString(){
        String res=String.format("Name:%s\nSex:%c\nAadhaar:%s\n"
                ,this.getName()
                ,this.getSex()
                ,this.getAadhaar()
        );
        return res;
    }

    public boolean judgeByAadhaaar(String aadhaar){
        if(aadhaar.equals(String.format("%04d%04d%04d",this.region,this.race,this.identity))){
            return true;
        }
        return false;
    }

    public void buyTicket(String [] commandList){
        this.trainNumber.add(commandList[1]);
        this.startStation.add(commandList[2]);
        this.endStation.add(commandList[3]);
        this.seatId.add(commandList[4]);
        this.ticketNumber.add(Integer.valueOf(commandList[5]));
        this.orderStatus.add('F');
    }

    private static ArrayList<User> userArray = new ArrayList<User>();
    /**
     * 创建用户命令的处理函数
     * @param commandList
     */
    public static void commandAddUser(String [] commandList){
        if(commandList.length!=4){
            System.out.println("Arguments illegal");
            return;
        }
        User newUser=new User();
        if(!checkName(commandList[1])){
            System.out.println("Name illegal");
            return;
        }
        newUser.setName(commandList[1]);
        if(!checkSex(commandList[2])){
            System.out.println("Sex illegal");
            return;
        }
        newUser.setSex(commandList[2].charAt(0));
        if(!checkAadhaar(commandList[3],newUser.getSex())){
            System.out.println("Aadhaar number illegal");
            return;
        }
        newUser.setAadhaar(commandList[3]);
        if(checkAadhaarExists(commandList[3])){
            System.out.println("Aadhaar number exist");
            return;
        }
        User.addUser(newUser);
        System.out.print(newUser.toString());
    }

    public static boolean checkName(String name){
        char c;
        for(int i=0;i<name.length();i++){
            c=name.charAt(i);
            if(c!='_'&&(c<'a'||c>'z')&&(c<'A'||c>'Z'))return false;
        }
        return true;
    }

    public static boolean checkSex(String sex){
        if(sex.length()!=1)return false;
        char c=sex.charAt(0);
        if(c=='F'||c=='M'||c=='O')return true;
        return false;
    }

    public static boolean checkAadhaar(String Aadhaar,char sex){
        if(Aadhaar.length()!=12)return false;
        try{
            if(!checkRegion(getRegionByAadhaar(Aadhaar)))return false;
            if(!checkRace(getRaceByAadhaar(Aadhaar)))return false;
            if(!checkIdentity(getIdentityByAadhaar(Aadhaar),sex)) return false;
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static int getRegionByAadhaar(String Aadhaar){
        int regionNumber=0;
        for(int i=0;i<4;i++){
            regionNumber=regionNumber*10+Aadhaar.charAt(i)-'0';
        }
        return regionNumber;
    }

    public static int getRaceByAadhaar(String Aadhaar){
        int raceNumber=0;
        for(int i=4;i<8;i++){
            raceNumber=raceNumber*10+Aadhaar.charAt(i)-'0';
        }
        return raceNumber;
    }

    public static int getIdentityByAadhaar(String Aadhaar){
        int identityNumber=0;
        for(int i=8;i<12;i++){
            identityNumber=identityNumber*10+Aadhaar.charAt(i)-'0';
        }
        return identityNumber;
    }

    public static boolean checkRegion(int region){
        if(1<=region&&region<=1237)return true;
        return false;
    }

    public static boolean checkRace(int race){
        if(20<=race&&race<=460)return true;
        return false;
    }

    public static boolean checkIdentity(int identity,char sex){
        if(identity/10>100)return false;
        int sexNumber=identity%10;
        if(sexNumber==0&&sex=='F')return true;
        if(sexNumber==1&&sex=='M')return true;
        if(sexNumber==2&&sex=='O')return true;
        return false;
    }

    public static boolean checkAadhaarExists(String Aadhaar){
        for(int i=0;i<userArray.size();i++){
            if(Aadhaar.equals(userArray.get(i).getAadhaar()))return true;
        }
        return false;
    }

    public static void addUser(User newUser){
        User.userArray.add(newUser);
    }

    public static int commandLogin(String [] commandList,int login){
        if(commandList.length!=3){
            System.out.println("Arguments illegal");
            return login;
        }
        if(login!=-1){
            System.out.println("You have logged in");
            return login;
        }
        User user=null;
        int nowUser=-1;
        for(int i=0;i<userArray.size();i++){
            user=userArray.get(i);
            if(user.judgeByAadhaaar(commandList[1])){
                nowUser=i;
                break;
            }
        }
        if(nowUser==-1){
            System.out.println("User does not exist");
            return login;
        }
        if(!user.getName().equals(commandList[2])){
            System.out.println("Wrong name");
            return login;
        }
        System.out.println("Login success");
        return nowUser;
    }

    public static int commandLogout(String [] commandList,int login){
        if(commandList.length!=1){
            System.out.println("Arguments illegal");
            return login;
        }
        if(login==-1){
            System.out.println("No user has logged in");
            return login;
        }
        System.out.println("Logout success");
        return -1;
    }

    public static void commandBuyTicket(String [] commandList,int login){
        if(commandList.length!=6){
            System.out.println("Arguments illegal");
            return;
        }
        if(login==-1){
            System.out.println("Please login first");
            return;
        }
        User user=userArray.get(login);
        if(!Train.buyTicket(commandList)){
            return;
        }
        user.buyTicket(commandList);
        System.out.println("Thanks for your order");
    }

    public static void commandListOrder(String [] commandList,int login){
        if(commandList.length!=1){
            System.out.println("Arguments illegal");
            return;
        }
        if(login==-1){
            System.out.println("Please login first");
            return;
        }
        User user=userArray.get(login);
        if(user.trainNumber.size()==0){
            System.out.println("No order");
            return;
        }
        for(int i=user.trainNumber.size()-1;i>=0;i--){
            System.out.println(
                    String.format("[%s: %s->%s] seat:%s num:%d price:%.2f"
                    ,user.trainNumber.get(i),user.startStation.get(i),user.endStation.get(i)
                    ,user.seatId.get(i),user.ticketNumber.get(i)
                    ,user.ticketNumber.get(i)*Train.getPrice(user.trainNumber.get(i),user.startStation.get(i),user.endStation.get(i),user.seatId.get(i)))
            );
        }
    }


}
