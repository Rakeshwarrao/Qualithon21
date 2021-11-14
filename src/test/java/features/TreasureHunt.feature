#Author: Rakeshwar Rao
#Feature: Treasure Hunt Scenario

@Hunt
Feature: Treausre Hunt Feature

  @TREASURE
  Scenario: End to end treasure hunt scenario
    Given I navigate to Qualithon home page
    When I enter the Qualithon 2021 Competition
    And I solve the puzzle 1 about random access
    And I solve the puzzle 2 about video player
    And I solve the puzzle 3 about crystal maze
    And I solve the puzzle 4 about map position
    And I solve the puzzle 5 about capta entry
    And I solve the puzzle 6 about socket gate
    Then I finally got my treasure
