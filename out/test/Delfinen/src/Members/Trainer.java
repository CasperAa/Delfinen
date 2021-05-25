package Members;
//@Amanda

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Trainer extends Member {
    protected String discipline;

    //Constructor
    public Trainer(String name, String ID, String birthdate, String memberStatus, String memberGroup, String memberType,
                             String telephoneNo, String email, String startDate, boolean hasPayed, String discipline) {
        super(name, ID, birthdate, memberStatus, memberGroup, memberType,
                telephoneNo, email, startDate, hasPayed);

        this.discipline = discipline;


    }

}
