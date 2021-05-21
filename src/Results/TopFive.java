package Results;

import java.io.File;

public class TopFive {

    public static void main(String[] args) {
        fileReader();
    }
    public static void fileReader (){
        File [] memberFiles = new File("src/Files/membersResults").listFiles();

        for(File currentFile: memberFiles) {

            System.out.println(currentFile.getName());

        }

    }
}
