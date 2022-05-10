import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        String [] commandList;
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
            System.out.println("Command does not exist");
        }
    }
}
