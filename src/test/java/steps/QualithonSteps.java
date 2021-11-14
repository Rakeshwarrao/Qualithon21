package steps;

import application.QualithonApp;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import utilities.ReportGeneration;

public class QualithonSteps {
	
	QualithonApp app = new QualithonApp();
	ReportGeneration report = new ReportGeneration();
	
	@Before
    public void beforeHook(Scenario scenario) {
		report.updateScenarioDescription(scenario.getName());
    }
	
	@Given("I navigate to Qualithon home page")
    public void iNavigateToQualithonHomePage() {
        app.launchApplication();
    }
	
	@When("I enter the Qualithon 2021 Competition")
    public void iEnterTheQualithon2021Competition() {
		app.navigateToPuzzle();
    }
	
	@And("I solve the puzzle 1 about random access")
	public void iSolveThePuzzle1AboutRandomAccess() {
		app.solvePuzzle1();
	}
	
	@And("I solve the puzzle 2 about video player")
	public void iSolveThePuzzle2AboutVideoPlayer() {
		app.solvePuzzle2();
	}
	
	@And("I solve the puzzle 3 about crystal maze")
	public void iSolveThePuzzle3AboutCrystalMaze() {
		app.solvePuzzle3();
	}
	
	@And("I solve the puzzle 4 about map position")
	public void iSolveThePuzzle4AboutMapPosition() {
		app.solvePuzzle4();
	}
	
	@And("I solve the puzzle 5 about capta entry")
	public void iSolveThePuzzle5AboutCaptaEntry() {
		app.solvePuzzle5();
	}
	
	@And("I solve the puzzle 6 about socket gate")
	public void iSolveThePuzzle6AboutSocketGate() {
		app.solvePuzzle6();
	}
	
	@And("I finally got my treasure")
	public void iFinallyGotMyTreasure() {
		app.finishQualithon();
	}

}
