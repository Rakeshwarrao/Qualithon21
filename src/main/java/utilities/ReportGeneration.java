package utilities;

import com.relevantcodes.extentreports.LogStatus;

import runner.RunQualithonTest;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportGeneration {

    RunQualithonTest run = new RunQualithonTest();

    public void logReport(LogStatus logStatus, String logDescription){
        run.getScenario().log(logStatus, logDescription);
    }

    public void updateScenarioDescription(String scenarioName){
        run.getScenario().getTest().setName(scenarioName);
        run.getScenario().setDescription(scenarioName);
    }

    public void logReportWithAssersion(String expectedString, String actualString, String logDescription, String attachFilePath){
        if(expectedString.equals(actualString)){
            run.getScenario().log(LogStatus.PASS, "<a href='file:/"+ attachFilePath +"'>Step Passed : " + logDescription +"</a>");
        }else{
            run.getScenario().log(LogStatus.FAIL, "Step Failed : " + logDescription);
        }
    }

    public void logReportWithScreenshot(String attachFilePath){
        File f1 = new File(attachFilePath);
        File f2 = new File(f1.getParent());
        run.getScenario().log(LogStatus.PASS,run.getScenario().addScreenCapture(".\\" + f2.getName() + "\\" + f1.getName()));
        // Working Step run.getScenario().log(LogStatus.PASS,run.getScenario().addScreenCapture(attachFilePath));
        //run.getScenario().addScreencast(attachFilePath);
        //run.getScenario().addBase64ScreenShot(attachFilePath);
        //run.getScenario().log(LogStatus.INFO, "<a href='file:/"+ attachFilePath +"'>Screenshot Attached</a>");
    }

    public void logReportWithAssersionScreenshot(String expectedString, String actualString, String logDescription, String attachFilePath){
        if(expectedString.equals(actualString)){
            run.getScenario().log(LogStatus.PASS, "<a href='file:/"+ attachFilePath +"'>Step Passed : " + logDescription +"</a>");
        }else{
            run.getScenario().log(LogStatus.FAIL, "Step Failed : " + logDescription);
        }
    }

    public void logTestExtentReportWithAttachment(String logDescription, String attachFilePath){
        run.getScenario().log(LogStatus.INFO, logDescription + " : " + run.getScenario().addScreenCapture(attachFilePath));
    }

    public void logTestExtentReportWithHyperlink(LogStatus logStatus, String logDescription, String attachFilePath){
        run.getScenario().log(logStatus, "<a href='file:/"+ attachFilePath +"'>" + logDescription +"</a>");
    }

    public void logInfo(String logDescription){
        run.getScenario().log(LogStatus.INFO, logDescription);
    }

    public void logError(String errorDescription){
        run.getScenario().log(LogStatus.ERROR, errorDescription);
    }

}
