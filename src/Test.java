import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        String [] commandList;
        int state=0,login=-1;
        while (true){
            String str=in.nextLine();
            if(str.equals("QUIT")){
                System.out.println("----- Good Bye! -----");
                break;
            }
            commandList=str.split(" ");
            if(commandList[0].equals("addUser")){
                User.commandAddUser(commandList);
                continue;
            }
            if(commandList[0].equals("lineInfo")){
                Line.commandLineInfo(commandList);
                continue;
            }
            if(commandList[0].equals("listLine")){
                Line.commandListLine(commandList);
                continue;
            }
            if(commandList[0].equals("listTrain")){
                Train.commandListTrain(commandList);
                continue;
            }
            if(commandList[0].equals("login")){
                login=User.commandLogin(commandList,login);
                continue;
            }
            if(commandList[0].equals("logout")){
                login=User.commandLogout(commandList,login);
                continue;
            }
            if(commandList[0].equals("buyTicket")){
                User.commandBuyTicket(commandList,login);
                continue;
            }
            if(commandList[0].equals("listOrder")){
                User.commandListOrder(commandList,login);
                continue;
            }
            if(commandList[0].equals("rechargeBalance")){
                User.commandRechargeBalance(commandList,login);
                continue;
            }
            if(commandList[0].equals("checkBalance")){
                User.commandCheckBalance(commandList,login);
                continue;
            }
            if(commandList[0].equals("importCert")){
                User.commandImportCert(commandList);
                continue;
            }
            if(commandList[0].equals("cancelOrder")){
                User.commandCancelOrder(commandList,login);
                continue;
            }
            if(commandList[0].equals("payOrder")){
                User.commandPayOrder(commandList,login);
                continue;
            }
            if(state==0){
                if(commandList.length==1&&commandList[0].equals("TunakTunakTun")){
                    state=1;
                    System.out.println("DuluDulu");
                    continue;
                }else if(commandList.length==1&&commandList[0].equals("NutKanutKanut")){
                    System.out.println("WanNiBa");
                    continue;
                }
                if(normal(commandList)){
                    continue;
                }
            }else{
                if(commandList.length==1&&commandList[0].equals("NutKanutKanut")){
                    state=0;
                    System.out.println("DaDaDa");
                    continue;
                }else if(commandList.length==1&&commandList[0].equals("TunakTunakTun")){
                    System.out.println("WanNiBa");
                    continue;
                }
                if(admin(commandList)){
                    continue;
                }
            }
            System.out.println("Command does not exist");
        }
    }

    public static boolean admin(String [] commandList){
        if(commandList[0].equals("addLine")){
            Line.commandAddLine(commandList);
            return true;
        }
        if(commandList[0].equals("delLine")){
            Line.commandDelLine(commandList);
            return true;
        }
        if(commandList[0].equals("addStation")){
            Line.commandAddStation(commandList);
            return true;
        }
        if(commandList[0].equals("delStation")){
            Line.commandDelStation(commandList);
            return true;
        }
        if(commandList[0].equals("addTrain")){
            Train.commandAddTrain(commandList);
            return true;
        }
        if(commandList[0].equals("delTrain")){
            Train.commandDelTrain(commandList);
            return true;
        }
        return false;
    }

    public static boolean normal(String [] commandList){
        if(commandList[0].equals("checkTicket")){
            Train.commandCheckTicket(commandList);
            return true;
        }
        return false;
    }
    
}
