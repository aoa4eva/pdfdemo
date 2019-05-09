package me.aoa4eva.pdfdemo

import groovy.util.logging.Slf4j
import net.sourceforge.tess4j.ITesseract
import net.sourceforge.tess4j.Tesseract
import net.sourceforge.tess4j.TesseractException
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class MainController {

    @GetMapping("/")
    def run()
    {
        System.out.println("opening connection")
        URL url = new URL("")

        InputStream input = url.openStream()
        FileOutputStream fos = new FileOutputStream(new File("thefile.pdf"))


        log.info("reading from resource and writing to file...")
        int length = -1
        byte[] buffer = new byte[1024]// buffer for portion of data from connection
        while ((length = input.read(buffer)) > -1) {
            fos.write(buffer, 0, length)
        }
        fos.close()

        input.close()
        log.info "File has been saved"


        File pdfFile = new File("thefile.pdf");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping
        instance.setDatapath("tessdata"); // path to tessdata directory

        StringBuffer theResult = new StringBuffer();
        try {
            String result = instance.doOCR(pdfFile);
            theResult.append(result.replaceAll("e \"","* \""))
            System.out.println(result)
        } catch (TesseractException e) {
            log.error(e.getMessage());
        }
        theResult.toString()

    }
}
