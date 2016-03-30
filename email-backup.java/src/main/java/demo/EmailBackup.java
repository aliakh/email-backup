package demo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

final public class EmailBackup {

    public static void main(String[] args) {
        try {
            Flags flags = Configuration.parseFlags(args);
            if (flags != null) {
                String folderName = Names.getCurrentFolderName();
                String zipName = Names.getZipName(folderName, new Date());

                Timer timer1 = new Timer("Zipping");
                new Zipper(zipName, flags, folderName).zip();
                timer1.finish();

                Timer timer2 = new Timer("Sending");
                Sender.send(zipName, flags);
                timer2.finish();
            }
        } catch (IOException | MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
