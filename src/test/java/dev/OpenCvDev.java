package dev;

import org.junit.jupiter.api.Test;
import org.kushan.opencv.ImageVerification;
import org.kushan.opencv.*;

import java.io.IOException;

/*
* https://github.com/tobyqin/opencv-automation-demo
* */

public class OpenCvDev {

    @Test
    void openCvDevTest() throws IOException {

        ImageVerification iv = new ImageVerification();

        ret =  iv.SendImageCompare("http://www.imperial.ac.uk/ImageCropToolT4/imageTool/uploaded-images/newseventsimage_1529346275459_mainnews2012_x1.jpg", "");
    }
}
