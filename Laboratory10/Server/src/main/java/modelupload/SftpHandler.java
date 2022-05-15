package modelupload;

import com.jcraft.jsch.*;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class SftpHandler {
    public void transferFile(String path) throws JSchException, SftpException {
        File file = new File(path);

        // setUp
        JSch jSch = new JSch();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input user: ");
        String user = scanner.nextLine();
        String host = "localhost";
        int port = 22;
        System.out.print("Input password: ");
        String password = scanner.nextLine();

        // set config
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        // open the session
        Session session = jSch.getSession(user, host, port);
        session.setPassword(password);
        session.setConfig(config);
        session.connect();

        // Start a SFTP channel
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp chn = (ChannelSftp) channel;

        String home = chn.getHome();
        String testPath = home + "/test.svg";
        try {
            File testFile = new File(testPath);
            testFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        chn.put(path, testPath);
    }
}
