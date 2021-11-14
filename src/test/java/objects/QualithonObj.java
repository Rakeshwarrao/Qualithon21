package objects;

import org.openqa.selenium.By;

public class QualithonObj {
	
	public By introLink(){
        return By.xpath("//a[@href='/intro']");
    }
	
	public By buttonStart() {
		return By.xpath("//button[@id='start']");
	}
	
	public By randomAccessProceedButton(int buttonID) {
		return By.id("c1submitbutton" + buttonID);
	}
	
	public By randomAccessProceedButton() {
		return By.xpath("//button[contains(@id,'c1submitbutton')]");
	}
	
	public By pagePuzzle2() {
		return By.xpath("//span/h2[.='A Video Player']");
	}
	
	public By pagePuzzle3() {
		return By.xpath("//span/h2[.='Crystal Maze']");
	}
	
	public By pagePuzzle5() {
		return By.xpath("//span/h2[.='Not a Bot!']");
	}
	
	public By videoFrame() {
		return By.xpath("//div[@id='player']");
	}
	
	public By playButtonOnVideo() {
		return By.xpath("//button[@class='ytp-large-play-button ytp-button']");
	}
	
	public By muteButtonOnVideo() {
		return By.xpath("//button[@class='ytp-mute-button ytp-button']");
	}
	
	public By proceedButtonOnVideo() {
		return By.xpath("//button[.='Proceed']");
	}
	
	public By mazeTableRow() {
		return By.xpath("//table[@id='maze']/tr");
	}
	
	public By mazeTableColumn() {
		return By.xpath("//table[@id='maze']/tr[1]/td");
	}
	
	public By purpleObj() {
		return By.xpath("//td[contains(@class,'deep-purple')]");
	}
	
	public By greenObj() {
		return By.xpath("//td[contains(@class,'green')]");
	}
	
	public By purpleSquare(int xPos, int yPos) {
		return By.xpath("//td[@class='x"+xPos+" y"+yPos+" deep-purple']");
	}
	
	public By greySquare(int xPos, int yPos) {
		return By.xpath("//td[@class='x"+xPos+" y"+yPos+" blue-grey']");
	}
	
	public By blackSquare(int xPos, int yPos) {
		return By.xpath("//td[@class='x"+xPos+" y"+yPos+" black']");
	}
	
	public By greenSquare(int xPos, int yPos) {
		return By.xpath("//td[@class='x"+xPos+" y"+yPos+" green']");
	}
	
	public By upArrowCrystalMaze() {
		return By.xpath("//i[.='arrow_upward']");
	}
	
	public By downArrowCrystalMaze() {
		return By.xpath("//i[.='arrow_downward']");
	}
	
	public By forwardArrowCrystalMaze() {
		return By.xpath("//i[.='arrow_forward']");
	}
	
	public By backwardArrowCrystalMaze() {
		return By.xpath("//i[.='arrow_back']");
	}
	
	public By submitButtonCrystalMaze() {
		return By.xpath("//button[.='Submit']");
	}
	
	public By selectMap() {
		return By.xpath("//div[@id='map']");
	}
	
	public By mapCoordinates() {
		return By.xpath("//*[name()='svg']//*[name()='circle']");
	}
	
	public By proceedButtonOnMap() {
		return By.xpath("//button[.='Proceed']");
	}
	
	public By captchaInputTextBox() {
		return By.xpath("//input[@id='notABotCaptchaResponse']");
	}
	
	public By captchaButtonSubmit() {
		return By.xpath("//button[@id='notABotCaptchaSubmit']");
	}
	
	public By resumeFromCheckpoint() {
		return By.xpath("//button[.='Resume from checkpoint']");
	}
	
	public By socketGate() {
		return By.xpath("//span/h2[.='Socket Gate']");
	}
	
	public By getWSUrl() {
		return By.xpath("//span[@id='wsurl']");
	}
	
	public By getAccessTokenMessage() {
		return By.xpath("//li[contains(.,'Generate access token by sending message')]//following-sibling::div");
	}
	
	public By accessTokenTextBox() {
		return By.xpath("//input[@placeholder='Access Token']");
	}
	
	public By submitButtonAccessGate() {
		return By.xpath("//button[.='Submit']");
	}
	
	public By treasureFoundPage() {
		return By.xpath("//span/h3[.='Congratulations!! You Found the Treasure']");
	}


}
