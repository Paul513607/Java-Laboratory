package svgapp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Color;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.IOException;

import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.dom.GenericDOMImplementation;

import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;

public class TestSVGGen {

    public void paint(Graphics2D g2d) {
        g2d.setPaint(Color.red);
        g2d.fill(new Rectangle(10, 10, 100, 100));
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