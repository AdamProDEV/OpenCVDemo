package dev.openCvMatcher;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.Hashtable;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/*
* author https://docs.opencv.org/3.4/de/da9/tutorial_template_matching.html
* https://www.google.com/search?q=opencv+matchTemplate+java
*/
public class OpenCvMatcher {
    Boolean use_mask = false;
    Mat img = new Mat(), templ = new Mat();
    Mat mask = new Mat();
    int match_method = 5;

    public void run(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough parameters");
            System.out.println("Program arguments:\n<image_name> <template_name> [<mask_name>]");
            System.exit(-1);
        }
        System.out.println(args[0] + " : " + (new File(args[0]).exists()));
        System.out.println(args[1] + " : " + (new File(args[1]).exists()));
        img = Imgcodecs.imread(args[0], Imgcodecs.IMREAD_COLOR);
        templ = Imgcodecs.imread(args[1], Imgcodecs.IMREAD_COLOR);
        if (args.length > 2) {
            use_mask = true;
            mask = Imgcodecs.imread(args[2], Imgcodecs.IMREAD_COLOR);
        }
        if (img.empty() || templ.empty() || (use_mask && mask.empty())) {
            System.out.println("Can't read one of the images");
            System.exit(-1);
        }
        matchingMethod();
    }

    private void matchingMethod() {
        Mat result = new Mat();
        Mat img_display = new Mat();
        img.copyTo(img_display);
        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        result.create(result_rows, result_cols, CvType.CV_32FC1);
        Boolean method_accepts_mask = (Imgproc.TM_SQDIFF == match_method || match_method == Imgproc.TM_CCORR_NORMED);
        if (use_mask && method_accepts_mask) {
            Imgproc.matchTemplate(img, templ, result, match_method, mask);
        } else {
            Imgproc.matchTemplate(img, templ, result, match_method);
        }

        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        Point matchLoc;
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }
        Imgproc.rectangle(img_display, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
                new Scalar(0, 255, 0, 128), 2, 8, 0);
        saveImage(img_display, "src/test/resources/images/seleniumMatcher/result.png");
    }

    public void saveImage(Mat imageMatrix, String targetPath) {
        Imgcodecs imgcodecs = new Imgcodecs();
        imgcodecs.imwrite(targetPath, imageMatrix);
    }
}

