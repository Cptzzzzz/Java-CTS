import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GetTestData {
    public static void bufferedWriterMethod(String filepath, String content) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath))) {
            bufferedWriter.write(content);
        }
    }
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        String [] commandList;
        String allCommand="";
        while (true){
            String str=in.nextLine();
            allCommand+=str+"\n";
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
        String filepath=String.format("%d.txt",(int)(Math.random()*1000));
        try{
            bufferedWriterMethod(filepath,allCommand);
        }catch(Exception e){

        }
    }
}