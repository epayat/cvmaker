package com.progsan;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.*;
import org.xml.sax.SAXException;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;

/**
 * Created by XI313546 on 24.11.15.
 */
public class FOPTransformer {
    public static void transform(String configFileName, String inputFileName, String outputFileName, URI baseURL)
            throws IOException, SAXException, TransformerException, ConfigurationException {
        // Step 1: Construct a FopFactory
        // (reuse if you plan to render multiple documents!)
        //"C:/Temp/fop.xconf"
        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
        Configuration cfg = cfgBuilder.buildFromFile(new File(configFileName));
        FopFactoryBuilder builder = new FopFactoryBuilder(baseURL).setConfiguration(cfg);
        FopFactory fopFactory = builder.build();
        //FopFactory fopFactory = FopFactory.newInstance(new File(configFileName));

        //FopFactory fopFactory = FopFactory.newInstance();
        //fopFactory.setUserConfig(new File(configFileName));
        //fopFactory.getFontManager().setFontBaseURL(fontBaseURL);
        // do the following for each new rendering run
        FOUserAgent userAgent = fopFactory.newFOUserAgent();
        //userAgent.setBaseURL(baseURL);

        // Step 2: Set up output stream.
        // Note: Using BufferedOutputStream for performance reasons (helpful with FileOutputStreams).
        OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outputFileName)));

        try {
            // Step 3: Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, out);
            //Fop fop = fopFactory.newFop(MimeConstants.MIME_RTF, userAgent, out);

            // Step 4: Setup JAXP using identity transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // identity transformer

            // Step 5: Setup input and output for XSLT transformation
            // Setup input stream
            Source src = new StreamSource(new File(inputFileName));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Step 6: Start XSLT transformation and FOP processing
            transformer.transform(src, res);

        } finally {
            //Clean-up
            out.close();
        }
    }
}
