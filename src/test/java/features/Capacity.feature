#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Test tag 18 feature
  I want to use this template for my feature file

  

  @tag2
  Scenario Outline: Title of your scenario outline
    Given tag value  "<Vipin_Fix_tag11>" "<Vipin_Fix_tag18>" "<Vipin_Fix_tag8>" 
    ##When I check for the <value> in step
    #Then I verify the <status> in step
    #And some other precondition

    Examples: 
      | Vipin_Fix_tag18 | Vipin_Fix_tag18 | Vipin_Fix_tag18 |
      |VIP|VPMM|FIX|
     