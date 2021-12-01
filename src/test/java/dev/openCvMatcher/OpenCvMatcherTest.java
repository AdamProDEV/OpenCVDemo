package dev.openCvMatcher;

import nu.pattern.OpenCV;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;

public class OpenCvMatcherTest {

    @Test
    void MatcherTest(){

        // load the native OpenCV library
        OpenCV.loadShared();
        // run code
        new OpenCvMatcher().run(new String[]{"src/test/resources/images/hp.png", "src/test/resources/images/alvaaSmall.png"});
    }

}
