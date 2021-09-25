# R18_G2_ASM1
<b>ASSIGNMENT 1</b>

# ABOUT

For our first assignment, our task was to work collaboratively as a team to design and build an ATM application. We accessed tools such as Slack, Jenkins, Miro and google docs to demonstrate our participation and work in progress. These can be found in the links attached below!

The following information contains tasks for our group members to work on, git commands, how to work with gradle, testing and extra helpful links.

# Working in progress

-> make sure indenting is 2 spaces, use CamelCase for variable naming!
-> add in Javadoc comments + test cases
-> ensure we all have the same format for writing test cases/files
-> Starting working on report + allocate sections to write about
-> keep code consistent + nicely styled + minimise redundancy

# IMPORTANT!! Working with Jenkins 

Ensure that [gradle clean build test] works properly locally before you push any changes to the master branch as Jenkins will detect this and 
cause a build failure. Each time a build fails, we should aim to resolve the problem as soon as possible!!

# 1. FOLLOW THIS TO GRAB NEWEST CHANGES FROM GITHUB, merging with your local branch files!!!!
git checkout master --> git pull --> git checkout your_branch_name --> git merge master
 
# 2. FOLLOW THIS TO SUBMIT ONLY YOUR BRANCH/PART ONTO GITHUB!! 
Git add . (adds all your changes) OR git add (file) --> git commit -m “message” → git push -u origin branch_name

# 3. FOLLOW THIS TO MERGE UR CHANGES TO MASTER!! [ALWAYS pull before pushing!]
-> git pull (this prevents merge conflicts) -> git checkout master --> git merge branch_name --> git push

# 4. WHAT IF I ACCIDENTALLY ADDED FILES BUT ADDED TO THE WRONG PLACE?
  E.g. You accidentally added to master but wanted to add to your branch (haven't committed yet) --> UNDO action by using: reset <file>

# Acess to google docs for git notes + PLAN FOR REPORT

https://docs.google.com/document/d/1yB_5BBh-Ltyny6yReWHthtCTx16b87SztbjzvzdXsEM/edit


# Access to MIRO board for viewing/editing

Check this out: https://miro.com/app/board/o9J_ly_Mywo=/


# How to use JUNIT and examples of testing

Check this out: https://junit.org/junit5/docs/current/user-guide/


# GUI Design

Website for our design inspiration [for future development]: https://www.commbank.com.au/content/dam/commbank/personal/apply-online/download-printed-forms/atm-guide-for-users-with-low-vision-accessible.pdf


# Working with Gradle

Check the build.gradle file for more details on plugins, dependencies used and test.

<u>gradle run</u>--> builds + compiles all your files
<u>gradle clean build</u> --> initialises directory for storing reports
<u>gradle clean </u>--> cleans up any leftovers from previous builds + prevents getting side effects like causing build to fail
<u>gradle test</u> --> compiles and runs your test files
<u>gradle test JacocoTestReport</u> --> generates a report where you can see how much coverage you've done
<u>gradle javadoc</u> --> generates a set of HTML pages of API documentation

# Testing Standard IN/OUT in Java

Check these websites out!

https://www.danvega.dev/blog/2020/12/16/testing-standard-in-out-java/ 

http://one-line-it.blogspot.com/2013/05/java-testing-with-stdin-and-stdout.html

Super helpful website!!! :) --> https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
