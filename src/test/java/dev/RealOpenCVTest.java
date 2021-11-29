package dev;

import nu.pattern.OpenCV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class RealOpenCVTest {

    @BeforeEach
    void beforeEach() {

        OpenCV.loadShared();
    }

    @Test
    void realOpenCVTest() {

//        OpenCV.loadShared();
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat loadedImage = loadImage("src/test/resources/images/face.jpg");

    }

    @Test
    void compareImagesTest() {

        Mat loadedImage1 = loadImage("src/test/resources/images/image.jpg");
        Mat loadedImage2 = loadImage("src/test/resources/images/image1.jpg");
        Mat loadedImageDiff = new Mat();
        Core.subtract(loadedImage1, loadedImage2, loadedImageDiff);
        saveImage(loadedImageDiff, "src/test/resources/images/imageDiff.jpg");
//        loadedImageDiff.convertTo(loadedImageDiff, CvType.CV_16S)
    }

    @Test
    void compareImagesTest2() {

        Mat loadedImage1 = loadImage("src/test/resources/images/image.jpg");
        Mat loadedImage2 = loadImage("src/test/resources/images/image1.jpg");
        Mat loadedImageDiff = new Mat();
        Mat loadedImageDiffColored = new Mat();
        Core.bitwise_or(loadedImage1, loadedImage2, loadedImageDiff);
//        loadedImageDiff
        saveImage(loadedImageDiff, "src/test/resources/images/imageDiff.jpg");


}

    @Test
    void detectFaceTest() {
        OpenCV.loadShared();
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        detectFace("src/test/resources/images/face.jpg", "src/test/resources/images/face2.jpg");
    }

    void detectFace(String sourceImagePath, String targetImagePath) {

        Mat loadedImage = loadImage(sourceImagePath);
        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
        cascadeClassifier.load("src/test/resources/pretreined/haarcascade_frontalface_alt.xml");
        cascadeClassifier.detectMultiScale(loadedImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );
        Rect[] facesArray = facesDetected.toArray();
        for (Rect face : facesArray) {
            Imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
        }
        saveImage(loadedImage, targetImagePath);
    }

    public Image mat2Img(Mat mat) {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode("img", mat, bytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toArray());
        Image img = new Image(inputStream);
        return img;
    }

    public Mat loadImage(String imagePath) {
        Imgcodecs imageCodecs = new Imgcodecs();
        return imageCodecs.imread(imagePath);
    }

    public void saveImage(Mat imageMatrix, String targetPath) {
        Imgcodecs imgcodecs = new Imgcodecs();
        imgcodecs.imwrite(targetPath, imageMatrix);
    }
}
