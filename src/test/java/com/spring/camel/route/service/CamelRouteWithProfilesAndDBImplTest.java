package com.spring.camel.route.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "dev")
@RunWith(CamelSpringBootRunner.class) //that is which class this Test to run with.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // what this annotation does is, note
// this is a springtest annotation and what it does is clear out the cache application-context and re-load it after
// each test run. Basically if you have multiple test-cases meaning multiple methods in this test-case, everytime
// you run a test-case basically clear-out whatever cache or context that gets cached by the previous tests.
// That is the definition for this one.
// Here 'classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD', means that after each test method run,
//  clear out the context.
@SpringBootTest //since this is a SpringBoot application, hence added the annotation.
public class CamelRouteWithProfilesAndDBImplTest {

    @Autowired
    ProducerTemplate producerTemplate;
    //is used to produce some files and we are using this to the file into the input directory.

    @Autowired
    Environment environment;

    @BeforeClass
    public static void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("data/inputUsingProfilesAndDBImpl"));
        //FileUtils.deleteDirectory(new File("data/output")); //commented as for now we are not going to delete this output directory.
    }

    @Test
    public void testMoveFile() throws InterruptedException {

        String datetime = LocalDateTime.now().toString();
        //first creating content for the file
        String fileContent = "type,sku#,itemdescription,price,date-of-purchase\n"+
                "ADD,100,LG TV,500,"+datetime+"\n"+
                "ADD,101,AO SMITH,100,"+datetime;

        //
        String fileName = "file"+datetime.replaceAll(":", "_")+".txt";

        //in below method what heppens is that the content is placed into the file and then file will be placed into the input-directory as per ths use-case.
        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRouteForDBImpl"), fileContent, Exchange.FILE_NAME, fileName);

        //
        Thread.sleep(3000);

        // the next step is to check that file is actually moved to the output directory or not.
        File outFile = new File("data/output/" + fileName);
        assertTrue(outFile.exists()); //asserting to check that file actually moved or not to the output directory 'data/output'.
    }

  @Test
  public void testMoveFile_ADD_TO_DATABASE() throws InterruptedException, IOException {

        String datetime = LocalDateTime.now().toString();
        //first creating content for the file
        String fileContent = "type,sku#,itemdescription,price,date-of-purchase\n"+
                "ADD,100,LG TV,500,"+datetime+"\n"+
                "ADD,101,AO SMITH,100,"+datetime;

        //
        String fileName = "file"+datetime.replaceAll(":", "_")+".txt";

        //in below method what heppens is that the content is placed into the file and then file will be placed into the input-directory as per ths use-case.
        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRouteForDBImpl"), fileContent, Exchange.FILE_NAME, fileName);

        //
        Thread.sleep(3000);

        // the next step is to check that file is actually moved to the output directory or not.
        File outFile = new File("data/output/" + fileName);
        assertTrue(outFile.exists()); //asserting to check that file actually moved or not to the output directory 'data/output'.

        final String outputMessage = "Data Updated Successfully";

        // the next is to read the value from toRoute3 Success.txt file and to assert on it.
        String output = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        //NOTE: string output will holds the actual message that is logged into Success.txt iff there is no error.

        //
        assertEquals(outputMessage, output);

    }

  @Test
  public void testMoveFile_UPDATE_TO_DATABASE() throws InterruptedException, IOException {

        String datetime = LocalDateTime.now().toString();
        //first creating content for the file
      String fileContent = "type,sku#,itemdescription,price,date-of-purchase\n"+
              "UPDATE,100,LG TV,5000,"+datetime+"\n"+
              "UPDATE,101,AO SMITH,5100,"+datetime;

        //
        String fileName = "fileUpdate-"+datetime.replaceAll(":", "_")+".txt";

        //in below method what happens is that the content is placed into the file and then file will be placed into the input-directory as per ths use-case.
        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRouteForDBImpl"), fileContent, Exchange.FILE_NAME, fileName);

        //
        Thread.sleep(3000);

        // the next step is to check that file is actually moved to the output directory or not.
        File outFile = new File("data/output/" + fileName);
        assertTrue(outFile.exists()); //asserting to check that file actually moved or not to the output directory 'data/output'.

        final String outputMessage = "Data Updated Successfully";

        // the next is to read the value from toRoute3 Success.txt file and to assert on it.
        String output = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        //NOTE: string output will holds the actual message that is logged into Success.txt iff there is no error.

        //
        assertEquals(outputMessage, output);

    }

  @Test
  public void testMoveFile_DELETE_TO_DATABASE() throws InterruptedException, IOException {

        String datetime = LocalDateTime.now().toString();
        //first creating content for the file
        String fileContent = "type,sku#,itemdescription,price,date-of-purchase\n"+
                "DELETE,100,LG TV,5000,"+datetime+"\n"+
                "DELETE,101,AO SMITH,5100,"+datetime;

        //
        String fileName = "fileUpdate-"+datetime.replaceAll(":", "_")+".txt";

        //in below method what happens is that the content is placed into the file and then file will be placed into the input-directory as per ths use-case.
        producerTemplate.sendBodyAndHeader(environment.getProperty("fromRouteForDBImpl"), fileContent, Exchange.FILE_NAME, fileName);

        //
        Thread.sleep(3000);

        // the next step is to check that file is actually moved to the output directory or not.
        File outFile = new File("data/output/" + fileName);
        assertTrue(outFile.exists()); //asserting to check that file actually moved or not to the output directory 'data/output'.

        final String outputMessage = "Data Updated Successfully";

        // the next is to read the value from toRoute3 Success.txt file and to assert on it.
        String output = new String(Files.readAllBytes(Paths.get("data/output/Success.txt")));
        //NOTE: string output will holds the actual message that is logged into Success.txt iff there is no error.

        //
        assertEquals(outputMessage, output);

    }
}
