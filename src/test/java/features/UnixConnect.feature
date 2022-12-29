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
Feature: Fix message test automation
  I want to use this template for my feature file

  @serverconnection
 Scenario: Connect to unix server using JSCH
    Given unix terminal connection using putty
    
  @extractlogs
  Scenario Outline: Extract the Fixed Income logs from from murexxx server
   Given based on given Compliance ID "<compliance ID>" extract the FI logs
    Examples: 
      |compliance ID|
      |VIPIN|
      #|ABCD2|
    
    @compliancelogs
    Scenario: using the RegEx and Pattern extract Only Fix message
    Given extract Fix logs only
        
  @fixtoJSON
  Scenario: convert the fix message into JSON
    Given convert the fix message into JSON and parse it for validation
    #And Validate Expected values vs Actual Values
    #When I complete action
    #And some other action
    #And yet another action
    #Then Extract the fix log from log file based on "<compliance ID>"
    #And check more outcomes
  #@fixtoJSON
  #
  #@Validation
  #Scenario: Fixed Income - US client US market - Verify the Order Capacity and Trading Capacity
    #Given based on given Compliance ID "<compliance ID>" extract the FI logs
    #Examples: 
      #|compliance ID|20029|Client|Broker
      #|ABCD1|R|DEAL|DEAL
      #|ABCD2|
#
  #@GivenFixMessages
 # Scenario Outline: US client trading in US - Expected fix messages as test data
  #  Given Fix values "<t8>" "<t9>" "<t10>" "<t11>" "<t12>" "<t13>" "<t14>" "<t15>" "<t16>" "<t17>" "<t18>" "<Fix_tag18>"
    
   
   # Examples:
    #|t8|t9|t10|t11|t12|t13|t14|t15|t16|t17|t18|Fix_tag18|
    #|1 |2 |3  |4  |5  |6  |7  |8  |GBP  |10 |MULEY|1883|
    
    @Validation
    Scenario: Fix message valition for US client trading in US
    And Tag "t18" should have value as "VPM"
    #And Tag "t16" should have value as "USD"
    
@tag18
  Scenario Outline: Title of your scenario outline
    Given tag value  "<f8>" "<f11>" "<f18>"  
    ##When I check for the <value> in step
    #Then I verify the <status> in step
    #And some other precondition

    Examples: 
      |f8|f11|f18|
      |FIX4.4|VIP|1990|
      
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    