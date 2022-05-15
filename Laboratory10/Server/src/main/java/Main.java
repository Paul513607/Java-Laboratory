import algorithms.MaximumFlowAlgorithm;
import algorithms.ProprietiesAnalyzer;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import datasource.EntityManagerFactoryCreator;
import model.User;
import model.UserFriendship;
import model.UserGraph;
import modelupload.SftpHandler;
import repositories.UserRepository;
import server.Server;
import modelupload.TestSVGGen;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Main {
    public void testSVG() {
        TestSVGGen testSVGGen = new TestSVGGen();
        testSVGGen.createSVG();
    }

    public void testSftpUpload() {
        SftpHandler sftpHandler = new SftpHandler();
        try {
            sftpHandler.transferFile("src/main/resources/svg-gen/SVGGen1.svg");
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }

    public void findMaximumFlow(String nameSource, String nameDest) {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        UserRepository userRepo = new UserRepository(em);
        List<User> userList = userRepo.findAll();

        Set<User> users = new HashSet<>();
        Set<UserFriendship> friendships = new HashSet<>();

        for (User user : userList) {
            users.add(user);
            for (User friend : user.getUserFriends()) {
                friendships.add(new UserFriendship(user, friend));
                friendships.add(new UserFriendship(friend, user));
            }
        }

        Optional<User> userSource = userRepo.findByName(nameSource);
        Optional<User> userDest = userRepo.findByName(nameDest);

        if (userSource.isEmpty() || userDest.isEmpty()) {
            System.out.println("User source/dest not found!");
            return;
        }

        UserGraph graph = new UserGraph(users, friendships);
        MaximumFlowAlgorithm alg = new MaximumFlowAlgorithm(graph, userSource.get(), userDest.get());
        alg.runAlgorithm();

        alg.printSolution();

        em.getTransaction().commit();
        em.close();
    }

    public static void main(String[] args) {
        Main main = new Main();

        // main.testSVG();
        // main.testSftpUpload();
        main.findMaximumFlow("paul", "mary");

        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
