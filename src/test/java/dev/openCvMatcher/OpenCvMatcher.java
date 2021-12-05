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

    public Point match(String screenshotPath, String partialImagePath) {

        OpenCV.loadShared();
        Mat img = Imgcodecs.imread(screenshotPath);
        Mat templ = Imgcodecs.imread(partialImagePath);

        Mat result = new Mat();

//        int result_cols = img.cols() - templ.cols() + 1;
//        int result_rows = img.rows() - templ.rows() + 1;

//        result.create(result_rows, result_cols, CvType.CV_32FC1);

        Imgproc.matchTemplate(img, templ, result, Imgproc.TM_CCORR_NORMED);

//        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        Point matchLoc = mmr.maxLoc;

        System.out.println("Match result = " + mmr.maxVal);

        Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()),
                new Scalar(0, 255, 0, 128), 2, 8, 0);
        Imgcodecs.imwrite( "src/test/resources/images/seleniumMatcher/result.png", img);

        return new Point(matchLoc.x + (templ.cols() / 2), matchLoc.y + (templ.rows() / 2));
    }


}

