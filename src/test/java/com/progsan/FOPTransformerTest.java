package com.progsan;

import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FOPTransformerTest {

    @Test
    public void testTransform() throws Exception {

        String inputFileName = "/CV.Erdal.Payat.DEU.fo";
        String outputFileName = "CV.Erdal.Payat.DEU.pdf";
        String configFileName = "/conf/fop.xconf";

        String workDir = System.getProperty("user.dir");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));


        Path configFilePath = getFullPathName(configFileName);
        Path inputFilePath = getFullPathName(inputFileName);
        //String inputFilePath = Paths.get(new URL(workDir + "/target" + inputFileName));
        String outputFile = workDir + "/target/" + outputFileName;
        URI baseURL = new URI("file:///" + inputFilePath.getParent().toString().replaceAll("\\\\", "/").replaceAll(" ", "%20")+"/");
        System.out.println("basePath: " + baseURL);
        FOPTransformer.transform(configFilePath.toString().replaceAll(" ", "%20"), inputFilePath.toString(), outputFile, baseURL);
    }

    public static Path getFullPathName(String relFileName) throws URISyntaxException {
        URL resourceUrl = FOPTransformer.class.getResource(relFileName);
        Path resourcePath = Paths.get(resourceUrl.toURI());
        System.out.println("fullPath: " + resourcePath);
        return resourcePath;
    }
}