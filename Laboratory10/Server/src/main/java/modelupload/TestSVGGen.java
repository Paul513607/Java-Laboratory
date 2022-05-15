package modelupload;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;

import javax.persistence.EntityManager;

import datasource.EntityManagerFactoryCreator;
import model.User;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import util.Point;

import java.awt.Graphics2D;
import java.awt.Color;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.List;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import repositories.UserRepository;

public class TestSVGGen {
    private final double CIRCLE_DIAMETER = 10;
    private final double CIRCLE_RADIUS = CIRCLE_DIAMETER / 2;
    private List<User> userList = new ArrayList<>();
    private Map<User, Point> usersCoords = new HashMap<>();

    private boolean isClose(Point myPoint) {
        for (Point point : usersCoords.values()) {
            if (Math.abs(point.getX() - myPoint.getX()) <= CIRCLE_DIAMETER ||
                    Math.abs(point.getY() - myPoint.getY()) <= CIRCLE_DIAMETER)
                return true;
        }
        return false;
    }

    private void setUp(Graphics2D g2d) {
        EntityManager em = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        UserRepository userRepo = new UserRepository(em);
        userList = userRepo.findAll();

        Random random = new Random();

        for (User user : userList) {
            Point userPoint;
            do {
                double x = random.nextDouble(10, 150);
                double y = random.nextDouble(10, 150);
                userPoint = new Point(x, y);
            } while (isClose(userPoint));
            g2d.setPaint(Color.blue);
            g2d.draw(new Ellipse2D.Double(userPoint.getX(), userPoint.getY(), CIRCLE_DIAMETER, CIRCLE_DIAMETER));
            g2d.drawString(user.getName(), ((int) (userPoint.getX())),
                    ((int) (userPoint.getY())));
            usersCoords.put(user, userPoint);
        }

        for (User user : userList) {
            Point userPoint = usersCoords.get(user);
            for (User friend : user.getUserFriends()) {
                Point friendPoint = usersCoords.get(friend);
                g2d.setPaint(Color.green);
                g2d.draw(new Line2D.Double(userPoint.getX() + CIRCLE_RADIUS, userPoint.getY() + CIRCLE_RADIUS,
                            friendPoint.getX() + CIRCLE_RADIUS, friendPoint.getY() + CIRCLE_RADIUS));
            }
        }

        em.getTransaction().commit();
        em.close();
    }


    public void paint(Graphics2D g2d) {
        setUp(g2d);
    }

    public void createSVG() {
        // Get a DOMImplementation.
        DOMImplementation domImpl =
                GenericDOMImplementation.getDOMImplementation();

        // Create an instance of org.w3c.dom.Document.
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);

        // Create an instance of the SVG Generator.
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        // Ask the test to render into the SVG Graphics2D implementation.
        TestSVGGen test = new TestSVGGen();
        test.paint(svgGenerator);

        // Finally, stream out SVG to the standard output using
        // UTF-8 encoding.
        boolean useCSS = true; // we want to use CSS style attributes
        Writer out = null;
        File file = new File("src/main/resources/svg-gen/SVGGen1.svg");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            out = new OutputStreamWriter(fileOutputStream, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            svgGenerator.stream(out, useCSS);
        } catch (SVGGraphics2DIOException e) {
            e.printStackTrace();
        }
    }
}