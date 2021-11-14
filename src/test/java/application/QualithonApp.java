package application;

import java.util.Random;

import org.openqa.selenium.Keys;

import com.relevantcodes.extentreports.LogStatus;

import api.WSClient;
import automation.SeleniumOperations;
import objects.QualithonObj;
import runner.RunQualithonTest;
import utilities.ConfigFileReader;
import utilities.ReportGeneration;

public class QualithonApp {
	
	SeleniumOperations selOps = new SeleniumOperations();
	WSClient wsClient = new WSClient();
	QualithonObj qaObj = new QualithonObj();
	ConfigFileReader config = new ConfigFileReader();
    ReportGeneration report = new ReportGeneration();
    RunQualithonTest run = new RunQualithonTest();

	
	public void launchApplication() {
		selOps.launchBrowser(config.getConfig("appUrl"));
		//selOps.getConsoleLog();
	}
	
	public void navigateToPuzzle() {
		selOps.click(qaObj.introLink(), "Enter Qualithon 2021 Competition");
		selOps.click(qaObj.buttonStart(), "Start Puzzle");
	}
	
	public void solvePuzzle1() {
		int objCount = selOps.getObjectCounts(qaObj.randomAccessProceedButton());
		for(int i=1;i<=objCount;i++) {
			selOps.clickNoLog(qaObj.randomAccessProceedButton(i));
			if(selOps.elementExists(qaObj.pagePuzzle2())){
				selOps.getScreenshot();
				report.logReport(LogStatus.PASS, "Puzzle 1 Completed");
				break;
			}
		}
	}
	
	public void solvePuzzle2() {
		selOps.getAndSwitchFrame(qaObj.playButtonOnVideo(),"iframe");
		selOps.scrollToElement(qaObj.videoFrame());
		selOps.click(qaObj.playButtonOnVideo(), "Play Video");
		//selOps.click(qaObj.muteButtonOnVideo(), "Unmute Video");
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		selOps.mouseHover(qaObj.muteButtonOnVideo());
		//selOps.sendKeys(qaObj.muteButtonOnVideo(), Keys.TAB);
		selOps.click(qaObj.muteButtonOnVideo(), "Unmute Video");
		selOps.getAndSwitchFrame(qaObj.proceedButtonOnVideo(),"iframe");
		selOps.click(qaObj.proceedButtonOnVideo(), "Proceed Button on Play Video Screen");
		if(selOps.elementExists(qaObj.pagePuzzle3())){
			selOps.getScreenshot();
			report.logReport(LogStatus.PASS, "Puzzle 2 Completed");
		}
	}
	
	public void solvePuzzle3() {	
		int mazeRow = selOps.getObjectCounts(qaObj.mazeTableRow());
		int mazeColumn = selOps.getObjectCounts(qaObj.mazeTableColumn());
		
		String purpleObj = selOps.getAttribute(qaObj.purpleObj(),"class");
		int purpleObjxPos = Integer.parseInt(purpleObj.split(" ")[0].replace("x", ""));
		int purpleObjyPos = Integer.parseInt(purpleObj.split(" ")[1].replace("y", ""));
		
		String greenObj = selOps.getAttribute(qaObj.greenObj(),"class");
		int greenObjxPos = Integer.parseInt(greenObj.split(" ")[0].replace("x", ""));
		int greenObjyPos = Integer.parseInt(greenObj.split(" ")[1].replace("y", ""));
		
		int random = 1;
		String mode="f2-d2-f2-u3-b2-u2-f1-d1-f2-u2-f1-d1-f1-d3-f2-d4-b1-d1-b3-d1-b1-d2-f6-u2-f1-d1-f1-u1-f2";
		int initCount = 2;
		selOps.scrollToElement(qaObj.purpleObj());
		while(!selOps.elementExists(qaObj.purpleSquare(greenObjxPos, greenObjyPos))) {
			String modes[] = mode.split("-");
			for(String modeItem : modes) {
				String direction = modeItem.substring(0,1);
				int counter = Integer.parseInt(modeItem.substring(1, 2));
				for(int i=1;i<=counter;i++) {
					if(direction.equalsIgnoreCase("f")){
						selOps.clickNoLog(qaObj.forwardArrowCrystalMaze());
					}else if(direction.equalsIgnoreCase("d")) {
						selOps.clickNoLog(qaObj.downArrowCrystalMaze());
					}else if(direction.equalsIgnoreCase("u")) {
						selOps.clickNoLog(qaObj.upArrowCrystalMaze());
					}else if(direction.equalsIgnoreCase("b")) {
						selOps.clickNoLog(qaObj.backwardArrowCrystalMaze());
					}
				}
			}
			/*while(selOps.elementExists(qaObj.greySquare(purpleObjxPos+1,purpleObjyPos))){
				selOps.clickNoLog(qaObj.forwardArrowCrystalMaze());
				System.out.println("Forward : " + purpleObjxPos + "-" + purpleObjyPos);
				purpleObjxPos = purpleObjxPos+1;
				purpleObjyPos = purpleObjyPos;				
			}
			while(selOps.elementExists(qaObj.greySquare(purpleObjxPos, purpleObjyPos+1))) {
				selOps.clickNoLog(qaObj.downArrowCrystalMaze());
				System.out.println("Down : " + purpleObjxPos + "-" + purpleObjyPos);
				purpleObjxPos = purpleObjxPos;
				purpleObjyPos = purpleObjyPos+1;				
			}
			while(selOps.elementExists(qaObj.greySquare(purpleObjxPos-1,purpleObjyPos))){
				selOps.clickNoLog(qaObj.backwardArrowCrystalMaze());
				System.out.println("Backward : " + purpleObjxPos + "-" + purpleObjyPos);
				purpleObjxPos = purpleObjxPos-1;
				purpleObjyPos = purpleObjyPos;				
			}
			while(selOps.elementExists(qaObj.greySquare(purpleObjxPos, purpleObjyPos-1))) {
				selOps.clickNoLog(qaObj.upArrowCrystalMaze());
				System.out.println("Upward : " + purpleObjxPos + "-" + purpleObjyPos);
				purpleObjxPos = purpleObjxPos;
				purpleObjyPos = purpleObjyPos-1;				
			}*/
		}
		//selOps.getConsoleLog();
		selOps.click(qaObj.submitButtonCrystalMaze(), "Submit Crystal");
		if(selOps.elementExists(qaObj.selectMap())){
			selOps.getScreenshot();
			report.logReport(LogStatus.PASS, "Puzzle 3 Completed");
		}
	}
	
