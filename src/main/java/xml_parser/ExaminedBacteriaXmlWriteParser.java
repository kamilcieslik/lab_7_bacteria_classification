package xml_parser;

import database.entity.Examined;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ExaminedBacteriaXmlWriteParser {

    public ExaminedBacteriaXmlWriteParser() {
    }

    public void writeTestTemplate(List<Examined> examinedBactriaList, String directoryPath) throws JAXBException {
        ExaminedBacteriaContainer examinedBacteriaContainer = new ExaminedBacteriaContainer(examinedBactriaList);
        JAXBContext jaxbContext = JAXBContext.newInstance(ExaminedBacteriaContainer.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File testTemplateFile = new File(directoryPath, generateFilename());
        jaxbMarshaller.marshal(examinedBacteriaContainer, testTemplateFile);
    }

    private String generateFilename() {
        return "examined_bacteria_list" + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date()) + ".xml";
    }
}
