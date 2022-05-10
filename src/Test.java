import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void bufferedWriterMethod(String filepath, String content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(content);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] commandList;
//        String allCommand = "";
        int state = 0, login = -1;
        while (true) {
            String str = in.nextLine();
//            allCommand += str + "\n";
            if (str.equals("QUIT")) {
                System.out.println("----- Good Bye! -----");
                break;
            }
            commandList = str.split(" ");
            if (commandList[0].equals("addUser")) {
                User.commandAddUser(commandList);
                continue;
            }
            if (commandList[0].equals("lineInfo")) {
                Line.commandLineInfo(commandList);
                continue;
            }
            if (commandList[0].equals("listLine")) {
                Line.commandListLine(commandList);
                continue;
            }
            if (commandList[0].equals("listTrain")) {
                Train.commandListTrain(commandList);
                continue;
            }
            if (commandList[0].equals("login")) {
                login = User.commandLogin(commandList, login);
                continue;
            }
            if (commandList[0].equals("logout")) {
                login = User.commandLogout(commandList, login);
                continue;
            }
            if (commandList[0].equals("buyTicket")) {
                User.commandBuyTicket(commandList, login);
                continue;
            }
            if (commandList[0].equals("listOrder")) {
                User.commandListOrder(commandList, login);
                continue;
            }
            if (commandList[0].equals("rechargeBalance")) {
                User.commandRechargeBalance(commandList, login);
                continue;
            }
            if (commandList[0].equals("checkBalance")) {
                User.commandCheckBalance(commandList, login);
                continue;
            }
            if (commandList[0].equals("importCert")) {
                User.commandImportCert(commandList);
                continue;
            }
            if (commandList[0].equals("cancelOrder")) {
                User.commandCancelOrder(commandList, login);
                continue;
            }
            if (commandList[0].equals("payOrder")) {
                User.commandPayOrder(commandList, login);
                continue;
            }
            if (state == 0) {
                if (commandList.length == 1 && commandList[0].equals("TunakTunakTun")) {
                    state = 1;
                    System.out.println("DuluDulu");
                    continue;
                } else if (commandList.length == 1 && commandList[0].equals("NutKanutKanut")) {
                    System.out.println("WanNiBa");
                    continue;
                }
                if (normal(commandList)) {
                    continue;
                }
            } else {
                if (commandList.length == 1 && commandList[0].equals("NutKanutKanut")) {
                    state = 0;
                    System.out.println("DaDaDa");
                    continue;
                } else if (commandList.length == 1 && commandList[0].equals("TunakTunakTun")) {
                    System.out.println("WanNiBa");
                    continue;
                }
                if (admin(commandList)) {
                    continue;
                }
            }
            System.out.println("Command does not exist");
        }
//        String filepath = String.format("D:\\VsCodeCodes\\java\\OO\\Pat\\CTS-2-Rebuild\\testdata\\CTS-1\\%d.txt", (int) (Math.random() * 1000));
//        try {
//            bufferedWriterMethod(filepath, allCommand);
//        } catch (Exception e) {
//
//        }
    }

    public static boolean admin(String[] commandList) {
        if (commandList[0].equals("addLine")) {
            Line.commandAddLine(commandList);
            return true;
        }
        if (commandList[0].equals("delLine")) {
            Line.commandDelLine(commandList);
            return true;
        }
        if (commandList[0].equals("addStation")) {
            Line.commandAddStation(commandList);
            return true;
        }
        if (commandList[0].equals("delStation")) {
            Line.commandDelStation(commandList);
            return true;
        }
        if (commandList[0].equals("addTrain")) {
            Train.commandAddTrain(commandList);
            return true;
        }
        if (commandList[0].equals("delTrain")) {
            Train.commandDelTrain(commandList);
            return true;
        }
        return false;
    }

    public static boolean normal(String[] commandList) {
        if (commandList[0].equals("checkTicket")) {
            Train.commandCheckTicket(commandList);
            return true;
        }
        return false;
    }

}