	public void solvePuzzle4() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selOps.click(qaObj.selectMap(), "Select Map");
		selOps.sendKeys(qaObj.selectMap(), Keys.TAB);
		selOps.sendKeys(qaObj.selectMap(), "i");
		
		//System.out.println(selOps.getAttribute(qaObj.mapCoordinates(), "cx"));
		//System.out.println(selOps.getAttribute(qaObj.mapCoordinates(), "cy"));
		
		double mapXCoordinates = Double.valueOf(selOps.getAttribute(qaObj.mapCoordinates(), "cx"));
		double mapYCoordinates = Double.valueOf(selOps.getAttribute(qaObj.mapCoordinates(), "cy"));
		//System.out.println(mapXCoordinates + "-" + mapYCoordinates);
		
		
		while(!(mapXCoordinates < 120)) {
			selOps.sendKeys(qaObj.selectMap(), Keys.LEFT);
			mapXCoordinates = Double.valueOf(selOps.getAttribute(qaObj.mapCoordinates(), "cx"));
		}
		
		while(!(mapYCoordinates < 75)) {
			selOps.sendKeys(qaObj.selectMap(), Keys.UP);
			mapYCoordinates = Double.valueOf(selOps.getAttribute(qaObj.mapCoordinates(), "cy"));
		}
		
		selOps.click(qaObj.proceedButtonOnMap(), "Proceed Map");
		if(selOps.elementExists(qaObj.pagePuzzle5())){
			selOps.getScreenshot();
			report.logReport(LogStatus.PASS, "Puzzle 4 Completed");
		}
	}
	
	public void solvePuzzle5() {
		//System.out.println("Captcha: " + selOps.getConsoleLog());
		//selOps.launchBrowser("http://54.80.137.197:5000/c/not_a_bot");
		String possCaptas[] = config.getConfig("captcha").split("-");
		
		for(String possCapta:possCaptas) {
			Boolean exitFlag = false;
			selOps.setText(qaObj.captchaInputTextBox(), possCapta);
			selOps.clickNoLog(qaObj.captchaButtonSubmit());
			while(selOps.elementExists(qaObj.socketGate())) {
				exitFlag = true;
				break;
			}
			if(exitFlag) {
				break;
			}
			selOps.clickNoLog(qaObj.resumeFromCheckpoint());
		}		
		if(selOps.elementExists(qaObj.socketGate())){
			selOps.getScreenshot();
			report.logReport(LogStatus.PASS, "Puzzle 5 Completed");
		}
	}
	
	public void solvePuzzle6() {
		String uri = selOps.getAttribute(qaObj.getWSUrl(), "innerText");
		String message = selOps.getAttribute(qaObj.getAccessTokenMessage(), "innerText");
		//String uri = "ws://54.80.137.197:5001";
		//String message = "6c9ce4acb831a0f4b3c4e4a57975f4535771d0af24b545ef1d351b20bb0729fd.1636897344.socket_gate.e6155ac6-81b6-43f6-a79c-176125dbd854";
		String token = wsClient.connectWSClient(uri, message);
		selOps.setText(qaObj.accessTokenTextBox(), token);
		selOps.click(qaObj.submitButtonAccessGate(), "Submit Access Gate");
		if(selOps.elementExists(qaObj.treasureFoundPage())){
			report.logReport(LogStatus.PASS, "Puzzle 6 Completed");
		}
	}
	
	public void finishQualithon() {
		if(selOps.elementExists(qaObj.treasureFoundPage())){
			selOps.getScreenshot();
			report.logReport(LogStatus.PASS, "Treasure Found");
		}else {
			selOps.getScreenshot();
			report.logReport(LogStatus.FAIL, "Treasure Not Found");
		}
	}

}
